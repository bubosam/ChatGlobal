package com.example.boush.dreamchat;

import android.content.Context;
import android.content.Intent;
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
 * Created by BousH on 24.5.2016.
 */
public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {

    private Context context;
    private List<HelpItem> titleList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageview;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            imageview = (ImageView) view.findViewById(R.id.person_photo);
            context=view.getContext();
        }
    }

    public HelpAdapter(List<HelpItem> titleList) {
        this.titleList = titleList;
    }

    @Override
    public HelpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_row, parent, false);

        final MyViewHolder holder = new MyViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpItem title = titleList.get(holder.getAdapterPosition());
                Log.d("onclick", title.getTitle());


                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HelpItem helpItem = titleList.get(position);
        holder.title.setText(helpItem.getTitle());
        holder.imageview.setImageResource(helpItem.getPhotoId());

        final int pos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpItem setting = titleList.get(pos);
                Toast.makeText(context, setting.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });
    }


    @Override
    public int getItemCount() {
        return titleList.size();
    }
}
