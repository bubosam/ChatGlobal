package com.example.boush.dreamchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import org.json.JSONArray;
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
            // Network access.
            new RegisterTask(new AsyncTaskCallback() {
                @Override
                public void onTaskCompleted(List result) {

                }

                @Override
                public void onTaskCompleted(Contact result) {

                }

                @Override
                public void onTaskCompleted(int result) {

                }

                @Override
                public void onTaskCompleted(String result) {
                    Log.d("Result", result);
                    if (result.equals("true")){
                        showMessage();
                    }
                    else{
                        showErrorMessage();
                    }

                }
            }).execute(usernameStr, emailStr, passwordStr);
        }
    }

    public class RegisterTask extends AsyncTask<String, Void, Void>{
        private AsyncTaskCallback listener;
        public RegisterTask(AsyncTaskCallback listener){
            this.listener=listener;
        }

        @Override
        protected Void doInBackground(String... params) {
            String usernameStr = params[0];
            String emailStr = params[1];
            String passwordStr = params[2];
            new Server().register(usernameStr, emailStr, passwordStr, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {

                }

                @Override
                public void onSuccess(JSONArray result) {

                }

                @Override
                public void onSuccess(String result) {
                    listener.onTaskCompleted(result);
                }

                @Override
                public void onSuccess(int result) {

                }
            });

            return null;
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
        alertDialog.setMessage("You have been registered! Now you can login.");
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

    private void showErrorMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
        alertDialog.setTitle("Registration");
        alertDialog.setMessage("Registration unsuccessful, please try again later.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
