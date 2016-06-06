package com.example.boush.dreamchat;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Client on 6.6.2016.
 */

public interface VolleyCallback{
    void onSuccess(JSONObject result);
    void onSuccess(JSONArray result);
    void onSuccess(String result);
    void onSuccess(int result);
}