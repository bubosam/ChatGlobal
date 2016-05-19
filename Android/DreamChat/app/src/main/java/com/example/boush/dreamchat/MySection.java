package com.example.boush.dreamchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by Client on 19.5.2016.
 */
class MySection extends StatelessSection {

    List<String> myList = Arrays.asList("Friends", "Others");

    public MySection() {
        // call constructor with layout resources for this Section header and items
        super(R.layout.subheader_row, R.layout.contact_row);
    }

    @Override
    public int getContentItemsTotal() {
        return myList.size(); // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        itemHolder.tvItem.setText(myList.get(position));
    }
}

class MyItemViewHolder extends RecyclerView.ViewHolder {

    final TextView tvItem;

    public MyItemViewHolder(View itemView) {
        super(itemView);

        tvItem = (TextView) itemView.findViewById(R.id.title);
    }
}
