package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;


import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.tungphan.wizelinecleanshortenchallenge.BR;
import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.LoaderConstant;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters.GalleryImageAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NewTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityStartStopListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewModelListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.model.NewTweetActivityModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by tungphan on 3/9/17.
 */

public class NewTweetActivityViewModel extends RootViewModel implements IRootViewModelListener, LoaderManager.LoaderCallbacks {
    private final int MAX_TWEET_LENGTH = 140;
    private final NewTweetActivityModel newTweetActivityModel = new NewTweetActivityModel();
    private NewTweetActivityBinding newTweetActivityBinding;
    private GalleryImageAdapter galleryImageAdapter;
    private Activity activity;
    private ArrayList<String> imagesPath = new ArrayList<>();

    public static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    public NewTweetActivityViewModel(Activity activity, NewTweetActivityBinding newTweetActivityBinding) {
        injectDagger(WizelineApp.getInstance().getAppComponent());
        this.activity = activity;
        this.newTweetActivityBinding = newTweetActivityBinding;
    }

    public IRootViewModelListener getIActitivyListener() {
        return this;
    }

    public IActivityStartStopListener getIActivityStartStopListener() {
        return super.getIActivityStartStopListener();
    }

    public void setTweetCount(int tweetCount) {
        newTweetActivityModel.setTweetCount(tweetCount);
        notifyPropertyChanged(BR.tweetCount);
    }

    @Bindable
    public int getTweetCount() {
        return newTweetActivityModel.getTweetCount();
    }

    public void setTweetCountText(String tweetCountText) {
        newTweetActivityModel.setTweetCountText(tweetCountText);
        notifyPropertyChanged(BR.tweetCountText);
    }

    @Bindable
    public String getTweetCountText() {
        return newTweetActivityModel.getTweetCountText();
    }

    @Override
    public void onCreate() {
        setTweetDescriptionChanged();
        setupSoftKeyboardListener();
        activity.getLoaderManager().initLoader(LoaderConstant.EXTERNAL_IMAGES_LOADER_ID, null, this);
    }

    private void setupSoftKeyboardListener() {
        final View contentView = activity
                .findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int previousHeight;

            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                newTweetActivityBinding.tweetDescription.getWindowVisibleDisplayFrame(rect);
                int newHeight = rect.height();
                if (previousHeight != 0) {
                    if (previousHeight > newHeight) {
                        newTweetActivityBinding.tweetDescription.setHeight((int) activity.getResources()
                                .getDimension(R.dimen.new_tweet_description_height_keyboard_shown));
                    } else if (previousHeight < newHeight) {
                        newTweetActivityBinding.tweetDescription.setHeight((int) activity.getResources()
                                .getDimension(R.dimen.default_new_tweet_description_height));
                    } else {
                    }
                }
                previousHeight = newHeight;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDestroy() {

    }

    @Override
    public void onResume() {
    }

    private void setTweetDescriptionChanged() {
        newTweetActivityBinding.tweetDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tweetContent = newTweetActivityBinding.tweetDescription.getText().toString();
                setTweetCountText(String.valueOf(MAX_TWEET_LENGTH - tweetContent.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setShowKeyboard(boolean showKeyboard) {
        newTweetActivityModel.setShowKeyboard(showKeyboard);
        notifyPropertyChanged(BR.showKeyboard);
    }

    @Bindable
    public boolean getShowKeyboard() {
        return newTweetActivityModel.getShowKeyboard();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MediaStore.Images.Media.DATA
        };
        return new CursorLoader(activity
                , MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Cursor cursor = (Cursor) data;
        if (data != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                do {
                    imagesPath.add(cursor.getString(columnIndex));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        //TODO:reuse adapter object
        galleryImageAdapter = new GalleryImageAdapter(activity, imagesPath);
        newTweetActivityBinding.gridviewCategory
                .setOnScrollListener(galleryImageAdapter.getOnScrollListener());
        newTweetActivityBinding.gridviewCategory.setAdapter(galleryImageAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public void clickNewTweetButton(@NonNull final View view) {
        subscriptions.add(service.postNewTweet(getTweetDescription()).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                activity.setResult(ActivityResult.FALSE);
                activity.finish();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                activity.setResult(ActivityResult.OK);
                activity.finish();
            }
        }));
    }

    public String getTweetDescription() {
        return newTweetActivityBinding.tweetDescription.getText().toString();
    }
}
