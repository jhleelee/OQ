package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.view.View;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
 import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.fragment.PostCommentFrag;


/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class ViewPhotoActivity extends BaseFragmentContainFullDialogActivity {




    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        fab.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();
        tvToolbarTitle.setText(JM.strById(R.string.write_comment));
        fab.setVisibility(View.GONE);

    }



    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();

        Profile  profile = (Profile)getIntent().getSerializableExtra("Profile");
        String pid  = getIntent().getStringExtra("pid");

        if (profile!=null) {
            bundle.putSerializable("Profile", profile);
        }

        if (pid!=null) {
            bundle.putString("pid", pid);
        }

//        showFrag(ViewPhotoFrag.newInstance(bundle), R.id.fr_content);
    }
}
