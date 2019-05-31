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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.model.FeedResponse;
import com.example.sehatqassignment.model.Hits;
import com.example.sehatqassignment.presenterview.SearchPresenter;
import com.example.sehatqassignment.presenterview.SearchViewInterface;
import com.example.sehatqassignment.utility.MyUtils;
import com.example.sehatqassignment.view.activity.ItemDetailActivity;
import com.example.sehatqassignment.view.adapter.FeedItemListener;
import com.example.sehatqassignment.view.adapter.FeedsAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements SearchViewInterface {
    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;

    @BindView(R.id.rv_feed)
    RecyclerView recyclerView;

    @BindView(R.id.lay_view)
    RelativeLayout layView;

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindView(R.id.btn_back)
    ImageView btnBack;

    private SearchPresenter presenter;
    private ArrayList<Hits> feeds;
    private FeedsAdapter adapter;

    public static final String TAG = "mydbg_search_frag";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SearchPresenter(this);
        feeds = new ArrayList<>();
        adapter = new FeedsAdapter(feeds, getContext());
        init();
    }

    private void init() {
        feeds.clear();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (etSearch.getText() != null) {
                        String searchWord = etSearch.getText().toString();
                        feeds.clear();
                        loadingBar.setVisibility(View.VISIBLE);
                        presenter.getData(searchWord);
                        MyUtils.hideKeyboard(getActivity());
                    }
                }
                return false;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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

    }

    @Override
    public void onNetworkProblem() {

    }

    private void getFeedList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return true;
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
