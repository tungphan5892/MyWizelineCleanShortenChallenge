package com.example.tungphan.wizelinecleanshortenchallenge.di.component;

import com.example.tungphan.wizelinecleanshortenchallenge.di.module.NetworkModule;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel.RootViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tung phan on 3/12/17.
 */
@Singleton
@Component(modules = {NetworkModule.class,})
public interface AppComponent {

    void inject(RootViewModel rootViewModel);

}
