package com.example.boush.dreamchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class RegistrationActivity extends AppCompatActivity {
    private EditText username;
    private AutoCompleteTextView email;
    private EditText password;
    private EditText confirmPassword;
    private Button register;


    private static final String registerUrl = "http://10.0.2.2:1337/register";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = (EditText) findViewById(R.id.username);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        register = (Button) findViewById(R.id.b_register_confirm);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {

        email.setError(null);
        username.setError(null);
        password.setError(null);

        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String confirmPassStr = confirmPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!isValidUsername(usernameStr)) {
            username.setError("Invalid username. Username must contains 3 - 16 characters.");
            focusView = username;
            cancel = true;
        }

        if (!isValidEmail(emailStr)) {
            email.setError("Invalid email.");
            focusView = email;
            cancel = true;
        }

        if (!isValidPassword(passwordStr)) {
            password.setError("Invalid password. Password must contains 8 - 16 characters.");
            focusView = password;
            cancel = true;
        }

        if (!confirmPassStr.equals(passwordStr)) {
            confirmPassword.setError("Passwords are not equal.");
            password.setError("Passwords are not equal.");
            focusView = confirmPassword;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            showMessage();
            try {
                // Network access.
                new Server().register(usernameStr, emailStr, passwordStr, getApplicationContext());
                showMessage();

                /*Map<String, String> postParam = new HashMap<String, String>();
                postParam.put("username", usernameStr);
                postParam.put("email", emailStr);
                postParam.put("password", passwordStr);
                Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        registerUrl, new JSONObject(postParam),
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
                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);*/


            } catch (Exception e) {
                VolleyLog.d("Volley ", "Error: "+e.toString());
            }


        }
    }

    private boolean isValidUsername(String username){
        String regex = "[\\w\\d]{3,16}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(username).matches();
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        String regex = "[\\w\\d]{8,16}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(password).matches();
    }

    private void showMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
        alertDialog.setTitle("Registration");
        alertDialog.setMessage("You have been registered ! , now you can login ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialog.show();

    }

}
