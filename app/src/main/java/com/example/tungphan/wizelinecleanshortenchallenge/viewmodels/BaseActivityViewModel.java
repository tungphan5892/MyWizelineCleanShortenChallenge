package com.example.tungphan.wizelinecleanshortenchallenge.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.contants.ActivityRequestCode;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.BaseActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.IBaseActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.models.BaseActivityModel;
import com.example.tungphan.wizelinecleanshortenchallenge.views.NewTweetActivity;

/**
 * Created by tungphan on 3/17/17.
 */

public class BaseActivityViewModel extends BaseObservable implements IBaseActivityListener{
    private BaseActivityModel baseActivityModel = new BaseActivityModel();
    private Context context;

    public IBaseActivityListener getIBaseActivityListener(){
        return this;
    }

    public BaseActivityViewModel(Context context, BaseActivityBinding baseActivityBinding){
        this.context = context;
    }

    public void clickAddTweetButton(@NonNull final View view) {
        Intent intent = new Intent(view.getContext(), NewTweetActivity.class);
        ((Activity) context).startActivityForResult(intent
                , ActivityRequestCode.START_NEW_TWEET_ACTIVITY_REQUEST_CODE);
    }
}
