package com.jackleeentertainment.oq.object.util;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqDoPair;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.types.OqDoListT;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class OqDoUtil {

    static String TAG = "OqDoUtil";


    public static Profile getOppoProfileFromOqDo(OqDo oqDo, Activity activity) {
        if (!oqDo.profilea.uid.equals(App.getUid(activity))) {
            return oqDo.profilea;
        } else {
            return oqDo.profileb;
        }
    }



    public static String getOqDoListMostRecentStr(List<OqDo> oqDoList){

        String str = null;

        sortList(oqDoList);

        OqDo oqDoMostRecent = oqDoList.get(oqDoList.size()-1);

        str = getOqDoDeedStr(oqDoMostRecent);

        return str;

    }




    public static ArrayList<ArrayList<OqDo>> getArlArlOqDoPerReferOid(List<OqDo> oqDoList,
                                                                      Activity activity) {

        ArrayList<ArrayList<OqDo>> arlArlOqDoPerReferOid = new ArrayList<ArrayList<OqDo>>();

        ArrayList<String> arlReferOid = new ArrayList<>();

        for (OqDo oqDo : oqDoList) {
            if (!arlReferOid.contains(oqDo.referoid)) {
                arlReferOid.add(oqDo.referoid);
            }
        }

        for (String referOid : arlReferOid) {
            ArrayList<OqDo> arrayListItem = new ArrayList<>();
            for (OqDo oqDo : oqDoList) {
                if (oqDo.referoid.equals(referOid)) {
                    arrayListItem.add(oqDo);
                }
            }
            arlArlOqDoPerReferOid.add(arrayListItem);
        }
        return arlArlOqDoPerReferOid;
    }


    public static ArrayList<ArrayList<OqDo>> getArlArlOqDoPerPeople(List<OqDo> oqDoList,
                                                                    Activity activity) {

        ArrayList<ArrayList<OqDo>> arlArlOqDoPerPeople = new ArrayList<ArrayList<OqDo>>();

        ArrayList<String> arlUid = new ArrayList<>();

        for (OqDo oqDo : oqDoList) {
            if (!arlUid.contains(oqDo.profilea.uid)) {
                arlUid.add(oqDo.profilea.uid);
            }
            if (!arlUid.contains(oqDo.profileb.uid)) {
                arlUid.add(oqDo.profileb.uid);
            }
        }


        for (String uid : arlUid) {

            if (!uid.equals(App.getUid(activity))) {

                ArrayList<OqDo> arrayList = new ArrayList<>();

                for (OqDo oqDo : oqDoList) {

                    if (oqDo.profilea.uid.equals(uid) || oqDo.profileb.uid.equals(uid)) {

                        arrayList.add(oqDo);

                    }

                }

                arlArlOqDoPerPeople.add(arrayList);

            }
        }


        return arlArlOqDoPerPeople;
    }


    public static ArrayList<OqDoPair> getArlOqDoPair(List<OqDo> oqDoList) {

        ArrayList<OqDoPair> arlOqDoPair = new ArrayList<>();
        boolean isAddedToExistingOqDoPair = false;
        for (OqDo oqDo : oqDoList) {

            //search if there is oqDoPair already containning referoid
            if (arlOqDoPair != null && arlOqDoPair.size() > 0) {
                for (OqDoPair oqDoPair : arlOqDoPair) {
                    if (oqDoPair.listOqDo != null && oqDoPair.listOqDo.size() > 0) {
                        String referOid = oqDoPair.listOqDo.get(0).getReferoid();
                        if (referOid != null && referOid.equals(oqDo.getReferoid())) {
                            oqDoPair.listOqDo.add(oqDo);
                            isAddedToExistingOqDoPair = true;
                        }
                    }
                }
            }
            if (!isAddedToExistingOqDoPair) {
                OqDoPair oqDoPair = new OqDoPair();
                oqDoPair.listOqDo = new ArrayList<>();
                oqDoPair.listOqDo.add(oqDo);
                arlOqDoPair.add(oqDoPair);
            }
        }
        return arlOqDoPair;
    }


    public static long getLastTs(List<OqDo> oqDoList) {

        long ts = 0;

        for (OqDo oqDo : oqDoList) {

            if (ts < oqDo.getTs()) {
                ts = oqDo.getTs();
            }

        }

        return ts;
    }


    public static ArrayList<String> getUidsNotMe(OqDo oqDo, Activity activity) {
        ArrayList<String> arl = new ArrayList<>();
        if (!oqDo.profilea.uid.equals(App.getUid(activity))) {
            arl.add(oqDo.profilea.uid);
        }
        if (!oqDo.profileb.uid.equals(App.getUid(activity))) {
            arl.add(oqDo.profileb.uid);
        }
        return arl;
    }


    public static void sortList(List<OqDo> listoqdo) {

        if (listoqdo == null || listoqdo.size() == 0) {
            return;
        }

        Collections.sort(listoqdo, new OqDoComparator());

    }


    public static class OqDoComparator implements Comparator<OqDo> {
        @Override
        public int compare(OqDo t1, OqDo t2) {
            return Long.compare(t1.getTs(), t2.getTs());
        }
    }


    public static long getSumAmt(List<OqDo> arloqdo) {
        Log.d(TAG, "getSumAmt()");
        long returnVal = 0;

        for (int i = 0; i < arloqdo.size(); i++) {
            Log.d(TAG, "getSumAmt() " + String.valueOf(arloqdo.get(i).getAmmount()));
            returnVal += arloqdo.get(i).getAmmount();
        }
        Log.d(TAG, "getSumAmt() return : " + J.st(returnVal));
        return returnVal;
    }


    public static List<OqDo> getListAbGetFuture(List<OqDo> allSortedList) {

        Log.d(TAG, "getListAbGetFuture()");

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                if (oqDo.profilea.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profileb.uid.equals(firstOqdo.profileb.uid)) {

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)) {
                        Log.d(TAG, "getListAbGetFuture() " + "GET, FUTURE");
                        rtnList.add(oqDo);

                    }


                }
            }

        }
        if (rtnList == null) {
            Log.d(TAG, "rtnList==null");
        } else {
            Log.d(TAG, "rtnList.size() : " + J.st(rtnList.size()));
        }

        return rtnList;
    }

    public static List<OqDo> getListAbGetPast(List<OqDo> allSortedList) {

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                if (oqDo.profilea.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profileb.uid.equals(firstOqdo.profileb.uid)) {

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)) {
                        Log.d(TAG, "getListAbGetPast() " + "GET, PAST");

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }


    public static List<OqDo> getListAbPayFuture(List<OqDo> allSortedList) {

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                if (oqDo.profilea.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profileb.uid.equals(firstOqdo.profileb.uid)) {

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)) {
                        Log.d(TAG, "getListAbPayFuture() " + "PAY, FUTURE");

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }


    public static List<OqDo> getListAbPayPast(List<OqDo> allSortedList) {

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                if (oqDo.profilea.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profileb.uid.equals(firstOqdo.profileb.uid)) {

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)) {
                        Log.d(TAG, "getListAbPayPast() " + "PAY, PAST");

                        rtnList.add(oqDo);

                    }
                }
            }

        }

        return rtnList;
    }


    public static List<OqDo> getListBaGetFuture(List<OqDo> allSortedList) {

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                if (oqDo.profileb.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profilea.uid.equals(firstOqdo.profileb.uid)) {

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)) {
                        Log.d(TAG, "getListBaGetFuture() " + "GET, FUTURE");

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }


    public static List<OqDo> getListBaGetPast(List<OqDo> allSortedList) {

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                if (oqDo.profileb.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profilea.uid.equals(firstOqdo.profileb.uid)) {

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.GET) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)) {
                        Log.d(TAG, "getListBaGetPast() " + "GET, PAST");

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }


    public static List<OqDo> getListBaPayFuture(List<OqDo> allSortedList) {

        Log.d(TAG, "getListBaPayFuture()");

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                Log.d(TAG, oqDo.profilea.uid + " " + oqDo.profileb.uid);
                Log.d(TAG, firstOqdo.profilea.uid + " " + firstOqdo.profileb.uid);

                if (oqDo.profileb.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profilea.uid.equals(firstOqdo.profileb.uid)) {


                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)) {
                        Log.d(TAG, "getListBaPayFuture() " + "PAY, FUTURE");
                        rtnList.add(oqDo);
                    }
                }
            }

        }

        return rtnList;
    }

    public static List<OqDo> getListBaPayPast(List<OqDo> allSortedList) {

        List<OqDo> rtnList = new ArrayList<>();

        if (allSortedList != null && allSortedList.size() > 0) {

            OqDo firstOqdo = allSortedList.get(0);

            for (OqDo oqDo : allSortedList) {

                if (oqDo.profileb.uid.equals(firstOqdo.profilea.uid) &&
                        oqDo.profilea.uid.equals(firstOqdo.profileb.uid)) {

                    if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                            oqDo.getOqwhen().equals(OQT.DoWhen.PAST)) {
                        Log.d(TAG, "getListBaPayPast() " + "PAY, PAST");

                        rtnList.add(oqDo);

                    }


                }
            }

        }

        return rtnList;
    }

    //From OqItem Legacy
    //From OqItem Legacy


    public static ArrayList<String> getArlUidBFromArlOqDo(ArrayList<OqDo> arlOqDo) {

        ArrayList<String> arlUidB = new ArrayList<>();

        if (arlOqDo != null && arlOqDo.size() > 0) {
            for (OqDo oqDo : arlOqDo) {
                String uidB = oqDo.profileb.uid;
                arlUidB.add(uidB);
            }
        }

        return arlUidB;
    }


    public static boolean isFalseOqItem(ArrayList<NewOQActivity.TempProAmt> arl) {

        for (NewOQActivity.TempProAmt t : arl) {

            if (t.ammount == 0) {
                return true;
            }

        }

        return false;
    }


    public static OqDo getOqDoWithUidB(ArrayList<OqDo> arrayList, String uidclaimee) {

        for (OqDo oqItem : arrayList) {

            if (oqItem.profileb.uid != null &&
                    oqItem.profileb.uid.equals(uidclaimee)) {
                return oqItem;
            }

        }

        return null;
    }


    public static OqDo getInstanceIClaimThatIGet(Profile profilePayerAndClaimee, Activity activity) {

        /*

    String oid;
    String uida;
    String namea;
    String emaila;
    String uidb;
    String nameb;
    String emailb;
    long ammount;
    long ts;
    long currency;
    String oqwhen;//point - future=obligation, now=paying, past=paid
    String oqwhat;//get ; pa
         */


        String oid = App.fbaseDbRef.child("push").push().getKey();
        OqDo oqDo = new OqDo();
        oqDo.setOid(oid);
        oqDo.setUidab(App.getUid(activity) + ",," + profilePayerAndClaimee.uid);
        Profile profilea = new Profile();
        profilea.setUid(App.getUid(activity));
        profilea.setFull_name(App.getUname(activity));
        profilea.setEmail(App.getUemail(activity));
        Profile profileb = new Profile();
        profileb.setUid(profilePayerAndClaimee.getUid());
        profileb.setFull_name(profilePayerAndClaimee.getFull_name());
        profileb.setEmail(profilePayerAndClaimee.getEmail());


        oqDo.setAmmount(0);
        oqDo.setTs(0);
        oqDo.setOqwhat(OQT.DoWhat.GET);
        oqDo.setOqwhen(OQT.DoWhen.FUTURE);
        oqDo.setCurrency(JM.strById(R.string.currency_code));
        return oqDo;
    }

    public static OqDo getInstanceIClaimThatIPay(Profile profileGettorAndClaimee, Activity
            activity) {


        String oid = App.fbaseDbRef.child("push").push().getKey();
        OqDo oqDo = new OqDo();
        oqDo.setOid(oid);
        oqDo.setUidab(App.getUid(activity) + ",," + profileGettorAndClaimee.uid);
        Profile profilea = new Profile();
        profilea.setUid(App.getUid(activity));
        profilea.setFull_name(App.getUname(activity));
        profilea.setEmail(App.getUemail(activity));
        Profile profileb = new Profile();
        profileb.setUid(profileGettorAndClaimee.getUid());
        profileb.setFull_name(profileGettorAndClaimee.getFull_name());
        profileb.setEmail(profileGettorAndClaimee.getEmail());

        oqDo.setAmmount(0);
        oqDo.setTs(0);
        oqDo.setOqwhat(OQT.DoWhat.PAY);
        oqDo.setOqwhen(OQT.DoWhen.FUTURE);
        oqDo.setCurrency(JM.strById(R.string.currency_code));
        return oqDo;


    }


