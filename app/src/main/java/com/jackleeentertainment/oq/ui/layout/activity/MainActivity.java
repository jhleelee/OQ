package com.jackleeentertainment.oq.ui.layout.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag0_OQItems;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag1_Feeds;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag2_ChatroomList;
import com.jackleeentertainment.oq.ui.widget.SlidingTabLayout;
import com.konifar.fab_transformation.FabTransformation;

import java.io.IOException;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String TAG = this.getClass().getSimpleName();
    final int REQ_PICK_IMAGE = 99;
    //Drawer
    TextView tvTitleDrawerHeader;
    TextView tvSubTitleDrawerHeader;
    ImageView ivUserProfile;

    //ViewPager
    MainActivityPagerAdapter mainActivityPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    //FAB
    FloatingActionButton fab;
    Toolbar toolbarFooter;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String searchKey = intent.getStringExtra("data");

            if (searchKey.equals("nav_choose_from_list_get")||
                    searchKey.equals("nav_choose_from_list_pay")||
                    searchKey.equals("nav_list_i_am_master")||
                    searchKey.equals("nav_list_i_am_member")
                    ){

                //fragment0

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mainActivityPagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainActivityPagerAdapter);
        setSupportActionBar(toolbar);


        final TabLayout.Tab tabLayoutHome = tabLayout.newTab();
        final TabLayout.Tab tabLayoutFeed = tabLayout.newTab();
        final TabLayout.Tab tabLayoutChat = tabLayout.newTab();

        //Setting Icons to our respective tabs
        tabLayoutHome.setIcon(R.drawable.ic_view_list_white_24dp);//white
        tabLayoutFeed.setIcon(R.drawable.ic_web_asset_white_24dp);//grey
        tabLayoutChat.setIcon(R.drawable.ic_chat_white_24dp);//grey

        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
        tabLayout.addTab(tabLayoutHome, 0);
        tabLayout.addTab(tabLayoutFeed, 1);
        tabLayout.addTab(tabLayoutChat, 2);

        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color
                .colorTabTextColor));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorTabIndicator));
        tabLayout.setSelectedTabIndicatorHeight(0);
        /*
        Adding a onPageChangeListener to the viewPager
        1st we add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        changes when a viewpager page changes.

        2nd We add the onPageChangeListener to change the icon when the page changes in the view Pager
         */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        /*
                        setting Home as White and rest grey
                        and like wise for all other positions
                         */
                        tabLayoutHome.setIcon(R.drawable.ic_view_list_white_24dp);//white
                        tabLayoutFeed.setIcon(R.drawable.ic_web_asset_black_24dp); //grey
                        tabLayoutChat.setIcon(R.drawable.ic_chat_black_24dp); //grey
                        break;
                    case 1:
                        tabLayoutHome.setIcon(R.drawable.ic_view_list_black_24dp); //grey
                        tabLayoutFeed.setIcon(R.drawable.ic_web_asset_white_24dp); //white
                        tabLayoutChat.setIcon(R.drawable.ic_chat_black_24dp); //grey
                        break;
                    case 2:
                        tabLayoutHome.setIcon(R.drawable.ic_view_list_black_24dp); //grey
                        tabLayoutFeed.setIcon(R.drawable.ic_web_asset_black_24dp); //grey
                        tabLayoutChat.setIcon(R.drawable.ic_chat_white_24dp); //white
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        /*
                        setting Home as White and rest grey
                        and like wise for all other positions
                         */
                        tabLayoutHome.setIcon(R.drawable.ic_view_list_white_24dp);//white
                        tabLayoutFeed.setIcon(R.drawable.ic_web_asset_black_24dp); //grey
                        tabLayoutChat.setIcon(R.drawable.ic_chat_black_24dp); //grey
                        break;
                    case 1:
                        tabLayoutHome.setIcon(R.drawable.ic_view_list_black_24dp); //grey
                        tabLayoutFeed.setIcon(R.drawable.ic_web_asset_white_24dp); //white
                        tabLayoutChat.setIcon(R.drawable.ic_chat_black_24dp); //grey
                        break;
                    case 2:
                        tabLayoutHome.setIcon(R.drawable.ic_view_list_black_24dp); //grey
                        tabLayoutFeed.setIcon(R.drawable.ic_web_asset_black_24dp); //grey
                        tabLayoutChat.setIcon(R.drawable.ic_chat_white_24dp); //white
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * FAB
         */
        toolbarFooter = (Toolbar) findViewById(R.id.toolbar_footer);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabTransformation.with(fab)
                        .duration(300)
                        .transformTo(toolbarFooter);
            }
        });

        /**
         * DrawerLayout
         */

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
        ivUserProfile = (ImageView) header.findViewById(R.id
                .ivUserProfile);


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

    ShareActionProvider  mShareActionProvider;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            //TOP

            case R.id.nav_share:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/html");
                i.putExtra(Intent.EXTRA_SUBJECT, JM.strById(R.string.app_name));
                i.putExtra(Intent.EXTRA_TEXT, JM.strById(R.string.app_slogan) +
                        "\n");
                i.putExtra(Intent.EXTRA_HTML_TEXT,
                        JM.strById(R.string.app_slogan_long) +  "\n" +

                        Uri.parse("http://"));
                startActivity(Intent.createChooser(i, JM.strById(R.string.choose_from_messengers)));

                break;


            // I Get

            case R.id.nav_take_receipt:

                break;

            case R.id.nav_load_receipt:

                Intent intent = new Intent();
                intent.setType("image/*");
                if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_PICK_IMAGE);

                break;

            case R.id.nav_load_sms:

                Intent intentLoadSMS = new Intent(this, SMSListActivity.class);
                startActivity(intentLoadSMS);


                break;

            case R.id.nav_choose_from_list_get:
                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_choose_from_list_get");

                break;

            case R.id.nav_input_manually_get:

                Intent intentManualGet = new Intent(this, NewOQActivity.class);
                intentManualGet.putExtra("GeneralT", OQT.GeneralT.CAUSE);
                intentManualGet.putExtra("WantT", OQT.WantT.GET);
                intentManualGet.putExtra("DoT", OQT.DoT.DIDDO); //delete?
                startActivity(intentManualGet);
                break;


            // I Pay

            case R.id.nav_choose_from_list_pay:
                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_choose_from_list_pay");

                break;

            case R.id.nav_input_manually_pay:
                Intent intentManualPay = new Intent(this, NewOQActivity.class);
                intentManualPay.putExtra("GeneralT", OQT.GeneralT.CAUSE);
                intentManualPay.putExtra("WantT", OQT.WantT.PAY);
                intentManualPay.putExtra("DoT", OQT.DoT.DIDDO); //delete?
                startActivity(intentManualPay);
                break;


            // I Hoibi

            case R.id.nav_new_group:

                break;

            case R.id.nav_list_i_am_master:
                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_list_i_am_master");

                break;

            case R.id.nav_list_i_am_member:
                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_list_i_am_member");
                break;

            // Account


            case R.id.nav_account_setting:

                break;

            case R.id.nav_logout:

                break;

        }


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_PICK_IMAGE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                //Intent.EXTRA_ALLOW_MULTIPLE
            } else {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

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
