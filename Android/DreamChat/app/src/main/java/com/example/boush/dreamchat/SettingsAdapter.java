package com.example.boush.dreamchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by BousH on 23.5.2016.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    private Context context;
    private List<SettingItem> titleList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageview;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);

            context=view.getContext();
        }
    }

    public SettingsAdapter(List<SettingItem> titleList) {
        this.titleList = titleList;
    }

    @Override
    public SettingsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_row, parent, false);

        final MyViewHolder holder = new MyViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingItem title = titleList.get(holder.getAdapterPosition());
                Log.d("onclick", title.getTitle());


                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SettingItem settingItem = titleList.get(position);
        holder.title.setText(settingItem.getTitle());
//        holder.imageview.setImageResource(settingItem.getPhotoId());

        final int pos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingItem setting = titleList.get(pos);
                Toast.makeText(context, setting.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });
    }


    @Override
    public int getItemCount() {
        return titleList.size();
    }
}
