package com.example.mobv.model;

public class User {

    private  String id;
    private  String username;
    private  String imageURL;
    private  Integer uid;

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public Integer getUid() {
        return uid;
    }

    public String getAccess() {
        return access;
    }

    public String getRefresh() {
        return refresh;
    }

    private  String access;
    private  String refresh;

    public User(String id, String username, String imageURL) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
