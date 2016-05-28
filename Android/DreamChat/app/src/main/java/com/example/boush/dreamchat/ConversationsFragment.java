package com.example.boush.dreamchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

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
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
                PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_info:
                                Toast.makeText(getActivity().getApplicationContext(), "Info Clicked", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_remove:
                                Toast.makeText(getActivity().getApplicationContext(), "Remove Clicked", Toast.LENGTH_SHORT).show();
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
        Message msg = new Message(R.drawable.ic_person,"Noro","Kanalos", "Ahoj");
        msg.setDate(sdf.format(c.getTime()));
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Brano","Mojsej", "Ahoj more");
        msg.setDate(sdf.format(c.getTime()));
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"David", "Golias", "Neviem programovat");
        msg.setDate(sdf.format(c.getTime()));
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Laci", "Strike", "Lets dance!");
        msg.setDate(sdf.format(c.getTime()));
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Oskar", "Kode", "Kral Ifov.");
        msg.setDate("16:18");
        messagesList.add(msg);

        msg = new Message(R.drawable.ic_person,"Patrik", "Vrbovsky", "Lorem ipsum dolor sit amet, " +
                "consectetuer adipiscing elit. Nulla pulvinar eleifend sem. Maecenas fermentum," +
                " sem in pharetra pellentesque, velit turpis volutpat ante, in pharetra metus odio a lectus. " +
                "Quisque porta. Nullam lectus justo, vulputate eget mollis sed, tempor sed magna. Nulla quis diam. " +
                "Nunc tincidunt ante vitae massa. Vivamus luctus egestas leo. Suspendisse sagittis ultrices augue. " +
                "Nullam sit amet magna in magna gravida vehicula.");
        msg.setDate(sdf.format(c.getTime()));
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
