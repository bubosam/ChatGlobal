package com.example.boush.dreamchat;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
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
import java.util.List;

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
    private List<Message> messagesList = new ArrayList<>();
    private MessageAdapter mAdapter;
    private ImageButton send;
    private ImageButton sendEmoticon;
    private LinearLayout emoticons;
    private ImageView imageSmiling, imageLaughing, imageSad, imageAngry, imageTeasing, imageInLove;

    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    private Database db = new Database(this);

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
                }
            }
        }
        setContentView(R.layout.activity_chat);
        initChat();
        mSocket.on("new message", onNewMessage);
        mSocket.connect();
    }

    public void initChat(){
        emoticons = (LinearLayout) findViewById(R.id.emoticonLayout);
        emoticons.setVisibility(View.GONE);

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

        sendEmoticon = (ImageButton) findViewById(R.id.btn_sendEmoticon);
        sendEmoticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoticons.setVisibility(View.VISIBLE);
            }
        });

        imageSmiling = (ImageView) findViewById(R.id.imageSmiling);
        imageLaughing = (ImageView) findViewById(R.id.imageLaughing);
        imageSad = (ImageView) findViewById(R.id.imageSad);
        imageAngry = (ImageView) findViewById(R.id.imageAngry);
        imageTeasing = (ImageView) findViewById(R.id.imageTeasing);
        imageInLove = (ImageView) findViewById(R.id.imageInLove);

        imageSmiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageBetweentext(imageSmiling.getDrawable());
                emoticons.setVisibility(View.VISIBLE);
            }
        });
        imageLaughing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageBetweentext(imageLaughing.getDrawable());
                emoticons.setVisibility(View.GONE);
            }
        });
        imageSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageBetweentext(imageSad.getDrawable());
                emoticons.setVisibility(View.GONE);
            }
        });
        imageAngry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageBetweentext(imageAngry.getDrawable());
                emoticons.setVisibility(View.GONE);
            }
        });
        imageTeasing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageBetweentext(imageTeasing.getDrawable());
                emoticons.setVisibility(View.GONE);
            }
        });
        imageInLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageBetweentext(imageInLove.getDrawable());
                emoticons.setVisibility(View.GONE);
            }
        });

    }

    private void addImageBetweentext(Drawable drawable) {
        drawable .setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        int selectionCursor = etxtSendMsg.getSelectionStart();
        etxtSendMsg.getText().insert(selectionCursor, ".");
        selectionCursor = etxtSendMsg.getSelectionStart();

        SpannableStringBuilder builder = new SpannableStringBuilder(etxtSendMsg.getText());
        builder.setSpan(new ImageSpan(drawable), selectionCursor - ".".length(), selectionCursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        etxtSendMsg.setText(builder);
        etxtSendMsg.setSelection(selectionCursor);
    }

    public void sendMessage(){

        String messageText = etxtSendMsg.getText().toString();

        if(!messageText.isEmpty()) {
            Message msg = new Message();
            msg.setMessageText(messageText);
            msg.setMe(true);
            msg.setRecId(recId);
            msg.setMyId(myId);
            messagesList.add(msg);
            mSocket.emit("new message", msg);
            //db.addMessage(msg);

        }
        mAdapter.notifyDataSetChanged();
        etxtSendMsg.setText("");
    }

    public void loadHistory(){

        Database db = new Database(this);

        messagesList = db.getHistory(myId,recId);

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
        messagesList.add(msg);

        mAdapter.notifyDataSetChanged();
    }
}
