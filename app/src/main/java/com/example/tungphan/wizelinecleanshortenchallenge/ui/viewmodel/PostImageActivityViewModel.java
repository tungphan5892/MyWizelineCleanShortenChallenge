package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.tungphan.wizelinecleanshortenchallenge.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.LoaderConstant;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.PostImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters.GalleryImageAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IActivityStartStopListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewModelListener;

import java.util.ArrayList;

/**
 * Created by tungphan on 3/23/17.
 */

public class PostImageActivityViewModel extends RootViewModel implements IRootViewModelListener, LoaderManager.LoaderCallbacks {

    private PostImageActivityBinding postImageActivityBinding;
    private GalleryImageAdapter galleryImageAdapter;
    private Activity activity;
    private ArrayList<String> imagesPath = new ArrayList<>();

    public PostImageActivityViewModel(Activity activity
            , PostImageActivityBinding postImageActivityBinding) {
        injectDagger(WizelineApp.getInstance().getAppComponent());
        this.activity = activity;
        this.postImageActivityBinding = postImageActivityBinding;
    }

    public IRootViewModelListener getIRootViewModelListener() {
        return this;
    }

    public IActivityStartStopListener getIActivityStartStopListener(){
        return super.getIActivityStartStopListener();
    }

    @Override
    public void onCreate() {
        activity.getLoaderManager().initLoader(LoaderConstant.EXTERNAL_IMAGES_LOADER_ID, null, this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
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
        postImageActivityBinding.gridviewCategory
                .setOnScrollListener(galleryImageAdapter.getOnScrollListener());
        postImageActivityBinding.gridviewCategory.setAdapter(galleryImageAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
