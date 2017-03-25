package com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener;

import android.content.Intent;

/**
 * Created by tungphan on 3/23/17.
 */

public interface IRootViewListener {

    void onCreate();

    void onResume();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onDestroy();

}
