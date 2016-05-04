package com.example.boush.dreamchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Client on 4.5.2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag=new MeFragment();
                break;
            case 1:
                frag=new ConversationsFragment();
                break;
            case 2:
                frag=new FriendsFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title ="Me";
                break;
            case 1:
                title="Conversations";
                break;
            case 2:
                title="Friends";
                break;
        }

        return title;
    }
}

