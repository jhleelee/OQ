package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.storage.StorageReference;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.firebase.storage.Upload;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag0_OQItems;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag1_Feeds;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag2_ChatroomList;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameViewHolder;
import com.konifar.fab_transformation.FabTransformation;
import com.soundcloud.android.crop.Crop;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String TAG = this.getClass().getSimpleName();
    final int REQ_PICK_IMAGE_FOR_RECEIPT = 99;
    final int REQ_PICK_SMS = 98;
    final int REQ_TAKE_TESSER_RECEIPT = 97;
    final int REQ_PROCESS_TESSER_RECEIPT = 96;
    final int REQ_PICK_IMAGE_FOR_PROFILECHANGE = 95;
    Uri outputCropUri;


    //Drawer
    DrawerLayout drawer;
    TextView tvTitleDrawerHeader;
    TextView tvSubTitleDrawerHeader;
    RelativeLayout ro_person_photo_48dip__lessmargin;
    TextView ro_person_photo_tv;
    ImageView ro_person_photo_iv;

    //Drawer2
    TextView tvTitleDrawerHeader2;


    TextView tvRecentTitle;
    SearchView searchViewRightDrawer;
    FirebaseRecyclerAdapter<Profile, AvatarNameViewHolder> frvAdapterMyRecent,
            frvAdapterAllMyContact;
    RecyclerView rvRightDrawerAll, rvRightDrawerRecent;

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
                startActivityForResultToTakeReceipt();
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
                intent.putExtra("OQTWantT_Future", OQT.WantT.GET);
                startActivity(intent);
            }
        });
        /**
         * DrawerLayout
         */

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        ro_person_photo_48dip__lessmargin = (RelativeLayout) header.findViewById(R.id
                .ro_person_photo_48dip__lessmargin);
        ro_person_photo_tv = (TextView) ro_person_photo_48dip__lessmargin.findViewById(R.id
                .ro_person_photo_tv);
        ro_person_photo_iv = (ImageView) ro_person_photo_48dip__lessmargin.findViewById(R.id
                .ro_person_photo_iv);

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
        tvRecentTitle = (TextView) navigationView2.findViewById(R.id.tvRecentTitle);
        rvRightDrawerRecent = (RecyclerView) navigationView2.findViewById(R.id.rvRightDrawerRecent);
        searchViewRightDrawer = (SearchView) navigationView2.findViewById(R.id.searchViewRightDrawer);
        rvRightDrawerAll = (RecyclerView) navigationView2.findViewById(R.id.rvRightDrawerAll);


        RecyclerView rvRightDrawerAll = (RecyclerView) findViewById(R.id.rvRightDrawerAll);
        RecyclerView rvRightDrawerRecent = (RecyclerView) findViewById(R.id.rvRightDrawerRecent);

        rvRightDrawerAll.setHasFixedSize(true);
        rvRightDrawerAll.setLayoutManager(new LinearLayoutManager(this));

        rvRightDrawerRecent.setHasFixedSize(true);
        rvRightDrawerRecent.setLayoutManager(new LinearLayoutManager(this));

        frvAdapterAllMyContact = new FirebaseRecyclerAdapter<Profile, AvatarNameViewHolder>
                (Profile.class,
                        R.layout.lo_avatar_name,
                        AvatarNameViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyContacts)
                                .child(App.getUid(this))
                ) {
            public void populateViewHolder(final AvatarNameViewHolder avatarNameViewHolder,
                                           Profile profile,
                                           int position) {
                avatarNameViewHolder.tvTitle__lo_avatar_name
                        .setText(profile.getFull_name()
                        );


                //set Image
                Glide.with(mActivity)
                        .using(new FirebaseImageLoader())
                        .load(App.fbaseStorageRef
                                .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                .child(App.getUid(mActivity))
                                .child(FStorageNode.createMediaFileNameToDownload(
                                        FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                        App.getUid(mActivity)
                                )))
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<StorageReference, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                                avatarNameViewHolder.ro_person_photo_iv.setVisibility(View.GONE);
                                avatarNameViewHolder.ro_person_photo_tv.setVisibility(View.VISIBLE);
                                avatarNameViewHolder.ro_person_photo_tv.setText(App.getUname(mActivity).substring(0, 1));
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                avatarNameViewHolder.ro_person_photo_iv.setVisibility(View.VISIBLE);
                                avatarNameViewHolder.ro_person_photo_tv.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(avatarNameViewHolder.ro_person_photo_iv);


            }
        };

        rvRightDrawerAll.setAdapter(frvAdapterAllMyContact);


        frvAdapterMyRecent = new FirebaseRecyclerAdapter<Profile, AvatarNameViewHolder>
                (Profile.class,
                        R.layout.lo_avatar_name,
                        AvatarNameViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyRecent)
                                .child(App.getUid(this))
                ) {
            public void populateViewHolder(
                  final  AvatarNameViewHolder avatarNameViewHolder,
                    final Profile profile, int position) {

                avatarNameViewHolder.tvTitle__lo_avatar_name
                        .setText(profile.getFull_name()
                        );

                avatarNameViewHolder.tvTitle__lo_avatar_name
                        .setTextColor(JM.colorById(R.color.text_black_87)
                        );
                //set Image
                if (profile.getUid() != null) {
                    Glide.with(mActivity)
                            .using(new FirebaseImageLoader())
                            .load(App.fbaseStorageRef
                                    .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                    .child(profile.getUid())
                                    .child(FStorageNode.createMediaFileNameToDownload(
                                            FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                            profile.getUid()
                                    )))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(avatarNameViewHolder.ro_person_photo_iv);



                    //set Image
                    Glide.with(mActivity)
                            .using(new FirebaseImageLoader())
                            .load(App.fbaseStorageRef
                                    .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                    .child(App.getUid(mActivity))
                                    .child(FStorageNode.createMediaFileNameToDownload(
                                            FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                            App.getUid(mActivity)
                                    )))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<StorageReference, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    avatarNameViewHolder.ro_person_photo_iv.setVisibility(View.GONE);
                                    avatarNameViewHolder.ro_person_photo_tv.setVisibility(View.VISIBLE);
                                    avatarNameViewHolder.ro_person_photo_tv.setText(App.getUname(mActivity).substring(0, 1));
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    avatarNameViewHolder.ro_person_photo_iv.setVisibility(View.VISIBLE);
                                    avatarNameViewHolder.ro_person_photo_tv.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(avatarNameViewHolder.ro_person_photo_iv);



                }

                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("diaFragT", DiaFragT.TransactChatOrShowProfile);
                        bundle.putString("title", profile.getFull_name() + " 님");
                        bundle.putSerializable("Profile", profile);

                        showDialogFragment(bundle);
                    }
                };

                avatarNameViewHolder.ro_person_photo_48dip__lo_avatar_name
                        .setOnClickListener(onClickListener);

                avatarNameViewHolder.ro_person_photo_iv
                        .setOnClickListener(onClickListener);

                avatarNameViewHolder.ro_person_photo_tv
                        .setOnClickListener(onClickListener);

                avatarNameViewHolder.tvTitle__lo_avatar_name
                        .setOnClickListener(onClickListener);


            }
        };
        rvRightDrawerRecent.setAdapter(frvAdapterMyRecent);


        //the intent filter will be action = "com.example.demo_service.action.SERVICE_FINISHED"
        IntentFilter filter = new IntentFilter("com.jackleeentertainment.oq." + LBR.IntentFilterT.MainActivityDrawerMenu);
        // register the receiver:
        registerReceiver(mMessageReceiver, filter);

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

        //noinspection SimplifiableIfStatement


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
                intentManualGet.putExtra("OQTWantT_Future", OQT.WantT.GET);
                startActivity(intentManualGet);
                break;


            // I Pay

