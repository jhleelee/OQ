package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.ui.layout.activity.progress.ProgressActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.layout.diafrag.UpdateContactsDiaFrag;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag0_OQItems;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag1_Feeds;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag2_ChatroomList;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameViewHolder;
import com.konifar.fab_transformation.FabTransformation;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean isContactItemExists = false;
    boolean isEmptyViewShown = false;
    boolean isProgressViewShown = true;

    String TAG = this.getClass().getSimpleName();
    final int REQ_PICK_SMS = 98;
    final int REQ_PICK_IMAGE_FOR_PROFILECHANGE = 95;
    final int REQ_PICK_IMAGE_FOR_BACKGROUND = 96;


    //Drawer
    DrawerLayout drawer;
    TextView tvTitleDrawerHeader;
    TextView tvSubTitleDrawerHeader;
    RelativeLayout ro_person_photo_48dip__lessmargin;
    TextView tvAvaDrawer;
    ImageView ivAvaDrawer;
    ImageView ivBG;

    //Drawer2
    TextView tvTitleDrawerHeader2;
    TextView tvUpdateContacts;
    SearchView searchViewRightDrawer;
    FirebaseRecyclerAdapter<Profile, AvatarNameViewHolder> frvAdapterMyRecent,
            frvAdapterAllMyContact;
    RecyclerView rvRightDrawerAll;
    RelativeLayout ro_empty_list_rightdrawer, vProgress_rightdrawer;

    //ViewPager
    MainActivityPagerAdapter mainActivityPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    //FAB
    FloatingActionButton fab;
    Toolbar toolbarFooter;
    RelativeLayout roFootTab0, roFootTab1, roFootTab2;
    TextView tvFootTitle;
    ImageView ivFootTab0, ivFootTab1, ivFootTab2;
    Activity mActivity = this;
    View vScrim;

    //from DiaFrag :onResume
    public boolean isToStartPhoneSync = false;
    public boolean isToStartEmailSync = false;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String searchKey = intent.getStringExtra("data");

            if (searchKey.equals("nav_choose_from_list_get") ||
                    searchKey.equals("nav_choose_from_list_pay") ||
                    searchKey.equals("nav_list_i_am_master") ||
                    searchKey.equals("nav_list_i_am_member")
                    ) {
                tabLayout.setScrollPosition(0, 0f, true);
                viewPager.setCurrentItem(0);
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
        vScrim = (View) findViewById(R.id.vScrim);
        mainActivityPagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainActivityPagerAdapter);
        setSupportActionBar(toolbar);


        final TabLayout.Tab tabLayoutHome = tabLayout.newTab();
        final TabLayout.Tab tabLayoutFeed = tabLayout.newTab();
        final TabLayout.Tab tabLayoutChat = tabLayout.newTab();

        //Setting Icons to our respective tabs
        tabLayoutHome.setIcon(
                JM.tintedDrawable(
                        R.drawable.ic_view_list_white_24dp,
                        R.color.colorTabTextColor,
                        App.getContext())
        ); //grey
        tabLayoutFeed.setIcon(
                JM.tintedDrawable(
                        R.drawable.ic_web_asset_white_24dp,
                        R.color.colorTabTextColorUnselected,
                        App.getContext())
        ); //white
        tabLayoutChat.setIcon(
                JM.tintedDrawable(
                        R.drawable.ic_chat_white_24dp,
                        R.color.colorTabTextColorUnselected,
                        App.getContext())
        ); //grey



        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
        tabLayout.addTab(tabLayoutChat);
        tabLayout.addTab(tabLayoutFeed);
        tabLayout.addTab(tabLayoutHome);

        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

        tabLayout.setTabTextColors(
                ContextCompat.getColorStateList(this, R.color.colorTabTextColor)
        );

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorTabIndicator));
        tabLayout.setSelectedTabIndicatorHeight(JM.pxFromDp(this, 2));
        /*
        Adding a onPageChangeListener to the viewPager
        1st we add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        changes when a viewpager page changes.

        2nd We add the onPageChangeListener to change the icon when the page changes in the view Pager
         */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected " + J.st(tab.getPosition()));

                switch (tab.getPosition()) {


                    case 0:
                        /*
                        setting Home as White and rest grey
                        and like wise for all other positions
                         */
                        tabLayoutHome.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_view_list_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //grey
                        tabLayoutFeed.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_web_asset_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //white
                        tabLayoutChat.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_chat_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        break;


                    case 1:
                        tabLayoutHome.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_view_list_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        tabLayoutFeed.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_web_asset_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //white
                        tabLayoutChat.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_chat_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        break;
                    case 2:
                        tabLayoutHome.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_view_list_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        tabLayoutFeed.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_web_asset_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //white
                        tabLayoutChat.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_chat_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //grey
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
                        tabLayoutHome.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_view_list_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        );//white
                        tabLayoutFeed.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_web_asset_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        tabLayoutChat.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_chat_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        break;
                    case 1:
                        tabLayoutHome.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_view_list_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())

                        ); //grey
                        tabLayoutFeed.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_web_asset_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //white
                        tabLayoutChat.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_chat_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        break;


                    case 2:
                        tabLayoutHome.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_view_list_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        tabLayoutFeed.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_web_asset_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        tabLayoutChat.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_chat_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //white
                        break;


                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);

        /**
         * FAB
         */
        toolbarFooter = (Toolbar) findViewById(R.id.toolbar_footer);


        tvFootTitle = (TextView) findViewById(R.id.tvFootTitle);
        ivFootTab0 = (ImageView) findViewById(R.id.ivFootTab0);
        ivFootTab1 = (ImageView) findViewById(R.id.ivFootTab1);
        ivFootTab2 = (ImageView) findViewById(R.id.ivFootTab2);

        roFootTab0 = (RelativeLayout) findViewById(R.id.roFootTab0);
        roFootTab1 = (RelativeLayout) findViewById(R.id.roFootTab1);
        roFootTab2 = (RelativeLayout) findViewById(R.id.roFootTab2);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_add_white_48dp,
                        R.color.colorPrimary,
                        this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabTransformation.with(fab)
                        .duration(200)
                        .transformTo(toolbarFooter);
                vScrim.setVisibility(View.VISIBLE);
            }
        });

        vScrim.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FabTransformation.with(fab)
                        .duration(100)
                        .transformFrom(toolbarFooter);
                v.setVisibility(View.GONE);
                return false;
            }
        });

        ivFootTab0.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_photo_camera_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );


        ivFootTab1.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_credit_card_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );

        // upto provider
        ivFootTab2.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_create_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );


        roFootTab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResultToTakeReceipt();
            }
        });
        roFootTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResultToLoadSMS();
            }
        });
        roFootTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, NewOQActivity.class);
                intent.putExtra("mDoWhat", OQT.DoWhat.GET);
                startActivity(intent);
            }
        });
        /**
         * DrawerLayout
         */

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d(TAG, "onDrawerOpened()");
                initUiDataDrawer();
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Beppi
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);
        tvTitleDrawerHeader = (TextView) header.findViewById(R.id.tvTitle_DrawerHeader);
        tvSubTitleDrawerHeader = (TextView) header.findViewById(R.id
                .tvSubTitle_DrawerHeader);
        ro_person_photo_48dip__lessmargin = (RelativeLayout) header.findViewById(R.id
                .ro_person_photo_48dip__lessmargin);
        tvAvaDrawer = (TextView) ro_person_photo_48dip__lessmargin.findViewById(R.id
                .tvAva);
        ivAvaDrawer = (ImageView) ro_person_photo_48dip__lessmargin.findViewById(R.id
                .ivAva);
        ivBG = (ImageView) header.findViewById(R.id
                .ivBG);


        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mActivity, ProfileActivity.class);
