package com.example.tungphan.wizelinecleanshortenchallenge.dagger2.modules;

import android.app.Application;
import android.util.Log;

import com.example.tungphan.wizelinecleanshortenchallenge.app.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.network.NetworkService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tung Phan on 03/17/2017
 */
@Module
public class NetworkModule {
    private final String TAG = NetworkModule.class.getSimpleName();
    private static final String ROOT_URL = "https://wizetwitterproxy.herokuapp.com";
    private static final int OFFLINE_EXPIRE_TIME_DAY = 7;
    private static final int EXPIRE_TIME_MINS = 2;
    private static final String CACHE_CONTROL = "cache_control";
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final String HTTP_CACHE = "wizeline_http_cache";
    private Application application;

    public NetworkModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache())
                .build();
    }

    @Provides
    @Singleton
    Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(WizelineApp.getInstance().getCacheDir(), HTTP_CACHE),
                    CACHE_SIZE); // 10 MB
        } catch (Exception e) {
            Log.e(TAG, "Could not create Cache!");
        }
        return cache;
    }

    public NetworkService getNetworkService() {
        return getRetrofitInstance().create(NetworkService.class);
    }

    public static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(EXPIRE_TIME_MINS, TimeUnit.MINUTES)
                        .build();
                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!WizelineApp.isConnectToNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(OFFLINE_EXPIRE_TIME_DAY, TimeUnit.DAYS)
                            .build();
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }


}
