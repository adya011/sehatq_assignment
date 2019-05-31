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

public class SearchPresenter implements SearchPresenterInterface {
    SearchViewInterface searchView;
    NetworkInterface netInterface;
    Call<FeedResponse> call;

    public SearchPresenter(SearchViewInterface searchView) {
        this.searchView = searchView;
    }

    @Override
    public void getData(String searchWords) {
        netInterface = RestProvider.getRetroFeed().create(NetworkInterface.class);
        call = netInterface.searchFeed(Constants.API_KEY, searchWords);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                try {
                    Log.d(DashboardFragment.TAG, "== Get Search Data ==");
                    Log.d(DashboardFragment.TAG, "URL: " + response.raw().request().url());
                    Log.d(DashboardFragment.TAG, "code: " + response.code());

                    if (response.code() == 200 && response.isSuccessful()) {
                        searchView.onSuccess(response.body());

                    }else if(response.code() == 429){
                        call.clone().enqueue(this);
                    } else {
                        searchView.onError();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                try {
                    if ((t instanceof SocketTimeoutException || t instanceof IOException || t instanceof UnknownHostException)) {
                        searchView.onNetworkProblem();

                    } else {
                        searchView.onError();
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public void dropData() {

    }
}
