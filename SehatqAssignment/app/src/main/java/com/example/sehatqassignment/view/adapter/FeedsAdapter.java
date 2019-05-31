package com.example.sehatqassignment.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sehatqassignment.R;
import com.example.sehatqassignment.model.Hits;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {
    private ArrayList<Hits> hits;
    private Context context;

    public FeedsAdapter(ArrayList<Hits> hits, Context context) {
        this.hits = hits;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_feeds, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Hits feedDetail = hits.get(i);
        viewHolder.tvFeed.setText(feedDetail.getPageURL());
        Picasso.with(context)
                .load(feedDetail.getLargeImageURL())
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgFeed);
    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFeed;
        public ImageView imgFeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFeed = itemView.findViewById(R.id.img_feed);
            tvFeed = itemView.findViewById(R.id.tv_feed);
        }
    }
}
