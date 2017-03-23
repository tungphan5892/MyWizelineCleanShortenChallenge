package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.LoadImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityStartStopListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewModelListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.LoadingImageActivityViewModel;

/**
 * Created by tungphan on 3/23/17.
 */

public class LoadImageActivity extends BaseActivity {
    private LoadImageActivityBinding loadImageActivityBinding;
    private LoadingImageActivityViewModel loadImageLoadingImageActivityViewModel;
    private IRootViewModelListener iRootViewModelListener;
    private IActivityStartStopListener iActivityStartStopListener;

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
        loadImageLoadingImageActivityViewModel = new LoadingImageActivityViewModel(this, loadImageActivityBinding);
        loadImageActivityBinding.setViewModel(loadImageLoadingImageActivityViewModel);
        iRootViewModelListener = loadImageLoadingImageActivityViewModel.getIRootViewModelListener();
        iRootViewModelListener.onCreate();
        iActivityStartStopListener = loadImageLoadingImageActivityViewModel.getIActivityStartStopListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iRootViewModelListener.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iActivityStartStopListener.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iRootViewModelListener.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}
