package com.example.boush.dreamchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    private TextView text;
    private TextView text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        text = (TextView) findViewById(R.id.textView12);
        text2 = (TextView) findViewById(R.id.textView10);

        Intent intent = getIntent();
        String number = intent.getExtras().getString("extra");

        if(number.equals("2") || number.equals("3") ){
            final ScrollView scrollView =(ScrollView) findViewById(R.id.scrollViewer);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, text.getTop());
                }
            });
        }





    }
}
