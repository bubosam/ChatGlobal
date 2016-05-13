package com.example.boush.dreamchat;

/**
 * Created by Client on 12.5.2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {

    private List<Friend> friendsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, group;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            group = (TextView) view.findViewById(R.id.group);
            date = (TextView) view.findViewById(R.id.date);
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
        holder.group.setText(friend.getGroup());
        holder.date.setText(friend.getDate());
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}