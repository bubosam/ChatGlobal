package com.example.boush.dreamchat;

/**
 * Created by Client on 12.5.2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private List<Contact> contactList;
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


    public ContactsAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);

        //final MyViewHolder holder = new MyViewHolder(itemView);

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact friend = contactList.get(holder.getAdapterPosition());
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.title.setText(contact.getTitle());
        holder.nickname.setText(contact.getNickname());
        holder.avatar.setImageResource(R.drawable.ic_person);

        /*if (contact.isOnline()) {
            button.setText("Message");
            button.setEnabled(true);
        }
        else {
            button.setText("Offline");
            button.setEnabled(false);
        }*/
        //holder.date.setText(contact.getDate());
        final int pos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = contactList.get(pos);
                //Toast.makeText(context, contact.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("firstName", contact.getFirstName());
                intent.putExtra("lastName", contact.getLastName());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return contactList.size();
    }

}