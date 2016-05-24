package com.example.boush.dreamchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Client on 20.5.2016.
 */
public class Server {
    private static final String loginUrl = "http://10.0.2.2:1337/login";
    private static final String registerUrl = "http://10.0.2.2:1337/register";
    private static final String updateUrl = "http://10.0.2.2:1337/update";
    private static final String logoutUrl = "http://10.0.2.2:1337/logout";

    private static final String tag_json_obj = "json_obj_req";

    private int userid;
    private String token;


    public void login(String email, String password, final Context context){

        Map<String, String> postParam = new HashMap<String, String>();
            postParam.put("email", email);
            postParam.put("password", password);
            Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    loginUrl, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Volley ", response.toString());
                            //String token = null;
                            //int userid;
                            try {
                                token = response.getString("token");
                                userid = response.getInt("userid");
                                Log.d("Volley token", token);
                                Log.d("Volley userid", String.valueOf(userid));
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.apply();
                                editor.putInt("userid", userid);
                                editor.putString("token", token);
                                editor.apply();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Volley ", "Error: " + error.getMessage());
                }
            })

            {
                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);

        /*HttpURLConnection connection = (HttpURLConnection) new URL(loginUrl).openConnection();
                String encoded = Base64.encodeToString((email + ":" + password).getBytes("UTF-8"), Base64.NO_WRAP);
                connection.setRequestProperty("Authorization", "Basic " + encoded);
                connection.setRequestMethod("POST");
                connection.setInstanceFollowRedirects(true);
                connection.connect();
                int resCode = connection.getResponseCode();
                Log.d("Response kod: ", String.valueOf(resCode));*/


    }

    private boolean regSuccess=false;

    public void setReg(boolean s){
        regSuccess=s;
    }

    public boolean register(String nickname, String email, String password, Context context ){

        boolean regSuccess;

            Map<String, String> postParam = new HashMap<String, String>();
            postParam.put("email", email);
            postParam.put("password", password);
            postParam.put("nickname", nickname);
            Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

            final Context appcontext = context;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    registerUrl, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Volley ", response.toString());

                            String success = null;

                            try {
                                success = response.getString("success");
                                Log.d("Volley Reg Success", success);
                                if (success.equals("true")){
                                    setReg(true);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //VolleyLog.d("Volley ", "Error: " + error.getMessage())
                            Log.d("Error kod", error.getMessage()+"");
                        }
            }) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
       return this.regSuccess;
    }

    public void update(String username, String email, String password, String phone, String firstName, String lastName, Context context){
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("username", username);
        postParam.put("email", email);
        postParam.put("password", password);
        postParam.put("phone", phone);
        postParam.put("firstName", firstName);
        postParam.put("lastName", lastName);

        Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

        final Context appContext = context;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                updateUrl, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley ", response.toString());
                        String token = null;
                        int userid;
                        try {
                            token = response.getString("token");
                            userid = response.getInt("userid");
                            Log.d("Volley token", token);
                            Log.d("Volley userid", String.valueOf(userid));
                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(appContext);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.clear();
                            editor.apply();
                            editor.putInt("userid", userid);
                            editor.putString("token", token);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley ", "Error: " + error.getMessage());
            }
        })

        {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}
