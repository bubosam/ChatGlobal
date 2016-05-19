package com.example.boush.dreamchat;

/**
 * Created by Client on 12.5.2016.
 */
public class Contact {
    private String firstName;
    private String lastName;
    private String nickname;
    private boolean isFriend;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return firstName +" "+ lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
