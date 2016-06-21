package com.example.boush.dreamchat;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment implements SearchView.OnQueryTextListener,SwipeRefreshLayout.OnRefreshListener {


    public ContactsFragment() {
        // Required empty public constructor
    }

    private Context context;
    private List<Contact> contactList = new ArrayList<>();
    private List<Contact> friendList = new ArrayList<>();
    private List<Contact> requestList = new ArrayList<>();
    private RecyclerView recyclerView;
    //private ContactsAdapter mAdapter;
    private SearchView search;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private FloatingActionButton fab;
    private ProgressBar pb;
    //private View rootView;

    SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        context = getActivity();

        //rootView=view;

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_contacts);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        pb = (ProgressBar) view.findViewById(R.id.progressContacts);

        /*mAdapter = new ContactsAdapter(contactList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/

        sectionAdapter = new SectionedRecyclerViewAdapter();

        runReqTask();

        //prepareContactData();

        /*contactList.addAll(requestList);
        contactList.addAll(friendList);

        sectionAdapter.notifyDataSetChanged();
        if (requestList.size()>0){
            sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_requests), requestList));
        }
        if (friendList.size()>0){
            sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_friends), friendList));
        }
        List<Contact> others = getOthers(contactList);
        if (others.size() > 0) {
            sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_others), others));
        }

        sectionAdapter.notifyDataSetChanged();*/


        /*for (int i=0; i<contactList.size(); i++){
            Log.d("ContactList", contactList.get(i).getTitle());
        }
        sectionAdapter.notifyDataSetChanged();
        List<Contact> friends = getFriends(contactList);
        if (friends.size() > 0) {
            sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_friends), friends));
        }
        List<Contact> others = getOthers(contactList);
        if (others.size() > 0) {
            sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_others), others));
        }*/

        /*recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);*/

        fab = (FloatingActionButton) getActivity().findViewById(R.id.contactsFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AutocompleteActivity.class);
                context.startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 || dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        search = (SearchView) view.findViewById(R.id.searchView);
        search.setOnQueryTextListener(this);

        return view;
    }

    public void runReqTask(){
        new RequestLoad(new AsyncTaskCallback() {
            @Override
            public void onTaskCompleted(List result) {
                requestList.clear();
                sectionAdapter.notifyDataSetChanged();
                for (int i=0; i<result.size(); i++){
                    int userid=((Request) result.get(i)).getUserid();
                    String name=((Request) result.get(i)).getName();
                    String surname=((Request) result.get(i)).getSurname();
                    String nick=((Request) result.get(i)).getNickname();
                    int reqid =((Request) result.get(i)).getRequestid();
                    Log.d("Req", name+surname+nick+userid+reqid);
                    requestList.add(new Contact(userid, name, surname, nick, false, true, reqid));
                }
                runContactTask();
            }

            @Override
            public void onTaskCompleted(Contact result) {

            }

            @Override
            public void onTaskCompleted(int result) {
                if (result==401){
                    Toast.makeText(getContext(), "Error fetching contacts - unauthorized access", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTaskCompleted(String result) {

            }
        }).execute();
    }

    public void runContactTask(){
        new ContactFetch(new AsyncTaskCallback() {
            @Override
            public void onTaskCompleted(List result) {
                friendList.clear();
                sectionAdapter.notifyDataSetChanged();
                //contactList.clear();
                //contactList = result;
                friendList = result;

                sectionAdapter = new SectionedRecyclerViewAdapter();

                if (requestList.size()>0){
                    sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_requests), requestList));
                }

                if (friendList.size()>0){
                    sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_friends), friendList));
                }

                sectionAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sectionAdapter);

                /*for (int i=0; i<contactList.size(); i++){
                    Log.d("ContactList", contactList.get(i).getTitle());
                }
                sectionAdapter.notifyDataSetChanged();
                List<Contact> friends = getFriends(contactList);
                if (friends.size() > 0) {
                    sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_friends), friends));
                }
                List<Contact> others = getOthers(contactList);
                if (others.size() > 0) {
                    sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_others), others));
                }*/
            }

            @Override
            public void onTaskCompleted(Contact result) {

            }

            @Override
            public void onTaskCompleted(int result) {
                if (result==401){
                    Toast.makeText(getContext(), "Error fetching contacts - unauthorized access", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTaskCompleted(String result) {

            }
        }).execute();
    }

    private List<Contact> getOthers(List<Contact> list) {
        List<Contact> others = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            if (!list.get(i).isFriend()){
                others.add(list.get(i));
            }
        }
        return others;
    }

    private List<Contact> getFriends(List<Contact> list) {
        List<Contact> friends = new ArrayList<>();
        for (int i=0; i<list.size(); i++){
            if (list.get(i).isFriend()){
                friends.add(list.get(i));
            }
        }
        return friends;
    }

    @Override
    public void onRefresh() {
        runReqTask();
        swipeLayout.setRefreshing(false);
    }


    /*SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            /*
             for (Section section : sectionAdapter.getSectionsMap().values()) {
                if (section instanceof FilterableSection) {
                    ((FilterableSection)section).filter(query);
                }
            }
            sectionAdapter.notifyDataSetChanged();

            */

            /*query = query.toLowerCase();

            final List<Contact> filteredList = new ArrayList<>();

            for (int i = 0; i < contactList.size(); i++) {

                final String text1 = contactList.get(i).getNickname().toLowerCase();
                final String text2 = contactList.get(i).getFirstName().toLowerCase();
                final String text3 = contactList.get(i).getLastName().toLowerCase();
                if (text1.contains(query) || text2.contains(query) || text3.contains(query)) {
                    filteredList.add(contactList.get(i));
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new ContactsAdapter(filteredList);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();  // data set changed
            return true;

        }
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };*/

    public class RequestLoad extends AsyncTask <Void, Void, Void>{
        private AsyncTaskCallback listener;
        public RequestLoad(AsyncTaskCallback listener){
            this.listener=listener;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            new Server().loadReqs(context, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    Log.d("result", result.toString());
                    List<Request> requests = new ParseJSON().getRequests(result);
                    listener.onTaskCompleted(requests);
                }

                @Override
                public void onSuccess(JSONArray result) {

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

    public class ContactFetch extends AsyncTask <Void, Void, Void>{
        private AsyncTaskCallback listener;
        public ContactFetch(AsyncTaskCallback listener){
            this.listener=listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pb.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //SystemClock.sleep(3000);
            new Server().getContacts(context, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {

                }

                @Override
                public void onSuccess(JSONArray result) {
                    Log.d("JSONArray result", result.toString());
                    List<Contact> contactL = new ParseJSON().getContacts(result);
                    listener.onTaskCompleted(contactL);
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

    private void prepareContactData() {
        new Server().getContacts(context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {

            }

            @Override
            public void onSuccess(JSONArray result) {
                Log.d("JSONArray result", result.toString());
                contactList = new ParseJSON().getContacts(result);
                for (int i=0; i<contactList.size(); i++){
                    Log.d("ContactList", contactList.get(i).getTitle());
                }
                sectionAdapter.notifyDataSetChanged();

                List<Contact> friends = getFriends(contactList);
                if (friends.size() > 0) {
                    sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_friends), friends));
                }
                List<Contact> others = getOthers(contactList);
                if (others.size() > 0) {
                    sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_others), others));
                }
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onSuccess(int result) {
                if (result==401){
                    Toast.makeText(context, "Error fetching contacts - unauthorized access", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*for (int i=0; i<contactList.size(); i++){
            Log.d("ContactList", contactList.get(i).getTitle());
        }
        sectionAdapter.notifyDataSetChanged();*/
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        for (Section section : sectionAdapter.getSectionsMap().values()) {
            if (section instanceof FilterableSection) {
                ((FilterableSection)section).filter(query);
            }
        }
        sectionAdapter.notifyDataSetChanged();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        /*for (Section section : sectionAdapter.getSectionsMap().values()) {
            if (section instanceof FilterableSection) {
                ((FilterableSection)section).filter(query);
            }
        }
        sectionAdapter.notifyDataSetChanged();

        return true;*/
        if (TextUtils.isEmpty(query)) {
            for (Section section : sectionAdapter.getSectionsMap().values()) {
                if (section instanceof FilterableSection) {
                    ((FilterableSection)section).filter(query);
                }
            }
            sectionAdapter.notifyDataSetChanged();
        }

        return true;
    }

    class ContactsSection extends StatelessSection implements FilterableSection {

        String title;
        List<Contact> list;
        List<Contact> filteredList;

        public ContactsSection(String title, List<Contact> list) {
            super(R.layout.subheader_row, R.layout.contact_row);

            this.title = title;
            this.list = list;
            this.filteredList = new ArrayList<>(list);
        }

        @Override
        public int getContentItemsTotal() {
            return filteredList.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            Contact contact = filteredList.get(position);
            itemHolder.title.setText(contact.getTitle());
            itemHolder.nickname.setText(contact.getNickname());
            //itemHolder.avatar.setImageResource(R.drawable.ic_person);
            itemHolder.avatar.setImageResource(context.getResources().getIdentifier("id"+contact.getUserid(), "drawable", context.getPackageName()));

            final int pos = position;

            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact contact = filteredList.get(pos);
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra(Constants.KEY_CONTACT, contact);
                    /*intent.putExtra("firstName", contact.getFirstName());
                    intent.putExtra("lastName", contact.getLastName());
                    intent.putExtra("isFriend", contact.isFriend());*/
                    context.startActivity(intent);
                    //Toast.makeText(getContext(), contact.getTitle(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), String.format("Clicked on position #%s of Section %s", sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()), title), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);

        }

        @Override
        public void filter(String query) {
            if (TextUtils.isEmpty(query)) {
                filteredList = new ArrayList<>(list);
                this.setVisible(true);
            }
            else {
                filteredList.clear();
                query = query.toLowerCase();

                for (int i = 0; i < list.size(); i++) {
                    final String text1 = list.get(i).getNickname().toLowerCase();
                    final String text2 = list.get(i).getFirstName().toLowerCase();
                    final String text3 = list.get(i).getLastName().toLowerCase();
                    if (text1.contains(query) || text2.contains(query) || text3.contains(query)) {
                        filteredList.add(list.get(i));
                    }
                }

                this.setVisible(!filteredList.isEmpty());
            }
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public HeaderViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.subheader);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView title, nickname;
        private final ImageView avatar;

        public ItemViewHolder(View view) {
            super(view);
            rootView = view;
            title = (TextView) view.findViewById(R.id.title);
            nickname = (TextView) view.findViewById(R.id.nickname);
            avatar = (ImageView) view.findViewById(R.id.fAvatar);
        }
    }

    interface FilterableSection {
        void filter(String query);
    }

}
