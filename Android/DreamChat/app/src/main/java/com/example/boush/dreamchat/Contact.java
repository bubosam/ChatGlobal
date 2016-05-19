package com.example.boush.dreamchat;

import android.media.Image;

/**
 * Created by Client on 12.5.2016.
 */
public class Contact {
    private String firstName;
    private String lastName;
    private String nickname;
    private boolean isFriend;
    private int userid;
    private String email;
    private String phone;
    //private profilePic;

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getUserid() {
        return userid;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public Contact(String firstName, String lastName, String nickname, boolean friend) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.isFriend = friend;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return firstName + " " + lastName;
    }

    public String getNickname() {
        return nickname;
    }

}
