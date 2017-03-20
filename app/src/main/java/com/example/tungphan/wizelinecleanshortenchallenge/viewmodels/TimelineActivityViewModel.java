package com.example.tungphan.wizelinecleanshortenchallenge.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.tungphan.wizelinecleanshortenchallenge.BR;
import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.adapters.TweetsListRecyclerAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.contants.ActivityRequestCode;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.TimelineActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.User;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.ITimelineActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.models.TimelineActivityModel;
import com.example.tungphan.wizelinecleanshortenchallenge.network.NetworkError;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;

import java.util.List;


/**
 * Created by tungphan on 3/17/17.
 */

public class TimelineActivityViewModel extends BaseObservable implements ITimelineActivityListener {

    private final Service service;
    private Context context;
    private TimelineActivityBinding timelineActivityBinding;
    private final TimelineActivityModel timelineActivityModel = new TimelineActivityModel();

    public TimelineActivityViewModel(Context context, TimelineActivityBinding timelineActivityBinding, Service service) {
        this.context = context;
        this.timelineActivityBinding = timelineActivityBinding;
        this.service = service;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        timelineActivityBinding.tweetsRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadTimeline() {
        service.getUserTimelineFromService(new Service.GetTimelineCallback() {
            @Override
            public void onSuccess(List<Tweet> tweetList) {
                finishLoadingTimeline(tweetList);
            }

            @Override
            public void onError(NetworkError networkError) {
                errorLoadingTimeline();
            }
        });
    }

    public ITimelineActivityListener getITimelineActivityListener() {
        return this;
    }

    @Override
    public void onCreate() {
        loadTimeline();
        timelineActivityBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTimeline();
            }
        });
    }

    private void finishLoadingTimeline(List<Tweet> tweetList) {
        TweetsListRecyclerAdapter adapter = new TweetsListRecyclerAdapter(context, tweetList);
        timelineActivityBinding.tweetsRecyclerView.setAdapter(adapter);
    }

    private void errorLoadingTimeline() {
        Snackbar.make(timelineActivityBinding.parentView, R.string.error_data_in_response
                , Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityRequestCode.START_NEW_TWEET_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, R.string.tweet_successfully, Toast.LENGTH_SHORT).show();
                loadTimeline();
            } else {
                Toast.makeText(context, R.string.tweet_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
