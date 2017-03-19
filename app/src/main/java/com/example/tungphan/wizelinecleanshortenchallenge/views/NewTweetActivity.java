package com.example.tungphan.wizelinecleanshortenchallenge.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NewTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.INewTweetActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.viewmodels.NewTweetActivityViewModel;


/**
 * Created by tungphan on 3/9/17.
 */

public class NewTweetActivity extends BaseActivity {
    private NewTweetActivityBinding newTweetActivityBinding;
    private NewTweetActivityViewModel newTweetViewModel;
    private INewTweetActivityListener iNewTweetActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        disableDrawerState();
        newTweetActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.new_tweet_activity, baseActivityBinding.appBarBase.contentLayout, true);
        newTweetViewModel = new NewTweetActivityViewModel(this,newTweetActivityBinding);
        newTweetActivityBinding.setViewModel(newTweetViewModel);
        iNewTweetActivityListener = newTweetViewModel.getINewTweetViewModel();
        iNewTweetActivityListener.onCreate();
    }

    protected void injectDagger(AppComponent appComponent){
        appComponent.inject(this);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        toolbarVM.setBtnSearchVisible(false);
//        toolbarVM.setBtnCloseVisible(true);
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
        iNewTweetActivityListener.onResume();
    }
}
