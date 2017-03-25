package com.example.tungphan.wizelinecleanshortenchallenge.model;

/**
 * Created by tungphan on 3/24/17.
 */

public class PostImageEvent {

    private String imagePath;

    public PostImageEvent(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath(){
        return imagePath;
    }

}
