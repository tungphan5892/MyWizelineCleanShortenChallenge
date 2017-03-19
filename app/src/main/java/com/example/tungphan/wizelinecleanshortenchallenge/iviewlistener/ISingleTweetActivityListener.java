package com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener;

import android.content.Intent;

/**
 * Created by tungphan on 3/16/17.
 */

public interface ISingleTweetActivityListener {

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void retreivedIntentFromOtherActivity(Intent intent);

}
