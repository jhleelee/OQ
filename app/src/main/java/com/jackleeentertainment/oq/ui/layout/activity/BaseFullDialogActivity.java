package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 10. 21..
 */

public class BaseFullDialogActivity extends BaseActivity {

    Toolbar toolbar;
    RelativeLayout roClose;
    TextView tvToolbarTitle , tvToolbarPreview, tvToolbarSave;
    TabLayout tabLayout ;
    ViewPager viewPager ;
    FrameLayout fr_content ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulldialog);
        initUIOnCreate();
        setSupportActionBar(toolbar);
    }



    void initUIOnCreate(){
        toolbar  = (Toolbar)findViewById(R.id.toolbar) ;
        roClose   = (RelativeLayout)findViewById(R.id.roClose) ;
        tvToolbarTitle  = (TextView)findViewById(R.id.tvToolbarTitle) ;
        tvToolbarPreview = (TextView)findViewById(R.id.tvToolbarPreview) ;
        tvToolbarSave  = (TextView)findViewById(R.id.tvToolbarSave) ;
        tabLayout  = (TabLayout)findViewById(R.id.tabLayout) ;
        viewPager  = (ViewPager)findViewById(R.id.viewPager) ;
        fr_content   = (FrameLayout)findViewById(R.id.fr_content) ;
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








}
