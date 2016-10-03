package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.ui.layout.fragment.RecentAndContactProfileFrag;
import com.jackleeentertainment.oq.ui.layout.fragment.SearchProfileFrag;
import com.jackleeentertainment.oq.ui.widget.TextViewToggleJack;


/**
 * Created by Jacklee on 2016. 9. 4..
 */
public class PeopleActivity extends BaseActivity {

    Toolbar toolbar;
    FloatingActionButton fab;
    TextViewToggleJack tvTab0, tvTab1;
    int currentFragDx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        initUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initClickListener();
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


    void initUi() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tvTab0 = (TextViewToggleJack) findViewById(R.id.tvTab0);
        tvTab1 = (TextViewToggleJack) findViewById(R.id.tvTab1);
        tvTab0.setColors(R.color.primary, R.color.white, R.color.light_grey, R.color.dark_grey);
        tvTab1.setColors(R.color.primary, R.color.white, R.color.light_grey, R.color.dark_grey);

    }

    void initClickListener() {
        tvTab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragmentAndTextViewJack(0);
            }
        });

        tvTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFragmentAndTextViewJack(1);
            }
        });
    }


    public void goToFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl__activity_people_content, fragment, fragment.getClass().getSimpleName());
//        transaction.addToBackStack(null);
        transaction.commit();
    }


    void setCurrentFragmentAndTextViewJack(int fragDx) {
        currentFragDx = fragDx;
        if (fragDx == 0) {
            goToFragment(new RecentAndContactProfileFrag());
            tvTab0.setChecked(true);
            tvTab1.setChecked(false);
        } else {
            goToFragment(new SearchProfileFrag());
            tvTab0.setChecked(false);
            tvTab1.setChecked(true);
        }
    }

}
