package com.example.sehatqassignment.presenterview;

import com.example.sehatqassignment.model.FeedResponse;

public interface NewsViewInterface {
    void onSuccess(FeedResponse response);

    void onError();

    void onNetworkProblem();
}
