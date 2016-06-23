package com.example.boush.dreamchat;

/**
 * Created by BousH on 23.6.2016.
 */
public class Conversation {
    private int myId;
    private int recieverId;
    private String message;
    private int conversationId;
    private String firstName;
    private String lastName;


    public int getMyId() {
        return myId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public String getMessage() {
        return message;
    }

    public int getConversationId() {
        return conversationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Conversation() {
    }

    public Conversation( int conversationId,int myId, int recieverId, String message,String firstName,String lastName) {
        this.myId = myId;
        this.recieverId = recieverId;
        this.message = message;
        this.conversationId = conversationId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
