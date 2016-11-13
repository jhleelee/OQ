package com.jackleeentertainment.oq.ui.layout.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Group;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.GroupUtil;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.fragment.NewGroupFrag;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 21..
 */

public class NewGroupActivity extends BaseFragmentContainFullDialogActivity {
    String TAG = "NewGroupActivity";
    final int REQ_PEOPLE = 99;
    final int REQ_PHOTO_TITLE = 90;
    public  ArrayList<Profile> arlProfiles = new ArrayList<>();
   public  Uri photoUri;
    public Group group = GroupUtil.getInstance();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQ_PHOTO_TITLE) {
                photoUri = intent.getData();
            }

            if (requestCode == REQ_PEOPLE) {
                //get it
                String str = intent.getStringExtra("result");
                arlProfiles = ProfileUtil
                        .getArlProfileFromJson(intent
                        .getStringExtra("result"));
                Log.d(TAG, "intent.getStringExtra(result) : " + str);


            }

        }

    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public void startActivityForResultPeopleActivity() {
        Intent i = new Intent(this, PeopleActivity.class);
        i.putExtra("arlProfiles", new Gson().toJson(arlProfiles));
        startActivityForResult(i, REQ_PEOPLE);
    }

    public void startActivityForResultPhotoGalleryForFeed() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.attach_photo)),
                REQ_PHOTO_TITLE);
    }
}
