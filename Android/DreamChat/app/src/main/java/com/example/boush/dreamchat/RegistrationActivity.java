package com.example.boush.dreamchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class RegistrationActivity extends AppCompatActivity {
    private EditText username;
    private AutoCompleteTextView email;
    private EditText password;
    private EditText confirmPassword;
    private Button register;
    private String emailTxt;
    private String passwordTxt;
    List<NameValuePair> params;

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

        if(!isValidUsername(usernameStr)){
            username.setError("Invalid username. Username must contains 3 - 16 characters.");
            focusView = username;
            cancel = true;
        }

        if(!isValidEmail(emailStr)){
            email.setError("Invalid email.");
            focusView = email;
            cancel = true;
        }

        if(!isValidPassword(passwordStr)){
            password.setError("Invalid password. Password must contains 8 - 16 characters.");
            focusView = password;
            cancel = true;
        }

        if(!confirmPassStr.equals(passwordStr)){
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
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",usernameStr));
            params.add(new BasicNameValuePair("email", emailStr));
            params.add(new BasicNameValuePair("password", passwordStr));
            ServerRequest sr = new ServerRequest();
            JSONObject json = sr.getJSON("http://10.0.2.2:1337/register", params);
            //JSONObject json = sr.getJSON("http://192.168.56.1:8080/register",params);
            if (json != null) {
                try {
                    String jsonstr = json.getString("response");

                    Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();

                    Log.d("Hello", jsonstr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new AlertDialog.Builder(this)
                        .setTitle("Registration")
                        .setMessage("You have been successfully registered, now you will be logged in.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RegistrationActivity.this, ChatActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else if (json == null){
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("There was problem with registration, check your internet connection and try it again.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(RegistrationActivity.this, ChatActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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


}
