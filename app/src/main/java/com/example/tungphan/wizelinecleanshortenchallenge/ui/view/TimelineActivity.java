package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NavHeaderBaseBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.TimelineActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityStartStopListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewModelListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ITimelineActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.MyNavViewHeaderViewModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.TimelineActivityViewModel;

/**
 * Created by tungphan on 3/17/17.
 */

public class TimelineActivity extends BaseActivity {

    private TimelineActivityBinding timelineActivityBinding;
    private TimelineActivityViewModel timelineActivityViewModel;
    private ITimelineActivityListener iTimelineActivityListener;
    private ITimelineActivityListener iNavViewHeaderListener;
    private IActivityStartStopListener iActivityStartStopListener;
    private MyNavViewHeaderViewModel myNavViewHeaderViewModel;
    private NavHeaderBaseBinding myNavHeaderBaseBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableShowNavDrawer();
        disableShowHomAsUp();
        initTimelineViews();
        initHeaderViews();
        iTimelineActivityListener.onCreate();
        iNavViewHeaderListener.onCreate();
    }

    private void initTimelineViews() {
        timelineActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.timeline_activity, baseActivityBinding.appBarBase.contentLayout, true);
        timelineActivityViewModel = new TimelineActivityViewModel(this, timelineActivityBinding);
        timelineActivityBinding.setViewModel(timelineActivityViewModel);
        iTimelineActivityListener = timelineActivityViewModel.getITimelineActivityListener();
        iActivityStartStopListener = timelineActivityViewModel.getIActivityStartStopListener();
    }

    private void initHeaderViews() {
        myNavHeaderBaseBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_base
                , baseActivityBinding.navView, true);
        myNavViewHeaderViewModel = new MyNavViewHeaderViewModel(this, myNavHeaderBaseBinding);
        myNavHeaderBaseBinding.setViewModel(myNavViewHeaderViewModel);
        iNavViewHeaderListener = myNavViewHeaderViewModel.getITimelineActivityListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iTimelineActivityListener.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        iActivityStartStopListener.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iActivityStartStopListener.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iTimelineActivityListener.onDestroy();
        iNavViewHeaderListener.onDestroy();
    }
}
