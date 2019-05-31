package com.example.sehatqassignment.network;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCallback<T> implements Callback<T> {
    private MyResponseListener listener;
    String TAG = "mydbg_callback";

    public MyCallback(MyResponseListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.d(TAG, "Response callback. URL: " + response.raw().request().url());
        Log.d(TAG, "Response callback. Code: " + response.code());

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}
