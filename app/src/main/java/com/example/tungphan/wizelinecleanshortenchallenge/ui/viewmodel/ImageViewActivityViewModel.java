package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.ImageViewActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IImageViewActivityListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstants.IMAGE_DESCRIPTION;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstants.IMAGE_ID;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstants.IMAGE_URL;

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
                .into(getTarget(imageName));
    }

    private static Target getTarget(final String imageName) {
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(imageRoot, imageName);
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();
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
