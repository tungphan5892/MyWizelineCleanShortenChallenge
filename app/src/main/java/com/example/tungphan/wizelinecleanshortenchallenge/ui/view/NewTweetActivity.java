package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NewTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.NewTweetActivityViewModel;

import javax.inject.Inject;


/**
 * Created by tungphan on 3/9/17.
 */

public class NewTweetActivity extends BaseActivity {
    private NewTweetActivityBinding newTweetActivityBinding;
    private NewTweetActivityViewModel newTweetViewModel;
    private IActivityListener iActivityListener;

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
        iActivityListener = newTweetViewModel.getIActitivyListener();
        iActivityListener.onCreate();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iActivityListener.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iActivityListener.onResume();
    }
}
