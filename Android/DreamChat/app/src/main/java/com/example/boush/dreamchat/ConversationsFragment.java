package com.example.boush.dreamchat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private List<Conversation> array = new ArrayList<>();
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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        myId = prefs.getInt(Constants.KEY_USERID, 0);

        runConversationTask();

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


        try {
            array = db.getConversations(myId);
            for (int i = 0; i < array.size(); i++) {
                conversationId = array.get(i).getConversationId();
                recId = array.get(i).getRecieverId();
                String message = array.get(i).getMessage();
                String firstName = array.get(i).getFirstName();
                String lastName = array.get(i).getLastName();

                Message msg = new Message();
                msg.setFirstName(firstName);
                msg.setLastName(lastName);
                msg.setMessageText(message);
                messagesList.add(msg);
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
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

    public void runConversationTask(){
        new FetchConversationsTask(new AsyncTaskCallback() {
            @Override
            public void onTaskCompleted(List result) {
                //nastavenie listu a adaptera
            }

            @Override
            public void onTaskCompleted(Contact result) {

            }

            @Override
            public void onTaskCompleted(int result) {
                if (result==401){
                    Toast.makeText(getContext(), "Error fetching conversations - unauthorized access", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTaskCompleted(String result) {

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public class FetchConversationsTask extends AsyncTask<Void, Void, Void>{

        private AsyncTaskCallback listener;
        public FetchConversationsTask(AsyncTaskCallback listener){
            this.listener=listener;
        }

        @Override
        protected Void doInBackground(Void... params) {
            new Server().getConversations(getContext(), new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {

                }

                @Override
                public void onSuccess(JSONArray result) {
                    Log.d("JSONArray result", result.toString());
                    List<Conversation> conversationL = new ParseJSON().getConversations(result);
                    listener.onTaskCompleted(conversationL);
                }

                @Override
                public void onSuccess(String result) {

                }

                @Override
                public void onSuccess(int result) {
                    listener.onTaskCompleted(result);
                }
            });
            return null;
        }
    }
}
