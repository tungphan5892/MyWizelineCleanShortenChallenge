package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SearchActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.SearchActivityViewModel;


/**
 * Created by tungphan on 3/17/17.
 */

public class SearchActivity extends BaseActivity {
    private SearchActivityBinding searchActivityBinding;
    private SearchActivityViewModel searchActivityViewModel;
    private IRootViewListener iRootViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        enableShowHomeAsUp();
        initViews();
        setBackButtonClickListener();
    }

    private void initViews() {
        searchActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.search_activity, baseActivityBinding.appBarBase.contentLayout, true);
        searchActivityViewModel = new SearchActivityViewModel(this, searchActivityBinding);
        searchActivityBinding.setViewModel(searchActivityViewModel);
        iRootViewListener = searchActivityViewModel.getIRootViewListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iRootViewListener.onDestroy();
    }
}
