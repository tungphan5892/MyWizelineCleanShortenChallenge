package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.LoadImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityStartStopListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ILoadingImageActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.LoadingImageActivityViewModel;

/**
 * Created by tungphan on 3/23/17.
 */

public class LoadImageActivity extends BaseActivity {
    private LoadImageActivityBinding loadImageActivityBinding;
    private LoadingImageActivityViewModel loadingImageActivityViewModel;
    private IRootViewListener iRootViewListener;
    private ILoadingImageActivityListener iLoadingImageActivityListener;
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
        loadingImageActivityViewModel = new LoadingImageActivityViewModel(this, loadImageActivityBinding);
        loadImageActivityBinding.setViewModel(loadingImageActivityViewModel);
        iRootViewListener = loadingImageActivityViewModel.getIRootViewModelListener();
        iRootViewListener.onCreate();
        iActivityStartStopListener = loadingImageActivityViewModel.getIActivityStartStopListener();
        iLoadingImageActivityListener = loadingImageActivityViewModel.getLoadingImageActivityListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loading_images_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_post_image) {
            loadingImageActivityViewModel.postImage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iRootViewListener.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iActivityStartStopListener.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iRootViewListener.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iRootViewListener.onActivityResult(requestCode, resultCode, data);
    }
}
