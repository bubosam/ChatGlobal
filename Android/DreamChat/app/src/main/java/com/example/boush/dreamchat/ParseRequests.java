package com.example.boush.dreamchat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Client on 26.5.2016.
 */
public class ParseRequests {
    public static int[] reqids;
    public static int[] userids;
    public static String[] names;
    public static String[] surnames;
    public static String[] nicknames;
    public static String message;

    private JSONArray requests = null;

    private String json;

    public ParseRequests(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            requests = jsonObject.getJSONArray(Constants.KEY_RESULTS);

            reqids = new int[requests.length()];
            userids = new int[requests.length()];
            names = new String[requests.length()];
            surnames = new String[requests.length()];
            nicknames = new String[requests.length()];

            for(int i = 0; i< requests.length(); i++){
                JSONObject jo = requests.getJSONObject(i);
                reqids[i] = jo.getInt(Constants.KEY_REQUESTID);
                userids[i]=jo.getInt(Constants.KEY_USERID);
                names[i] = jo.getString(Constants.KEY_NAME);
                surnames[i] = jo.getString(Constants.KEY_SURNAME);
                nicknames[i]=jo.getString(Constants.KEY_NICKNAME);
            }

            message=jsonObject.getString(Constants.KEY_MESSAGE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
