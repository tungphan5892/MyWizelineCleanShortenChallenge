package com.example.tungphan.wizelinecleanshortenchallenge.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;

import com.example.tungphan.wizelinecleanshortenchallenge.databinding.BaseActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.IBaseActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.models.BaseActivityModel;

/**
 * Created by tungphan on 3/17/17.
 */

public class BaseActivityViewModel extends BaseObservable implements IBaseActivityListener{
    private BaseActivityModel baseActivityModel = new BaseActivityModel();

    public IBaseActivityListener getIBaseActivityListener(){
        return this;
    }

    public BaseActivityViewModel(Context context, BaseActivityBinding baseActivityBinding){

    }
}
