package com.example.boush.dreamchat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    ViewPager pager;
    private Toolbar toolbar;
    TabLayout tabLayout;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private FloatingActionButton fab;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://127.0.0.1:8080");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTitle = mDrawerTitle = getTitle();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        

        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);


        final TabLayout.Tab conversations = tabLayout.newTab();
        final TabLayout.Tab friends = tabLayout.newTab();


        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        conversations.setText((getString(R.string.tab_conversations)));
        friends.setText((getString(R.string.tab_contacts)));

        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        )


        {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

               for(int i =0;i<=3;i++){
                   mDrawerList.setItemChecked(i,false);
               }


            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };


        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];
        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_action_settings,"Profile");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_action_user,"Settings");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_action_logout,"Help");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_action_user,"Logout");

        DrawerItemCustomAdapter adapters = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setAdapter(adapters);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        tabLayout.addTab(conversations, 0);
        tabLayout.addTab(friends,1);


        FragmentManager manager=getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager) ;
        pager.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.contactsFAB);
        fab.hide();
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "FAB clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    fab.show();
                }
                if (tab.getPosition() == 0) {
                    fab.hide();
                }
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                fab.hide(new FloatingActionButton.OnVisibilityChangedListener() { // Hide FAB
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fab.show(); // After FAB is hidden show it again
                    }
                });
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(mDrawerToggle !=null){
            mDrawerToggle.syncState();
        }



    }



    public class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            selectItem(position);


        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new MeFragment();

                break;
            case 1:
                fragment = new SettingsFragment();

                break;
            case 2:
                fragment = new HelpFragment();
                break;
            case 3 :
                mSocket.emit("client:user_disconnected");
                mSocket.disconnect();
                showMessage();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("MenuActivity").commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void showMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
        alertDialog.setTitle("Log out");
        alertDialog.setMessage("Are you sure, you want to logout? ");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new LogoutTask(new AsyncTaskCallback() {
                            @Override
                            public void onTaskCompleted(List result) {

                            }

                            @Override
                            public void onTaskCompleted(Contact result) {

                            }

                            @Override
                            public void onTaskCompleted(int result) {
                                if (result==200){
                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.remove(Constants.KEY_TOKEN);
                                    editor.remove(Constants.KEY_USERID);
                                    editor.apply();
                                    Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (result==401){
                                    Toast.makeText(getApplicationContext(), result+ "- authorization failed. Logout unsuccessful.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onTaskCompleted(String result) {

                            }
                        }).execute(getApplicationContext());
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public class LogoutTask extends AsyncTask<Context, Void, Void>{
        private AsyncTaskCallback listener;

        public LogoutTask(AsyncTaskCallback listener){
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Context... params) {
            new Server().logout(params[0], new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {

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

}
