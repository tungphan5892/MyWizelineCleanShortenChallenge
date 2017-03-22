package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;

import com.example.tungphan.wizelinecleanshortenchallenge.BR;
import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.EventBusConstant;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.NavHeaderBaseBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.User;
import com.example.tungphan.wizelinecleanshortenchallenge.model.FinishLoadingUserInfoEvent;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IMyNavViewHeaderListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.model.MyNavViewHeaderModel;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * Created by tungphan on 3/20/17.
 */

public class MyNavViewHeaderViewModel extends BaseObservable implements IMyNavViewHeaderListener {
    private final MyNavViewHeaderModel myNavViewHeaderModel = new MyNavViewHeaderModel();
    private Service service;
    private NavHeaderBaseBinding myNavViewHeaderBinding;
    private Context context;

    public IMyNavViewHeaderListener getIMyNavViewHeaderListener() {
        return this;
    }

    public MyNavViewHeaderViewModel(Context context, NavHeaderBaseBinding myNavViewHeaderBinding, Service service) {
        this.context = context;
        this.myNavViewHeaderBinding = myNavViewHeaderBinding;
        this.service = service;
    }

    @Bindable
    public String getUserName() {
        return myNavViewHeaderModel.getUserName();
    }

    public void setUserName(String userName) {
        myNavViewHeaderModel.setUserName(userName);
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserDescription() {
        return myNavViewHeaderModel.getUserDescription();
    }

    public void setUserDescription(String userDescription) {
        myNavViewHeaderModel.setUserDescription(userDescription);
        notifyPropertyChanged(BR.userDescription);
    }

    @Override
    public void onCreate() {
        loadingUserInfo();
    }


    private void loadingUserInfo() {
        service.getUserFromService()
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        errorLoadingUserInfo();
                    }

                    @Override
                    public void onNext(User user) {
                        finishLoadingUserInfo(user);
                    }
                });
    }

    private void finishLoadingUserInfo(User user) {
        //update views data
        setUserName(user.getName());
        setUserDescription(user.getDescription());
        Picasso.with(context)
                .load(user.getProfileImageUrl())
                .placeholder(R.drawable.face)
                .fit()
                .into(myNavViewHeaderBinding.userProfileImage);
        Picasso.with(context)
                .load(user.getProfileBackgroundImageUrl())
                .fit()
                .into(myNavViewHeaderBinding.userBackgroundImage);
        //send this event to BaseActivity to update the menu toggle background image
        EventBus.getDefault().post(new FinishLoadingUserInfoEvent(EventBusConstant.OK
                , user.getProfileImageUrl()));
    }

    private void errorLoadingUserInfo() {
        Snackbar.make(myNavViewHeaderBinding.parentView, R.string.error_loading_user_info
                , Snackbar.LENGTH_LONG).show();
    }
}
