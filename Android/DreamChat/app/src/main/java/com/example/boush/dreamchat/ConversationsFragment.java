package com.example.boush.dreamchat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Message> messagesList = new ArrayList<>();
    private LastMessageAdapter mAdapter;

    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    private int myId;
    private int recId;
    private int conversationId;
    private Database db = new Database(getActivity());

    public ConversationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.conversationsList);



        mAdapter = new LastMessageAdapter(messagesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        registerForContextMenu(recyclerView);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),
                                            recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Message message = messagesList.get(position);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("firstName", message.getFirstName());
                intent.putExtra("lastName", message.getLastName());
                intent.putExtra("message",message.getMessageText());
                intent.putExtra("date",message.getDate());
                intent.putExtra("recId",message.getRecId());
                intent.putExtra("conversationId",conversationId);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);

                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_details:
                                Toast.makeText(getActivity().getApplicationContext(), "Details Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_profile:
                                Toast.makeText(getActivity().getApplicationContext(), "Profile Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_remove:
                                Toast.makeText(getActivity().getApplicationContext(), "Conversation removed.", Toast.LENGTH_SHORT).show();
                                messagesList.remove(position);
                                mAdapter.notifyDataSetChanged();
                                return true;
                        }
                        return false;
                    }
                };
                popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
                popupMenu.inflate(R.menu.menu_popup);
                popupMenu.show();

            }

        }));

        prepareMessages();
        return view;
    }

    private void prepareMessages() {

        String jsonStr = "[ {\"firstName\":\"John\", \"lastName\":\"Doe\", \"message\":\"Ahoj\", \"conversationId:\":\"1\"},\n" +
                "    {\"firstName\":\"Anna\", \"lastName\":\"Smith\", \"message\":\"Ahoj\", \"conversationId:\":\"2\"},\n" +
                "    {\"firstName\":\"Peter\", \"lastName\":\"Jones\", \"message\":\"Ahoj\", \"conversationId:\":\"3\"}\n" +
                "]}";
        JSONArray jsonarray = null;
        try {
            jsonarray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String firstName = jsonobject.getString("firstName");
                String lastName = jsonobject.getString("lastName");
                String message = jsonobject.getString("message");
                conversationId = jsonobject.getInt("conversationId");

                Message msg = new Message();
                msg.setFirstName(firstName);
                msg.setLastName(lastName);
                msg.setMessageText(message);
                messagesList.add(msg);
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ConversationsFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                     final ConversationsFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