//                intent.putExtra("isMe", true);
//                startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("diaFragT", DiaFragT.MyProfileBackgroundPhoto);
                showDialogFragment(bundle);
            }
        });


        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        navigationView2.setNavigationItemSelectedListener(this);

        tvTitleDrawerHeader2 = (TextView) findViewById(R.id.tvTitle_DrawerHeader2);
        tvTitleDrawerHeader2.setText(JM.strById(R.string.contacts));

        tvUpdateContacts = (TextView) findViewById(R.id.tvUpdateContacts);

        searchViewRightDrawer = (SearchView) findViewById(R.id.searchViewRightDrawer);
//        rvRightDrawerAll = (RecyclerView) navigationView2.findViewById(R.id.rvRightDrawerAll);


        rvRightDrawerAll = (RecyclerView) findViewById(R.id.rvRightDrawerAll);
        ro_empty_list_rightdrawer = (RelativeLayout) findViewById(R.id.ro_empty_list_rightdrawer);
        vProgress_rightdrawer = (RelativeLayout) findViewById(R.id.vProgress_rightdrawer);


        rvRightDrawerAll.setHasFixedSize(true);
        rvRightDrawerAll.setLayoutManager(new LinearLayoutManager(this));


        rvRightDrawerAll.setAdapter(frvAdapterAllMyContact);

        tvUpdateContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("diaFragT", DiaFragT.UpdateContacts);
                bundle.putString("activityT", UpdateContactsDiaFrag.ActivityT.MainActivity);
                showDialogFragment(bundle);
            }
        });


        //the intent filter will be action = "com.example.demo_service.action.SERVICE_FINISHED"
        IntentFilter filter = new IntentFilter("com.jackleeentertainment.oq." + LBR.IntentFilterT.MainActivityDrawerMenu);
        // register the receiver:
        registerReceiver(mMessageReceiver, filter);

    }

    String myUid = null;

    @Override
    protected void onResume() {
        super.onResume();
        initUiDataDrawer();
        myUid = App.getUid(this);

        initContactsAdapter();
        rvRightDrawerAll.setAdapter(frvAdapterAllMyContact);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isContactItemExists = false) {

                    if (frvAdapterAllMyContact.getItemCount() == 0) {

                        vProgress_rightdrawer.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list_rightdrawer.setVisibility(View.VISIBLE);
                        isEmptyViewShown = true;

                        isContactItemExists = false;


                    } else {
                        vProgress_rightdrawer.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list_rightdrawer.setVisibility(View.GONE);
                        isEmptyViewShown = false;

                        isContactItemExists = true;

                    }
                } else {
                    vProgress_rightdrawer.setVisibility(View.GONE);
                    isProgressViewShown = false;

                    ro_empty_list_rightdrawer.setVisibility(View.GONE);
                    isEmptyViewShown = true;

                }
            }
        }, 3000);

        if (isToStartPhoneSync) {
            Intent intent = new Intent(this, ProgressActivity.class);
            intent.putExtra("progressT", ProgressT.UPDATE_CONTACT_PHONE);
            startActivity(intent);
            isToStartPhoneSync = false;
        }

        if (isToStartEmailSync) {
            Intent intent = new Intent(this, ProgressActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("progressT", ProgressT.UPDATE_CONTACT_EMAIL);
            intent.putExtras(bundle);
            startActivity(intent);
            isToStartEmailSync = false;
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        searchView.setQueryHint(JM.strById(R.string.search_dotdot));

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(
                        getComponentName()));

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Toast.makeText(getApplicationContext(), "onQueryTextSubmit", Toast.LENGTH_SHORT).show();

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Toast.makeText(getApplicationContext(), "onQueryTextChange", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Begin", Toast.LENGTH_SHORT).show();
            }
        });

        View vFriends =
                MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        vFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
                initContactsAdapter();

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (item != null && item.getItemId() == android.R.id.home) {
//            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                mDrawerLayout.closeDrawer(Gravity.RIGHT);
//            } else {
//                mDrawerLayout.openDrawer(Gravity.RIGHT);
//            }
//        }


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        // Beppi
        if (id == R.id.action_friends) {
            drawer.openDrawer(GravityCompat.END, true);
            initContactsAdapter();
            //noinspection SimplifiableIfStatement
        }

        return super.onOptionsItemSelected(item);


    }

    ShareActionProvider mShareActionProvider;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            //TOP

            case R.id.nav_share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        JM.strById(R.string.app_slogan_long) + "\n" +
                                "https://play.google" +
                                ".com/store/apps/details?id=com.google.android.apps.plus");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/html");
