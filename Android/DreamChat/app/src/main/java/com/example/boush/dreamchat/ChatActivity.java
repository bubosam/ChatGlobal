package com.example.boush.dreamchat;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {
    private String name;
    private TextView txtName;
    private EditText etxtSendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        name = prefs.getString("Name",null);
        //Log.d("BULLSHIT", "onCreate: "+name);
        txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText(name);
        etxtSendMsg = (EditText) findViewById(R.id.etxtSendMsg);
        etxtSendMsg.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            sendMessage();
                            etxtSendMsg.setText("");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }

    public void sendMessage(){
        Log.d("THIS IS SPARTAAAAA", "sendMessage: "+etxtSendMsg.getText());
    }
}
