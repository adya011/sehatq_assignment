package com.example.sehatqassignment.presenterview;

import com.example.sehatqassignment.model.FeedResponse;

public interface SearchViewInterface {
    void onSuccess(FeedResponse response);

    void onError();

    void onNetworkProblem();
}
