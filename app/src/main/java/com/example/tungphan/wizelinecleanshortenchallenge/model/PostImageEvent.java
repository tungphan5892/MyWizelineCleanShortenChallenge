package com.example.tungphan.wizelinecleanshortenchallenge.model;

/**
 * Created by tungphan on 3/24/17.
 */

public class PostImageEvent {

    private String imagePath;
    private String eToken;

    public PostImageEvent(String eToken, String imagePath) {
        this.eToken = eToken;
        this.imagePath = imagePath;
    }

    public String getImagePath(){
        return imagePath;
    }

    public String getEToken(){
        return eToken;
    }

}
