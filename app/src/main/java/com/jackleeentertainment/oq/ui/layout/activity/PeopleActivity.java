package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.ui.layout.fragment.RecentAndContactProfileFrag;
import com.jackleeentertainment.oq.ui.layout.fragment.SearchProfileFrag;


/**
 * Created by Jacklee on 2016. 9. 4..
 */
public class PeopleActivity extends BaseViewPagerFullDialogActivity {

    //fab
    FloatingActionButton fab; //to invite people from contacts

    //ViewPager
    PeopleActivityPagerAdapter peopleActivityPagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulldialog);
    }


    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        JM.G(tvToolbarPreview);
        JM.V(fab);
        fab.setImageResource(R.drawable.ic_person_add_white_48dp);
    }

    @Override
    void initOnClickListenerOnResume() {
        super.initOnClickListenerOnResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    void initTabLayoutViewPager() {
        super.initTabLayoutViewPager();

        final TabLayout.Tab tabLayout0_RecentAndContact = tabLayout.newTab();
        final TabLayout.Tab tabLayout1_Search = tabLayout.newTab();

        //Setting Icons to our respective tabs
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



        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
        tabLayout.addTab(tabLayout0_RecentAndContact, 0);
        tabLayout.addTab(tabLayout1_Search, 1);

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

    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
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



}
