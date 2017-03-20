package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstants;
import com.example.tungphan.wizelinecleanshortenchallenge.di.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SingleTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ISingleTweetActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.SingleTweetActivityViewModel;


/**
 * Created by Tung Phan on 2/15/2017.
 */

public class SingleTweetActivity extends BaseActivity {
    private static final String TAG = SingleTweetActivity.class.getSimpleName();
    private SingleTweetActivityViewModel singleTweetActivityViewModel;
    private SingleTweetActivityBinding singleTweetActivityBinding;
    private ISingleTweetActivityListener iSingleTweetActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableDrawerState();
        singleTweetActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.single_tweet_activity, baseActivityBinding.appBarBase.contentLayout, true);
        singleTweetActivityViewModel = new SingleTweetActivityViewModel(this, singleTweetActivityBinding);
        iSingleTweetActivityListener = singleTweetActivityViewModel.getISingleTweetViewModel();
        singleTweetActivityBinding.setViewModel(singleTweetActivityViewModel);
        iSingleTweetActivityListener.retreivedIntentFromOtherActivity(getIntent());
        setBackgroundForToggleMenuButton();
    }

    protected void injectDagger(AppComponent appComponent){
        appComponent.inject(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        baseActivityBinding.appBarBase.searchButton.setVisibility(View.GONE);
        baseActivityBinding.appBarBase.closeButton.setVisibility(View.VISIBLE);
        baseActivityBinding.appBarBase.fab.setVisibility(View.GONE);
        return super.onPrepareOptionsMenu(menu);
    }
}