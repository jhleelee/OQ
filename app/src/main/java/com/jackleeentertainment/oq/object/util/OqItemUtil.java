package com.jackleeentertainment.oq.object.util;

import android.app.Activity;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.types.OQT;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

/**
 * Created by Jacklee on 2016. 10. 5..
 */

public class OqItemUtil {

    public class OQItemPivotT {

        public final static String UID_PAYER = "uidpayer";
        public final static String UID_GETTOR = "uidgettor";
        public final static String UID_GAVE_BENEFIT = "uidgavebnf";
        public final static String UID_RECEIVED_BENEFIT = "uidreceivedbnf";

    }


    public static OqItem getInstance(){
        OqItem oqItem = new OqItem();
        oqItem.setAmmount(0);
        return oqItem;
    }

//
//    public static OqItem setMyUidToOneSide(OqItem oqItem, Activity activity){
//        if (oqItem.getOqgnltype()!=null&&
//                oqItem.getOqwnttype()!=null){
//
//            if (oqItem.getOqwnttype().equals(OQT.WantT.GET)){
//                oqItem.setUidgettor(
//                        App.getUid(activity)
//                );
//            } else
//
//            if (oqItem.getOqwnttype().equals(OQT.WantT.PAY))
//
//            {
//                oqItem.setUidpayer(
//                        App.getUid(activity)
//                );
//            }
//
//        }
//
//        return oqItem;
//    }





    public static OqItem copyOqItemValues(OqItem oqItem) {
        Gson gson = new Gson();
        String strItem = gson.toJson(oqItem);
        OqItem oqItem1 = gson.fromJson(strItem, OqItem.class);
        return oqItem1;
    }


//    public static ArrayList<String> getUnduplacatedArlUidsFromArlOqItems(
//            ArrayList<OqItem> arlOqItems, String oqItemPivotT) {
//        ArrayList<String> arlUids = new ArrayList<>();
//        if (oqItemPivotT.equals(OQItemPivotT.UID_RECEIVED_BENEFIT)) {
//            for (int i = 0; i < arlOqItems.size(); i++) {
//                String name = arlOqItems.get(i).getUidreceivedbnf();
//                if (!arlUids.contains(name)) {
//                    arlUids.add(name);
//                }
//            }
//        } else if (oqItemPivotT.equals(OQItemPivotT.UID_GAVE_BENEFIT)) {
//            for (int i = 0; i < arlOqItems.size(); i++) {
//                String name = arlOqItems.get(i).getUidgavebnf();
//                if (!arlUids.contains(name)) {
//                    arlUids.add(name);
//                }
//            }
//        } else if (oqItemPivotT.equals(OQItemPivotT.UID_GETTOR)) {
//            for (int i = 0; i < arlOqItems.size(); i++) {
//                String name = arlOqItems.get(i).getUidgettor();
//                if (!arlUids.contains(name)) {
//                    arlUids.add(name);
//                }
//            }
//        } else if (oqItemPivotT.equals(OQItemPivotT.UID_PAYER)) {
//            for (int i = 0; i < arlOqItems.size(); i++) {
//                String name = arlOqItems.get(i).getUidpayer();
//                if (!arlUids.contains(name)) {
//                    arlUids.add(name);
//                }
//            }
//        }
//
//        return arlUids;
//    }


    public static long getSumOqItemAmmounts(ArrayList<OqItem> arlOqItems) {

        long returnVal = 0;

        for (int i = 0; i < arlOqItems.size(); i++) {
            returnVal +=arlOqItems.get(i).getAmmount();
        }

        return returnVal;
    }


//    public static OqItem mergeOqItemsPivotPerson(ArrayList<OqItem> arlOqItems, String oqItemPivotT) {
//
//        ArrayList<ArrayList<OqItem>> arlReturn = new ArrayList<>();
//
//        /**
//         * Get uids without Duplication
//         */
//        ArrayList<String> arlUidsByPivot = getUnduplacatedArlUidsFromArlOqItems(
//                arlOqItems,
//                oqItemPivotT);
//
//        ArrayList<OqItem> arl = new ArrayList<OqItem>();
//
//        for (int i = 0; i < arlUidsByPivot.size(); i++) {
//
//            for (int j = 0; j < arlOqItems.size(); j++) {
//
//                long ammountPerPerson = 0;
//
//                if (arlOqItems.get(j).getUidreceivedbnf().equals(arlUidsByPivot.get(i))) {
//
//                    ammountPerPerson = ammountPerPerson + arlOqItems.get(j).getAmmount();
//
//                    if (arl.get(i) == null) {
//                        OqItem newOqItem = arlOqItems.get(0);
//                        newOqItem.setAmmount(0);
//                        arl.set(i, newOqItem);
//                    }
//                    arl.get(i).setAmmount(ammountPerPerson);
//                }
//
//            }
//
//        }
//
//    }


//    public static OqItem getEffectOqItem_FromCauseOqItem(OqItem oqItem) {
//
//        if (oqItem.getUidgavebnf() != null &&
//                oqItem.getUidreceivedbnf() != null) {
//
//        }
//
//    }
//
//
//    public static OqItem fillInfo(OqItem oqItem) {
//
//        if (oqItem.getUidgavebnf() != null &&
//                oqItem.getUidreceivedbnf() != null) {
//
//        }
//
//    }
//
//
//    @DebugLog
//    public static OqItem getCompensatingOqItem_FromBenefittingOqItem(
//            String uidGaveBnf,
//            OqItem benefittingOqItem) {
//
//        OqItem oqItem = new OqItem();
//
//
//        for (int i = 0; i < benefittingOqItems.size(); i++) {
//            oqItem.setAmmount(oqItem.getAmmount() + benefittingOqItems.get(i).getAmmount());
//        }
//
//        oqItem.setUidgettor(benefittingOqItems.get(0).getUidgavebnf());
//        oqItem.setUidpayer(null);
//        return oqItem;
//    }


//    @DebugLog
//    public static OqItem getCompensatingOqItem_FromBenefittingOqItems(
//            String uidGaveBnf,
//            ArrayList<OqItem> benefittingOqItems) {
//        OqItem oqItem = new OqItem();
//
//        for (int i = 0; i < benefittingOqItems.size(); i++) {
//            oqItem.setAmmount(oqItem.getAmmount() + benefittingOqItems.get(i).getAmmount());
//        }
//
//        oqItem.setUidgettor(benefittingOqItems.get(0).getUidgavebnf());
//        oqItem.setUidpayer(null);
//        return oqItem;
//    }
//
//    public static ArrayList<OqItem> getBenefittingOqItems_FromCompensatingOqItemFrom(
//            OqItem compensatingOqItem) {
//        ArrayList<OqItem> arl = new ArrayList<OqItem>();
//
//
//    }

}
