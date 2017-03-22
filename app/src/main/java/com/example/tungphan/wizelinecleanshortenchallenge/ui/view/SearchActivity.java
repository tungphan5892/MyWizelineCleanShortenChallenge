package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.di.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SearchActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.StartSearchTweetEvent;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ISearchActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.SearchActivityViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * Created by tungphan on 3/17/17.
 */

public class SearchActivity extends BaseActivity {
    private SearchActivityBinding searchActivityBinding;
    private SearchActivityViewModel searchActivityViewModel;
    private ISearchActivityListener iSearchActivityListener;
    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        enableShowHomeAsUp();
        searchActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.search_activity, baseActivityBinding.appBarBase.contentLayout, true);
        searchActivityViewModel = new SearchActivityViewModel(this,searchActivityBinding,service);
        searchActivityBinding.setViewModel(searchActivityViewModel);
        iSearchActivityListener = searchActivityViewModel.getISearchTweetViewModel();
        setBackButtonClickListener();
    }

    protected void injectDagger(AppComponent appComponent){
        appComponent.inject(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(StartSearchTweetEvent startSearchTweetEvent) {
        iSearchActivityListener.searchEditTextDone(startSearchTweetEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iSearchActivityListener.onDestroy();
    }
}
