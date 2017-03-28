package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.BaseActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IBaseActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.BaseActivityViewModel;


public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivityBinding baseActivityBinding;
    private BaseActivityViewModel baseActivityViewModel;
    private IBaseActivityListener iBaseActivityListener;
    private IRootViewListener iRootViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        iRootViewListener.onCreate();
    }

    private void initViews() {
        //init databinding, base view model and set up IBaseActivity Listener.
        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.base_activity);
        baseActivityViewModel = new BaseActivityViewModel(this, baseActivityBinding);
        baseActivityBinding.setViewModel(baseActivityViewModel);
        iBaseActivityListener = baseActivityViewModel.getIBaseActivityListener();
        iRootViewListener = baseActivityViewModel.getIRootViewListener();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        iBaseActivityListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        this.setResult(ActivityResult.CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        iBaseActivityListener.onPrepareOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iRootViewListener.onStop();
    }

    protected void enableShowNavDrawer() {
        baseActivityViewModel.enableShowNavDrawer();
    }

    protected void disableShowNavDrawer() {
        baseActivityViewModel.disableShowNavDrawer();
    }

    protected void enableShowHomeAsUp() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void disableShowHomAsUp() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    protected void setBackButtonClickListener() {
        baseActivityViewModel.setBackButtonClickListener();
    }

    protected void setBackgroundForToggleMenuButton() {
        baseActivityViewModel.setBackgroundForToggleMenuButton();
    }

}
