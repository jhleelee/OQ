package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag0_OQItems;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag1_Feeds;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag2_ChatroomList;
import com.jackleeentertainment.oq.ui.widget.SlidingTabLayout;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String TAG = this.getClass().getSimpleName();

    //Drawer
    TextView tvTitleDrawerHeader;
    TextView tvSubTitleDrawerHeader;
    ImageView ivUserProfile;

    //ViewPager
    MainActivityPagerAdapter mainActivityPagerAdapter;
    ViewPager viewPager;
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);


        tvTitleDrawerHeader = (TextView) header.findViewById(R.id.tvTitle_DrawerHeader);
        tvSubTitleDrawerHeader = (TextView) header.findViewById(R.id
                .tvSubTitle_DrawerHeader);
        ivUserProfile = (ImageView)header. findViewById(R.id
                .ivUserProfile);

        // Instantiate a ViewPager and a PagerAdapter.
        mainActivityPagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(mainActivityPagerAdapter);

        // Assiging the Sliding Tab Layout View
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);
        slidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

                slidingTabLayout.setCustomTabView(
                R.layout.tab0_mainactivity, R.id.textView
        );
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        slidingTabLayout.setViewPager(viewPager);

    }


    @Override
    protected void onResume() {
        super.onResume();
        initUiDataDrawer();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){

            // I Get

            case R.id.nav_take_receipt:

                break;

            case R.id.nav_load_receipt:

                break;

            case R.id.nav_load_sms:

                break;

            case R.id.nav_choose_from_list_get:

                break;

            case R.id.nav_input_manually_get:

                break;



            // I Pay

            case R.id.nav_choose_from_list_pay:

                break;

            case R.id.nav_input_manually_pay:

                break;


            // I Hoibi

            case R.id.nav_new_group:

                break;

            case R.id.nav_list_i_am_master:

                break;

            case R.id.nav_list_i_am_member:

                break;

            // Account


            case R.id.nav_account_setting:

                break;

            case R.id.nav_logout:

                break;

        }

//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_logout) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void initUiDataDrawer() {
        tvTitleDrawerHeader.setText(App.getUname(this));
        tvSubTitleDrawerHeader.setText(App.getUemail(this));

        //set Image
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(App.fbaseStorageRef
                        .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                        .child(App.getUid(this))
                        .child(FStorageNode.createMediaFileNameToDownload(
                                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                App.getUid(this)
                        )))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivUserProfile);

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class MainActivityPagerAdapter extends FragmentPagerAdapter {
        public MainActivityPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {

            Log.d(TAG, "position : " + String.valueOf(position));

            switch (position) {


                case 0:
                    return MainFrag0_OQItems.newInstance();

                case 1:
                    return MainFrag1_Feeds.newInstance();

                case 2:

                    return MainFrag2_ChatroomList.newInstance();


                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }


    }

}
