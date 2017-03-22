package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.tungphan.wizelinecleanshortenchallenge.BR;
import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters.TweetsListRecyclerAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityRequestCode;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.TimelineActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ITimelineActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.model.TimelineActivityModel;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;


/**
 * Created by tungphan on 3/17/17.
 */

public class TimelineActivityViewModel extends BaseObservable implements ITimelineActivityListener {

    private final Service service;
    private Context context;
    private TimelineActivityBinding timelineActivityBinding;
    private final TimelineActivityModel timelineActivityModel = new TimelineActivityModel();
    private TweetsListRecyclerAdapter tweetsListAdapter;
    private Subscription subscription;

    public TimelineActivityViewModel(Context context, TimelineActivityBinding timelineActivityBinding, Service service) {
        this.context = context;
        this.timelineActivityBinding = timelineActivityBinding;
        this.service = service;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        timelineActivityBinding.tweetsRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadTimeline() {
        visibleLoading();
        service.getUserTimelineFromService()
                .subscribe(new Subscriber<List<Tweet>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        timelineActivityBinding.swipeRefreshLayout.setRefreshing(false);
                        errorLoadingTimeline();
                    }

                    @Override
                    public void onNext(List<Tweet> tweetList) {
                        timelineActivityBinding.swipeRefreshLayout.setRefreshing(false);
                        setVisibleEmptyBackground(false);
                        finishLoadingTimeline(tweetList);
                    }
                });
    }

    private void visibleLoading(){
        if (tweetsListAdapter == null) {
            setVisibleEmptyBackground(true);
        } else if (tweetsListAdapter.getItemCount() == 0
                && !timelineActivityBinding.swipeRefreshLayout.isRefreshing()) {
            setVisibleProgressBar(true);
        }
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
        if (tweetsListAdapter == null) {//init tweet recycler view adapter for the first time
            setVisibleEmptyBackground(false);
            tweetsListAdapter = new TweetsListRecyclerAdapter(context, tweetList);
            timelineActivityBinding.tweetsRecyclerView.setAdapter(tweetsListAdapter);
        } else {
            setVisibleProgressBar(false);
            tweetsListAdapter.setTimeline(tweetList);
            tweetsListAdapter.notifyDataSetChanged();
        }
    }

    private void errorLoadingTimeline() {
        Snackbar.make(timelineActivityBinding.parentView, R.string.error_loading_user_timeline
                , Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityRequestCode.START_NEW_TWEET_ACTIVITY_REQUEST_CODE) {
            if (resultCode == ActivityResult.OK) {
                Toast.makeText(context, R.string.tweet_successfully, Toast.LENGTH_SHORT).show();
                loadTimeline();
            } else if (resultCode == ActivityResult.FALSE) {
                Toast.makeText(context, R.string.tweet_fail, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.tweet_cancel, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == ActivityRequestCode.START_SHARE_TWEET_ACTIVITY_REQUEST_CODE) {
            if (resultCode == ActivityResult.OK) {
                Toast.makeText(context, R.string.text_share_tweet_successful, Toast.LENGTH_SHORT).show();
                loadTimeline();
            } else if (resultCode == ActivityResult.FALSE) {
                Toast.makeText(context, R.string.text_share_tweet_fail, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.share_tweet_cancel, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }

    @Bindable
    public boolean isVisibleEmptyBackground() {
        return timelineActivityModel.isVisibleEmptyBackground();
    }

    public void setVisibleEmptyBackground(boolean visible) {
        timelineActivityModel.setVisibleEmptyBackground(visible);
        notifyPropertyChanged(BR.visibleEmptyBackground);
    }

    public void setVisibleProgressBar(boolean visibleProgressBar) {
        timelineActivityModel.setVisibleProgressBar(visibleProgressBar);
        notifyPropertyChanged(BR.visibleProgressBar);
    }

    @Bindable
    public boolean isVisibleProgressBar() {
        return timelineActivityModel.isVisibleProgressBar();
    }

}