//            case R.id.nav_choose_from_list_pay:
//                LBR.send(LBR.IntentFilterT.MainActivityDrawerMenu, "nav_choose_from_list_pay");
//
//                break;

            case R.id.nav_input_manually_pay:
                Intent intentManualPay = new Intent(this, NewOQActivity.class);
                intentManualPay.putExtra("OQTWantT_Future", OQT.WantT.PAY);
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
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ro_person_photo_iv.setVisibility(View.GONE);
                        ro_person_photo_tv.setVisibility(View.VISIBLE);
                        ro_person_photo_tv.setText(App.getUname(mActivity).substring(0, 1));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ro_person_photo_iv.setVisibility(View.VISIBLE);
                        ro_person_photo_tv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ro_person_photo_iv)

        ;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQ_PICK_IMAGE_FOR_RECEIPT
                    && data != null
                    && data.getData() != null) {
                Log.d(TAG, "data available");

                if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                    //Intent.EXTRA_ALLOW_MULTIPLE
                    Log.d(TAG, "VERSION.SDK_INT >= 18 " + data.getData().toString());
                    Intent intent = new Intent(this, TesserPhotoListActivity.class);
                    intent.putExtra("uriPhoto", data.getData().toString());
                    startActivityForResult(intent,
                            REQ_PROCESS_TESSER_RECEIPT);
                } else {
                    Log.d(TAG, "VERSION.SDK_INT < 18 " + data.getData().toString());
                    Intent intent = new Intent(this, TesserPhotoListActivity.class);
                    intent.putExtra("uriPhoto", data.getData().toString());
                    startActivityForResult(intent,
                            REQ_PROCESS_TESSER_RECEIPT);
                }
            } else if (requestCode == REQ_PICK_IMAGE_FOR_PROFILECHANGE) {

                if (data != null
                        && data.getData() != null) {
                    Log.d(TAG, "data available");

                    if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                        //Intent.EXTRA_ALLOW_MULTIPLE
                        Log.d(TAG, "VERSION.SDK_INT >= 18 " + data.getData().toString());
                        Crop.of(data.getData(), outputCropUri).asSquare().start(this);


                    } else {
                        Log.d(TAG, "VERSION.SDK_INT < 18 " + data.getData().toString());
                        Crop.of(data.getData(), outputCropUri).asSquare().start(this);
                    }
                }


            } else if (requestCode == Crop.REQUEST_CROP) {
                Upload.uploadMyProfileImagesToFirebaseStorage(outputCropUri, this);
            }

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


  public   void startActivityForResultToTakeReceipt() {
        Intent intentTesserCam = new Intent(this, TesserCameraActivity.class);
        startActivityForResult(intentTesserCam, REQ_TAKE_TESSER_RECEIPT);
    }

    ;


    public void startActivityForResultToLoadReceiptGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.receipt_photo)),
                REQ_PICK_IMAGE_FOR_RECEIPT);
    }

    public void startActivityForResultToLoadSMS() {
        Intent intentLoadSMS = new Intent(this, SMSListActivity.class);
        startActivityForResult(intentLoadSMS, REQ_PICK_SMS);
    }


    public  void startActivityForResultPhotoGalleryToPROFILECHANGE() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.receipt_photo)),
                REQ_PICK_IMAGE_FOR_PROFILECHANGE);
    }

}
