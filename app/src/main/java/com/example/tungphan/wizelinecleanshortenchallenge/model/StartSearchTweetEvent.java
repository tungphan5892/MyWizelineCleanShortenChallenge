package com.example.tungphan.wizelinecleanshortenchallenge.model;

/**
 * Created by tungphan on 3/17/17.
 */

public class StartSearchTweetEvent {

    private String searchQuery;

    public StartSearchTweetEvent(String searchQuery){
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery(){
        return searchQuery;
    }
}
