package com.example.boush.dreamchat;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class ProfileActivity extends AppCompatActivity {

    private Button sendReq;
    private Button removeFriend;
    private Button sendMessage;
    private Button acceptReq;
    private Button cancelReq;

    private ImageView imageView;
    private TextView name;
    private TextView nickname;
    private TextView phone;
    private TextView email;

    private boolean isFriend;
    private boolean isRequest;

    private String firstName;
    private String lastName;
    private String title;
    private String nicknameStr;
    private String phoneStr;
    private String emailStr;

    private Contact contact;

    public ProfileActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                contact = (Contact) extras.getParcelable(Constants.KEY_CONTACT);
            }
        }
        else{
            contact = savedInstanceState.getParcelable(Constants.KEY_CONTACT);
        }

        firstName = contact.getFirstName();
        lastName = contact.getLastName();
        title = contact.getTitle();
        phoneStr = contact.getPhone();
        emailStr = contact.getEmail();
        nicknameStr = contact.getNickname();
        isFriend = contact.isFriend();
        isRequest = contact.isRequest();


        //Log.d("profil", String.valueOf(contact.getUserid()));

        if (isFriend){
            setContentView(R.layout.activity_profile_friend);
            sendMessage = (Button) findViewById(R.id.sendMessage);
            removeFriend = (Button) findViewById(R.id.removeFriend);

            imageView = (ImageView) findViewById(R.id.profilePic);
            nickname = (TextView) findViewById(R.id.contactNickname);
            email = (TextView) findViewById(R.id.contactEmail);
            phone = (TextView) findViewById(R.id.contactPhone);
            name = (TextView) findViewById(R.id.contactName);

            name.setText(title);
            nickname.setText(nicknameStr);
            phone.setText(phoneStr);
            email.setText(emailStr);

            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra(Constants.KEY_CONTACT, contact);
                    //intent.putExtra("firstName", firstName);
                    //intent.putExtra("lastName", lastName);
                    //intent.putExtra("message", message.getMessageText());
                    //intent.putExtra("date", message.getDate());
                    startActivity(intent);
                }
            });

            removeFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), title +" removed", Toast.LENGTH_SHORT).show();
                    //new Server().removeFriend();
                }
            });


        }
        else if (isRequest){
            setContentView(R.layout.activity_profile_request);

            imageView = (ImageView) findViewById(R.id.profilePic);
            nickname = (TextView) findViewById(R.id.contactNickname);
            name = (TextView) findViewById(R.id.contactName);

            nickname.setText(nicknameStr);
            name.setText(title);
            acceptReq = (Button) findViewById(R.id.acceptReq);
            cancelReq = (Button) findViewById(R.id.cancelReq);

            acceptReq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AcceptRequestTask(getApplicationContext(), contact.getRequestid(), new AsyncTaskCallback() {
                        @Override
                        public void onTaskCompleted(List result) {

                        }

                        @Override
                        public void onTaskCompleted(Contact result) {

                        }

                        @Override
                        public void onTaskCompleted(int result) {
                            if (result==400){
                                Toast.makeText(getApplicationContext(), "Request not found", Toast.LENGTH_SHORT).show();
                            }
                            else if (result==401){
                                Toast.makeText(getApplicationContext(), "Authorization failed", Toast.LENGTH_SHORT).show();
                            }
                            else if (result==500){
                                Toast.makeText(getApplicationContext(), "Unexpected database error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onTaskCompleted(String result) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            contact.setFriend(true);
                            contact.setRequest(false);
                            intent.putExtra(Constants.KEY_CONTACT, contact);
                            finish();
                            startActivity(intent);
                        }
                    }).execute();
                }
            });

            cancelReq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CancelRequestTask(getApplicationContext(), contact.getRequestid(), new AsyncTaskCallback() {
                        @Override
                        public void onTaskCompleted(List result) {

                        }

                        @Override
                        public void onTaskCompleted(Contact result) {

                        }

                        @Override
                        public void onTaskCompleted(int result) {
                            Log.d("kod", String.valueOf(result));
                            if (result==401){
                                Toast.makeText(getApplicationContext(), "Authorization failed", Toast.LENGTH_SHORT).show();
                            }
                            else if (result==500){
                                Toast.makeText(getApplicationContext(), "Unexpected database error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onTaskCompleted(String result) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                }
            });
        }
        else{
            setContentView(R.layout.activity_profile);

            imageView = (ImageView) findViewById(R.id.profilePic);
            nickname = (TextView) findViewById(R.id.contactNickname);
            name = (TextView) findViewById(R.id.contactName);

            nickname.setText(nicknameStr);
            name.setText(title);
            sendReq = (Button) findViewById(R.id.sendMessage);

            sendReq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SendRequestTask(getApplicationContext(), contact.getUserid(), new AsyncTaskCallback() {
                        @Override
                        public void onTaskCompleted(List result) {

                        }

                        @Override
                        public void onTaskCompleted(Contact result) {

                        }

                        @Override
                        public void onTaskCompleted(int result) {
                            Log.d("kod", String.valueOf(result));
                            if (result==401){
                                Toast.makeText(getApplicationContext(), "Authorization failed", Toast.LENGTH_SHORT).show();
                            }
                            else if (result==409){
                                Toast.makeText(getApplicationContext(), "Friend request already sent or users are already friends", Toast.LENGTH_SHORT).show();
                            }
                            else if (result==500){
                                Toast.makeText(getApplicationContext(), "Unexpected database error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onTaskCompleted(String result) {
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                    }).execute();
                }
            });

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.KEY_CONTACT, contact);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contact = savedInstanceState.getParcelable(Constants.KEY_CONTACT);
    }

    public class SendRequestTask extends AsyncTask<Void, Void, Void>{
        private AsyncTaskCallback listener;
        private int userid;
        private Context context;

        public SendRequestTask(Context context, int userid, AsyncTaskCallback listener){
            this.context = context;
            this.userid = userid;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... params) {
            new Server().sendRequest(context, userid, new VolleyCallback() {
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
                    listener.onTaskCompleted(result);
                }
            });
            return null;
        }
    }

    public class AcceptRequestTask extends AsyncTask<Void, Void, Void>{
        private AsyncTaskCallback listener;
        private int reqid;
        private Context context;

        public AcceptRequestTask(Context context, int reqid, AsyncTaskCallback listener){
            this.context = context;
            this.reqid = reqid;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... params) {
            new Server().acceptReq(reqid, context, new VolleyCallback() {
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
                    listener.onTaskCompleted(result);
                }
            });
            return null;
        }
    }

    public class CancelRequestTask extends AsyncTask<Void, Void, Void>{
        private AsyncTaskCallback listener;
        private int reqid;
        private Context context;

        public CancelRequestTask(Context context, int reqid, AsyncTaskCallback listener){
            this.context = context;
            this.reqid = reqid;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... params) {
            new Server().cancelReq(reqid, context, new VolleyCallback() {
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
                    listener.onTaskCompleted(result);
                }
            });
            return null;
        }
    }

    public void fillFriendInfo(){

        firstName = contact.getFirstName();
        lastName = contact.getLastName();
        title = contact.getTitle();
        phoneStr = contact.getPhone();
        emailStr = contact.getEmail();
        nicknameStr = contact.getNickname();
    }
}