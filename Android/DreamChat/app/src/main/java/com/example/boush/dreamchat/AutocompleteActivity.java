package com.example.boush.dreamchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteActivity extends AppCompatActivity {

    private AutoCompleteTextView autocomplete;

    private AutoCAdapter acAdapter;
    private List<Contact> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete);

        autocomplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autocomplete.setThreshold(1);

        friendList = fetchFriends();
        acAdapter = new AutoCAdapter(this, R.layout.dropdown, friendList);
        autocomplete.setAdapter(acAdapter);

        autocomplete.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                autocomplete.showDropDown();
                return false;
            }
        });

        autocomplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    autocomplete.showDropDown();
                }
            }
        });

       autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = ((Contact)parent.getItemAtPosition(position));
                //Toast.makeText(getApplicationContext(), contact.getTitle() + " is clicked", Toast.LENGTH_SHORT).show();
                Context context = getApplicationContext();
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra(Constants.KEY_CONTACT, contact);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                finish();
            }
        });

    }

    public List<Contact> fetchFriends(){
        List<Contact> friends = new ArrayList<>();
        friends.add(new Contact(2, "Patrik", "Patinák", "Patres", true, "email@domena.sk", "0901234567"));
        friends.add(new Contact(3, "Martin", "Tarhanič", "Matolator", true, "email@domena.sk", "0901234567"));
        friends.add(new Contact(4, "Monika", "Jaššová", "monikka", true, "email@domena.sk", "0901234567"));
        friends.add(new Contact(5, "Michal", "Borovský", "Michaljevič", true, "email@domena.sk", "0901234567"));
        friends.add(new Contact(6, "Matúš", "Kokoška", "MK", true, "email@domena.sk", "0901234567"));
        return friends;
    }
}