//    public static     ArrayList<OqDo> getInstances(
//            ArrayList<Profile> arlSelectedProfiles,
//            long standardAmount,
//            String oqsumtype,
//            Activity activity
//    ) {
//        String qid = App.fbaseDbRef.child("push").push().getKey();
//
//        ArrayList<OqItem> arrayListOqItems = new ArrayList<>();
//
//        for (int i = 0; i < arlSelectedProfiles.size(); i++) {
//
//            OqDo oqItem = new OqDo();
//
//            if (oqsumtype.equals(OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU_AND_ME)) {
//
//                oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount / 2);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU)) {
//
//
//                oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYou.ANYWAY)) {
//
//
//                oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME)) {
//
//                oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(getDivideByMeAndOthers(standardAmount, arlSelectedProfiles.size()));
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME)) {
//
//
//                oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(getDivideByOthers(standardAmount, arlSelectedProfiles.size()));
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYouGuys.N_ANYWAY)) {
//
//                oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToPAY.YOU_PAID_FOR_ME) || oqsumtype.equals(OQSumT.SoIWantToPAY.ANYWAY)) {
//
//                oqItem =  getInstanceIClaimThatIPay(
//                        arlSelectedProfiles.get(i),
//                        activity);
//                oqItem.setAmmount(standardAmount);
//
//
//            }
//            oqItem.setQid(qid);
//            arrayListOqItems.add(oqItem);
//        }
//
//        return arrayListOqItems;
//    }


    public static long getDivideByMeAndOthers(long ammount, int numOthers) {

        return ammount / (numOthers + 1);

    }

    public static long getDivideByOthers(long ammount, int numOthers) {

        return ammount / (numOthers);

    }


    public static OqDo copyOqItemValues(OqDo oqdo) {
        Gson gson = new Gson();
        String strItem = gson.toJson(oqdo);
        OqDo oqItem1 = gson.fromJson(strItem, OqDo.class);
        return oqItem1;
    }


    public static long getSumOqDoAmmounts(ArrayList<OqDo> arlOqItems) {

        long returnVal = 0;

        for (int i = 0; i < arlOqItems.size(); i++) {
            Log.d(TAG, "getSumOqItemAmmounts() " + String.valueOf(arlOqItems.get(i).getAmmount()));
            returnVal += arlOqItems.get(i).getAmmount();
        }

        return returnVal;
    }


    public static long getSumOqDoAmmountsAgreed(ArrayList<OqDo> arlOqItems, Activity activity) {

        long returnVal = 0;

        ArrayList<ArrayList<OqDo>> arlArlOqDoPerReferOid = getArlArlOqDoPerReferOid(arlOqItems,
                activity);

        for (ArrayList<OqDo> arlItem : arlArlOqDoPerReferOid) {
            if (getOqDoListT(arlItem).equals(OqDoListT.ASentGetReq__BNoArgued_NotPaidAll)) {
                returnVal += arlItem.get(0).ammount;
            }
        }

        return returnVal;
    }


    public static long getSumOqDoAmmountsDisAgreed(ArrayList<OqDo> arlOqItems, Activity activity) {

        long returnVal = 0;

        ArrayList<ArrayList<OqDo>> arlArlOqDoPerReferOid = getArlArlOqDoPerReferOid(arlOqItems,
                activity);

        for (ArrayList<OqDo> arlItem : arlArlOqDoPerReferOid) {
            if (getOqDoListT(arlItem).equals(OqDoListT.ASentGetReq__BArgued)
                    ) {
                returnVal += arlItem.get(0).ammount;
            }
        }

        return returnVal;
    }




    public static String getOqDoDeedStr(OqDo oqDo) {

        Resources res = App.getContext().getResources();



        if (oqDo.getOqwhat().equals(OQT.DoWhat.GET) &&
                oqDo.getOqwhat().equals(OQT.DoWhen.FUTURE)) {

            return res.getString(
                    R.string.deed_req_a_get_future);

        }


        if (oqDo.getOqwhat().equals(OQT.DoWhat.GET) &&
                oqDo.getOqwhat().equals(OQT.DoWhen.PAST)) {

            return res.getString(
                    R.string.deed_a_get_past);

        }

        return null;


    }





























    public static String getOqDoListStr(List<OqDo> listoqdo) {
        Resources res = App.getContext().getResources();

        OqDoUtil.sortList(listoqdo);

        OqDo firstOqdo = listoqdo.get(0);

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.GET) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.FUTURE)) {

            List<OqDo> listBaPayFuture = OqDoUtil.getListBaPayFuture(listoqdo);
            long sumListBaPayFuture = OqDoUtil.getSumAmt(listBaPayFuture);

            List<OqDo> listAbGetPast = OqDoUtil.getListAbGetPast(listoqdo);
            long sumListAbGetPast = OqDoUtil.getSumAmt(listAbGetPast);

            List<OqDo> listBaPayPast = OqDoUtil.getListBaPayPast(listoqdo);
            long sumListBaPayPast = OqDoUtil.getSumAmt(listBaPayPast);


            if (firstOqdo.getAmmount() == sumListBaPayFuture) { //Basic - B agrees with A's req

                if (firstOqdo.getAmmount() == sumListBaPayPast) { // B claims he paid to A. is it true?

                    if (firstOqdo.getAmmount() == sumListAbGetPast) { // A admits is is paid all

                        //return OqDoListT.ASentGetReq__BNoArgued_ClaimPaidAll__ADidConfirmed;
                        return res.getString(
                                R.string.req_amtvar_whatphrasevar,
                                J.strAmt(firstOqdo.getAmmount()),
                                res.getString(R.string.whovar_all_paid_confirmed, firstOqdo
                                        .profileb.full_name));


                    } else {
                        //return OqDoListT.ASentGetReq__BNoArgued_ClaimPaidAll__AYetConfirmed;

                        return res.getString(
                                R.string.req_amtvar_whatphrasevar,
                                J.strAmt(firstOqdo.getAmmount()),
                                res.getString(R.string.whovar_all_claimpaid,
                                        firstOqdo.profileb.full_name));


                    }


                } else { //not paid all (incl partially paid)
                    //return OqDoListT.ASentGetReq__BNoArgued_NotPaidAll;

                    if (sumListBaPayPast == 0) {
                        return res.getString(
                                R.string.req_amtvar_whatphrasevar,
                                J.strAmt(firstOqdo.getAmmount()),
                                res.getString(R.string.whovar_all_notpaid,
                                        firstOqdo.profileb.full_name));
                    } else {

                    }


                }


            } else if (firstOqdo.getAmmount() > sumListBaPayFuture) { // B does not agree with A's req
                return OqDoListT.ASentGetReq__BArgued;

            }

        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.GET) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaPayPast = OqDoUtil.getListBaPayPast(listoqdo);
            long sumListBaPayPast = OqDoUtil.getSumAmt(listBaPayPast);

            if (firstOqdo.getAmmount() == sumListBaPayPast) {
                return OqDoListT.ASentGetPastNoti__BNoArgued;

            } else {
                return OqDoListT.ASentGetPastNoti__BArgued;

            }


        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.FUTURE)) {

            List<OqDo> listBaGetFuture = OqDoUtil.getListBaGetFuture(listoqdo);
            long sumListBaGetFuture = OqDoUtil.getSumAmt(listBaGetFuture);

            List<OqDo> listBaGetPast = OqDoUtil.getListBaGetPast(listoqdo);
            long sumListBaGetPast = OqDoUtil.getSumAmt(listBaGetPast);

            if (firstOqdo.getAmmount() == sumListBaGetFuture) {

                if (firstOqdo.getAmmount() == sumListBaGetPast) {
                    return OqDoListT.ASentPaySuggest__BNoArgued_DidReceivedAll;

                } else {
                    return OqDoListT.ASentPaySuggest__BNoArgued_NotReceivedAll;

                }


            } else {
                return OqDoListT.ASentPaySuggest__BArgued;


            }


        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaGetPast = OqDoUtil.getListBaGetPast(listoqdo);
            long sumListBaGetPast = OqDoUtil.getSumAmt(listBaGetPast);

            if (firstOqdo.getAmmount() == sumListBaGetPast) {
                return OqDoListT.ASentPayPastNoti__BDidConfirmed;

            } else {
                return OqDoListT.ASentPayPastNoti__BYetConfirmed;

            }


        }

        return null;


    }


    public static String getOqDoListT(List<OqDo> listoqdo) {

        OqDoUtil.sortList(listoqdo);

        OqDo firstOqdo = listoqdo.get(0);

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.GET) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.FUTURE)) {

            List<OqDo> listBaPayFuture = OqDoUtil.getListBaPayFuture(listoqdo);
            long sumListBaPayFuture = OqDoUtil.getSumAmt(listBaPayFuture);

            List<OqDo> listAbGetPast = OqDoUtil.getListAbGetPast(listoqdo);
            long sumListAbGetPast = OqDoUtil.getSumAmt(listAbGetPast);

            List<OqDo> listBaPayPast = OqDoUtil.getListBaPayPast(listoqdo);
            long sumListBaPayPast = OqDoUtil.getSumAmt(listBaPayPast);


            if (firstOqdo.getAmmount() == sumListBaPayFuture) { //Basic - B agrees with A's req

                if (firstOqdo.getAmmount() == sumListBaPayPast) { // B claims he paid to A. is it true?

                    if (firstOqdo.getAmmount() == sumListAbGetPast) { // A admits is is paid all

                        return OqDoListT.ASentGetReq__BNoArgued_ClaimPaidAll__ADidConfirmed;

                    } else { //not paid all (incl partially paid)
                        return OqDoListT.ASentGetReq__BNoArgued_ClaimPaidAll__AYetConfirmed;

                    }


                } else { //not paid all (incl partially paid)
                    return OqDoListT.ASentGetReq__BNoArgued_NotPaidAll;

                }


            } else if (firstOqdo.getAmmount() > sumListBaPayFuture) { // B does not agree with A's req
                return OqDoListT.ASentGetReq__BArgued;

            }

        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.GET) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaPayPast = OqDoUtil.getListBaPayPast(listoqdo);
            long sumListBaPayPast = OqDoUtil.getSumAmt(listBaPayPast);

            if (firstOqdo.getAmmount() == sumListBaPayPast) {
                return OqDoListT.ASentGetPastNoti__BNoArgued;

            } else {
                return OqDoListT.ASentGetPastNoti__BArgued;

            }


        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.FUTURE)) {

            List<OqDo> listBaGetFuture = OqDoUtil.getListBaGetFuture(listoqdo);
            long sumListBaGetFuture = OqDoUtil.getSumAmt(listBaGetFuture);

            List<OqDo> listBaGetPast = OqDoUtil.getListBaGetPast(listoqdo);
            long sumListBaGetPast = OqDoUtil.getSumAmt(listBaGetPast);

            if (firstOqdo.getAmmount() == sumListBaGetFuture) {

                if (firstOqdo.getAmmount() == sumListBaGetPast) {
                    return OqDoListT.ASentPaySuggest__BNoArgued_DidReceivedAll;

                } else {
                    return OqDoListT.ASentPaySuggest__BNoArgued_NotReceivedAll;

                }


            } else {
                return OqDoListT.ASentPaySuggest__BArgued;


            }


        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaGetPast = OqDoUtil.getListBaGetPast(listoqdo);
            long sumListBaGetPast = OqDoUtil.getSumAmt(listBaGetPast);

            if (firstOqdo.getAmmount() == sumListBaGetPast) {
                return OqDoListT.ASentPayPastNoti__BDidConfirmed;

            } else {
                return OqDoListT.ASentPayPastNoti__BYetConfirmed;

            }


        }

        return null;

    }


    public static void ivTwoAvaRelation(ImageView iv, List<OqDo> oqDoList) {


        String t = getOqDoListT(oqDoList);


        if (t.equals(OqDoListT.ASentGetReq__BArgued)) {
            JM.BGD(iv, R.drawable.cir_requested_but_argued);
            JM.ID(iv, R.drawable.ic_arrow_forward_white_24dp);

        } else if (t.equals(OqDoListT.ASentGetReq__BNoArgued_NotPaidAll)) {
            JM.BGD(iv, R.drawable.cir_requested);
            JM.ID(iv, R.drawable.ic_arrow_forward_white_24dp);

        } else if (t.equals(OqDoListT.ASentGetReq__BNoArgued_ClaimPaidAll__AYetConfirmed)) {
            JM.BGD(iv, R.drawable.cir_requested_so_claimpaid);
            JM.ID(iv, R.drawable.ic_check_white_24dp);

        } else if (t.equals(OqDoListT.ASentGetReq__BNoArgued_ClaimPaidAll__ADidConfirmed)) {
            JM.BGD(iv, R.drawable.cir_requested_so_paid);
            JM.ID(iv, R.drawable.ic_check_white_24dp);

        } else if (t.equals(OqDoListT.ASentGetPastNoti__BArgued)) {
            // consider a/b avatar position shift?

        } else if (t.equals(OqDoListT.ASentGetPastNoti__BNoArgued)) {

        } else if (t.equals(OqDoListT.ASentPaySuggest__BArgued)) {

        } else if (t.equals(OqDoListT.ASentPaySuggest__BNoArgued_NotReceivedAll)) {

        } else if (t.equals(OqDoListT.ASentPaySuggest__BNoArgued_DidReceivedAll)) {

        } else if (t.equals(OqDoListT.ASentPayPastNoti__BYetConfirmed)) {

        } else if (t.equals(OqDoListT.ASentPayPastNoti__BDidConfirmed)) {

        }


    }


}
