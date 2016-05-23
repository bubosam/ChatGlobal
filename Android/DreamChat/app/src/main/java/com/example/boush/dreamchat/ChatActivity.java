package com.example.boush.dreamchat;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends ListActivity {
    private String firstName;
    private String lastName;
    private String messageText;
    private String date;
    private Contact contact;

    private TextView txtName;
    private EditText etxtSendMsg;
    private List<Message> messagesList = new ArrayList<>();
    private MessageAdapter mAdapter;
    private ImageButton send;

    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                if (getIntent().hasExtra("contact")){
                    contact = (Contact) extras.getParcelable("contact");
                    firstName = contact.getFirstName();
                    lastName = contact.getLastName();
                }
                else{
                    firstName=extras.getString("firstName");
                    lastName=extras.getString("lastName");
                    messageText=extras.getString("message");
                    date=extras.getString("date");
                }
            }
        }
        setContentView(R.layout.activity_chat);
        initChat();
        mSocket.on("new message", onNewMessage);
        mSocket.connect();
    }

    public void initChat(){

        txtName = (TextView) findViewById(R.id.txtName);
        etxtSendMsg = (EditText) findViewById(R.id.etxtSendMsg);

        mAdapter = new MessageAdapter(this, messagesList);
        setListAdapter(mAdapter);

        loadHistory();
        txtName.setText(firstName+" "+lastName);

        send = (ImageButton) findViewById(R.id.btn_sendMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    public void sendMessage(){


        String messageText = etxtSendMsg.getText().toString();

        if(!messageText.isEmpty()){
            Message msg = new Message();
            msg.setMessageText(messageText);
            msg.setMe(true);
            messagesList.add(msg);
            mSocket.emit("new message", msg);
        }

        mAdapter.notifyDataSetChanged();
        etxtSendMsg.setText("");
    }

    public void loadHistory(){
        Message msg = new Message();
        msg.setMessageText(messageText);
        msg.setMe(false);
        messagesList.add(msg);

        mAdapter.notifyDataSetChanged();
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];

                        String message;
                        try {

                            message = data.getString("message");
                        } catch (JSONException e) {
                            return;
                        }

                        // add the message to view
                        addMessage(message);
                    }
                });
            }
        };

    private void addMessage(String message) {
        Message msg = new Message();
        msg.setMessageText(message);
        msg.setMe(false);
        messagesList.add(msg);

        mAdapter.notifyDataSetChanged();
    }
}
