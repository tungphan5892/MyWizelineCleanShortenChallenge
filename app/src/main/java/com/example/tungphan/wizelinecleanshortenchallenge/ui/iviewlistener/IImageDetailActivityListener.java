package com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener;

import android.content.Intent;

/**
 * Created by tungphan on 3/23/17.
 */

public interface IImageDetailActivityListener {

    void onCreate(Intent intent);

    void onResume();

    void onDestroy();

    void saveImage();
}
