package com.example.tungphan.wizelinecleanshortenchallenge.di.module;

import com.example.tungphan.wizelinecleanshortenchallenge.eventbus.RxEventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tungphan on 3/22/17.
 */
@Module
public class EventBusModule {

    @Provides
    @Singleton
    public RxEventBus getEventBus() {
        return new RxEventBus();
    }

}
