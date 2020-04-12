package com.android.application.util;

public interface ResponseListener<T> {
    void onResponseReceived(T data);
}
