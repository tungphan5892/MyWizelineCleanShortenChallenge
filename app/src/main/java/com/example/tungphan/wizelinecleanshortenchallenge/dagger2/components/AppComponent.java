package com.example.tungphan.wizelinecleanshortenchallenge.dagger2.components;

import com.example.tungphan.wizelinecleanshortenchallenge.dagger2.modules.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.viewmodels.TimelineActivityViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tungphan on 3/12/17.
 */
@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {

    void inject(TimelineActivityViewModel timelineActivityViewModel);

}
