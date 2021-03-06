package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.PostImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IRootViewListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.PostImageActivityViewModel;

/**
 * Created by tungphan on 3/23/17.
 */

public class PostImageActivity extends BaseActivity {
    private PostImageActivityBinding postImageActivityBinding;
    private PostImageActivityViewModel postImageActivityViewModelModel;
    private IRootViewListener iRootViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        enableShowHomeAsUp();
        initViews();
        setBackButtonClickListener();
    }

    private void initViews() {
        postImageActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.post_image_activity, baseActivityBinding.appBarBase.contentLayout, true);
        postImageActivityViewModelModel = new PostImageActivityViewModel(this, postImageActivityBinding);
        postImageActivityBinding.setViewModel(postImageActivityViewModelModel);
        iRootViewListener = postImageActivityViewModelModel.getIRootViewListener();
        iRootViewListener.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iRootViewListener.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iRootViewListener.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        iRootViewListener.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iRootViewListener.onDestroy();
    }
}
