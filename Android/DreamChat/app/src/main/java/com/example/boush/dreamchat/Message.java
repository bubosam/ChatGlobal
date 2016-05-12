package com.example.boush.dreamchat;

import android.graphics.drawable.Drawable;

/**
 * Created by Okay-PC on 11.5.2016.
 */
public class Message {
    private int avatarId;
    private String messageText;
    private String name;

    public Message(int avatarId, String name, String messageText){
        this.avatarId = avatarId;
        this.name = name;
        this.messageText = messageText;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getAvatarId(){
        return avatarId;
    }
    public void setAvatarId(int avatarID){
        this.avatarId=avatarID;
    }
}
