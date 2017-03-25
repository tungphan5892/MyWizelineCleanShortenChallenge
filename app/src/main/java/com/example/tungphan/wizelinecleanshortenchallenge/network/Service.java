package com.example.tungphan.wizelinecleanshortenchallenge.network;

import com.example.tungphan.wizelinecleanshortenchallenge.model.ImagesInfo;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Login;
import com.example.tungphan.wizelinecleanshortenchallenge.model.SearchTweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.User;

import java.io.File;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tung phan on 03/17/2017.
 */
public class Service {
    private final String USER_NAME = "wizeline";
    private final String USER_NAME_KEY = "username";
    private final String PRE_TOKEN = "bearer ";
    //    private final String HASH_TOKEN = "bearer wize@123456";
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

    public Observable<ImagesInfo> getImagesFromService(String eToken) {
        return networkService.getImagesInfo(PRE_TOKEN + eToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }

    public Observable<Login> login() {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), USER_NAME);
        return networkService.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }

    public Observable<ImagesInfo> postImageToServer(String eToken, String filePath) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        return networkService.postImage(PRE_TOKEN + eToken, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> Observable.error(throwable));
    }
}
