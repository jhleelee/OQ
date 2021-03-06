package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 10. 25..
 */

public class FragmentContainBaseActivity extends BaseActivity {

    Toolbar toolbar;
    RelativeLayout roClose;
    TextView tvToolbarTitle, tvToolbarPreview, tvToolbarSave;
    TabLayout tabLayout;
    ViewPager viewPager;
    FrameLayout fr_content;
    FloatingActionButton fab; //to invite people from contacts
    Toolbar toolbarFooter;
    TextView tvFootTitle;
    RelativeLayout roFootTab0 ;
    RelativeLayout roFootTab1 ;
    RelativeLayout  roFootTab2 ;
    ImageView ivFootTab0 ;
    ImageView ivFootTab1 ;
    ImageView ivFootTab2 ;
    TextView tvFootTab0;
    TextView tvFootTab1;
    TextView tvFootTab2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulldialog);
        initUIOnCreate();
        setSupportActionBar(toolbar);
    }


    void initUIOnCreate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        roClose = (RelativeLayout) findViewById(R.id.roClose);
        tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        tvToolbarPreview = (TextView) findViewById(R.id.tvToolbarPreview);
        tvToolbarSave = (TextView) findViewById(R.id.tvToolbarSave);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fr_content = (FrameLayout) findViewById(R.id.fr_content);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbarFooter = (Toolbar) findViewById(R.id.toolbar_footer);
        tvFootTitle = (TextView) findViewById(R.id.tvFootTitle);
        roFootTab0 = (RelativeLayout) findViewById(R.id.roFootTab0);
        roFootTab1 = (RelativeLayout) findViewById(R.id.roFootTab1);
        roFootTab2 = (RelativeLayout) findViewById(R.id.roFootTab2);
        ivFootTab0 = (ImageView) findViewById(R.id.ivFootTab0);
        ivFootTab1 = (ImageView) findViewById(R.id.ivFootTab1);
        ivFootTab2 = (ImageView) findViewById(R.id.ivFootTab2);
        tvFootTab0 = (TextView) findViewById(R.id.tvFootTab0);
        tvFootTab1 = (TextView) findViewById(R.id.tvFootTab1);
        tvFootTab2 = (TextView) findViewById(R.id.tvFootTab2);
//        searchView= (SearchView) findViewById(R.id.searchView);

    }


    @Override
    void initOnClickListenerOnResume() {
        super.initOnClickListenerOnResume();
        roClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override public void finish() { super.finish(); overridePendingTransition(0, 0); }
}
