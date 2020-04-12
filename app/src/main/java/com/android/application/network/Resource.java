package com.android.application.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.android.application.network.Status.ERROR;
import static com.android.application.network.Status.LOADING;
import static com.android.application.network.Status.NETWORK_ERROR;
import static com.android.application.network.Status.SUCCESS;


public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public T data;

    @Nullable
    private final String message;

    private int statusCode;


    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    private Resource(@NonNull Status status, @Nullable String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }



    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null);
    }

    public static <T> Resource<T> networkError() {
        return new Resource<>(NETWORK_ERROR, null, "Please check network");
    }

    @Nullable
    public String getMessage() {
        return message;
    }

}