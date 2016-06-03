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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Client on 20.5.2016.
 */
public class Server {


    private boolean sendReqSucc=false;

    public void setSendReq(boolean sendReqSucc) {
        this.sendReqSucc = sendReqSucc;
    }

    private boolean regSuccess=false;

    public void setReg(boolean s){
        regSuccess=s;
    }

    private boolean logoutSuccess=false;

    public void setLogout(boolean logout) {
        this.logoutSuccess = logout;
    }

    private boolean cancelReq=false;

    public void setCancelReq(boolean cancelReq) {
        this.cancelReq = cancelReq;
    }

    private boolean acceptReq=false;

    public void setAcceptReq(boolean acceptReq) {
        this.acceptReq = acceptReq;
    }

    public void login(String email, String password, final Context context){


        Map<String, String> postParam = new HashMap<String, String>();
            postParam.put(Constants.KEY_EMAIL, email);
            postParam.put(Constants.KEY_PASSWORD, password);
            Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Constants.loginUrl, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Volley ", response.toString());
                            Log.d("Response status code", String.valueOf(response));
                            String token = null;
                            int userid;
                            try {
                                token = response.getString(Constants.KEY_TOKEN);
                                userid = response.getInt(Constants.KEY_USERID);
                                Log.d("Volley token", token);
                                Log.d("Volley userid", String.valueOf(userid));
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove(Constants.KEY_TOKEN);
                                editor.remove(Constants.KEY_USERID);
                                editor.apply();
                                editor.putInt(Constants.KEY_USERID, userid);
                                editor.putString(Constants.KEY_TOKEN, token);
                                editor.apply();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {



                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Volley ", "Error: " + error.getMessage());
                    // Handle the error
                    Log.d("Error status code", String.valueOf(error.networkResponse.statusCode));
                    //error.networkResponse.data;

                }
            })

            {
                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);

        /*HttpURLConnection connection = (HttpURLConnection) new URL(loginUrl).openConnection();
                String encoded = Base64.encodeToString((email + ":" + password).getBytes("UTF-8"), Base64.NO_WRAP);
                connection.setRequestProperty("Authorization", "Basic " + encoded);
                connection.setRequestMethod("POST");
                connection.setInstanceFollowRedirects(true);
                connection.connect();
                int resCode = connection.getResponseCode();
                Log.d("Response kod: ", String.valueOf(resCode));*/


    }

    public boolean register(String nickname, String email, String password, Context context ){

            Map<String, String> postParam = new HashMap<String, String>();
            postParam.put(Constants.KEY_EMAIL, email);
            postParam.put(Constants.KEY_PASSWORD, password);
            postParam.put(Constants.KEY_NICKNAME, nickname);
            Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

            final Context appcontext = context;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    Constants.registerUrl, new JSONObject(postParam),
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
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);
       return this.regSuccess;
    }

    /*public void update(String username, String email, String password, String phone, String firstName, String lastName, Context context){
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
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }*/

  /*  public boolean updatePassword(String password, Context context){
        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("password", password);

        Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

        final Context appContext = context;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.updateUrl, new JSONObject(postParam),
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);
    }
    */


    public boolean logout(final Context context){

            Map<String, String> postParam = new HashMap<String, String>();
            Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                    Constants.loginUrl, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Volley ", response.toString());
                            String success = null;

                            try {
                                success = response.getString("success");
                                Log.d("Volley Reg Success", success);
                                if (success.equals("true")){
                                    setLogout(true);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {



                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d("Volley ", "Error: " + error.getMessage());
                    Log.d("Error kod", error.getMessage()+"");
                }
            })

            {
                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = prefs.getString(Constants.KEY_TOKEN, null);
                    int userid = prefs.getInt(Constants.KEY_USERID, 0);
                    headers.put(Constants.KEY_USERID, String.valueOf(userid));
                    headers.put(Constants.KEY_TOKEN, token);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);

        return logoutSuccess;
    }

    public boolean sendRequest(final Context context, int id){
        Map<String, Integer> postParam = new HashMap<String, Integer>();
        postParam.put(Constants.KEY_RECEIVER, id);

        Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.requestUrl, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley ", response.toString());
                        String success = null;

                        try {
                            success = response.getString(Constants.KEY_MESSAGE);
                            Log.d("Volley Reg Success", success);
                            if (success.equals("request sent successfully")){
                                setSendReq(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("Volley ", "Error: " + error.getMessage());
                Log.d("Error kod", error.getMessage()+"");
            }
        })

        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String token = prefs.getString(Constants.KEY_TOKEN, null);
                int userid = prefs.getInt(Constants.KEY_USERID, 0);
                headers.put(Constants.KEY_USERID, String.valueOf(userid));
                headers.put(Constants.KEY_TOKEN, token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);

        return sendReqSucc;
    }

    public boolean cancelReq(int requestid, final Context context){
        Map<String, Integer> postParam = new HashMap<String, Integer>();
        postParam.put(Constants.KEY_REQUESTID, requestid);

        Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                Constants.requestUrl, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley ", response.toString());
                        String success = null;

                        try {
                            success = response.getString(Constants.KEY_MESSAGE);
                            Log.d("Volley Reg Success", success);
                            if (success.equals("request canceled successfully")){
                                setCancelReq(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("Volley ", "Error: " + error.getMessage());
                Log.d("Error kod", error.getMessage()+"");
            }
        })

        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String token = prefs.getString(Constants.KEY_TOKEN, null);
                int userid = prefs.getInt(Constants.KEY_USERID, 0);
                headers.put(Constants.KEY_USERID, String.valueOf(userid));
                headers.put(Constants.KEY_TOKEN, token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);

        return cancelReq;
    }

    public boolean acceptReq(int requestid, final Context context){
        Map<String, Integer> postParam = new HashMap<String, Integer>();
        postParam.put(Constants.KEY_REQUESTID, requestid);

        Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.reqAcceptUrl, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley ", response.toString());
                        String success = null;

                        try {
                            success = response.getString(Constants.KEY_MESSAGE);
                            Log.d("Volley Reg Success", success);
                            if (success.equals("request acception successful")){
                                setAcceptReq(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("Volley ", "Error: " + error.getMessage());
                Log.d("Error kod", error.getMessage()+"");
            }
        })

        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                String token = prefs.getString(Constants.KEY_TOKEN, null);
                int userid = prefs.getInt(Constants.KEY_USERID, 0);
                headers.put(Constants.KEY_USERID, String.valueOf(userid));
                headers.put(Constants.KEY_TOKEN, token);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);

        return acceptReq;
    }

    public void loadReqs(final Context context){
        Map<String, String> postParam = new HashMap<String, String>();
        Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.reqAcceptUrl, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley ", response.toString());
                        ParseRequests reqs = new ParseRequests(response.toString());
                        reqs.parseJSON();
                        Log.d("Volley message", ParseRequests.message);
                        Log.d("Volley array[0]", String.valueOf(ParseRequests.reqids[0])+" "+String.valueOf(ParseRequests.userids[0])
                        +" "+ ParseRequests.names[0]+" "+ ParseRequests.surnames[0]+" "+ ParseRequests.nicknames[0]);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley ", "Error: " + error.getMessage());
                // Handle the error
                Log.d("Error status code", String.valueOf(error.networkResponse.statusCode));
                //error.networkResponse.data;

            }
        })

        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, Constants.tag_json_obj);
    }

}
