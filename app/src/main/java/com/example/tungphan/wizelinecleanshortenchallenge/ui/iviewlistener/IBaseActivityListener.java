package com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener;

import android.support.annotation.NonNull;

/**
 * Created by tungphan on 3/17/17.
 */

public interface IBaseActivityListener {

    void onCreate();

    void onPrepareOptionsMenu();

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

}
