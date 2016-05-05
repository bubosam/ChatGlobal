package com.example.boush.dreamchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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
