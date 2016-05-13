package com.example.boush.dreamchat;

/**
 * Created by Client on 12.5.2016.
 */
public class Friend {
    private String firstName;
    private String lastName;
    private String group;
    private String date;

    public Friend(String firstName, String lastName, String group, String date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.date = date;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitle() {
        return firstName +" "+ lastName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
