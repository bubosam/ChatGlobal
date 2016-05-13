package com.example.boush.dreamchat;

/**
 * Created by Client on 12.5.2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

    private List<Friend> friendsList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, nickname;
        public ImageView avatar;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            nickname = (TextView) view.findViewById(R.id.nickname);
            avatar = (ImageView) view.findViewById(R.id.fAvatar);
            context=view.getContext();
        }
    }


    public FriendsAdapter(List<Friend> friendList) {
        this.friendsList = friendList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Friend friend = friendsList.get(position);
        holder.title.setText(friend.getTitle());
        holder.nickname.setText(friend.getNickname());
        holder.avatar.setImageResource(R.drawable.ic_person);
        //holder.date.setText(friend.getDate());
        final int pos = position;

        holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Friend friend = friendsList.get(pos);
                    Toast.makeText(context, friend.getTitle(), Toast.LENGTH_SHORT).show();
                }
        });

        holder.nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = friendsList.get(pos);
                Toast.makeText(context, friend.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}