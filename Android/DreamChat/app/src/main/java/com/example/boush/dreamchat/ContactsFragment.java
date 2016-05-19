package com.example.boush.dreamchat;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment implements SearchView.OnQueryTextListener{


    public ContactsFragment() {
        // Required empty public constructor
    }

    private Context context;
    private List<Contact> contactList = new ArrayList<>();
    private RecyclerView recyclerView;
    //private ContactsAdapter mAdapter;
    private SearchView search;
    private SectionedRecyclerViewAdapter sectionAdapter;
    //private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        context = getActivity();
        //rootView=view;

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_contacts);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        /*mAdapter = new ContactsAdapter(contactList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);*/

        sectionAdapter = new SectionedRecyclerViewAdapter();

        prepareContactData();

        List<Contact> friends = getFriends(contactList);
        if (friends.size() > 0) {
            sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_friends), friends));
        }
        List<Contact> others = getOthers(contactList);
        if (others.size() > 0) {
            sectionAdapter.addSection(new ContactsSection(getString(R.string.subheader_others), others));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);

        search = (SearchView) view.findViewById(R.id.searchView);
        search.setOnQueryTextListener(this);

        return view;
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

    private void prepareContactData() {
        Contact contact = new Contact("Iba", "Meliško", "Meliško", false);
        contactList.add(contact);

        contact = new Contact("Patrik", "Patinák", "Patres", true);
        contactList.add(contact);

        contact = new Contact("Martin", "Tarhanič", "Matolator", true);
        contactList.add(contact);

        contact = new Contact("Monika", "Jaššová", "monikka", true);
        contactList.add(contact);

        contact = new Contact("Michal", "Borovský", "Michaljevič", true);
        contactList.add(contact);

        contact = new Contact("Matúš", "Kokoška", "DreamTeam", true);
        contactList.add(contact);

        contact = new Contact("Roman", "Klimčík", "Global Logic", false);
        contactList.add(contact);

        contact = new Contact("X", "Y", "Slovensko", false);
        contactList.add(contact);

        contact = new Contact("Meno", "Priezvisko", "Nick", false);
        contactList.add(contact);

        sectionAdapter.notifyDataSetChanged();
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        for (Section section : sectionAdapter.getSectionsMap().values()) {
            if (section instanceof FilterableSection) {
                ((FilterableSection)section).filter(query);
            }
        }
        sectionAdapter.notifyDataSetChanged();

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
            itemHolder.avatar.setImageResource(R.drawable.ic_person);

            final int pos = position;

            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact contact = filteredList.get(pos);
                    Toast.makeText(getContext(), contact.getTitle(), Toast.LENGTH_SHORT).show();
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
