package com.jackleeentertainment.oq.ui.layout.activity;

import android.os.Bundle;
import android.view.View;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.ui.layout.fragment.PostCommentFrag;

/**
 * Created by Jacklee on 2016. 11. 8..
 */

public class PostCommentActivity extends BaseFragmentContainFullDialogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putString("pid", getIntent().getStringExtra("pid"));

        showFrag(PostCommentFrag.newInstance(bundle), R.id.fr_content);
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
        tvToolbarTitle.setText(JM.strById(R.string.write_comment));
        fab.setVisibility(View.GONE);

    }




}
