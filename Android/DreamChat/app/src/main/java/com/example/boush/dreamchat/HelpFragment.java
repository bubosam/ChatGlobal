package com.example.boush.dreamchat;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BousH on 19.5.2016.
 */
public class HelpFragment extends Fragment {

    private List<HelpItem> titleList = new ArrayList<>();
    private HelpAdapter mAdapter;
    private RecyclerView recyclerView;
    private Context context;
    ImageView personPhoto;

    public HelpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_help, container, false);
        context = getActivity();
        personPhoto = (ImageView)rootView.findViewById(R.id.person_photo);

        titleList.add (new HelpItem("How to register on DreamChat ?",R.drawable.help_white_18x18));
        titleList.add (new HelpItem("What usernames are allowed on DreamChat ?",R.drawable.help_white_18x18));
        titleList.add (new HelpItem("How do I change my password ?",R.drawable.help_white_18x18));
        titleList.add (new HelpItem("How can I make my password strong?",R.drawable.help_white_18x18));


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);


        mAdapter = new HelpAdapter(titleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }
}