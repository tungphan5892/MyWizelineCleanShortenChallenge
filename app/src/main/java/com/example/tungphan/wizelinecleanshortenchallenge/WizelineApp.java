package com.example.tungphan.wizelinecleanshortenchallenge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.tungphan.wizelinecleanshortenchallenge.di.component.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.di.component.DaggerAppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.di.module.EventBusModule;
import com.example.tungphan.wizelinecleanshortenchallenge.di.module.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.distractrabit.Crypto;

import java.util.concurrent.Callable;

import javax.crypto.SecretKey;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.example.tungphan.wizelinecleanshortenchallenge.constant.PrefConstant.PREFS_LOGIN;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.PrefConstant.PREFS_LOGIN_ETOKEN;

/**
 * Created by tungphan on 3/17/17.
 */
public class WizelineApp extends Application {
    private final String ETOKEN_KEYSTORE_ALIAS = "etoken_keystore_alias";
    private static WizelineApp instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(this))
                .eventBusModule(new EventBusModule())
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Crypto.generateAesSecretKey(ETOKEN_KEYSTORE_ALIAS);
        }
    }

    public static WizelineApp getInstance() {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void encryptETokenAES(String token) {
        Observable.fromCallable(encryptETokenLogic(token))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(saveETokenToSharePreference());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Callable<String> encryptETokenLogic(String token) {
        return () -> {
            SecretKey key = Crypto.getAesSecretKey(ETOKEN_KEYSTORE_ALIAS);
            String success = Crypto.encryptAesCbc(token, key);
            return success;
        };
    }

    private Subscriber<String> saveETokenToSharePreference() {
        return (new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_LOGIN, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PREFS_LOGIN_ETOKEN, s);
                editor.apply();
            }
        });
    }

    public Callable<String> decryptETokenLogic() {
        return () -> {
            SecretKey key = Crypto.getAesSecretKey(ETOKEN_KEYSTORE_ALIAS);
            return Crypto.decryptAesCbc(loadETokenFromSharePreference(), key);
        };
    }

    private String loadETokenFromSharePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_LOGIN, 0);
        return sharedPreferences.getString(PREFS_LOGIN_ETOKEN, "");
    }

}
