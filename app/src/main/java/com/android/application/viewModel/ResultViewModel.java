package com.android.application.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.application.model.ResultItem;
import com.android.application.network.Resource;
import com.android.application.repository.UserResultRepository;

import java.util.List;

public class ResultViewModel extends AndroidViewModel {

    private UserResultRepository mRepository;

    public ResultViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserResultRepository(application.getApplicationContext());
    }


    public MutableLiveData<Resource<List<ResultItem>>> getResult() {
        return mRepository.getResult();
    }

    public void updateLikeDislike(int position, int msg) {
        mRepository.updateLikeDisLike(position, msg);
    }

    public void loadMore() {
        mRepository.loadMoreResult();
    }
}
