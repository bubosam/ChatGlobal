package com.example.boush.dreamchat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 8.6.2016.
 */
public class AutoCAdapter extends ArrayAdapter<Contact> {
    private final Context mContext;
    private final List<Contact> friends;
    private final List<Contact> friends_All;
    private final List<Contact> friends_Suggestion;
    private final int mLayoutResourceId;

    public AutoCAdapter(Context context, int resource, List<Contact> friends) {
        super(context, resource, friends);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.friends = new ArrayList<>(friends);
        this.friends_All = new ArrayList<>(friends);
        this.friends_Suggestion = new ArrayList<>();
    }

    public int getCount() {
        return friends.size();
    }

    public Contact getItem(int position) {
        return friends.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, parent, false);
            }
            Contact contact = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.autoText);
            name.setText(contact.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((Contact) resultValue).getTitle();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    friends_Suggestion.clear();
                    for (Contact friend : friends_All) {
                        if (friend.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            friends_Suggestion.add(friend);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = friends_Suggestion;
                    filterResults.count = friends_Suggestion.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                friends.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using friends.addAll((ArrayList<Department>) results.values);
                    List<?> result = (List<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof Contact) {
                            friends.add((Contact) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    friends.addAll(friends_All);
                }
                notifyDataSetChanged();
            }
        };
    }
}
