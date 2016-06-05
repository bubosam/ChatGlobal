package com.example.boush.dreamchat;

/**
 * Created by Monika on 05-Jun-16.
 */
public class Request {
    private int requestid;
    private int userid;
    private String name;
    private String surname;
    private String nickname;

    private int receiver;

    public Request(int requestid, int userid, String name, String surname, String nickname) {
        this.requestid = requestid;
        this.userid = userid;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
    }

    public int getRequestid() {
        return requestid;
    }

    public int getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }

    public int getReceiver() {
        return receiver;
    }
}
