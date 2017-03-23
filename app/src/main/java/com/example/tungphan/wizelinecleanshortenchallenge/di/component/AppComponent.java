package com.example.tungphan.wizelinecleanshortenchallenge.di.component;

import com.example.tungphan.wizelinecleanshortenchallenge.di.module.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.LoadImageActivityViewModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.MyNavViewHeaderViewModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.NewTweetActivityViewModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.PostImageActivityViewModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.SearchActivityViewModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.TimelineActivityViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tungphan on 3/12/17.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface AppComponent {

    void inject(NewTweetActivityViewModel newTweetActivityViewModel);
    void inject(SearchActivityViewModel searchActivityViewModel);
    void inject(LoadImageActivityViewModel loadImageActivityViewModel);
    void inject(TimelineActivityViewModel timelineActivityViewModel);
    void inject(MyNavViewHeaderViewModel myNavViewHeaderViewModel);
    void inject(PostImageActivityViewModel postImageActivityViewModel);

}
