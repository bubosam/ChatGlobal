package com.example.boush.dreamchat;

/**
 * Created by BousH on 24.5.2016.
 */
public class HelpItem {
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

    public HelpItem(String title,int photoId) {
        this.title = title;
        this.photoId=photoId;

    }
}
