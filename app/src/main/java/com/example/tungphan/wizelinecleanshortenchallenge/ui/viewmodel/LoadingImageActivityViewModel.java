package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityRequestCode;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.LoadImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Login;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters.ImagesFromServiceAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.PostImageActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityRequestCode.START_POST_IMAGE_ACTIVITY_REQUEST_CODE;

/**
 * Created by tungphan on 3/23/17.
 */

public class LoadingImageActivityViewModel extends RootViewModel {
    private LoadImageActivityBinding loadImageActivityBinding;
    private ImagesFromServiceAdapter imagesFromServiceAdapter;
    private Context context;

    public LoadingImageActivityViewModel(Context context
            , LoadImageActivityBinding loadImageActivityBinding) {
        injectDagger(WizelineApp.getInstance().getAppComponent());
        this.context = context;
        this.loadImageActivityBinding = loadImageActivityBinding;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityRequestCode.START_NEW_TWEET_ACTIVITY_REQUEST_CODE) {
            if (resultCode == ActivityResult.OK) {
                Toast.makeText(context, R.string.post_image_successfully, Toast.LENGTH_SHORT).show();
                decryptETokenAESThenReloadImages();
            } else if (resultCode == ActivityResult.FALSE) {
                Toast.makeText(context, R.string.post_image_fail, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.post_image_cancel, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void decryptETokenAESThenReloadImages() {
        Observable.fromCallable(WizelineApp.getInstance().decryptETokenLogic())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getImagesFromService);
    }

    private void login() {
        subscriptions.add(service.login().subscribe(new Subscriber<Login>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(loadImageActivityBinding.parentView, R.string.login_fail
                        , BaseTransientBottomBar.LENGTH_LONG).show();
            }

            @Override
            public void onNext(Login login) {
                Snackbar.make(loadImageActivityBinding.parentView, R.string.login_success
                        , BaseTransientBottomBar.LENGTH_LONG).show();
                WizelineApp.getInstance().encryptETokenAES(login.getToken());
                getImagesFromService(login.getToken());
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
                        imagesFromServiceAdapter = new ImagesFromServiceAdapter(context, data);
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

    public void postImage() {
        Intent intent = new Intent(context, PostImageActivity.class);
        ((Activity) context).startActivityForResult(intent, START_POST_IMAGE_ACTIVITY_REQUEST_CODE);
    }
}
