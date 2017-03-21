package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.di.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NewTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.INewTweetActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.NewTweetActivityViewModel;

import javax.inject.Inject;


/**
 * Created by tungphan on 3/9/17.
 */

public class NewTweetActivity extends BaseActivity {
    private NewTweetActivityBinding newTweetActivityBinding;
    private NewTweetActivityViewModel newTweetViewModel;
    private INewTweetActivityListener iNewTweetActivityListener;
    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        disableShowHomAsUp();
        newTweetActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.new_tweet_activity, baseActivityBinding.appBarBase.contentLayout, true);
        newTweetViewModel = new NewTweetActivityViewModel(this,newTweetActivityBinding,service);
        newTweetActivityBinding.setViewModel(newTweetViewModel);
        iNewTweetActivityListener = newTweetViewModel.getINewTweetViewModel();
        iNewTweetActivityListener.onCreate();
        setBackgroundForToggleMenuButton();
    }

    protected void injectDagger(AppComponent appComponent){
        appComponent.inject(this);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iNewTweetActivityListener.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        baseActivityBinding.appBarBase.fab.setVisibility(View.GONE);
        iNewTweetActivityListener.onResume();
    }
}
