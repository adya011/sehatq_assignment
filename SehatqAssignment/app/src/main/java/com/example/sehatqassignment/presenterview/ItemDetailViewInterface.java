package com.example.sehatqassignment.presenterview;

import com.example.sehatqassignment.model.FeedResponse;

public interface ItemDetailViewInterface {
    void onSuccess(FeedResponse response);

    void onError(FeedResponse response);

    void onFailed();
}
