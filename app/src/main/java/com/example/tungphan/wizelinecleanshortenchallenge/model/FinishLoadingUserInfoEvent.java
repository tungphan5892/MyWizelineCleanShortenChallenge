package com.example.tungphan.wizelinecleanshortenchallenge.model;

/**
 * Created by tungphan on 3/10/17.
 */

public class FinishLoadingUserInfoEvent {

    private String userProfileImageUrl;
    private int resultCode;

    public FinishLoadingUserInfoEvent(int resultCode, String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
        this.resultCode = resultCode;
    }

    public String getUserProfileImageUrl(){
        return userProfileImageUrl;
    }

    public int getResultCode(){
        return resultCode;
    }
}
