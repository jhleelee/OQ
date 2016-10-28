package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.Profile;
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
    final int REQ_SUMTYPE = 98;

    //temp data (1)
    public String OQTWantT_Future = null; //PAY/GET
    public ArrayList<Profile> arlOppoProfile = new ArrayList<>();

    //temp data (2)
    public long ammountAsStandard = 0;
    public String OQSumT = null;

    public ArrayList<OqItem> arlOQItem_Paid = new ArrayList<>();
    public ArrayList<OqItem> arlOQItem_Now = new ArrayList<>();
    public ArrayList<OqItem> arlOQItem_ToPay = new ArrayList<>();


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

        if (OQSumT != null) {
            i.putExtra("OQSumT", OQSumT);
        }

        if (OQTWantT_Future != null) {
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
                String str = intent.getStringExtra("result");
                Log.d(TAG, "intent.getStringExtra(result) : " + str);
                arlOppoProfile = ProfileUtil.getArlProfileFromJson(intent.getStringExtra("result"));
                Log.d(TAG, "arlOppoProfile.size() " + J.st(arlOppoProfile.size()));

            }

            if (requestCode == REQ_SUMTYPE) {
                OQSumT = intent.getStringExtra("OQSumT");
                ammountAsStandard = intent.getLongExtra("ammountAsStandard", 0);

                if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToGETFromYou.I_PAID_FOR_YOU_AND_ME)) {

                    /**
                     *    //id
                     String oid; //this object's id
                     String gid; //human group's id (if any)
                     String qid; //oqitem group's id (in the case of case oqitem)

                     //people
                     String uidpayer;
                     String uidgettor;

                     //ammount
                     long ammount;

                     //point - future=obligation, now=paying, past=paid
                     String oqtype;

                     //ts
                     long ts;

                     String currency;
                     String duedate;
                     */

                    OqItem oqItemPaid = new OqItem();
                    oqItemPaid.setUidpayer(App.getUid(this));
                    oqItemPaid.setUidgettor(null);
                    oqItemPaid.setAmmount(ammountAsStandard);
                    oqItemPaid.setOqtype(OQT.PointT.PAID);
                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
                    oqItemPaid.setDuedate(null);
                    arlOQItem_Paid.set(0, oqItemPaid);

                    OqItem oqItemToPay = new OqItem();
                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
                    oqItemToPay.setUidgettor(App.getUid(this));
                    oqItemToPay.setAmmount(ammountAsStandard / 2);
                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                    oqItemToPay.setDuedate(null);
                    arlOQItem_ToPay.set(0, oqItemToPay);

                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToGETFromYou.I_PAID_FOR_YOU)) {

                    OqItem oqItemPaid = new OqItem();
                    oqItemPaid.setUidpayer(App.getUid(this));
                    oqItemPaid.setUidgettor(null);
                    oqItemPaid.setAmmount(ammountAsStandard);
                    oqItemPaid.setOqtype(OQT.PointT.PAID);
                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
                    oqItemPaid.setDuedate(null);
                    arlOQItem_Paid.set(0, oqItemPaid);

                    OqItem oqItemToPay = new OqItem();
                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
                    oqItemToPay.setUidgettor(App.getUid(this));
                    oqItemToPay.setAmmount(ammountAsStandard);
                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                    oqItemToPay.setDuedate(null);
                    arlOQItem_ToPay.set(0, oqItemToPay);

                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToGETFromYou.ANYWAY)) {

                    OqItem oqItemToPay = new OqItem();
                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
                    oqItemToPay.setUidgettor(App.getUid(this));
                    oqItemToPay.setAmmount(ammountAsStandard);
                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                    oqItemToPay.setDuedate(null);
                    arlOQItem_ToPay.set(0, oqItemToPay);


                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME)) {

                    OqItem oqItemPaid = new OqItem();
                    oqItemPaid.setUidpayer(App.getUid(this));
                    oqItemPaid.setUidgettor(null);
                    oqItemPaid.setAmmount(ammountAsStandard);
                    oqItemPaid.setOqtype(OQT.PointT.PAID);
                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
                    oqItemPaid.setDuedate(null);
                    arlOQItem_Paid.set(0, oqItemPaid);

                    int pplNumWithoputMe = arlOppoProfile.size();
                    long eachBurden = ammountAsStandard / (pplNumWithoputMe + 1);

                    for (int i = 0; i < arlOppoProfile.size(); i++) {
                        OqItem oqItemToPay = new OqItem();
                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
                        oqItemToPay.setUidgettor(App.getUid(this));
                        oqItemToPay.setAmmount(eachBurden);
                        oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                        oqItemToPay.setDuedate(null);
                        arlOQItem_ToPay.set(i, oqItemToPay);
                    }

                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME)) {

                    OqItem oqItemPaid = new OqItem();
                    oqItemPaid.setUidpayer(App.getUid(this));
                    oqItemPaid.setUidgettor(null);
                    oqItemPaid.setAmmount(ammountAsStandard);
                    oqItemPaid.setOqtype(OQT.PointT.PAID);
                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
                    oqItemPaid.setDuedate(null);
                    arlOQItem_Paid.set(0, oqItemPaid);

                    int pplNumWithoputMe = arlOppoProfile.size();
                    long eachBurden = ammountAsStandard / (pplNumWithoputMe);

                    for (int i = 0; i < arlOppoProfile.size(); i++) {
                        OqItem oqItemToPay = new OqItem();
                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
                        oqItemToPay.setUidgettor(App.getUid(this));
                        oqItemToPay.setAmmount(eachBurden);
                        oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                        oqItemToPay.setDuedate(null);
                        arlOQItem_ToPay.set(i, oqItemToPay);
                    }


                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToGETFromYouGuys.N_ANYWAY)) {

                    int pplNumWithoputMe = arlOppoProfile.size();
                    long eachBurden = ammountAsStandard / (pplNumWithoputMe);

                    for (int i = 0; i < arlOppoProfile.size(); i++) {
                        OqItem oqItemToPay = new OqItem();
                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
                        oqItemToPay.setUidgettor(App.getUid(this));
                        oqItemToPay.setAmmount(eachBurden);
                        oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                        oqItemToPay.setDuedate(null);
                        arlOQItem_ToPay.set(i, oqItemToPay);
                    }

                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToPAY.YOU_PAID_FOR_ME)) {

                    OqItem oqItemPaid = new OqItem();
                    oqItemPaid.setUidpayer(arlOppoProfile.get(0).getUid());
                    oqItemPaid.setUidgettor(null);
                    oqItemPaid.setAmmount(ammountAsStandard);
                    oqItemPaid.setOqtype(OQT.PointT.PAID);
                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
                    oqItemPaid.setDuedate(null);
                    arlOQItem_Paid.set(0, oqItemPaid);


                    OqItem oqItemToPay = new OqItem();
                    oqItemToPay.setUidpayer(App.getUid(this));
                    oqItemToPay.setUidgettor(arlOppoProfile.get(0).getUid());
                    oqItemToPay.setAmmount(ammountAsStandard);
                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                    oqItemToPay.setDuedate(null);
                    arlOQItem_ToPay.set(0, oqItemToPay);


                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                        .SoIWantToPAY.ANYWAY)) {

                    OqItem oqItemToPay = new OqItem();
                    oqItemToPay.setUidpayer(App.getUid(this));
                    oqItemToPay.setUidgettor(arlOppoProfile.get(0).getUid());
                    oqItemToPay.setAmmount(ammountAsStandard);
                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
                    oqItemToPay.setDuedate(null);
                    arlOQItem_ToPay.set(0, oqItemToPay);


                }
            }

        }

    }
}
