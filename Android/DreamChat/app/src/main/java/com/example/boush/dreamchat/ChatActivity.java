package com.example.boush.dreamchat;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends ListActivity {
    private String firstName;
    private String lastName;
    private String messageText;
    private String date;
    private Contact contact;
    private int myId = 1;
    private int recId = 2;

    private TextView txtName;
    private EditText etxtSendMsg;
    private ImageButton send;
    private ImageButton info;
    private ImageButton sendPhoto;

    private List<Message> messagesList = new ArrayList<>();
    private MessageAdapter mAdapter;
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private Database db;

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
                if (getIntent().hasExtra(Constants.KEY_CONTACT)){
                    contact = (Contact) extras.getParcelable(Constants.KEY_CONTACT);
                    firstName = contact.getFirstName();
                    lastName = contact.getLastName();
                }
                else{
                    firstName=extras.getString("firstName");
                    lastName=extras.getString("lastName");
                    messageText=extras.getString("message");
                    date=extras.getString("date");
                    recId = extras.getInt("recId");

                    /*vyhadzuje null pointer exception
                    Message msg = new Message();
                    msg.setMessageText(messageText);
                    msg.setDate(date);
                    msg.setRecId(recId);
                    msg.setMyId(myId);
                    msg.setMe(false);

                    messagesList.add(msg);
                    db.addMessage(msg);*/
                }
            }
        }
        setContentView(R.layout.activity_chat);

        db = new Database(this);
        date = sdf.format(c.getTime());

        initChat();

        mSocket.on("new message", onNewMessage);
        mSocket.connect();
    }

    public void initChat(){

        txtName = (TextView) findViewById(R.id.txtName);
        etxtSendMsg = (EditText) findViewById(R.id.etxtSendMsg);

        messagesList = db.getHistory(myId,recId);

        mAdapter = new MessageAdapter(this, messagesList);
        setListAdapter(mAdapter);


        txtName.setText(firstName+" "+lastName);

        send = (ImageButton) findViewById(R.id.btn_sendMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        info = (ImageButton) findViewById(R.id.btn_Info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DAGGGGG", "onClick: INFO BUTTON WAS CLICKED.");
            }
        });

        sendPhoto = (ImageButton) findViewById(R.id.btn_sendPhoto);
        sendPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DAGGGGG", "onClick: SEND PHOTO BUTTON WAS CLICKED.");
            }
        });
    }

    public void sendMessage(){

        String messageText = etxtSendMsg.getText().toString();

        if(!messageText.isEmpty()) {
            Message msg = new Message();
            msg.setMessageText(messageText);
            msg.setMe(true);
            msg.setRecId(recId);
            msg.setMyId(myId);
            msg.setDate(date);
            messagesList.add(msg);

            mAdapter.notifyDataSetChanged();
            mSocket.emit("new message", msg);
            db.addMessage(msg);
        }

        etxtSendMsg.setText("");
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
                            message = data.getString(Constants.KEY_MESSAGE);
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
        msg.setRecId(recId);
        msg.setMyId(myId);
        msg.setDate(date);

        messagesList.add(msg);
        db.addMessage(msg);
        mAdapter.notifyDataSetChanged();
    }
}
