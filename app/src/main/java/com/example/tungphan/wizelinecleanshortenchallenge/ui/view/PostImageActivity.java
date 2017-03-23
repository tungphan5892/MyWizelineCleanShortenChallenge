package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.PostImageActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IPostImageActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.PostImageActivityViewModel;

/**
 * Created by tungphan on 3/23/17.
 */

public class PostImageActivity extends BaseActivity {
    private PostImageActivityBinding postImageActivityBinding;
    private PostImageActivityViewModel postImageActivityViewModel;
    private IPostImageActivityListener iPostImageActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        disableShowHomAsUp();
        initViews();
        setBackgroundForToggleMenuButton();
    }

    private void initViews(){
        postImageActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.post_image_activity, baseActivityBinding.appBarBase.contentLayout, true);
        postImageActivityViewModel = new PostImageActivityViewModel(this,postImageActivityBinding);
        postImageActivityBinding.setViewModel(postImageActivityViewModel);
        iPostImageActivityListener = postImageActivityViewModel.getIPostImageActivityListener();
        iPostImageActivityListener.onCreate();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();
        iPostImageActivityListener.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iPostImageActivityListener.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        iPostImageActivityListener.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPostImageActivityListener.onDestroy();
    }
}
