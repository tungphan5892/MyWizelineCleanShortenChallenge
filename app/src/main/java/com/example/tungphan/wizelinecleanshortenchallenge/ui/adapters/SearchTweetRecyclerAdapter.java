package com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstant;
import com.example.tungphan.wizelinecleanshortenchallenge.model.SearchTweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Status;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.SingleTweetActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tungphan on 3/20/17.
 */

public class SearchTweetRecyclerAdapter extends RecyclerView.Adapter<SearchTweetRecyclerAdapter.ViewHolder> {

    private List<Status> statuses;
    private Context context;
    private float profileImageSize;

    public SearchTweetRecyclerAdapter(Context context, SearchTweet searchTweet) {
        this.context = context;
        this.statuses = searchTweet.getStatuses();
        profileImageSize = context.getResources().getDimension(R.dimen.tweet_image_size);
    }

    public void setSearchTweet(SearchTweet searchTweet) {
        this.statuses = searchTweet.getStatuses();
    }

    @Override
    public SearchTweetRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.timline_recycler_view_item, parent, false);
        return new SearchTweetRecyclerAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(SearchTweetRecyclerAdapter.ViewHolder viewHolder, int position) {
        final Status status = statuses.get(position);
        TextView nameTextView = viewHolder.nameTextView;
        TextView tweetTextView = viewHolder.tweetTextView;
        ImageView profilePicImView = viewHolder.profileImageView;
        LinearLayout itemWrapLayout = viewHolder.itemWrapLayout;
        nameTextView.setText(status.getUser().getName());
        tweetTextView.setText(status.getText());
        Picasso.with(context)
                .load(status.getUser().getProfileImageUrl())
                .placeholder(R.drawable.face)
                .resize((int) profileImageSize, (int) profileImageSize)
                .onlyScaleDown()
                .into(profilePicImView);
        final Context context = viewHolder.context;
        itemWrapLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleTweetActivity.class);
            intent.putExtra(IntentConstant.OWNER_NAME, status.getUser().getName());
            intent.putExtra(IntentConstant.ONER_DESCRIPTION, status.getUser().getDescription());
            intent.putExtra(IntentConstant.TWEET_CONTENT, status.getText());
            intent.putExtra(IntentConstant.OWNER_PROFILE_IMAGE_URL, status.getUser().getProfileImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView tweetTextView;
        public ImageView profileImageView;
        public LinearLayout itemWrapLayout;
        public Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            this.nameTextView = (TextView) itemView.findViewById(R.id.user_name_view);
            this.tweetTextView = (TextView) itemView.findViewById(R.id.tweet_view);
            this.profileImageView= (ImageView) itemView.findViewById(R.id.profile_image_view);
            this.itemWrapLayout = (LinearLayout) itemView.findViewById(R.id.item_wrap_layout);
        }
    }
}

