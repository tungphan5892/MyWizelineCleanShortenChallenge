package com.example.tungphan.wizelinecleanshortenchallenge.ui.model;

/**
 * Created by tungphan on 3/17/17.
 */

public class SearchActivityModel {

    private boolean visibleEmptyBackground = true;
    private boolean visibleProgressBar = false;

    public void setVisibleEmptyBackground(boolean visibleEmptyBackground) {
        this.visibleEmptyBackground = visibleEmptyBackground;
    }

    public boolean isVisibleEmptyBackground() {
        return visibleEmptyBackground;
    }

    public void setVisibleProgressBar(boolean visibleProgressBar) {
        this.visibleProgressBar = visibleProgressBar;
    }

    public boolean isVisibleProgressBar() {
        return visibleProgressBar;
    }
}
