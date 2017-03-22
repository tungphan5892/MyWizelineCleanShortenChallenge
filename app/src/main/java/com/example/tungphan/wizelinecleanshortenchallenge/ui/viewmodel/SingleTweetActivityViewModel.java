package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.tungphan.wizelinecleanshortenchallenge.BR;
import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityRequestCode;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.IntentConstants;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SingleTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ISingleTweetActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.model.SingleTweetActivityModel;
import com.squareup.picasso.Picasso;


/**
 * Created by tungphan on 3/9/17.
 */

public class SingleTweetActivityViewModel extends BaseObservable implements ISingleTweetActivityListener {
    private final String INTENT_TEXT_SHARE_TYPE = "text/plain";

    private Context context;
    private SingleTweetActivityBinding singleTweetActivityBinding;
    final private SingleTweetActivityModel singleTweetActivityModel = new SingleTweetActivityModel();

    public ISingleTweetActivityListener getISingleTweetViewModel() {
        return this;
    }

    public SingleTweetActivityViewModel(Context context, SingleTweetActivityBinding singleTweetActivityBinding) {
        this.context = context;
        this.singleTweetActivityBinding = singleTweetActivityBinding;
    }

    public void clickShareButton(@NonNull final View view) {
        String tweetContent = singleTweetActivityBinding.tweetContent.getText().toString();
        Intent textShareIntent = new Intent(Intent.ACTION_SEND);
        textShareIntent.putExtra(Intent.EXTRA_TEXT, tweetContent);
        textShareIntent.setType(INTENT_TEXT_SHARE_TYPE);
        ((Activity) context).startActivityForResult(Intent.createChooser(textShareIntent
                , context.getResources().getString(R.string.text_share_tweet))
                , ActivityRequestCode.START_SHARE_TWEET_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void retreivedIntentFromOtherActivity(Intent intent) {
        if (intent == null) {
            return;
        }
        if (intent.getExtras() == null) {
            return;
        } else {
            Bundle bundle = intent.getExtras();
            setSingleTweetUserName(bundle.getString(IntentConstants.OWNER_NAME));
            setUserDescription(bundle.getString(IntentConstants.ONER_DESCRIPTION));
            setSingleTweetContent(bundle.getString(IntentConstants.TWEET_CONTENT));
            Picasso.with(context)
                    .load(bundle.getString(IntentConstants.OWNER_PROFILE_IMAGE_URL))
                    .placeholder(R.drawable.face)
                    .fit()
                    .into(singleTweetActivityBinding.profileImage);
        }
    }

    @Bindable
    public String getSingleTweetUserName() {
        return singleTweetActivityModel.getSingleTweetUserName();
    }

    public void setSingleTweetUserName(String singleTweetUserName) {
        singleTweetActivityModel.setSingleTweetUserName(singleTweetUserName);
        notifyPropertyChanged(BR.singleTweetUserName);
    }

    @Bindable
    public String getProfilePictureUrl() {
        return singleTweetActivityModel.getProfilePictureUrl();
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        singleTweetActivityModel.setProfilePictureUrl(profilePictureUrl);
        notifyPropertyChanged(BR.profilePictureUrl);
    }

    @Bindable
    public String getSingleTweetContent() {
        return singleTweetActivityModel.getSingleTweetContent();
    }

    public void setSingleTweetContent(String singleTweetContent) {
        singleTweetActivityModel.setSingleTweetContent(singleTweetContent);
        notifyPropertyChanged(BR.singleTweetContent);
    }

    @Bindable
    public String getUserDescription(){
        return singleTweetActivityModel.getUserDescription();
    }
    public void setUserDescription(String userDescription){
        singleTweetActivityModel.setUserDescription(userDescription);
        notifyPropertyChanged(BR.userDescription);
    }

}
