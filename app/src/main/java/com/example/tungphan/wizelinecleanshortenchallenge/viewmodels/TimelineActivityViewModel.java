package com.example.tungphan.wizelinecleanshortenchallenge.viewmodels;

import android.content.Context;
import android.net.Network;

import com.example.tungphan.wizelinecleanshortenchallenge.app.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.TimelineActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.User;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.ITimelineActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.network.NetworkError;
import com.example.tungphan.wizelinecleanshortenchallenge.network.NetworkService;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tungphan on 3/17/17.
 */

public class TimelineActivityViewModel implements ITimelineActivityListener{
    @Inject
    private Service service;

    public TimelineActivityViewModel(Context context, TimelineActivityBinding timelineActivityBinding){
        loadTimeline();
        initInjector(WizelineApp.getInstance().getAppComponent());
    }

    private void initInjector(AppComponent appComponent){
        appComponent.inject(this);
    }

    private void loadTimeline(){
        service.getUserTimelineFromService(new Service.GetTimelineCallback() {
            @Override
            public void onSuccess(List<Tweet> tweetList) {

            }

            @Override
            public void onError(NetworkError networkError) {

            }
        });
    }

    public ITimelineActivityListener getITimelineActivityListener(){
        return this;
    }

    @Override
    public void onCreate() {
    }
}
