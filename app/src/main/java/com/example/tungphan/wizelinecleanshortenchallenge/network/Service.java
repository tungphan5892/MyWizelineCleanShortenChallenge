package com.example.tungphan.wizelinecleanshortenchallenge.network;

import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.User;

import java.util.List;

import javax.inject.Singleton;

import dagger.Provides;
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

    public Subscription getUserFromService(final GetUserCallback callback) {

        return networkService.getUserJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends User>>() {
                    @Override
                    public Observable<? extends User> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(User user) {
                        callback.onSuccess(user);

                    }
                });
    }

    public interface GetUserCallback {
        void onSuccess(User user);

        void onError(NetworkError networkError);
    }

    public Subscription getUserTimelineFromService(final GetTimelineCallback callback) {

        return networkService.getUserTimelineJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Tweet>>>() {
                    @Override
                    public Observable<? extends List<Tweet>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<Tweet>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<Tweet> tweetList) {
                        callback.onSuccess(tweetList);
                    }
                });
    }

    public interface GetTimelineCallback {
        void onSuccess(List<Tweet> tweetList);

        void onError(NetworkError networkError);
    }

    public Subscription postNewTweet(String status, final PostNewTweetCallback callback) {

        return networkService.postNewTweet(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseBody>>() {
                    @Override
                    public Observable<? extends ResponseBody> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        callback.onSuccess(responseBody);
                    }
                });
    }

    public interface PostNewTweetCallback {
        void onSuccess(ResponseBody responseBody);

        void onError(NetworkError networkError);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            NetworkService networkService) {
        return new Service(networkService);
    }
}
