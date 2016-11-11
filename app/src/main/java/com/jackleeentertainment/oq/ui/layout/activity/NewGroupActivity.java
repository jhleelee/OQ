package com.jackleeentertainment.oq.ui.layout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Group;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.GroupUtil;
import com.jackleeentertainment.oq.ui.layout.fragment.NewGroupFrag;

/**
 * Created by Jacklee on 2016. 10. 21..
 */

public class NewGroupActivity extends BaseFragmentContainFullDialogActivity {


    final int REQ_PHOTO_TITLE=90;
    Group group = GroupUtil.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        group.setOqgnltype(getIntent().getStringExtra("GeneralT"));
//        group.setOqwnttype(getIntent().getStringExtra("DoWhat"));
//        OqItemUtil.setMyUidToOneSide(oqItemEffect, this);
        showFrag(new NewGroupFrag(), R.id.fr_content);
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
               tvToolbarTitle.setText(JM.strById(R.string.new_group));
        fab.setVisibility(View.GONE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==REQ_PHOTO_TITLE){
                LBR.send(LBR.IntentFilterT.NewGroupActivityTitlePhoto, data.getData().toString());
            }
        }

    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
