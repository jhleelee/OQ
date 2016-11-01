package com.jackleeentertainment.oq.ui.layout.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0Neo;

/**
 * Created by Jacklee on 2016. 10. 31..
 */

public class PurchaseActivity extends BaseFragmentContainFullDialogActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new Bundle();
        showFrag(NewOQFrag0Neo.newInstance(bundle), R.id.fr_content);


    }


    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        fab.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();

    }




}
