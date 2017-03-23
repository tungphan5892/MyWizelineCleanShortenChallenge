package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.ImageViewActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.di.component.AppComponent;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IImageViewActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.ImageViewActivityViewModel;


/**
 * Created by tungphan on 3/23/17.
 */

public class ImageDetailActivity extends BaseActivity {
    private ImageViewActivityBinding imageViewActivityBinding;
    private ImageViewActivityViewModel imageViewActivityViewModel;
    private IImageViewActivityListener iImageViewActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        enableShowHomeAsUp();
        initViews();
        setBackgroundForToggleMenuButton();
        iImageViewActivityListener.onCreate(getIntent());
    }

    private void initViews() {
        imageViewActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.image_view_activity, baseActivityBinding.appBarBase.contentLayout, true);
        imageViewActivityViewModel = new ImageViewActivityViewModel(this, imageViewActivityBinding);
        imageViewActivityBinding.setViewModel(imageViewActivityViewModel);
        iImageViewActivityListener = imageViewActivityViewModel.getIImageViewActivityListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            iImageViewActivityListener.saveImage();
        } else if (id == R.id.action_post_image) {
            iImageViewActivityListener.postImage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iImageViewActivityListener.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iImageViewActivityListener.onResume();
    }
}