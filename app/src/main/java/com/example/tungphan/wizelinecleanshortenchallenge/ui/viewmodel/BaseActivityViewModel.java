package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityRequestCode;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.BaseActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IBaseActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.model.BaseActivityModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.NewTweetActivity;

/**
 * Created by tungphan on 3/17/17.
 */

public class BaseActivityViewModel extends BaseObservable implements IBaseActivityListener {
    private BaseActivityModel baseActivityModel = new BaseActivityModel();
    private Context context;
    private BaseActivityBinding baseActivityBinding;

    private View.OnClickListener fabClickListenter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickAddTweetButton();
        }
    };
    private View.OnClickListener closeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity)context).setResult(ActivityResult.CANCELED);
            ((Activity)context).finish();
        }
    };

    public IBaseActivityListener getIBaseActivityListener() {
        return this;
    }

    public BaseActivityViewModel(Context context, BaseActivityBinding baseActivityBinding) {
        this.context = context;
        this.baseActivityBinding = baseActivityBinding;
        baseActivityBinding.appBarBase.fab.setOnClickListener(fabClickListenter);
        baseActivityBinding.appBarBase.closeButton.setOnClickListener(closeBtnClickListener);
    }


    public void clickAddTweetButton() {
        Intent intent = new Intent(context, NewTweetActivity.class);
        ((Activity) context).startActivityForResult(intent
                , ActivityRequestCode.START_NEW_TWEET_ACTIVITY_REQUEST_CODE);
    }


//        baseActivityBinding.appBarBase.fab.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }
//    });
}
