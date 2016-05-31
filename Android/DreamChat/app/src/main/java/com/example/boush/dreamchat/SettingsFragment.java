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
public class SettingsFragment extends Fragment {

    private List<SettingItem> titleList = new ArrayList<>();
    private SettingsAdapter mAdapter;
    private RecyclerView recyclerView;
    private Context context;
    ImageView personPhoto;

    public SettingsFragment(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        context = getActivity();
        personPhoto = (ImageView)rootView.findViewById(R.id.person_photo);

        titleList.add (new SettingItem("Notifications and sounds",R.drawable.ic_notif));
        titleList.add (new SettingItem("People",R.drawable.ic_people));
        titleList.add (new SettingItem("Photos",R.drawable.ic_phot));
        titleList.add (new SettingItem("Accounts",R.drawable.ic_acc));
        titleList.add (new SettingItem("Profile",R.drawable.ic_person_white));
        titleList.add (new SettingItem("Themes",R.drawable.palette_white_18x18));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);


        mAdapter = new SettingsAdapter(titleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }



}