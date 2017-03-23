package com.example.tungphan.wizelinecleanshortenchallenge.network;

import android.util.ArrayMap;

import com.example.tungphan.wizelinecleanshortenchallenge.model.Datum;
import com.example.tungphan.wizelinecleanshortenchallenge.model.ImagesInfo;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Login;
import com.example.tungphan.wizelinecleanshortenchallenge.model.SearchTweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.User;
import com.example.tungphan.wizelinecleanshortenchallenge.model.UserInfo;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tung phan on 03/17/2017.
 */
public class Service {
    private final String USER_NAME = "wizeline";
    private final String USER_NAME_KEY = "username";
    private final String HASH_TOKEN = "bearer wize@123456";
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

    public Observable<ImagesInfo> getImagesFromService() {
        return networkService.getImagesInfo(HASH_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }

    public Observable<Login> login() {
        String username = "wizeline";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), username);
        return networkService.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }
}
