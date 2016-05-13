package com.example.boush.dreamchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Okay-PC on 11.5.2016.
 */
public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<Message> messagesList;

    public MessageAdapter(List<Message> messagesList) {
        this.messagesList = messagesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message message = messagesList.get(position);
        holder.mAvatar.setImageResource(message.getAvatarId());
        holder.mName.setText(message.getFirstName()+" "+message.getLastName());
        holder.mText.setText(message.getMessageText());
        holder.mDate.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView mAvatar;
        public TextView mName;
        public TextView mText;
        public TextView mDate;

        public MyViewHolder(View view) {
            super(view);
            mAvatar = (ImageView) view.findViewById(R.id.mAvatar);
            mName = (TextView) view.findViewById(R.id.mName);
            mText = (TextView) view.findViewById(R.id.mText);
            mDate = (TextView) view.findViewById(R.id.mDate);
        }
    }
}

