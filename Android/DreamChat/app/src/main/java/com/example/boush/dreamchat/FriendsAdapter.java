package com.example.boush.dreamchat;

/**
 * Created by Client on 12.5.2016.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

    private List<Friend> friendsList;
    private Context context;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView title, nickname;
        public ImageView avatar;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            nickname = (TextView) view.findViewById(R.id.nickname);
            avatar = (ImageView) view.findViewById(R.id.fAvatar);
            context=view.getContext();
            view.setOnCreateContextMenuListener(this);
            view.setLongClickable(true);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Friend friend = friendsList.get(getAdapterPosition());
            menu.setHeaderTitle(friend.getTitle());
            menu.add(Menu.NONE, R.id.menu_friend_message, Menu.NONE, R.string.menu_friend_message);//groupId, itemId, order, title
            menu.add(Menu.NONE, R.id.menu_friend_profile, Menu.NONE, R.string.menu_friend_profile);
            menu.add(Menu.NONE, R.id.menu_friend_remove, Menu.NONE, R.string.menu_friend_remove);
        }
    }


    public FriendsAdapter(List<Friend> friendList) {
        this.friendsList = friendList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_row, parent, false);

        final MyViewHolder holder = new MyViewHolder(itemView);

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = friendsList.get(holder.getAdapterPosition());
                Log.d("onclick", friend.getLastName());
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("firstName", friend.getFirstName());
                editor.putString("lastName", friend.getLastName());
                editor.apply();

                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });*/

        return new MyViewHolder(itemView);
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Friend friend = friendsList.get(position);
        holder.title.setText(friend.getTitle());
        holder.nickname.setText(friend.getNickname());
        holder.avatar.setImageResource(R.drawable.ic_person);
        //holder.date.setText(friend.getDate());
        final int pos = position;
        setPosition(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend = friendsList.get(pos);
                Log.d("onclick", friend.getLastName());
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("firstName", friend.getFirstName());
                editor.putString("lastName", friend.getLastName());
                editor.apply();

                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(pos);
                return false;
            }
        });

        /*holder.title.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

}