package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQSumT;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class NewOQActivity extends BaseFragmentContainFullDialogActivity {
    String TAG = "NewOQActivity";
    final int REQ_PEOPLE = 99;
    final int REQ_SUMTYPE= 98;

    //temp data (1)
    public String OQTWantT_Future = null; //PAY/GET
    public ArrayList<Profile> arlOppoProfile = new ArrayList<>();

    //temp data (2)
    public long ammountAsStandard = 0;
    public String OQSumT = null;

    public ArrayList<OqItem> arlOQItem_Past = new ArrayList<>();
    public ArrayList<OqItem> arlOQItem_Now = new ArrayList<>();


    public ArrayList<OqItem> arlOQItem_Future = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OQTWantT_Future = (getIntent().getStringExtra("OQTWantT_Future"));

        showFrag(new NewOQFrag0(), R.id.fr_content);
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

        //toolbar title
        if (OQTWantT_Future != null && OQTWantT_Future.equals(OQT.WantT.GET)) {
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_get));
        } else if (OQTWantT_Future != null && OQTWantT_Future.equals(OQT.WantT.PAY)) {
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_pay));
        }


    }


    public void startActivityForResultPeopleActivity() {

        Intent i = new Intent(this, PeopleActivity.class);
        if (arlOppoProfile != null && arlOppoProfile.size() > 0) {
            i.putExtra("alreadySelected", new Gson().toJson(arlOppoProfile));
        }
        startActivityForResult(i, REQ_PEOPLE);
    }


    public void startActivityForResultSumTypeActivity() {

        Intent i = new Intent(this, SumTypeActivity.class);
        if (arlOppoProfile != null && arlOppoProfile.size() > 0) {
            i.putExtra("alreadySelected", new Gson().toJson(arlOppoProfile));
        }

        if (ammountAsStandard != 0) {
            i.putExtra("ammountAsStandard", ammountAsStandard);
        }

        if (   OQSumT!=null ) {
            i.putExtra("OQSumT", OQSumT);
        }

        if (OQTWantT_Future!=null){
            i.putExtra("OQTWantT_Future", OQTWantT_Future);
        }

        startActivityForResult(i, REQ_SUMTYPE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(TAG, "onActivityResult()");
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQ_PEOPLE) {
                String json = intent.getStringExtra("result");
                Gson gson = new Gson();
                arlOppoProfile = ProfileUtil.getArlProfileFromJson(json);
             }

        }

    }
}
