package com.example.tungphan.wizelinecleanshortenchallenge.ui.model;

/**
 * Created by tungphan on 3/9/17.
 */

public class SingleTweetActivityModel {

    private String singleTweetUserName;
    private String profilePictureUrl;
    private String userDescription;
    private String singleTweetContent;



    public String getSingleTweetUserName() {
        return singleTweetUserName;
    }

    public void setSingleTweetUserName(String userName) {
        this.singleTweetUserName = userName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getSingleTweetContent() {
        return singleTweetContent;
    }

    public void setSingleTweetContent(String tweetContent) {
        this.singleTweetContent = tweetContent;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }
}
