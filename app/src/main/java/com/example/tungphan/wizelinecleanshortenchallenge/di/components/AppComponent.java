package com.example.tungphan.wizelinecleanshortenchallenge.di.components;

import com.example.tungphan.wizelinecleanshortenchallenge.di.modules.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.NewTweetActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.SearchActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.SingleTweetActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.TimelineActivity;

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
