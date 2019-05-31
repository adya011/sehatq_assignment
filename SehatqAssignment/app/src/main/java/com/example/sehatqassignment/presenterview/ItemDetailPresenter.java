package com.example.sehatqassignment.presenterview;

import android.util.Log;

import com.example.sehatqassignment.utility.Constants;
import com.example.sehatqassignment.model.FeedResponse;
import com.example.sehatqassignment.network.NetworkInterface;
import com.example.sehatqassignment.network.RestProvider;
import com.example.sehatqassignment.view.fragment.DashboardFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailPresenter implements ItemDetailPresenterInterface {
    ItemDetailViewInterface itemDetView;
    NetworkInterface netInterface;
    Call<FeedResponse> call;
    int id;

    public ItemDetailPresenter(ItemDetailViewInterface itemDetView, int id){
        this.itemDetView = itemDetView;
        this.id = id;
    }

    @Override
    public void getData() {
        netInterface = RestProvider.getRetroFeed().create(NetworkInterface.class);
        call = netInterface.getFeedDetail(Constants.API_KEY, id);
        call.enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                try {
                    Log.d(DashboardFragment.TAG, "URL: " + response.raw().request().url());
                    Log.d(DashboardFragment.TAG, "code: " + response.code());
                    if (response.code() == 200 && response.isSuccessful()) {
                        itemDetView.onSuccess(response.body());
                    } else if (response.code() == 202) {
                        itemDetView.onError(response.body());
                    } else {
                        itemDetView.onFailed();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void dropData() {

    }
}