//                i.putExtra(Intent.EXTRA_SUBJECT, JM.strById(R.string.app_name));
////                i.putExtra(Intent.EXTRA_TEXT, JM.strById(R.string.app_slogan) +
////                        "\n");
//                i.putExtra(Intent.EXTRA_HTML_TEXT,
//                        JM.strById(R.string.app_slogan_long) + "\n" +
//
//                                Uri.parse("http://"));
//                startActivity(Intent.createChooser(i, JM.strById(R.string.choose_from_messengers)));
//
//                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("address", "12125551212");
//                smsIntent.putExtra("sms_body","Body of Message");
//                startActivity(smsIntent);
                break;


            // I Get

//            case R.id.nav_take_receipt:
//                startActivityForResultToTakeReceipt();
//
//                break;
//
//            case R.id.nav_load_receipt:
//                startActivityForResultToLoadReceiptGallery();
//                break;

            case R.id.nav_load_sms:
                startActivityForResultToLoadSMS();
                break;

//            case R.id.nav_choose_from_list_get:
//                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_choose_from_list_get");
//
//                break;

            case R.id.nav_input_manually_get:

                Intent intentManualGet = new Intent(this, NewOQActivity.class);
                intentManualGet.putExtra("mDoWhat", OQT.DoWhat.GET);
                startActivity(intentManualGet);
                break;


            // I Pay

