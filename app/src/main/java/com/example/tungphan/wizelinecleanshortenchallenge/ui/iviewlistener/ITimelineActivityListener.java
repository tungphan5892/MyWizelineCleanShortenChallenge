package com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener;

import android.content.Intent;

/**
 * Created by tungphan on 3/17/17.
 */

public interface ITimelineActivityListener {

    void onCreate();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onResume();

    void onDestroy();

}
