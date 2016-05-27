package com.example.boush.dreamchat;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView text;
    private Contact friends[] = {new Contact(1, "Iba", "Meliško", "Meliško", false, "email@domena.sk", "0901234567"),
            new Contact(2, "Patrik", "Patinák", "Patres", true, "email@domena.sk", "0901234567"),
            new Contact(3, "Martin", "Tarhanič", "Matolator", true, "email@domena.sk", "0901234567"),
            new Contact(4, "Monika", "Jaššová", "monikka", true, "email@domena.sk", "0901234567"),
            new Contact(5, "Michal", "Borovský", "Michaljevič", true, "email@domena.sk", "0901234567"),
            new Contact(6, "Matúš", "Kokoška", "DreamTeam", true, "email@domena.sk", "0901234567"),
            new Contact(7, "Roman", "Klimčík", "Global Logic", false, "email@domena.sk", "0901234567"),
            new Contact(8, "X", "Y", "Slovensko", false, "email@domena.sk", "0901234567"),
            new Contact(9, "Meno", "Priezvisko", "Nick", false, "email@domena.sk", "0901234567")};

    private ArrayList<String> items=new ArrayList<>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        for (int i=0; i<friends.length; i++){
            items.add(friends[i].getTitle());
        }

        text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        text.setAdapter(adapter);

        /*text.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                text.showDropDown();
                return false;
            }
        });*/

        text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    text.showDropDown();
                }
            }
        });

       text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), s + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
