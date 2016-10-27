package com.jackleeentertainment.oq.ui.layout.activity;

import android.view.View;

/**
 * Created by Jacklee on 2016. 10. 21..
 */

public class BaseFragmentContainFullDialogActivity extends BaseFullDialogActivity{

    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        viewPager.setVisibility(View.GONE);
        fr_content.setVisibility(View.VISIBLE);
    }


}
