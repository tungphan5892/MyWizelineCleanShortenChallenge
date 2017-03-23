package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.tungphan.wizelinecleanshortenchallenge.databinding.LoadImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Login;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters.PhoneImagesGridVAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ILoadImageActivityListener;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tungphan on 3/23/17.
 */

public class LoadImageActivityViewModel extends BaseObservable implements ILoadImageActivityListener {
    private LoadImageActivityBinding loadImageActivityBinding;
    private PhoneImagesGridVAdapter phoneImagesGridVAdapter;
    private Activity activity;
    private Service service;
    private CompositeSubscription subscriptions;

    public LoadImageActivityViewModel(Activity activity
            , LoadImageActivityBinding loadImageActivityBinding, Service service) {
        this.activity = activity;
        this.loadImageActivityBinding = loadImageActivityBinding;
        this.service = service;
    }

    public ILoadImageActivityListener getILoadImageActivityListener() {
        return this;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        subscriptions = new CompositeSubscription();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDestroy() {

    }

    @Override
    public void onResume() {
//        login();
        getImagesFromService();
    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
        subscriptions = null;
    }

    private void login() {
        subscriptions.add(service.login().subscribe(new Subscriber<Login>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TFunk", e.getMessage());
            }

            @Override
            public void onNext(Login login) {
                Log.e("TFunk", login.toString());
            }
        }));
    }

    private void getImagesFromService() {
        subscriptions.add(service.getImagesFromService()
                .flatMap(list -> Observable.from(list.getData()))
                .filter(data -> !data.isAnimated())
                .toList()
                .subscribe(data -> {
                    phoneImagesGridVAdapter = new PhoneImagesGridVAdapter(activity, data);
                    loadImageActivityBinding.loadingImageGridview
                            .setOnScrollListener(phoneImagesGridVAdapter.getOnScrollListener());
                    loadImageActivityBinding.loadingImageGridview.setAdapter(phoneImagesGridVAdapter);
                }));
    }

}
