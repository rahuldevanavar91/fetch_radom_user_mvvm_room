package com.android.application.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.android.application.adapter.UserListAdapter;
import com.android.application.database.AppDatabase;
import com.android.application.model.Result;
import com.android.application.model.ResultItem;
import com.android.application.network.ApiEndPoint;
import com.android.application.network.Resource;
import com.android.application.network.RetrofitService;
import com.android.application.util.ResponseListener;
import com.android.application.util.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserResultRepository {
    private static final String RESULT_PER_PAGE = "10";
    private Context mContext;
    private ApiEndPoint mApiEndPoint;
    private List<ResultItem> mResultList;
    private MutableLiveData<Resource<List<ResultItem>>> mResponseLiveData;
    private int mPageNumber;

    public UserResultRepository(Context context) {
        mContext = context;
        mApiEndPoint = RetrofitService.getRetrofitInstance().create(ApiEndPoint.class);
        mResponseLiveData = new MutableLiveData<>();
        mPageNumber = 1;
    }

    public MutableLiveData<Resource<List<ResultItem>>> getResult() {
        mResponseLiveData.setValue(Resource.<List<ResultItem>>loading());

        new GetResponseTask(mContext, new ResponseListener<List<ResultItem>>() {
            @Override
            public void onResponseReceived(List<ResultItem> data) {
                if (data != null) {
                    mResultList = data;
                    mResponseLiveData.setValue(Resource.success(mResultList));
                }
            }
        }).execute();
        makeNetworkRequest(mPageNumber);
        return mResponseLiveData;
    }

    public void loadMoreResult() {
        makeNetworkRequest(mPageNumber++);
    }

    private void makeNetworkRequest(int pageNumber) {
        if (Util.isNetworkConnected(mContext)) {
            mApiEndPoint.getUsers(RESULT_PER_PAGE, String.valueOf(pageNumber))
                    .enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            addResult(response);
                            mResponseLiveData.setValue(Resource.success(mResultList));
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            mResponseLiveData.setValue(Resource.<List<ResultItem>>networkError());
                        }
                    });
        } else {
            mResponseLiveData.setValue(Resource.<List<ResultItem>>networkError());
        }
    }

    private void addResult(Response<Result> response) {
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }
        if (!mResultList.isEmpty() &&
                mResultList.get(mResultList.size() - 1).getViewType() == UserListAdapter.VIEW_LOAD_MORE) {
            mResultList.remove(mResultList.size() - 1);
        }
        mResultList.addAll(response.body().getResults());
        ResultItem viewMore = new ResultItem();
        viewMore.setViewType(UserListAdapter.VIEW_LOAD_MORE);
        mResultList.add(viewMore);
        new InsertTask(mContext).execute(response.body());
    }


    public void updateLikeDisLike(int position, int status) {
        mResultList.get(position).setMemberRequestStatus(status);
        new UpdateTask(mContext)
                .execute(mResultList.get(position));
    }

    private static class InsertTask extends AsyncTask<Result, Void, Void> {
        private Context mContext;

        public InsertTask(Context context) {
            mContext = context;
        }


        @Override
        protected Void doInBackground(Result... results) {
            if (AppDatabase.getInstance(mContext).responseDao().getRowCount() > 50) {
                AppDatabase.getInstance(mContext)
                        .responseDao().deleteOldData();
            }
            AppDatabase.getInstance(mContext).responseDao().insertResponse(results[0].getResults());

            return null;
        }
    }

    private static class GetResponseTask extends AsyncTask<Void, Void, List<ResultItem>> {
        private Context mContext;
        private ResponseListener<List<ResultItem>> mListener;

        public GetResponseTask(Context context, ResponseListener<List<ResultItem>> listener) {
            mContext = context;
            mListener = listener;
        }


        @Override
        protected List<ResultItem> doInBackground(Void... voids) {
            return AppDatabase.getInstance(mContext)
                    .responseDao().getAllResponse();
        }

        @Override
        protected void onPostExecute(List<ResultItem> result) {
            mListener.onResponseReceived(result);
            super.onPostExecute(result);
        }
    }

    private static class UpdateTask extends AsyncTask<ResultItem, Void, Void> {
        private Context mContext;

        public UpdateTask(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(ResultItem... resultItems) {
            AppDatabase.getInstance(mContext).responseDao().updateLikeDislike(resultItems[0].getKey(), resultItems[0].getMemberRequestStatus());
            return null;
        }
    }

}