//            case R.id.nav_choose_from_list_pay:
//                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_choose_from_list_pay");
//
//                break;

            case R.id.nav_input_manually_pay:
                Intent intentManualPay = new Intent(this, NewOQActivity.class);
                intentManualPay.putExtra("mDoWhat", OQT.DoWhat.PAY);
                startActivity(intentManualPay);
                break;


            // I Hoibi

            case R.id.nav_new_group:
                Intent intentNewGroup = new Intent(this, NewGroupActivity.class);
                startActivity(intentNewGroup);
                break;

//            case R.id.nav_list_i_am_master:
//                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_list_i_am_master");
//
//                break;
//
//            case R.id.nav_list_i_am_member:
//                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_list_i_am_member");
//                break;

            // Account
            case R.id.nav_account_buy:
                break;


            case R.id.nav_account_setting:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                App.logout(mActivity);
                break;

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void initUiDataDrawer() {
        tvTitleDrawerHeader.setText(App.getUname(this));
        tvSubTitleDrawerHeader.setText(App.getUemail(this));


        JM.glideProfileThumb(
                App.getUid(mActivity),
                App.getUname(mActivity),
                ivAvaDrawer,
                tvAvaDrawer,
                mActivity
        );


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() " + J.st(requestCode) + " " + J.st(resultCode) + " ");

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQ_PICK_IMAGE_FOR_PROFILECHANGE) {

                if (data != null
                        && data.getData() != null) {
                    Log.d(TAG, "data available");
                    Uri imageUri = data.getData();
                    if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                        //Intent.EXTRA_ALLOW_MULTIPLE
                        Log.d(TAG, "VERSION.SDK_INT >= 18 " + data.getData().toString());
                        CropImage.activity(imageUri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(this);

                    } else {
                        Log.d(TAG, "VERSION.SDK_INT < 18 " + data.getData().toString());
                        CropImage.activity(imageUri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(this);
                    }
                }


            }


            if (requestCode == REQ_PICK_IMAGE_FOR_BACKGROUND) {

                if (data != null
                        && data.getData() != null) {
                    Log.d(TAG, "data available");
                    Uri imageUri = data.getData();
                    {

                        uploadBgPhoto(imageUri, App.getUid(this));

                    }
                }


            }


        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uploadProfPhoto(resultUri, App.getUid(this));

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d(TAG, error.toString());

            }
        }

    }

    StorageReference mTempStorageRefBG;
    StorageReference mTempStorageRefORIG;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx36;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx48;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx72;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx96;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx144;  //mTempStorageRef was previously used to transfer data.
    ArrayList<StorageReference> arlRef = new ArrayList<>();
    int counter = 0;

    public void uploadProfPhoto(
            final Uri uri,
            String uid
    ) {
        Log.d(TAG, "uploadProfPhoto()");

        if (uri == null) {
            Log.d(TAG, "uri==null");

            return;
        }
        if (uid == null) {
            Log.d(TAG, "uid==null");

            return;
        }

        /**
         * get Bitmap
         */

        try {

            ArrayList<Bitmap> arlBmp = new ArrayList<>();
            ArrayList<String> arlStr = new ArrayList<>();


            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            arlBmp.add(bitmap);
            arlStr.add(FStorageNode.pxProfileT.ORIG);
            //use bitmap and create thumnail file
            Bitmap thumbnail_036 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 36, 36);
            arlStr.add(FStorageNode.pxProfileT.px36);
            arlBmp.add(thumbnail_036);

            Bitmap thumbnail_048 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 48, 48);
            arlStr.add(FStorageNode.pxProfileT.px48);
            arlBmp.add(thumbnail_048);

            Bitmap thumbnail_072 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 72, 72);
            arlBmp.add(thumbnail_072);
            arlStr.add(FStorageNode.pxProfileT.px72);

            Bitmap thumbnail_096 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 96, 96);
            arlBmp.add(thumbnail_096);
            arlStr.add(FStorageNode.pxProfileT.px96);

            Bitmap thumbnail_144 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 144, 144);
            arlBmp.add(thumbnail_144);
            arlStr.add(FStorageNode.pxProfileT.px144);

            arlRef = new ArrayList<>();
            arlRef.add(mTempStorageRefORIG);
            arlRef.add(mTempStorageRefpx36);
            arlRef.add(mTempStorageRefpx48);
            arlRef.add(mTempStorageRefpx72);
            arlRef.add(mTempStorageRefpx96);
            arlRef.add(mTempStorageRefpx144);

            counter = 0;
            for (int i = 0; i < 6; i++) {

                Log.d(TAG, "uploadProfPhoto() loop " + J.st(i));


                ByteArrayOutputStream os = new ByteArrayOutputStream();

                if (arlBmp.get(i).compress(Bitmap.CompressFormat.JPEG, 100, os)) {
                    byte[] bytes = os.toByteArray();


                    /**
                     metaData
                     **/
                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .setCustomMetadata("uid", uid)
                            .build();
                    /**
                     path
                     **/
                    arlRef.set(i,
                            App.fbaseStorageRef
                                    .child(FStorageNode.FirstT.PROFILE_PHOTO)
                                    .child(arlStr.get(i))
                                    .child(uid));

                    /**
                     main
                     **/
                    UploadTask uploadTask =
                            arlRef.get(i)
                                    .putBytes(bytes, metadata);

                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            System.out.println("Upload is " + progress + "% done");
                        }
                    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload is paused");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Log.d(TAG, "onFailure");
                            Log.d(TAG, exception.toString());

                            counter++;

                            if (counter == 6) {
                                JM.glideProfileThumb(
                                        App.getUid(mActivity),
                                        App.getUname(mActivity),
                                        ivAvaDrawer,
                                        tvAvaDrawer,
                                        mActivity
                                );

                            }


                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Handle successful uploads on complete
                            Log.d(TAG, "onSuccess");
                            Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                            counter++;

                            if (counter == 6) {
                                JM.glideProfileThumb(
                                        App.getUid(mActivity),
                                        App.getUname(mActivity),
                                        ivAvaDrawer,
                                        tvAvaDrawer,
                                        mActivity
                                );

                            }

                        }
                    });


                }
            }


        } catch (FileNotFoundException e) {
            Log.d(TAG, e.toString());
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }
    }


    public void uploadBgPhoto(
            final Uri uri,
            final String uid
    ) {
        Log.d(TAG, "uploadBgPhoto()");

        if (uri == null) {
            Log.d(TAG, "uri==null");

            return;
        }
        if (uid == null) {
            Log.d(TAG, "uid==null");
            return;
        }

        /**
         * get Bitmap
         */

        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)) {
                byte[] bytes = os.toByteArray();

                /**
                 metaData
                 **/
                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setContentType("image/jpg")
                        .setCustomMetadata("uid", uid)
                        .build();
                /**
                 path
                 **/
                mTempStorageRefBG =
                        App.fbaseStorageRef
                                .child(FStorageNode.FirstT.BG_PHOTO)
                                .child(uid);

                /**
                 main
                 **/
                UploadTask uploadTask =
                        mTempStorageRefBG
                                .putBytes(bytes, metadata);

                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        System.out.println("Upload is " + progress + "% done");
                    }
                }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                        System.out.println("Upload is paused");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.d(TAG, "onFailure");
                        Log.d(TAG, exception.toString());

                        J.TOAST(JM.strById(R.string.fail_upload_bg));


                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Handle successful uploads on complete
                        Log.d(TAG, "onSuccess");
                        Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                        //set Image
                        Glide.with(mActivity)
                                .using(new FirebaseImageLoader())
                                .load(App.fbaseStorageRef
                                        .child(FStorageNode.FirstT.BG_PHOTO)
                                        .child(uid))
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        ivBG.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        ivBG.setVisibility(View.VISIBLE);
                                        return false;
                                    }
                                })
                                .into(ivBG);

                    }
                });


            }


        } catch (FileNotFoundException e) {
            Log.d(TAG, e.toString());
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }
    }


    /**
     * A simple pager frvAdapterAllMyContact that represents 5 ScreenSlidePageFragment objects, in
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


    public void startActivityForResultToLoadSMS() {
        Intent intentLoadSMS = new Intent(this, SMSListActivity.class);
        startActivityForResult(intentLoadSMS, REQ_PICK_SMS);
    }


    public void startActivityForResultPhotoGalleryToPROFILECHANGE() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.change_profile_photo)),
                REQ_PICK_IMAGE_FOR_PROFILECHANGE);
    }

    public void startActivityForResultPhotoGalleryToBGCHANGE() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.change_background_photo)),
                REQ_PICK_IMAGE_FOR_BACKGROUND);
    }

    public void initContactsAdapter() {
        frvAdapterAllMyContact = new FirebaseRecyclerAdapter<Profile, AvatarNameViewHolder>
                (Profile.class,
                        R.layout.lo_avatar_name,
                        AvatarNameViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyContacts)
                                .child(App.getUid(this))
                ) {
            public void populateViewHolder(final AvatarNameViewHolder avatarNameViewHolder,
                                           final Profile profile,
                                           int position) {
                JM.glideProfileThumb(
                        profile,
                        avatarNameViewHolder.ro_person_photo_iv,
                        avatarNameViewHolder.ro_person_photo_tv,
                        mActivity
                );
                avatarNameViewHolder.tvTitle__lo_avatar_name
                        .setText(profile.getFull_name());

                avatarNameViewHolder.mView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mActivity, ProfileActivity.class);
                                intent.putExtra("Profile", profile);
                                intent.putExtra("isMe", false);
                                startActivity(intent);
                                if (drawer.isDrawerOpen(GravityCompat.END)) {
                                    drawer.closeDrawer(GravityCompat.END);
                                }
                            }
                        }
                );


                isContactItemExists = true;

                if (isProgressViewShown) {
                    vProgress_rightdrawer.setVisibility(View.GONE);
                    isProgressViewShown = false;
                }

                if (isEmptyViewShown) {
                    ro_empty_list_rightdrawer.setVisibility(View.GONE);
                    isEmptyViewShown = false;
                }

            }


        };
    }


}
