package com.android.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;
import com.android.application.model.ResultItem;
import com.android.application.util.ItemClickListener;
import com.android.application.view.MainActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    public static final int VIEW_LOAD_MORE = 1;

    private List<ResultItem> mUserList;
    private Context mContext;
    private ItemClickListener mClickListener;
    private int mLastLoadPosition;

    public UserListAdapter(List<ResultItem> list, Context context, ItemClickListener clickListener) {
        mUserList = list;
        mContext = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_LOAD_MORE) {
            return new ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.load_more_layout, parent, false), viewType);
        } else {
            return new ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.user_list_item, parent, false), viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultItem item = mUserList.get(holder.getAdapterPosition());
        if (item.getViewType() != VIEW_LOAD_MORE) {
            updateUi(holder, item);
        } else if (mLastLoadPosition != holder.getAdapterPosition()) {
            mLastLoadPosition = holder.getAdapterPosition();
            ((MainActivity) mContext).loadMore();
        }
    }

    private void updateUi(ViewHolder holder, ResultItem item) {
        holder.name.setText(item.getName().getFirst() +
                " " + item.getName().getLast());
        holder.age.setText(item.getDob().getAge() +
                "," + item.getLocation().getCity() + ","
                + item.getLocation().getState());
        Glide.with(mContext)
                .load(item.getPicture().getLarge())
                .placeholder(R.color.silver)
                .into(holder.imageView);
        holder.rejectButton.setTag(holder.getAdapterPosition());
        holder.acceptButton.setTag(holder.getAdapterPosition());
        if (item.getMemberRequestStatus() != 0) {
            holder.acceptButton.setVisibility(View.GONE);
            holder.rejectButton.setVisibility(View.GONE);
            holder.message.setText(
                    item.getMemberRequestStatus() == 1 ? mContext.getString(R.string.accept_msg) :
                            mContext.getString(R.string.reject)
            );
            holder.message.setVisibility(View.VISIBLE);
        } else {
            holder.acceptButton.setVisibility(View.VISIBLE);
            holder.rejectButton.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mUserList.get(position).getViewType();
    }

    public void update(List<ResultItem> data) {
        mUserList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView name;
        private Button acceptButton;
        private Button rejectButton;
        private TextView message;
        private TextView age;

        private ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType != VIEW_LOAD_MORE) {
                imageView = itemView.findViewById(R.id.image);
                name = itemView.findViewById(R.id.name);
                acceptButton = itemView.findViewById(R.id.accept);
                rejectButton = itemView.findViewById(R.id.reject);
                message = itemView.findViewById(R.id.accept_reject_status_msg);
                age = itemView.findViewById(R.id.age);
                acceptButton.setOnClickListener(this);
                rejectButton.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.accept:
                    mClickListener.onItemClick(v, (int) v.getTag(), 1);
                    break;
                case R.id.reject:
                    mClickListener.onItemClick(v, (int) v.getTag(), 2);
                    break;
            }
        }
    }
}
