package com.android.application.network;


import com.android.application.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @GET("api")
    Call<Result> getUsers(@Query("results") String result, @Query("page") String pageNumber);
}
