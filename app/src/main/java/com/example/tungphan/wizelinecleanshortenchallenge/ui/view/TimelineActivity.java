package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.di.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NavHeaderBaseBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.TimelineActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IMyNavViewHeaderListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ITimelineActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.MyNavViewHeaderViewModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.TimelineActivityViewModel;

import javax.inject.Inject;

/**
 * Created by tungphan on 3/17/17.
 */

public class TimelineActivity extends BaseActivity {
    private TimelineActivityBinding timelineActivityBinding;
    private TimelineActivityViewModel timelineActivityViewModel;
    private ITimelineActivityListener iTimelineActivityListener;
    private MyNavViewHeaderViewModel myNavViewHeaderViewModel;
    private NavHeaderBaseBinding navHeaderBaseBinding;
    private IMyNavViewHeaderListener iMyNavViewHeaderListener;
    @Inject
    Service service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableShowNavDrawer();
        disableShowHomAsUp();
        timelineActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.timeline_activity, baseActivityBinding.appBarBase.contentLayout, true);
        navHeaderBaseBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_base
                , baseActivityBinding.navView, true);
        timelineActivityViewModel = new TimelineActivityViewModel(this, timelineActivityBinding, service);
        myNavViewHeaderViewModel = new MyNavViewHeaderViewModel(this, navHeaderBaseBinding, service);
        timelineActivityBinding.setViewModel(timelineActivityViewModel);
        navHeaderBaseBinding.setViewModel(myNavViewHeaderViewModel);
        iTimelineActivityListener = timelineActivityViewModel.getITimelineActivityListener();
        iMyNavViewHeaderListener = myNavViewHeaderViewModel.getIMyNavViewHeaderListener();
        iTimelineActivityListener.onCreate();
        iMyNavViewHeaderListener.onCreate();
    }

    protected void injectDagger(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iTimelineActivityListener.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        baseActivityBinding.appBarBase.closeButton.setVisibility(View.GONE);
        baseActivityBinding.appBarBase.searchButton.setVisibility(View.VISIBLE);
        baseActivityBinding.appBarBase.addButton.setVisibility(View.GONE);
        baseActivityBinding.appBarBase.fab.setVisibility(View.VISIBLE);
        baseActivityBinding.appBarBase.searchEdittext.setVisibility(View.GONE);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseActivityBinding.appBarBase.fab.setVisibility(View.VISIBLE);
    }
}
