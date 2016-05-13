package com.example.boush.dreamchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationsFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Message> messagesList = new ArrayList<>();
    private MessageAdapter mAdapter;

    public ConversationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.conversationsList);

        mAdapter = new MessageAdapter(messagesList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext()
                                        , LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),
                                            recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Message message = messagesList.get(position);
                //Toast.makeText(getActivity().getApplicationContext(), message.getName()
                //        + " is selected!", Toast.LENGTH_SHORT).show();
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Name",message.getName());
                editor.commit();
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Work in progress.", Toast.LENGTH_SHORT).show();
            }
        }));

        prepareMessages();
        return view;
    }

    private void prepareMessages() {
        Message msg = new Message(R.drawable.ic_person,"Noro Kanalos", "Ahoj");
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Brano Mojsej", "Ahoj more");
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Brano David", "Neviem programovat");
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Laci Strike", "Lets dance!");
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Cyka Blyat", "Why this shit is not working.");
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Oskar Kode", "Kral Ifov.");
        messagesList.add(msg);

        mAdapter.notifyDataSetChanged();
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
