package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NewTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.NewTweetActivityViewModel;


/**
 * Created by tungphan on 3/9/17.
 */

public class NewTweetActivity extends BaseActivity {
    private NewTweetActivityBinding newTweetActivityBinding;
    private NewTweetActivityViewModel newTweetViewModel;
    private IRootViewListener iRootViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        disableShowHomAsUp();
        initViews();
        setBackgroundForToggleMenuButton();
    }

    private void initViews() {
        newTweetActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.new_tweet_activity, baseActivityBinding.appBarBase.contentLayout, true);
        newTweetViewModel = new NewTweetActivityViewModel(this, newTweetActivityBinding);
        newTweetActivityBinding.setViewModel(newTweetViewModel);
        iRootViewListener = newTweetViewModel.getIRootViewListener();
        iRootViewListener.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iRootViewListener.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iRootViewListener.onResume();
    }
}
