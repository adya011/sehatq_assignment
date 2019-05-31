package com.example.sehatqassignment.presenterview;

import android.util.Log;

import com.example.sehatqassignment.model.FeedResponse;
import com.example.sehatqassignment.network.NetworkInterface;
import com.example.sehatqassignment.network.RestProvider;
import com.example.sehatqassignment.utility.Constants;
import com.example.sehatqassignment.view.fragment.DashboardFragment;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenter implements NewsPresenterInterface {
    NewsViewInterface newsView;
    NetworkInterface netInterface;
    Call<FeedResponse> call;

    public NewsPresenter(NewsViewInterface newsView){
        this.newsView = newsView;
    }

    @Override
    public void getData() {
        netInterface = RestProvider.getRetroFeed().create(NetworkInterface.class);
        call = netInterface.getFeeds(Constants.API_KEY);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                try {
                    Log.d(DashboardFragment.TAG, "== Get Data News ==");
                    Log.d(DashboardFragment.TAG, "URL: " + response.raw().request().url());
                    Log.d(DashboardFragment.TAG, "code: " + response.code());

                    if (response.code() == 200 && response.isSuccessful()) {
                        newsView.onSuccess(response.body());

                    }else if(response.code() == 429){
                        call.clone().enqueue(this);
                    } else {
                        newsView.onError();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                try {
                    if ((t instanceof SocketTimeoutException || t instanceof IOException || t instanceof UnknownHostException)) {
                        newsView.onNetworkProblem();

                    } else {
                        newsView.onError();
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public void dropData() {
        if (call != null) {
            call.cancel();
        }
    }
}
