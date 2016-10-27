package com.jackleeentertainment.oq.ui.layout.activity;

import android.view.View;

/**
 * Created by Jacklee on 2016. 10. 21..
 */

public class BaseViewPagerFullDialogActivity extends BaseFullDialogActivity{

    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        fr_content.setVisibility(View.GONE);
        initTabLayoutViewPagerOnCreate();
    }

    void initTabLayoutViewPagerOnCreate() {

    }

}
