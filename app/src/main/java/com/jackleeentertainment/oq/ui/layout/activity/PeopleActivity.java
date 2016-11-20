package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.progress.ProgressActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.layout.diafrag.UpdateContactsDiaFrag;
import com.jackleeentertainment.oq.ui.layout.fragment.ContactProfileFrag;
import com.jackleeentertainment.oq.ui.layout.fragment.SearchProfileFrag;
import com.konifar.fab_transformation.FabTransformation;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by Jacklee on 2016. 9. 4..
 */
public class PeopleActivity extends BaseViewPagerFullDialogActivity {

    Activity mActivity = this;
    final int REQ_SNS_FRIEND = 93;

    //ViewPager
    PeopleActivityPagerAdapter peopleActivityPagerAdapter;
    View vScrim;


    public ArrayList<Profile> arlSelectedProfile = new ArrayList<>();


    //from DiaFrag :onResume
    public boolean isToStartPhoneSync = false;
    public boolean isToStartEmailSync = false;


    @Override
    protected void onResume() {
        super.onResume();
        if (arlSelectedProfile != null) {
            bottomSheetControl(arlSelectedProfile.size());
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_peopleactivity, menu);

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
                        Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });



        View vFriends =
                MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        vFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("diaFragT",DiaFragT.UpdateContacts);
                bundle.putString("activityT", UpdateContactsDiaFrag.ActivityT.PeopleActivity);
                showDialogFragment(bundle);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Beppi
        if (id == R.id.action_addcontacts){
            Bundle bundle = new Bundle();
            bundle.putString("diaFragT",DiaFragT.UpdateContacts);
            bundle.putString("activityT", UpdateContactsDiaFrag.ActivityT.PeopleActivity);
            showDialogFragment(bundle);
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        JM.G(tvToolbarPreview);
        JM.V(fab);
        vScrim = (View) findViewById(R.id.vScrim);
        fab.setImageResource(R.drawable.ic_person_add_white_48dp);
        ivClose.setImageDrawable(JM.drawableById(R.drawable.ic_close_white_48dp));
        tvModify__ro_tv_done.setVisibility(View.VISIBLE);
        roFootTab2.setVisibility(View.GONE);
    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();

        String beforeProfiles = getIntent().getStringExtra("beforeProfiles");
        if (beforeProfiles != null) {
            ArrayList<Profile> arlBeforeProfiles = ProfileUtil.getArlProfileFromJson
                    (beforeProfiles);

            if (arlBeforeProfiles.size() > 0) {
                arlSelectedProfile.addAll(arlBeforeProfiles);
            }
            int size = arlSelectedProfile.size();
        }

        String beforeUids = getIntent().getStringExtra("beforeUids");
        if (beforeUids != null) {
            final ArrayList<String> arlBeforeUids = J.arlStringFromjsonArlString(beforeUids);

            for (String uid : arlBeforeUids) {
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .child(uid)
                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Profile profile = dataSnapshot.getValue(Profile.class);
                                            profile.setUid(dataSnapshot.getKey());
                                            arlSelectedProfile.add(profile);
                                            LBR.send(LBR.IntentFilterT.PeopleActivity_Frag0);
                                            LBR.send(LBR.IntentFilterT.PeopleActivity_Frag1);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );
            }
        }


        tvToolbarTitle.setText(JM.strById(R.string.select_oponent));
        tvFootTitle.setText(JM.strById(R.string.see_people_at_my_contact));
        fab.setVisibility(View.GONE);
    }

    @Override
    void initOnClickListenerOnResume() {
        super.initOnClickListenerOnResume();

        fab.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_person_add_white_48dp,
                        R.color.colorPrimary,
                        this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FabTransformation.with(fab)
                        .duration(300)
                        .transformTo(toolbarFooter);
                vScrim.setVisibility(View.VISIBLE);
                if (ro_tv_done.getVisibility() == View.VISIBLE) {
                    ro_tv_done.setVisibility(View.GONE);

                }
            }
        });

        vScrim.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FabTransformation.with(fab)
                        .duration(100)
                        .transformFrom(toolbarFooter);
                v.setVisibility(View.GONE);
                bottomSheetControl(
                        arlSelectedProfile.size());
                return false;
            }
        });

        ivFootTab0.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_contact_phone_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );


        ivFootTab1.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_contact_mail_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );

        // upto provider

        ivFootTab2.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.com_facebook_button_icon_white,
                        R.color.colorPrimary,
                        this
                )
        );

