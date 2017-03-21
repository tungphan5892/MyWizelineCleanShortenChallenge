package com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener;

import com.example.tungphan.wizelinecleanshortenchallenge.model.FinishLoadingUserInfoEvent;

/**
 * Created by tungphan on 3/17/17.
 */

public interface IBaseActivityListener {

    void onCreate();
    void onStop();
    void onPrepareOptionsMenu();
    void doThis(FinishLoadingUserInfoEvent finishLoadingUserInfoEvent);

}
