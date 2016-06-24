package com.example.boush.dreamchat;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Monika on 05-Jun-16.
 */
public class ParseJSON {

    public List<Contact> getContacts(JSONArray array){
        List<Contact> contactList = new ArrayList<>();
        try {

            for (int i=0; i<array.length(); i++){
                JSONObject jo = array.getJSONObject(i);
                int userid=jo.getInt(Constants.KEY_USERID);
                String name = jo.getString(Constants.KEY_NAME);
                String surname = jo.getString(Constants.KEY_SURNAME);
                String nickname=jo.getString(Constants.KEY_NICKNAME);
                contactList.add(new Contact(userid, name, surname, nickname, true));
                Log.d("Contact", contactList.get(i).getTitle());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public List<Message> getMessages(JSONArray array, int myId){
        List<Message> list = new ArrayList<>();
        try {

            for (int i=0; i<array.length(); i++){
                JSONObject jo = array.getJSONObject(i);
                /*"messageID": 1,
                    "conversationID": 1,
                    "userID": 3,
                    "message": "jhsdbsjbj",
                    "date": "2016-06-23T19:19:53.000Z"*/
                int conversationid=jo.getInt(Constants.KEY_CONVERSATIONID);
                int userid=jo.getInt("userID");
                int messageid=jo.getInt(Constants.KEY_MESSAGEID);
                String message = jo.getString(Constants.KEY_MESSAGE);
                String date = jo.getString(Constants.KEY_DATE);
                boolean isMe;
                isMe = myId == userid;
                Message msg = new Message();
                msg.setDate(date);
                msg.setMe(isMe);
                msg.setMyId(myId);
                msg.setMessageText(message);

                list.add(msg);
                Log.d("Message", msg.getMessageText());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Conversation> getConversations(JSONArray array){
        List<Conversation> list = new ArrayList<>();
        try {

            for (int i=0; i<array.length(); i++){
                JSONObject jo = array.getJSONObject(i);
                Log.d("tag", "getConversations: "+jo);
                /*"conversationid": 1,
                    "user1": 5,
                    "user2": 3,
                    "message": "Toto je sprava",
                    "date": "2016-06-22T15:32:00.000Z",
                    "userid": 3,
                    "name": "Oskar",
                    "surname": "Chmeľ",
                    "nickname": "oskar"*/
                int conversationid=jo.getInt("conversationid");
                int userid=jo.getInt("userid1");
                //int messageid=jo.getInt(Constants.KEY_MESSAGEID);
                String message = jo.getString(Constants.KEY_MESSAGE);
                String date = jo.getString(Constants.KEY_DATE);
                String name = jo.getString("name2");
                String surname = jo.getString("surname2");
                int receiverid;
                if (jo.getInt("user1")==userid){
                    receiverid = jo.getInt("user2");
                }
                else{
                    receiverid = jo.getInt("user1");
                }

                list.add(new Conversation(conversationid, userid, receiverid, message, name, surname));
                //list.add(new Conversation(userid, receiverid, message, conversationid));
                Log.d("Conversation", list.get(i).getMessage());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Request> getRequests(JSONObject jsonObject){
        List<Request> reqs = new ArrayList<>();
        try {
            //jsonObject = new JSONObject(json);
            JSONArray requests = jsonObject.getJSONArray(Constants.KEY_RESULTS);

            for(int i = 0; i<requests.length(); i++){
                JSONObject jo = requests.getJSONObject(i);
                int reqid = jo.getInt(Constants.KEY_REQUESTID);
                int userid = jo.getInt(Constants.KEY_USERID);
                String name = jo.getString(Constants.KEY_NAME);
                String surname = jo.getString(Constants.KEY_SURNAME);
                String nickname = jo.getString(Constants.KEY_NICKNAME);
                reqs.add(new Request(reqid, userid, name, surname, nickname));
            }
            //String message = jsonObject.getString(Constants.KEY_MESSAGE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqs;
    }

    public Contact getInfo(JSONObject object){
        int userid = 0;
        String name = null;
        String surname = null;
        String nickname = null;
        String phone = null;
        try {

                userid=object.getInt(Constants.KEY_USERID);
                 name = object.getString(Constants.KEY_NAME);
                 surname = object.getString(Constants.KEY_SURNAME);
                 nickname=object.getString(Constants.KEY_NICKNAME);
                 phone =object.getString(Constants.KEY_CONTACT);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Contact(userid,name,surname,nickname,phone);
    }
}
