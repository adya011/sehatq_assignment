package com.example.sehatqassignment.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.model.FeedResponse;
import com.example.sehatqassignment.model.Hits;
import com.example.sehatqassignment.presenterview.NewsPresenter;
import com.example.sehatqassignment.presenterview.NewsViewInterface;
import com.example.sehatqassignment.utility.MyUtils;
import com.example.sehatqassignment.view.activity.ItemDetailActivity;
import com.example.sehatqassignment.view.adapter.FeedItemListener;
import com.example.sehatqassignment.view.adapter.FeedsAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment implements NewsViewInterface {
    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;

    @BindView(R.id.lay_retry)
    RelativeLayout layRetry;

    @BindView(R.id.lay_view)
    RelativeLayout layView;

    @BindView(R.id.btn_retry)
    Button btnRetry;

    @BindView(R.id.rv_feed)
    RecyclerView recyclerView;

    private NewsPresenter presenter;
    private ArrayList<Hits> feeds;
    private FeedsAdapter adapter;

    public static final String TAG = "mydbg_news_frag";

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new NewsPresenter(this);
        feeds = new ArrayList<>();
        adapter = new FeedsAdapter(feeds, getContext());

        init();
    }

    private void init() {
        loadingBar.setVisibility(View.VISIBLE);
        feeds.clear();
        presenter.getData();

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData();
                isNetworkProb(false);
            }
        });
    }

    @Override
    public void onSuccess(FeedResponse response) {
        loadingBar.setVisibility(View.GONE);

        feeds.addAll(response.getHits());
        getFeedList();
    }

    @Override
    public void onError() {
        loadingBar.setVisibility(View.GONE);
        MyUtils.alertDialogOK(getContext(), getResources().getString(R.string.seomthing_wrong));
    }

    @Override
    public void onNetworkProblem() {
        loadingBar.setVisibility(View.GONE);
        isNetworkProb(true);
    }

    private void isNetworkProb(boolean bool) {
        if (bool) {
            layRetry.setVisibility(View.VISIBLE);
            layView.setVisibility(View.GONE);

        } else {
            layRetry.setVisibility(View.GONE);
            layView.setVisibility(View.VISIBLE);
        }
    }

    private void getFeedList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new FeedItemListener(getContext(), recyclerView,
                new FeedItemListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Hits feed = feeds.get(position);
                        int feedId = feed.getId();

                        Log.d(TAG, "choose id: " + feedId);

                        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                        intent.putExtra("feed_id", feedId);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadingBar.setVisibility(View.GONE);
        presenter.dropData();
    }
}
