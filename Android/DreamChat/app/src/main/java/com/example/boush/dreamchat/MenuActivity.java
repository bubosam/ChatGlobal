package com.example.boush.dreamchat;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabLayout;
    private ImageButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        menu = (ImageButton) findViewById(R.id.imageButton2);

        final TabLayout.Tab conversations = tabLayout.newTab();
        final TabLayout.Tab friends = tabLayout.newTab();




        conversations.setText((getString(R.string.tab_conversations)));
        friends.setText((getString(R.string.tab_contacts)));



        tabLayout.addTab(conversations, 0);
        tabLayout.addTab(friends,1);


        FragmentManager manager=getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager) ;
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(menu);
                openContextMenu(menu);



            }
        });


    }

    final int CONTEXT_MENU_VIEW =1;
    final int CONTEXT_MENU_EDIT =2;
    final int CONTEXT_MENU_ARCHIVE =3;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        //Context menu
        menu.setHeaderTitle("My Context Menu");
        menu.add(Menu.NONE, CONTEXT_MENU_VIEW, Menu.NONE, "Profile");
        menu.add(Menu.NONE, CONTEXT_MENU_EDIT, Menu.NONE, "Help");
        menu.add(Menu.NONE, CONTEXT_MENU_ARCHIVE, Menu.NONE, "Settings");
        menu.add("Logout");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId())
        {
            case CONTEXT_MENU_VIEW:
            {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.menuActivity, new MeFragment())
                        .commit();
            }
            break;
            case CONTEXT_MENU_EDIT:
            {
                // Edit Action

            }
            break;
            case CONTEXT_MENU_ARCHIVE:
            {

            }
            break;


        }

        return super.onContextItemSelected(item);
    }


}
