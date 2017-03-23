package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.LoadImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.di.component.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Login;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters.ImagesFromServiceAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityStartStopListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewModelListener;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by tungphan on 3/23/17.
 */

public class LoadingImageActivityViewModel extends RootViewModel implements IRootViewModelListener {
    private LoadImageActivityBinding loadImageActivityBinding;
    private ImagesFromServiceAdapter imagesFromServiceAdapter;
    private Activity activity;
    private static String eToken;

    public LoadingImageActivityViewModel(Activity activity
            , LoadImageActivityBinding loadImageActivityBinding) {
        injectDagger(WizelineApp.getInstance().getAppComponent());
        this.activity = activity;
        this.loadImageActivityBinding = loadImageActivityBinding;
    }

    public IRootViewModelListener getIRootViewModelListener() {
        return this;
    }

    @Override
    public void onCreate() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDestroy() {

    }

    @Override
    public void onResume() {
    }

    private void login() {
        subscriptions.add(service.login().subscribe(new Subscriber<Login>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TFunk", e.getMessage());
                Snackbar.make(loadImageActivityBinding.parentView, R.string.login_fail
                        , BaseTransientBottomBar.LENGTH_LONG).show();
            }

            @Override
            public void onNext(Login login) {
                Log.e("TFunk", login.toString());
                Snackbar.make(loadImageActivityBinding.parentView, R.string.login_success
                        , BaseTransientBottomBar.LENGTH_LONG).show();
                eToken = login.getToken();
                getImagesFromService(eToken);
            }
        }));
    }

    private void getImagesFromService(String eToken) {
        subscriptions.add(service.getImagesFromService(eToken)
                .flatMap(list -> Observable.from(list.getData()))
                .filter(data -> !data.isAnimated())
                .toList()
                .subscribe(data -> {
                    if (imagesFromServiceAdapter == null) {
                        imagesFromServiceAdapter = new ImagesFromServiceAdapter(activity, data);
                        loadImageActivityBinding.loadingImageGridview.setAdapter(imagesFromServiceAdapter);
                        loadImageActivityBinding.loadingImageGridview
                                .setOnScrollListener(imagesFromServiceAdapter.getOnScrollListener());
                    } else {
                        imagesFromServiceAdapter.setData(data);
                        imagesFromServiceAdapter.notifyDataSetChanged();
                    }
                }));
    }

    public void clickLoginButton(@NonNull final View view) {
        login();
    }


}
