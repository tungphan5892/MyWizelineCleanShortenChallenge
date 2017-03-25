package com.example.tungphan.wizelinecleanshortenchallenge.ui.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.ImageViewActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IImageDetailActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.ImageDetailActivityViewModel;


/**
 * Created by tungphan on 3/23/17.
 */

public class ImageDetailActivity extends BaseActivity {
    private ImageViewActivityBinding imageViewActivityBinding;
    private ImageDetailActivityViewModel imageViewActivityViewModel;
    private IImageDetailActivityListener iImageDetailActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableShowNavDrawer();
        enableShowHomeAsUp();
        initViews();
        setBackButtonClickListener();
        iImageDetailActivityListener.onCreate(getIntent());
    }

    private void initViews() {
        imageViewActivityBinding = DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.image_view_activity, baseActivityBinding.appBarBase.contentLayout, true);
        imageViewActivityViewModel = new ImageDetailActivityViewModel(this, imageViewActivityBinding);
        imageViewActivityBinding.setViewModel(imageViewActivityViewModel);
        iImageDetailActivityListener = imageViewActivityViewModel.getIImageViewActivityListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            iImageDetailActivityListener.saveImage();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iImageDetailActivityListener.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iImageDetailActivityListener.onResume();
    }
}
