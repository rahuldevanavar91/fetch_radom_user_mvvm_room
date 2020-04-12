package com.android.application.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;
import com.android.application.adapter.UserListAdapter;
import com.android.application.model.ResultItem;
import com.android.application.network.Resource;
import com.android.application.util.ItemClickListener;
import com.android.application.viewModel.ResultViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer<Resource<List<ResultItem>>>, ItemClickListener {

    private RecyclerView mRecyclerView;

    private ResultViewModel mResultViewModel;

    private ProgressBar mProgressBar;

    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        initViews();
        mResultViewModel.getResult().observe(this,this);
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler);
    }

    @Override
    public void onChanged(Resource<List<ResultItem>> listResource) {
        switch (listResource.status) {
            case LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                mProgressBar.setVisibility(View.GONE);
                if (mAdapter == null) {
                    mAdapter = new UserListAdapter(listResource.data, this, this);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.update(listResource.data);
                }
                break;
            case NETWORK_ERROR:
                Toast.makeText(getApplicationContext(), listResource.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        mResultViewModel.updateLikeDislike(position, (int) data);
        mAdapter.notifyItemChanged(position);
    }

    public void loadMore() {
        mResultViewModel.loadMore();
    }
}
