package com.example.tungphan.wizelinecleanshortenchallenge.network;

import com.example.tungphan.wizelinecleanshortenchallenge.model.SearchTweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.User;

import java.util.List;


import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tung phan on 03/17/2017.
 */
public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Observable<User> getUserFromService() {
        return networkService.getUserJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }

    public Observable<List<Tweet>> getUserTimelineFromService() {
        return networkService.getUserTimelineJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }

    public Observable<ResponseBody> postNewTweet(String status) {

        return networkService.postNewTweet(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }


    public Observable<SearchTweet> searchTweet(String query) {
        return networkService.searchTweet(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }
}
