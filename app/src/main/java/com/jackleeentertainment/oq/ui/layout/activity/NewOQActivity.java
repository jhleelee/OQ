package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag1;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class NewOQActivity extends BaseActivity {
    Toolbar toolbar;
    int currentFragDx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newoq);
        initUi();
        showFrag(new NewOQFrag0(), R.id.lofragmentlayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initClickListener();
    }


    void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    void initClickListener() {

    }
}
