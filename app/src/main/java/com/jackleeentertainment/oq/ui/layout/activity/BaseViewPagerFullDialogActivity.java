package com.jackleeentertainment.oq.ui.layout.activity;

import android.view.View;

/**
 * Created by Jacklee on 2016. 10. 21..
 */

public class BaseViewPagerFullDialogActivity extends BaseFullDialogActivity{

    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        fr_content.setVisibility(View.VISIBLE);
        initTabLayoutViewPager();
    }

    void initTabLayoutViewPager() {

    }

}
