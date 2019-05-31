package com.example.sehatqassignment.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.sehatqassignment.presenterview.DashboardPresenter;
import com.example.sehatqassignment.presenterview.DashboardViewInterface;
import com.example.sehatqassignment.utility.MyUtils;
import com.example.sehatqassignment.view.activity.ItemDetailActivity;
import com.example.sehatqassignment.view.adapter.FeedItemListener;
import com.example.sehatqassignment.view.adapter.FeedsAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends BaseFragment implements DashboardViewInterface {
    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;

    @BindView(R.id.rv_feed)
    RecyclerView recyclerView;

    @BindView(R.id.lay_retry)
    RelativeLayout layRetry;

    @BindView(R.id.lay_view)
    RelativeLayout layView;

    @BindView(R.id.btn_retry)
    Button btnRetry;

    private DashboardPresenter presenter;
    private ArrayList<Hits> feeds;
    private FeedsAdapter adapter;

    public static final String TAG = "mydbg_dash_frag";

    public static DashboardFragment newInstance() {
        DashboardFragment frag = new DashboardFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new DashboardPresenter(this);
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

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadingBar.setVisibility(View.GONE);
        presenter.dropData();
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
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
}
