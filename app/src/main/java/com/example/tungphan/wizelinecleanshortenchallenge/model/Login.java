package com.example.tungphan.wizelinecleanshortenchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tungphan on 3/23/17.
 */

public class Login {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("emails")
    @Expose
    private List<Email> emails = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

}
