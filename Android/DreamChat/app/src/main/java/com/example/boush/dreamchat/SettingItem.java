package com.example.boush.dreamchat;

/**
 * Created by BousH on 23.5.2016.
 */
public class SettingItem {
    private String title;
    private int photoId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhotoId() {
        return photoId;
    }

    public SettingItem(String title,int photoId) {
        this.title = title;
        this.photoId=photoId;

    }
}
