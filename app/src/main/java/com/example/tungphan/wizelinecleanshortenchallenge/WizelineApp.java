package com.example.tungphan.wizelinecleanshortenchallenge;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.tungphan.wizelinecleanshortenchallenge.di.component.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.di.component.DaggerAppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.di.module.EventBusModule;
import com.example.tungphan.wizelinecleanshortenchallenge.di.module.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.distractrabit.Crypto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.concurrent.Callable;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.example.tungphan.wizelinecleanshortenchallenge.constant.PrefConstant.PREFS_LOGIN;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.PrefConstant.PREFS_LOGIN_ETOKEN;

/**
 * Created by tungphan on 3/17/17.
 */
public class WizelineApp extends Application {
    private final String ENCRYPTION_KEY_NAME = "AES_ENCRYPTION_KEY_NAME";
    private final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private final String ETOKEN_KEYSTORE_ALIAS = "etoken_keystore_alias";
    public static final String UNLOCK_ACTION = "com.android.credentials.UNLOCK";
    private static WizelineApp instance;
    private AppComponent appComponent;
    private KeyStore keyStore;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule(this))
                .eventBusModule(new EventBusModule())
                .build();
        initKeyStore();
    }

    private void initKeyStore() {
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance(ANDROID_KEY_STORE);
            ks.load(null);
        } catch (IOException | NoSuchAlgorithmException | CertificateException
                | KeyStoreException e) {
            e.printStackTrace();
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

    public void encryptETokenAES(String token) {
        Observable.fromCallable(encryptETokenLogic(token))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(saveETokenToSharePreference());
    }

    private Callable<String> encryptETokenLogic(String token) {
        return () -> {
            SecretKey key = Crypto.generateAesKey();
            saveETokenSecretKey(key);
            return Crypto.encryptAesCbc(token, key);
        };
    }

    private void saveETokenSecretKey(SecretKey secretKey) throws KeyStoreException {
        KeyStore.SecretKeyEntry skEntry =
                new KeyStore.SecretKeyEntry(secretKey);
        keyStore.setEntry(ETOKEN_KEYSTORE_ALIAS, skEntry, null);
    }

    private SecretKey getETokenSecretKey() {
        KeyStore.SecretKeyEntry secretKeyEntry = null;
        try {
            secretKeyEntry = (KeyStore.SecretKeyEntry)
                    keyStore.getEntry(ETOKEN_KEYSTORE_ALIAS, null);
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            e.printStackTrace();
        }
        return secretKeyEntry.getSecretKey();
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

    public String decryptETokenAES() {
        final String[] eToken = new String[1];
        Observable.fromCallable(decryptETokenLogic())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> eToken[0] = s);
        return eToken[0];
    }

    private Callable<String> decryptETokenLogic() {
        return () -> {
            SecretKey key = getETokenSecretKey();
            return Crypto.decryptAesCbc(loadETokenFromSharePreference(), key);
        };
    }

    private String loadETokenFromSharePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_LOGIN, 0);
        return sharedPreferences.getString(PREFS_LOGIN_ETOKEN, "");
    }

}
