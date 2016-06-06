package com.example.boush.dreamchat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 05-Jun-16.
 */
public class ParseJSON {

    public List<Contact> getContacts(JSONArray array){
    //public List<Contact> getContacts(JSONObject ob){
        JSONArray arr = array;
        /*try {
            arr = ob.getJSONArray("");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        List<Contact> contactList = new ArrayList<>();
        try {

            for (int i=0; i<arr.length(); i++){
                JSONObject jo = arr.getJSONObject(i);
                int userid=jo.getInt(Constants.KEY_USERID);
                String name = jo.getString(Constants.KEY_NAME);
                String surname = jo.getString(Constants.KEY_SURNAME);
                String nickname=jo.getString(Constants.KEY_NICKNAME);
                contactList.add(new Contact(userid, name, surname, nickname));
                Log.d("Contact", contactList.get(i).getTitle());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contactList;
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
}
