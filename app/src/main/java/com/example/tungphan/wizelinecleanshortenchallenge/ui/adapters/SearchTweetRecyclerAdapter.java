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
import com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstants;
import com.example.tungphan.wizelinecleanshortenchallenge.model.SearchTweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Status;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.SingleTweetActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tungphan on 3/20/17.
 */

public class SearchTweetRecyclerAdapter extends RecyclerView.Adapter<TweetsListRecyclerAdapter.ViewHolder> {

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
    public TweetsListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.timline_recycler_view_item, parent, false);
        TweetsListRecyclerAdapter.ViewHolder viewHolder = new TweetsListRecyclerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetsListRecyclerAdapter.ViewHolder viewHolder, int position) {
        final Status status = statuses.get(position);
        TextView nameTextView = viewHolder.mNameTview;
        TextView tweetTextView = viewHolder.mTweetTView;
        ImageView profilePicImView = viewHolder.mProfilePicImView;
        LinearLayout itemWrapLayout = viewHolder.mItemWrapLayout;
        nameTextView.setText(status.getUser().getName());
        tweetTextView.setText(status.getText());
        Picasso.with(context)
                .load(status.getUser().getProfileImageUrl())
                .placeholder(R.drawable.face)
                .resize((int) profileImageSize, (int) profileImageSize)
                .onlyScaleDown()
                .into(profilePicImView);
        final Context context = viewHolder.mContext;
        itemWrapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleTweetActivity.class);
                intent.putExtra(IntentConstants.OWNER_NAME, status.getUser().getName());
                intent.putExtra(IntentConstants.ONER_DESCRIPTION, status.getUser().getDescription());
                intent.putExtra(IntentConstants.TWEET_CONTENT, status.getText());
                intent.putExtra(IntentConstants.OWNER_PROFILE_IMAGE_URL, status.getUser().getProfileImageUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameTview;
        public TextView mTweetTView;
        public ImageView mProfilePicImView;
        public LinearLayout mItemWrapLayout;
        public Context mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mNameTview = (TextView) itemView.findViewById(R.id.user_name_view);
            mTweetTView = (TextView) itemView.findViewById(R.id.tweet_view);
            mProfilePicImView = (ImageView) itemView.findViewById(R.id.profile_image_view);
            mItemWrapLayout = (LinearLayout) itemView.findViewById(R.id.item_wrap_layout);
        }
    }
}

