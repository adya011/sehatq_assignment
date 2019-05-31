package com.example.sehatqassignment.network;

import com.example.sehatqassignment.utility.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestProvider {
    public static OkHttpClient client;
    private static Retrofit retroFeed;
    private static final int TIMEOUT = 30;

    public static OkHttpClient okHttpClient(){
        client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        return client;
    }

    public static Retrofit getRetroFeed() {
        OkHttpClient client = okHttpClient();

        if (retroFeed == null) {
            retroFeed = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retroFeed;
    }

}
