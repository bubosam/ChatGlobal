package com.example.boush.dreamchat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Okay-PC on 11.5.2016.
 */
public class Message {
    private int avatarId;
    private boolean isMe;
    private String messageText;
    private String firstName;
    private String lastName;
    private String date;
    private boolean isStatusMessage;

    public Message(int avatarId, String firstName, String lastName, String messageText){
        this.avatarId = avatarId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.messageText = messageText;
    }

    public Message(){}

    public boolean isStatusMessage() { return isStatusMessage; }

    public void setStatusMessage(boolean statusMessage) { isStatusMessage = statusMessage; }

    public boolean isMe() { return isMe; }
    public void setMe(boolean me) { isMe = me; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getAvatarId(){
        return avatarId;
    }
    public void setAvatarId(int avatarID){ this.avatarId=avatarID; }

    public String getDate(){ return date; }
    public void setDate(String date){ this.date = date; }
}
