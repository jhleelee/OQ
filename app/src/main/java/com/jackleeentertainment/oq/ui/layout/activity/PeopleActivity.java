package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
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

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.SetValue;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.fragment.RecentAndContactProfileFrag;
import com.jackleeentertainment.oq.ui.layout.fragment.SearchProfileFrag;
import com.konifar.fab_transformation.FabTransformation;

import java.util.ArrayList;
import java.util.HashSet;

import static com.jackleeentertainment.oq.R.id.vScrim;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_onlysearch, menu);

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


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        JM.G(tvToolbarPreview);
        JM.G(fab);
        vScrim = (View) findViewById(R.id.vScrim);
        fab.setImageResource(R.drawable.ic_person_add_white_48dp);

    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();

        String alreadySelected = getIntent().getStringExtra("alreadySelected");

        if (alreadySelected!=null) {
            ArrayList<Profile> arlAlreadySelectedProfiles = ProfileUtil.getArlProfileFromJson
                    (alreadySelected);
            arlSelectedProfile.addAll(arlAlreadySelectedProfiles);
        }
        tvToolbarTitle.setText(JM.strById(R.string.select_oponent));
        tvFootTitle.setText(JM.strById(R.string.see_people_at_my_contact));

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

        roFootTab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogWithOkCancel(
                        R.string.sync_phone_contact,
                        new SyncPhoneContactTask()
                );
            }
        });

        roFootTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogWithOkCancel(
                        R.string.sync_email_contact,
                        new SyncEmailContactTask()
                );
            }
        });

        roFootTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResultPickSNSFriends();
            }
        });
        tvFootTab0.setText(JM.strById(R.string.contacts_phone));
        tvFootTab1.setText(JM.strById(R.string.contacts_email));
        tvFootTab2.setText(JM.strById(R.string.contacts_facebook));


        ro_tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arlSelectedProfile!=null&& arlSelectedProfile.size()>0){

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", new Gson().toJson(arlSelectedProfile));
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_SNS_FRIEND) {
                String[] ar = {"aa", "bb"};
                new SyncSnsFriendTask().execute(ar);
            }
        }
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
                        R.drawable.ic_search_white_24dp,
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
                                        R.drawable.ic_search_white_24dp,
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
                                        R.drawable.ic_search_white_24dp,
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
                                        R.drawable.ic_search_white_24dp,
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
                                        R.drawable.ic_search_white_24dp,
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
                    return RecentAndContactProfileFrag.newInstance();
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

    class SyncPhoneContactTask extends AsyncTask<Void, Void, Void> {

        //

        public SyncPhoneContactTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<String> arlPhoneContacts = getPhoneContacts();
            SetValue.myPossibleContactsWithPhoneOrEmail(arlPhoneContacts, mActivity);

            return null;
        }
    }







    class SyncEmailContactTask extends AsyncTask<Void, Integer, String> {

        public SyncEmailContactTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }
    }


    class SyncSnsFriendTask extends AsyncTask<String, Integer, String> {

        public SyncSnsFriendTask() {
            super();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
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
        String[] PROJECTION = new String[] { ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
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


    public  void bottomSheetControl(int selectedSmsNum) {
        Log.d(TAG, "bottomSheetControl()");
        if (selectedSmsNum == 0) {
            Log.d(TAG, "bottomSheetControl() :" + J.st(selectedSmsNum)  );
            ro_tv_done.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "bottomSheetControl() :" + J.st(selectedSmsNum)  );
            String sumStr = "명 선택됨";
            tvDone__ro_tv_done.setText(selectedSmsNum + sumStr);
            ro_tv_done.setVisibility(View.VISIBLE);

        }
    }





}
