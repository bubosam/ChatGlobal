package com.example.boush.dreamchat;

import android.content.Context;
import android.graphics.Color;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Okay-PC on 19.5.2016.
 */
public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Message> mMessages;



    public MessageAdapter(Context context, List<Message> messages) {
        super();
        this.mContext = context;
        this.mMessages = messages;
    }
    @Override
    public int getCount() {
        return mMessages.size();
    }
    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = (Message) this.getItem(position);

        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_bubble, parent, false);
            holder.message = (TextView) convertView.findViewById(R.id.txtMessage);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        holder.message.setText(message.getMessageText());

        LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();

        if(message.isMe())
            {
                holder.message.setBackgroundResource(R.drawable.in_message);
                lp.gravity = Gravity.RIGHT;
               holder.message.setTextColor(Color.WHITE);
            }

            else
            {
                holder.message.setBackgroundResource(R.drawable.out_message);
                lp.gravity = Gravity.LEFT;
                holder.message.setTextColor(Color.WHITE);
            }
            holder.message.setLayoutParams(lp);

        return convertView;
    }
    private static class ViewHolder
    {
        TextView message;
    }

    @Override
    public long getItemId(int position) {
        //Unimplemented, because we aren't using Sqlite.
        return position;
    }

}
