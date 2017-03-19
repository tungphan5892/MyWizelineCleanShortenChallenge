package com.example.tungphan.wizelinecleanshortenchallenge.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.DaggerAppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.modules.NetworkModule;

/**
 * Created by tungphan on 3/17/17.
 */
public class WizelineApp extends Application {
    private static WizelineApp instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder().networkModule(new NetworkModule(this)).build();
    }

    public static WizelineApp getInstance(){
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public boolean isConnectToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
