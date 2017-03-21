package com.example.tungphan.wizelinecleanshortenchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tungphan on 3/20/17.
 */

public class Sizes {
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("small")
    @Expose
    private Small small;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;
    @SerializedName("large")
    @Expose
    private Large large;

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Small getSmall() {
        return small;
    }

    public void setSmall(Small small) {
        this.small = small;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }
}
