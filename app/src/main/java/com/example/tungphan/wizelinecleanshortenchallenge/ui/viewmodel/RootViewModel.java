package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.databinding.BaseObservable;

import com.example.tungphan.wizelinecleanshortenchallenge.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.di.component.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.eventbus.RxEventBus;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityStartStopListener;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by tungphan on 3/23/17.
 */

public class RootViewModel extends BaseObservable implements IActivityStartStopListener {

    @Inject
    Service service;
    @Inject
    RxEventBus rxEventBus;
    protected CompositeSubscription subscriptions;

    protected void injectDagger(AppComponent appComponent) {
        appComponent.inject(this);
    }

    public IActivityStartStopListener getIActivityStartStopListener() {
        return this;
    }

    @Override
    public void onStart() {
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void onStop() {
        subscriptions.unsubscribe();
        subscriptions = null;
    }

}
