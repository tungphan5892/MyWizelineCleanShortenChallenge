package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.WizelineApp;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.ImageViewActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IImageViewActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.PostImageActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstant.IMAGE_DESCRIPTION;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstant.IMAGE_ID;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstant.IMAGE_URL;

/**
 * Created by tungphan on 3/23/17.
 */

public class ImageViewActivityViewModel extends BaseObservable implements IImageViewActivityListener {

    private static final String TAG = ImageViewActivityViewModel.class.getSimpleName();
    private ImageViewActivityBinding imageViewActivityBinding;
    private Context context;
    private String imageId = "";
    private String imageDescription = "";
    private String imageUrl = "";
    private static final File imageRoot = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES);

    public ImageViewActivityViewModel(Context context
            , ImageViewActivityBinding imageViewActivityBinding) {
        this.context = context;
        this.imageViewActivityBinding = imageViewActivityBinding;
    }

    public IImageViewActivityListener getIImageViewActivityListener() {
        return this;
    }

    @Override
    public void onCreate(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                imageId = bundle.getString(IMAGE_ID);
                imageDescription = bundle.getString(IMAGE_DESCRIPTION);
                imageUrl = bundle.getString(IMAGE_URL);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDestroy() {

    }

    @Override
    public void saveImage() {
        imageDownload(imageUrl, imageId);
    }

    @Override
    public void postImage() {
        Intent intent = new Intent(context, PostImageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.default_placeholder)
                .fit()
                .into(imageViewActivityBinding.fullImageView);
        imageViewActivityBinding.imageId.setText(imageId);
        imageViewActivityBinding.imageId.setText(imageDescription);
    }

    public void imageDownload(String imageUrl, String imageName) {
        Picasso.with(context)
                .load(imageUrl)
                .into(getTarget(imageUrl, imageName));
    }

    private static Target getTarget(final String imageUrl, final String imageName) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = MediaStore.Images.Media.insertImage(
                                WizelineApp.getInstance().getContentResolver()
                                , bitmap, imageName, imageUrl);
                        Log.e("TFunk",url);
                        addImageToGallery(url,WizelineApp.getInstance().getApplicationContext());
                    }
                }).start();
            }

            private void addImageToGallery(final String filePath, final Context context) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.MediaColumns.DATA, filePath);
                context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e(TAG, errorDrawable.toString());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
}
