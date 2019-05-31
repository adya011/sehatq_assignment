package com.example.sehatqassignment.network;

import retrofit2.Response;

public interface MyResponseListener<Response> {
    void onResponseSuccess(Response response); //200

    void onError(Response response); //202
}
