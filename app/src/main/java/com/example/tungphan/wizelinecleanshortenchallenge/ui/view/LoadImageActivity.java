package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.LoadImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.ActivityViewModel;

/**
 * Created by tungphan on 3/23/17.
 */

public class LoadImageActivity extends BaseActivity {
    private LoadImageActivityBinding loadImageActivityBinding;
    private ActivityViewModel loadImageActivityViewModel;
    private IActivityListener iActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableShowNavDrawer();
        disableShowHomAsUp();
        initViews();
        setBackgroundForToggleMenuButton();
    }

    private void initViews() {
        loadImageActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.load_image_activity, baseActivityBinding.appBarBase.contentLayout, true);
        loadImageActivityViewModel = new ActivityViewModel(this, loadImageActivityBinding);
        loadImageActivityBinding.setViewModel(loadImageActivityViewModel);
        iActivityListener = loadImageActivityViewModel.getILoadImageActivityListener();
        iActivityListener.onCreate();
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

    @Override
    protected void onStart() {
        super.onStart();
        iActivityListener.onStart();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}
