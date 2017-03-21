package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.EventBusConstant;
import com.example.tungphan.wizelinecleanshortenchallenge.di.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.BaseActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.FinishLoadingUserInfoEvent;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IBaseActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.BaseActivityViewModel;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivityBinding baseActivityBinding;
    private BaseActivityViewModel baseActivityViewModel;
    private IBaseActivityListener iBaseActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDagger(WizelineApp.getInstance().getAppComponent());
        initViews();
        iBaseActivityListener.onCreate();
    }

    private void initViews(){
        //init databinding, base view model and set up IBaseActivity Listener.
        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.base_activity);
        baseActivityViewModel = new BaseActivityViewModel(this, baseActivityBinding);
        baseActivityBinding.setViewModel(baseActivityViewModel);
        iBaseActivityListener = baseActivityViewModel.getIBaseActivityListener();
    }


    protected abstract void injectDagger(AppComponent appComponent);

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        iBaseActivityListener.onStop();
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

    protected void setBackButtonClickListener(){
        baseActivityViewModel.setBackButtonClickListener();
    }

    protected void setBackgroundForToggleMenuButton(){
        baseActivityViewModel.setBackgroundForToggleMenuButton();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(FinishLoadingUserInfoEvent finishLoadingUserInfoEvent) {
        iBaseActivityListener.doThis(finishLoadingUserInfoEvent);
    }
}
