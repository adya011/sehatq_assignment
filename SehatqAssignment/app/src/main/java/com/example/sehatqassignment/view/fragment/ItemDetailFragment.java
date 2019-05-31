package com.example.sehatqassignment.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.model.FeedResponse;
import com.example.sehatqassignment.presenterview.DashboardPresenter;
import com.example.sehatqassignment.presenterview.ItemDetailPresenter;
import com.example.sehatqassignment.presenterview.ItemDetailViewInterface;
import com.example.sehatqassignment.view.activity.ItemDetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends BaseFragment implements ItemDetailViewInterface {
    @BindView(R.id.loading_bar)
    ProgressBar loadingBar;

    @BindView(R.id.img_feed)
    ImageView imgFeed;

    @BindView(R.id.tv_feed_desc)
    TextView tvFeedDesc;

    final static String TAG = "mydbg_itemdet_frag";
    private ItemDetailPresenter presenter;
    int id;

    public static ItemDetailFragment newInstance(int id) {
        ItemDetailFragment frag = new ItemDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("feed_id", id);
        frag.setArguments(bundle);

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();

        id = getArguments().getInt("feed_id");

        Log.d(TAG, "item detail id: " + id);

        presenter = new ItemDetailPresenter(this, id);
        init();
    }

    private void initToolbar() {
        ((ItemDetailActivity) getActivity()).getSupportActionBar().setTitle("Feed Detail");
    }

    private void init() {
        loadingBar.setVisibility(View.VISIBLE);
        presenter.getData();
    }

    @Override
    public void onSuccess(FeedResponse response) {
        loadingBar.setVisibility(View.GONE);
        String imgFeedUrl = response.getHits().get(0).getLargeImageURL();
        Picasso.with(getContext())
                .load(imgFeedUrl)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_error)
                .into(imgFeed);

        String feedDesc = response.getHits().get(0).getTags() + " "
                + response.getHits().get(0).getPageURL() + " "
                + response.getHits().get(0).getPreviewURL();
        tvFeedDesc.setText(feedDesc);
    }

    @Override
    public void onError(FeedResponse response) {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailed() {
        loadingBar.setVisibility(View.GONE);
    }
}
