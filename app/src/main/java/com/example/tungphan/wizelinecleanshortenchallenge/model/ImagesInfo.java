package com.example.tungphan.wizelinecleanshortenchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tungphan on 3/23/17.
 */

public class ImagesInfo {
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
