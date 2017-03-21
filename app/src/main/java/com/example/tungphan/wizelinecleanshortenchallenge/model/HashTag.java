package com.example.tungphan.wizelinecleanshortenchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tungphan on 3/20/17.
 */

public class HashTag {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("indices")
    @Expose
    private List<Integer> indices = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }
}
