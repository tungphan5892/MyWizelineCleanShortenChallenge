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

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected BaseActivityBinding baseActivityBinding;
    private BaseActivityViewModel baseActivityViewModel;
    private IBaseActivityListener iBaseActivityListener;
    private static String userImageUrl;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDagger(WizelineApp.getInstance().getAppComponent());
        //init databinding, base view model and set up IBaseActivity Listener.
        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.base_activity);
        baseActivityViewModel = new BaseActivityViewModel(this, baseActivityBinding);
        baseActivityBinding.setViewModel(baseActivityViewModel);
        iBaseActivityListener = baseActivityViewModel.getIBaseActivityListener();
        iBaseActivityListener.onCreate();
        initToolbarAndNavigationDrawer();
    }

    protected abstract void injectDagger(AppComponent appComponent);

    private void initToolbarAndNavigationDrawer() {
        setSupportActionBar(baseActivityBinding.appBarBase.toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, baseActivityBinding.drawerLayout, baseActivityBinding.appBarBase.toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        baseActivityBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBarDrawerToggle.syncState();
        baseActivityBinding.navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        this.setResult(ActivityResult.CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void doThis(FinishLoadingUserInfoEvent finishLoadingUserInfoEvent) {
        if (finishLoadingUserInfoEvent.getResultCode() == EventBusConstant.OK) {
            userImageUrl = finishLoadingUserInfoEvent.getUserProfileImageUrl();
            setBackgroundForToggleMenuButton();
        }
    }

    protected void setBackgroundForToggleMenuButton() {
        if (userImageUrl != null && !userImageUrl.equalsIgnoreCase(""))
            Picasso.with(this)
                    .load(userImageUrl)
                    .placeholder(R.drawable.face)
                    .into(getToggleMenu());
    }

    private ImageButton getToggleMenu() {
        final ArrayList<View> outViews = new ArrayList<>();
        String contentDesc = getResources().getString(R.string.drawer_open);
        baseActivityBinding.appBarBase.toolbar.findViewsWithText(outViews, contentDesc
                , View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        return (ImageButton) outViews.get(0);
    }

    protected void setBackButtonClickListener(){
        getBackButtonInNavigationDrawer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private ImageButton getBackButtonInNavigationDrawer(){
        final ArrayList<View> outViews = new ArrayList<>();
        String contentDesc = getResources().getString(R.string.navigate_up);
        baseActivityBinding.appBarBase.toolbar.findViewsWithText(outViews, contentDesc
                , View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        return (ImageButton) outViews.get(0);
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
        baseActivityBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        actionBarDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    protected void disableShowNavDrawer() {
        baseActivityBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        actionBarDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
}
