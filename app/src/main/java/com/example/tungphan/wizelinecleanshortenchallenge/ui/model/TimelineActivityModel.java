package com.example.tungphan.wizelinecleanshortenchallenge.ui.model;

/**
 * Created by tungphan on 3/17/17.
 */

public class TimelineActivityModel {
    private boolean visibleEmptyBackground = true;
    private boolean visibleProgressBar = false;

    public boolean isVisibleEmptyBackground() {
        return visibleEmptyBackground;
    }

    public void setVisibleEmptyBackground(boolean visible) {
        visibleEmptyBackground = visible;
    }

    public void setVisibleProgressBar(boolean visibleProgressBar) {
        this.visibleProgressBar = visibleProgressBar;
    }

    public boolean isVisibleProgressBar() {
        return visibleProgressBar;
    }
}
