package com.example.tungphan.wizelinecleanshortenchallenge.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.app.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.TimelineActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.ITimelineActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.viewmodels.TimelineActivityViewModel;

import javax.inject.Inject;

/**
 * Created by tungphan on 3/17/17.
 */

public class TimelineActivity extends BaseActivity {
    private TimelineActivityBinding timelineActivityBinding;
    private TimelineActivityViewModel timelineActivityViewModel;
    private ITimelineActivityListener iTimelineActivityListener;
    @Inject public Service service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timelineActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.timeline_activity, baseActivityBinding.appBarBase.contentLayout, true);
        timelineActivityViewModel = new TimelineActivityViewModel(this, timelineActivityBinding, service);
        timelineActivityBinding.setViewModel(timelineActivityViewModel);
        iTimelineActivityListener = timelineActivityViewModel.getITimelineActivityListener();
        iTimelineActivityListener.onCreate();
    }

    protected void injectDagger(AppComponent appComponent){
        appComponent.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iTimelineActivityListener.onActivityResult(requestCode, resultCode, data);
    }
}
