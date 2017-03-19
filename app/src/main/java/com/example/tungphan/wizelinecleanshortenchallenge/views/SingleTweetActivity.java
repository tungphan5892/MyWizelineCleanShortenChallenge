package com.example.tungphan.wizelinecleanshortenchallenge.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.contants.IntentConstants;
import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SingleTweetActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.ISingleTweetActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.viewmodels.SingleTweetActivityViewModel;


/**
 * Created by Tung Phan on 2/15/2017.
 */

public class SingleTweetActivity extends BaseActivity {
    private static final String TAG = SingleTweetActivity.class.getSimpleName();
    private SingleTweetActivityViewModel singleTweetActivityViewModel;
    private SingleTweetActivityBinding singleTweetActivityBinding;
    private ISingleTweetActivityListener iSingleTweetActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        disableDrawerState();
        if (getIntent() != null && getIntent().getExtras() != null) {
            setUserProfileImageIntoNavigateUp(getIntent().getExtras()
                    .getString(IntentConstants.OWNER_PROFILE_IMAGE_URL));
        }
        singleTweetActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.single_tweet_activity, baseActivityBinding.appBarBase.contentLayout, true);
        singleTweetActivityViewModel = new SingleTweetActivityViewModel(this, singleTweetActivityBinding);
        iSingleTweetActivityListener = singleTweetActivityViewModel.getISingleTweetViewModel();
        singleTweetActivityBinding.setViewModel(singleTweetActivityViewModel);
        iSingleTweetActivityListener.retreivedIntentFromOtherActivity(getIntent());
    }

    protected void injectDagger(AppComponent appComponent){
        appComponent.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iSingleTweetActivityListener.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        toolbarVM.setVisibleTitle(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
