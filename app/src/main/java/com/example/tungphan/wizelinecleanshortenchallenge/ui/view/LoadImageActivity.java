package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.LoadImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.di.component.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ILoadImageActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.LoadImageActivityViewModel;

import javax.inject.Inject;

/**
 * Created by tungphan on 3/23/17.
 */

public class LoadImageActivity extends BaseActivity {
    private LoadImageActivityBinding loadImageActivityBinding;
    private LoadImageActivityViewModel loadImageActivityViewModel;
    private ILoadImageActivityListener iLoadImageActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        disableShowHomAsUp();
        initViews();
        setBackgroundForToggleMenuButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iLoadImageActivityListener.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iLoadImageActivityListener.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iLoadImageActivityListener.onStart();
    }

    private void initViews() {
        loadImageActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.load_image_activity, baseActivityBinding.appBarBase.contentLayout, true);
        loadImageActivityViewModel = new LoadImageActivityViewModel(this, loadImageActivityBinding);
        loadImageActivityBinding.setViewModel(loadImageActivityViewModel);
        iLoadImageActivityListener = loadImageActivityViewModel.getILoadImageActivityListener();
        iLoadImageActivityListener.onCreate();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}
