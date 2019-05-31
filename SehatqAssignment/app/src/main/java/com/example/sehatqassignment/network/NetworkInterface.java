package com.example.sehatqassignment.network;

import com.example.sehatqassignment.model.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {
    @GET(".")
    Call<FeedResponse> getFeeds(@Query("key") String key);

    @GET(".")
    Call<FeedResponse> getFeedDetail(@Query("key") String key,
                                     @Query("id") int id);

    @GET(".")
    Call<FeedResponse> searchFeed(@Query("key") String key,
                                  @Query("q") String q);
}
