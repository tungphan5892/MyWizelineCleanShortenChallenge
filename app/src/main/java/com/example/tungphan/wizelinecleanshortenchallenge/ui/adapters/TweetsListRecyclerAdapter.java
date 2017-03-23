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
import com.example.tungphan.wizelinecleanshortenchallenge.model.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.SingleTweetActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Tung Phan on 2/15/2017.
 */

public class TweetsListRecyclerAdapter extends RecyclerView.Adapter<SearchTweetRecyclerAdapter.ViewHolder> {

    private List<Tweet> timeline;
    private Context context;
    private float profileImageSize;

    public TweetsListRecyclerAdapter(Context context, List<Tweet> timeline) {
        this.context = context;
        this.timeline = timeline;
        profileImageSize = context.getResources().getDimension(R.dimen.tweet_image_size);
    }

    public void setTimeline(List<Tweet> timeline) {
        this.timeline = timeline;
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
        final Tweet tweet = timeline.get(position);
        TextView nameTextView = viewHolder.nameTextView;
        TextView tweetTextView = viewHolder.tweetTextView;
        ImageView profilePicImView = viewHolder.profileImageView;
        LinearLayout itemWrapLayout = viewHolder.itemWrapLayout;
        nameTextView.setText(tweet.getUser().getName());
        tweetTextView.setText(tweet.getText());
        Picasso.with(context)
                .load(tweet.getUser().getProfileImageUrl())
                .placeholder(R.drawable.face)
                .resize((int) profileImageSize, (int) profileImageSize)
                .onlyScaleDown()
                .into(profilePicImView);
        final Context context = viewHolder.context;
        itemWrapLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleTweetActivity.class);
            intent.putExtra(IntentConstant.OWNER_NAME, tweet.getUser().getName());
            intent.putExtra(IntentConstant.ONER_DESCRIPTION, tweet.getUser().getDescription());
            intent.putExtra(IntentConstant.TWEET_CONTENT, tweet.getText());
            intent.putExtra(IntentConstant.OWNER_PROFILE_IMAGE_URL, tweet.getUser().getProfileImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return timeline.size();
    }
}
