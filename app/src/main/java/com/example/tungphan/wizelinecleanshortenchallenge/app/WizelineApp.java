package com.example.tungphan.wizelinecleanshortenchallenge.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.modules.NetworkModule;

/**
 * Created by tungphan on 3/17/17.
 */

public class WizelineApp extends Application {
    private AppComponent appComponent;
    private static WizelineApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static WizelineApp getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .networkModule(new NetworkModule(this))
                    .build();
        }
        return appComponent;
    }

    public static boolean isConnectToNetwork() {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}
