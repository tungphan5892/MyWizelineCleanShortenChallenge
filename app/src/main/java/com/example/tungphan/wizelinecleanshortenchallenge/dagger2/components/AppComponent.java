package com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components;

import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.modules.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.views.BaseActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.views.NewTweetActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.views.SearchActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.views.SingleTweetActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.views.TimelineActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tungphan on 3/12/17.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface AppComponent {

    void inject(TimelineActivity timelineActivity);
    void inject(SingleTweetActivity singleTweetActivity);
    void inject(NewTweetActivity newTweetActivity);
    void inject(SearchActivity searchActivity);

}
