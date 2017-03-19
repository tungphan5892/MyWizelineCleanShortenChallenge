package com.example.tungphan.wizelinecleanshortenchallenge.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.example.tungphan.wizelinecleanshortenchallenge.app.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.DaggerAppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.modules.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.BaseActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.IBaseActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.viewmodels.BaseActivityViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected BaseActivityBinding baseActivityBinding;
    private BaseActivityViewModel baseActivityViewModel;
    private IBaseActivityListener iBaseActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDagger(WizelineApp.getInstance().getAppComponent());
        //init databinding, base view model and set up IBaseActivity Listener.
        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.base_activity);
        baseActivityViewModel = new BaseActivityViewModel(this,baseActivityBinding);
        baseActivityBinding.setViewModel(baseActivityViewModel);
        iBaseActivityListener = baseActivityViewModel.getIBaseActivityListener();
        initToolbarAndNavigationDrawer();
    }

    protected abstract void injectDagger(AppComponent appComponent);

    private void initToolbarAndNavigationDrawer(){
        setSupportActionBar(baseActivityBinding.appBarBase.toolbar);
        baseActivityBinding.appBarBase.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, baseActivityBinding.drawerLayout, baseActivityBinding.appBarBase.toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        baseActivityBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        baseActivityBinding.navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = baseActivityBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = baseActivityBinding.drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    protected void setUserProfileImageIntoNavigateUp(String userProfileImageUrl){
        Picasso.with(this)
                .load(userProfileImageUrl)
                .placeholder(R.drawable.face)
                .into(getNavigateUp());
    }

    private ImageButton getNavigateUp(){
        final ArrayList<View> outViews = new ArrayList<>();
        String contentDesc = getResources().getString(R.string.navigate_up);
        baseActivityBinding.appBarBase.toolbar.findViewsWithText(outViews, contentDesc
                , View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        return (ImageButton) outViews.get(0);
    }
}
