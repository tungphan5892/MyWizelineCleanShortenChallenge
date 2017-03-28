package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;

import com.example.tungphan.wizelinecleanshortenchallenge.di.component.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.eventbus.RxEventBus;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewListener;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by tungphan on 3/23/17.
 */

public class RootViewModel extends BaseObservable implements IRootViewListener {

    @Inject
    Service service;
    @Inject
    RxEventBus rxEventBus;
    protected CompositeSubscription subscriptions;

    protected void injectDagger(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public IRootViewListener getIRootViewListener() {
        return this;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onStop() {
        if (subscriptions != null) {
            subscriptions.unsubscribe();
            subscriptions = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onDestroy() {

    }

}
