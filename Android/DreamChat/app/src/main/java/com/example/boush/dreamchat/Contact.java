package com.example.boush.dreamchat;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Client on 12.5.2016.
 */
public class Contact implements Parcelable {
    private String firstName;
    private String lastName;
    private String nickname;
    private boolean isFriend;
    private int userid;
    private String email;
    private String phone;
    //private String profilePic;

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

    public Contact(int userid, String firstName, String lastName, String nickname) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
    }

    public Contact(int userid, String firstName, String lastName, String nickname, boolean friend, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.isFriend = friend;
        this.email = email;
        this.phone = phone;
        this.userid = userid;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(nickname);
        dest.writeByte((byte) (isFriend ? 0x01 : 0x00));
        dest.writeInt(userid);
        dest.writeString(email);
        dest.writeString(phone);
    }

    protected Contact(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        nickname = in.readString();
        isFriend = in.readByte() != 0x00;
        userid = in.readInt();
        email = in.readString();
        phone = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
