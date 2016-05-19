package com.example.boush.dreamchat;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;

    private TextView username;
    private Button sendReq;

    private TextView phone;
    private TextView firstName;
    private TextView lastName;
    private TextView email;

    public ProfileActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        imageView = (ImageView) findViewById(R.id.profilePic);
        username = (TextView) findViewById(R.id.contactNickname);
        email = (TextView) findViewById(R.id.contactEmail);
        sendReq = (Button) findViewById(R.id.sendRequest);
        phone = (TextView) findViewById(R.id.contactPhone);
        firstName = (TextView) findViewById(R.id.contactFirstName);
        lastName = (TextView) findViewById(R.id.contactLastName);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                firstName.setText(extras.getString("firstName"));
                lastName.setText(extras.getString("lastName"));
            }
        }

        sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Request to "+username +" sent", Toast.LENGTH_SHORT).show();
                //sendRequest();
            }
        });

    }
}