//        roFootTab0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAlertDialogWithOkCancel(
//                        R.string.sync_phone_contact,
//                        new SyncPhoneContactTask()
//                );
//            }
//        });
//
//        roFootTab1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAlertDialogWithOkCancel(
//                        R.string.sync_email_contact,
//                        new SyncEmailContactTask()
//                );
//            }
//        });
//
//        roFootTab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResultPickSNSFriends();
//            }
//        });
        tvFootTab0.setText(JM.strById(R.string.contacts_phone));
        tvFootTab1.setText(JM.strById(R.string.contacts_email));
        tvFootTab2.setText(JM.strById(R.string.contacts_facebook));


        ro_tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ro_tv_done");
                if (arlSelectedProfile != null && arlSelectedProfile.size() > 0) {
                    Log.d(TAG, "arlSelectedProfile.size() : " + J.st(arlSelectedProfile.size()));

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", new Gson().toJson(arlSelectedProfile));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();

                } else {
                    Log.d(TAG, "arlSelectedProfile==null...");

                }


            }
        });

        tvModify__ro_tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("diaFragT", DiaFragT.SelectedPeople);
                Gson gson = new Gson();
                String st = gson.toJson(arlSelectedProfile);
                bundle.putString("arlProfileJson", st);
                showDialogFragment(bundle);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQ_SNS_FRIEND) {
//                String[] ar = {"aa", "bb"};
//                new SyncSnsFriendTask().execute(ar);
//            }
//        }
    }

    @Override
    void initTabLayoutViewPagerOnCreate() {
        super.initTabLayoutViewPagerOnCreate();


        peopleActivityPagerAdapter = new PeopleActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(peopleActivityPagerAdapter);
        setSupportActionBar(toolbar);


        final TabLayout.Tab tabLayout0_RecentAndContact = tabLayout.newTab();
        final TabLayout.Tab tabLayout1_Search = tabLayout.newTab();

        //Setting Icons to our respective tabs
        tabLayout0_RecentAndContact.setIcon(
                JM.tintedDrawable(
                        R.drawable.ic_contacts_white_24dp,
                        R.color.colorTabTextColor,
                        App.getContext())
        );
        tabLayout1_Search.setIcon(
                JM.tintedDrawable(
                        R.drawable.ic_language_white_24dp,
                        R.color.colorTabTextColorUnselected,
                        App.getContext())
        );



        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
        tabLayout.addTab(tabLayout1_Search);
        tabLayout.addTab(tabLayout0_RecentAndContact);

        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

        tabLayout.setTabTextColors(
                ContextCompat.getColorStateList(this,
                        R.color.colorTabTextColor)
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
                switch (tab.getPosition()) {
                    case 0:
                        /*
                        setting Home as White and rest grey
                        and like wise for all other positions
                         */
                        tabLayout0_RecentAndContact.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_contacts_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //grey
                        tabLayout1_Search.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_language_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //white
                        break;
                    case 1:
                        tabLayout0_RecentAndContact.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_contacts_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        tabLayout1_Search.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_language_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //white
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
                        tabLayout0_RecentAndContact.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_contacts_white_24dp,
                                        R.color.colorTabTextColor,
                                        App.getContext())
                        ); //grey
                        tabLayout1_Search.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_language_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //white
                        break;
                    case 1:
                        tabLayout0_RecentAndContact.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_contacts_white_24dp,
                                        R.color.colorTabTextColorUnselected,
                                        App.getContext())
                        ); //grey
                        tabLayout1_Search.setIcon(
                                JM.tintedDrawable(
                                        R.drawable.ic_language_white_24dp,
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
    }


    /**
     * A simple pager frvAdapterAllMyContact that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class PeopleActivityPagerAdapter extends FragmentPagerAdapter {
        public PeopleActivityPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ContactProfileFrag.newInstance();
                case 1:
                    return SearchProfileFrag.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    void startActivityForResultPickSNSFriends() {

    }


    public ArrayList<String> getPhoneContacts() {
        //show prog dia
        Log.d(TAG, "getPhoneContacts()  ");


        ContentResolver cr = getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            ArrayList<String> alContacts = new ArrayList<String>();
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        alContacts.add(contactNumber);
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());

            Log.d(TAG, "getPhoneContacts() : " + J.st(alContacts.size()));


            return alContacts;

            // do something with alContacts
        }


        return null;
    }


    public ArrayList<String> getNameEmailDetails() {

        //show prog dia


        ArrayList<String> emlRecs = new ArrayList<String>();
        HashSet<String> emlRecsHS = new HashSet<String>();
        Context context = this;
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[]{ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID};
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                // names comes in hand sometimes
                String name = cur.getString(1);
                String emlAddr = cur.getString(3);

                // keep unique only
                if (emlRecsHS.add(emlAddr.toLowerCase())) {
                    emlRecs.add(emlAddr);
                }
            } while (cur.moveToNext());
        }

        cur.close();

        //end prog dia


        return emlRecs;
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//
//        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        //
//        searchView.setOnQueryTextListener(
//                new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        Toast.makeText(getApplicationContext(), "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        Toast.makeText(getApplicationContext(), "onQueryTextChange", Toast.LENGTH_SHORT).show();
//
//                        return false;
//                    }
//                });
//
//
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Begin", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return true;
//    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    String TAG = "PeopleActivity";


    public void bottomSheetControl(int selectedNum) {
        Log.d(TAG, "bottomSheetControl()");
        if (selectedNum == 0) {
            Log.d(TAG, "bottomSheetControl() :" + J.st(selectedNum));
            ro_tv_done.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "bottomSheetControl() :" + J.st(selectedNum));
            String sumStr = "명 선택됨";
            tvDone__ro_tv_done.setText(selectedNum + sumStr);
            ro_tv_done.setVisibility(View.VISIBLE);


        }
    }


}
