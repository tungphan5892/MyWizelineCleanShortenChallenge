package com.example.tungphan.wizelinecleanshortenchallenge.network;


import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.datamodels.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkService {

    @GET("/api/statuses/user_timeline")
    Observable<List<Tweet>> getUserTimelineJson();

    @GET("/api/user")
    Observable<User> getUserJson();

    @FormUrlEncoded
    @POST("/api/statuses/update")
    Observable<ResponseBody> postNewTweet(@Field("status") String status);

}
