package com.example.boush.dreamchat;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private String firstName;
    private String lastName;
    private String messageText;
    private String date;

    private TextView txtName;
    private EditText etxtSendMsg;
    private RecyclerView recyclerView;
    private List<Message> messagesList = new ArrayList<>();
    private MessageAdapter mAdapter;

    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initChat();
    }

    public void initChat(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        firstName = prefs.getString("firstName",null);
        lastName = prefs.getString("lastName",null);
        messageText = prefs.getString("message",null);
        date = prefs.getString("date",null);

        txtName = (TextView) findViewById(R.id.txtName);
        etxtSendMsg = (EditText) findViewById(R.id.etxtSendMsg);
        recyclerView = (RecyclerView) findViewById(R.id.messagesContainer);

        mAdapter = new MessageAdapter(messagesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        loadHistory();
        txtName.setText(firstName+" "+lastName);

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


        String messageText = etxtSendMsg.getText().toString();

        if(!messageText.isEmpty()){
            Message msg = new Message();
            msg.setMessageText(messageText);
            msg.setFirstName("");
            msg.setLastName("");
            msg.setAvatarId(R.drawable.ic_person);
            msg.setDate(sdf.format(c.getTime()));
            messagesList.add(msg);
        }

        etxtSendMsg.setText("");
    }

    public void loadHistory(){
        Message msg = new Message();
        msg.setMessageText(messageText);
        msg.setFirstName("");
        msg.setLastName("");
        msg.setAvatarId(R.drawable.ic_person);
        msg.setDate(date);
        messagesList.add(msg);

        mAdapter.notifyDataSetChanged();
    }
}
