package com.example.tungphan.wizelinecleanshortenchallenge.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SearchActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.eventbus.SearchEdittextDoneEvent;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.ISearchActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.viewmodels.SearchActivityViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by tungphan on 3/17/17.
 */

public class SearchActivity extends BaseActivity {
    private SearchActivityBinding searchActivityBinding;
    private SearchActivityViewModel searchActivityViewModel;
    private ISearchActivityListener iSearchActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        disableDrawerState();
        searchActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.search_activity, baseActivityBinding.appBarBase.contentLayout, true);
        searchActivityViewModel = new SearchActivityViewModel(this,searchActivityBinding);
        searchActivityBinding.setViewModel(searchActivityViewModel);
        iSearchActivityListener = searchActivityViewModel.getISearchTweetViewModel();
    }

    protected void injectDagger(AppComponent appComponent){
        appComponent.inject(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        toolbarVM.setVisibleSearchEditText(true);
//        toolbarVM.setVisibleTitle(false);
//        toolbarVM.setBtnSearchVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(SearchEdittextDoneEvent searchEdittextDoneEvent) {
        iSearchActivityListener.searchEditTextDone(searchEdittextDoneEvent);
    }
}
