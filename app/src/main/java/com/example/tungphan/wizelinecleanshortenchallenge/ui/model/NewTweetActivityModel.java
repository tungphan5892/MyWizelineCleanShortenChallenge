package com.example.tungphan.wizelinecleanshortenchallenge.ui.model;

/**
 * Created by tungphan on 3/9/17.
 */

public class NewTweetActivityModel {

    private int tweetCount;
    private String tweetCountText = "140";
    private boolean showKeyboard;

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCountText(String tweetCountText) {
        this.tweetCountText = tweetCountText;
    }

    public String getTweetCountText() {
        return tweetCountText;
    }

    public void setShowKeyboard(boolean showKeyboard) {
        this.showKeyboard = showKeyboard;
    }

    public boolean getShowKeyboard() {
        return showKeyboard;
    }
}
