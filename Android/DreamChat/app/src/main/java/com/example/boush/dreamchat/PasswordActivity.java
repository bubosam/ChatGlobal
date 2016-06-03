package com.example.boush.dreamchat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity {
    private EditText oldPass;
    private EditText newPass;
    private EditText passRetype;
    private Button submitUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        oldPass = (EditText) findViewById(R.id.old_passwordUpadte);
        newPass = (EditText) findViewById(R.id.new_Password_Update);
        passRetype = (EditText) findViewById(R.id.new_Retype_Password);
        submitUpdate = (Button) findViewById(R.id.submitUpdate);

        submitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptUpdate();
            }
        });
    }

    private void attemptUpdate() {
        oldPass.setError(null);
        newPass.setError(null);
        passRetype.setError(null);


        String oldPassword = oldPass.getText().toString();
        String newPassword = newPass.getText().toString();
        String repeatPassword = passRetype.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!isValidPassword(oldPassword)) {
            oldPass.setError("Invalid password. Password must contains 8 - 16 characters.");
            focusView = oldPass;
            cancel = true;
        }

        if (!isValidPassword(newPassword)) {
            newPass.setError("Invalid password. Password must contains 8 - 16 characters.");
            focusView = newPass;
            cancel = true;
        }


        if (!repeatPassword.equals(newPassword)) {
            passRetype.setError("Passwords are not equal.");
            newPass.setError("Passwords are not equal.");
            focusView = passRetype;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            showMessage();
            // Network access.

           /* if (new Server().updatePassword(repeatPassword, getApplicationContext())){
                showMessage();
            }
            else {
                showErrorMessage();
            }*/
        }
    }


    private boolean isValidPassword(String password){
        String regex = "[\\w\\d]{8,16}";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(password).matches();
    }

    private void showMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(PasswordActivity.this).create();
        alertDialog.setTitle("Password update");
        alertDialog.setMessage("Password has been successfully updated");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(PasswordActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialog.show();

    }

    private void showErrorMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(PasswordActivity.this).create();
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
