package com.example.tungphan.wizelinecleanshortenchallenge.network;


import com.example.tungphan.wizelinecleanshortenchallenge.model.ImagesInfo;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Login;
import com.example.tungphan.wizelinecleanshortenchallenge.model.PostImageResult;
import com.example.tungphan.wizelinecleanshortenchallenge.model.SearchTweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.Tweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by tung phan on 6/25/16.
 */
public interface NetworkService {

    @GET("/api/statuses/user_timeline")
    Observable<List<Tweet>> getUserTimelineJson();

    @GET("/api/user")
    Observable<User> getUserJson();

    @FormUrlEncoded
    @POST("/api/statuses/update")
    Observable<ResponseBody> postNewTweet(@Field("status") String status);

    @GET("/api/search/:query{query}")
    Observable<SearchTweet> searchTweet(@Path("query") String query);

    @GET("/image")
    Observable<ImagesInfo> getImagesInfo(@Header("Authorization") String eToken);

    @Multipart
    @POST("/login")
    Observable<Login> login(@Part("username") RequestBody userName);

    @Multipart
    @POST("/image")
//    Observable<ImagesInfo> postImage(@Header("Authorization") String eToken, @Part MultipartBody.Part image);
    Observable<PostImageResult> postImage(@Header("Authorization") String eToken, @Part("file\"; filename=\"pp.png\" ") RequestBody file);
}
