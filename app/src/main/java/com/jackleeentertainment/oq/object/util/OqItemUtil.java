//package com.jackleeentertainment.oq.object.util;
//
//import android.app.Activity;
//import android.util.Log;
//
//import com.google.gson.Gson;
//import com.jackleeentertainment.oq.App;
//import com.jackleeentertainment.oq.R;
//import com.jackleeentertainment.oq.generalutil.JM;
//import com.jackleeentertainment.oq.object.OqDo;
//import com.jackleeentertainment.oq.object.OqItem;
//import com.jackleeentertainment.oq.object.Profile;
//import com.jackleeentertainment.oq.object.types.OQSumT;
//import com.jackleeentertainment.oq.object.types.OQT;
//
//import java.util.ArrayList;
//
///**
// * Created by Jacklee on 2016. 10. 5..
// */
//
//public class OqItemUtil {
//
//    public class OQItemPivotT {
//
//        public final static String UID_PAYER = "uidpayer";
//        public final static String UID_GETTOR = "uidgettor";
//        public final static String UID_GAVE_BENEFIT = "uidgavebnf";
//        public final static String UID_RECEIVED_BENEFIT = "uidreceivedbnf";
//
//    }
//
//
//    public static OqItem getInstance() {
//        OqItem oqItem = new OqItem();
//        oqItem.setCurrency(JM.strById(R.string.currency_code));
//        oqItem.setAmmount(0);
//        return oqItem;
//    }
//
//
//
//    public static ArrayList<String> getArlUidBFromArlOqDo(ArrayList<OqDo> arlOqDo) {
//
//        ArrayList<String> arlUidClaimee = new ArrayList<>();
//
//        if (arlOqDo!=null&&arlOqDo.size()>0){
//            for (OqDo oqDo : arlOqDo){
//                String uidClaimee = oqDo.getUidb();
//                arlUidClaimee.add(uidClaimee);
//            }
//        }
//
//        return arlUidClaimee;
//    }
//
//
//
//
//
//
//
//    public static boolean isFalseOqItem(ArrayList<OqDo> arlOqDo){
//
//        for (OqDo oqDo : arlOqDo){
//
//            if (oqDo.getAmmount()==0){
//                return true;
//            }
//
//        }
//
//        return false;
//    }
//
//
//    public static OqItem getOqItemWithUidclaimee(ArrayList<OqItem> arrayList, String uidclaimee){
//
//        for (OqItem oqItem : arrayList){
//
//            if (oqItem.getUidclaimee()!=null &&
//                    oqItem.getUidclaimee().equals(uidclaimee)){
//                return oqItem;
//            }
//
//        }
//
//        return null;
//    }
//
//
//
//
//    public static OqItem getInstanceIClaimThatIGet(Profile profilePayerAndClaimee, Activity activity) {
//
//        String oid = App.fbaseDbRef.child("keygen").push().getKey();
//        OqItem oqItem = new OqItem();
//        oqItem.setOid(oid);
//        oqItem.setUidclaimer(App.getUid(activity));
//        oqItem.setNameclaimer(App.getName(activity));
//        oqItem.setUidclaimee(profilePayerAndClaimee.getUid());
//        oqItem.setNameclaimee(profilePayerAndClaimee.getFull_name());
//
//        oqItem.setUidgettor(App.getUid(activity));
//        oqItem.setNamegettor(App.getName(activity));
//        oqItem.setUidpayer(profilePayerAndClaimee.getUid());
//        oqItem.setNamepayer(profilePayerAndClaimee.getFull_name());
//
//        oqItem.setCurrency(JM.strById(R.string.currency_code));
//        oqItem.setOqtype(OQT.DoWhen.FUTURE);
//        return oqItem;
//    }
//
//    public static OqItem getInstanceIClaimThatIPay(Profile profileGettorAndClaimee, Activity
//            activity) {
//
//        String oid = App.fbaseDbRef.child("keygen").push().getKey();
//        OqItem oqItem = new OqItem();
//        oqItem.setOid(oid);
//
//        oqItem.setUidclaimer(App.getUid(activity));
//        oqItem.setNameclaimer(App.getName(activity));
//        oqItem.setUidclaimee(profileGettorAndClaimee.getUid());
//        oqItem.setNameclaimee(profileGettorAndClaimee.getFull_name());
//
//        oqItem.setUidgettor(profileGettorAndClaimee.getUid() );
//        oqItem.setNamegettor(profileGettorAndClaimee.getFull_name() );
//        oqItem.setUidpayer(App.getUid(activity));
//        oqItem.setNamepayer(App.getName(activity));
//
//        oqItem.setCurrency(JM.strById(R.string.currency_code));
//        oqItem.setOqtype(OQT.DoWhen.FUTURE);
//        return oqItem;
//    }
//
//
//
//
//    public static     ArrayList<OqItem> getInstances(
//            ArrayList<Profile> arlSelectedProfiles,
//            long standardAmount,
//            String oqsumtype,
//            Activity activity
//    ) {
//        String qid = App.fbaseDbRef.child("keygen").push().getKey();
//
//        ArrayList<OqItem> arrayListOqItems = new ArrayList<>();
//
//        for (int i = 0; i < arlSelectedProfiles.size(); i++) {
//
//            OqItem oqItem = new OqItem();
//
//            if (oqsumtype.equals(OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU_AND_ME)) {
//
//                  oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount / 2);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU)) {
//
//
//                  oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYou.ANYWAY)) {
//
//
//                  oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME)) {
//
//                  oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(getDivideByMeAndOthers(standardAmount, arlSelectedProfiles.size()));
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME)) {
//
//
//                  oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(getDivideByOthers(standardAmount, arlSelectedProfiles.size()));
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToGETFromYouGuys.N_ANYWAY)) {
//
//                  oqItem =
//                        getInstanceIClaimThatIGet(
//                                arlSelectedProfiles.get(i),
//                                activity);
//                oqItem.setAmmount(standardAmount);
//
//
//            } else if (oqsumtype.equals(OQSumT.SoIWantToPAY.YOU_PAID_FOR_ME) || oqsumtype.equals(OQSumT.SoIWantToPAY.ANYWAY)) {
//
//                  oqItem =  getInstanceIClaimThatIPay(
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
//
//
//    public static long getDivideByMeAndOthers(long ammount, int numOthers) {
//
//        return ammount / (numOthers + 1);
//
//    }
//
//    public static long getDivideByOthers(long ammount, int numOthers) {
//
//        return ammount / (numOthers);
//
//    }
//
//
////
////    public static OqItem setMyUidToOneSide(OqItem oqItem, Activity activity){
////        if (oqItem.getOqgnltype()!=null&&
////                oqItem.getOqwnttype()!=null){
////
////            if (oqItem.getOqwnttype().equals(OQT.DoWhat.GET)){
////                oqItem.setUidgettor(
////                        App.getUid(activity)
////                );
////            } else
////
////            if (oqItem.getOqwnttype().equals(OQT.DoWhat.PAY))
////
////            {
////                oqItem.setUidpayer(
////                        App.getUid(activity)
////                );
////            }
////
////        }
////
////        return oqItem;
////    }
//
//
//    public static OqItem copyOqItemValues(OqItem oqItem) {
//        Gson gson = new Gson();
//        String strItem = gson.toJson(oqItem);
//        OqItem oqItem1 = gson.fromJson(strItem, OqItem.class);
//        return oqItem1;
//    }
//
//
////    public static ArrayList<String> getUnduplacatedArlUidsFromArlOqItems(
////            ArrayList<OqItem> arlOqItems, String oqItemPivotT) {
////        ArrayList<String> arlUids = new ArrayList<>();
////        if (oqItemPivotT.equals(OQItemPivotT.UID_RECEIVED_BENEFIT)) {
////            for (int i = 0; i < arlOqItems.size(); i++) {
////                String name = arlOqItems.get(i).getUidreceivedbnf();
////                if (!arlUids.contains(name)) {
////                    arlUids.add(name);
////                }
////            }
////        } else if (oqItemPivotT.equals(OQItemPivotT.UID_GAVE_BENEFIT)) {
////            for (int i = 0; i < arlOqItems.size(); i++) {
////                String name = arlOqItems.get(i).getUidgavebnf();
////                if (!arlUids.contains(name)) {
////                    arlUids.add(name);
////                }
////            }
////        } else if (oqItemPivotT.equals(OQItemPivotT.UID_GETTOR)) {
////            for (int i = 0; i < arlOqItems.size(); i++) {
////                String name = arlOqItems.get(i).getUidgettor();
////                if (!arlUids.contains(name)) {
////                    arlUids.add(name);
////                }
////            }
////        } else if (oqItemPivotT.equals(OQItemPivotT.UID_PAYER)) {
////            for (int i = 0; i < arlOqItems.size(); i++) {
////                String name = arlOqItems.get(i).getUidpayer();
////                if (!arlUids.contains(name)) {
////                    arlUids.add(name);
////                }
////            }
////        }
////
////        return arlUids;
////    }
//   static String TAG = "OqItemUtil";
//
//    public static long getSumOqItemAmmounts(ArrayList<OqItem> arlOqItems) {
//
//        long returnVal = 0;
//
//        for (int i = 0; i < arlOqItems.size(); i++) {
//            Log.d(TAG , "getSumOqItemAmmounts() "+String.valueOf(arlOqItems.get(i).getAmmount()));
//            returnVal += arlOqItems.get(i).getAmmount();
//        }
//
//        return returnVal;
//    }
//
//
////    public static OqItem mergeOqItemsPivotPerson(ArrayList<OqItem> arlOqItems, String oqItemPivotT) {
////
////        ArrayList<ArrayList<OqItem>> arlReturn = new ArrayList<>();
////
////        /**
////         * Get uids without Duplication
////         */
////        ArrayList<String> arlUidsByPivot = getUnduplacatedArlUidsFromArlOqItems(
////                arlOqItems,
////                oqItemPivotT);
////
////        ArrayList<OqItem> arl = new ArrayList<OqItem>();
////
////        for (int i = 0; i < arlUidsByPivot.size(); i++) {
////
////            for (int j = 0; j < arlOqItems.size(); j++) {
////
////                long ammountPerPerson = 0;
////
////                if (arlOqItems.get(j).getUidreceivedbnf().equals(arlUidsByPivot.get(i))) {
////
////                    ammountPerPerson = ammountPerPerson + arlOqItems.get(j).getAmmount();
////
////                    if (arl.get(i) == null) {
////                        OqItem newOqItem = arlOqItems.get(0);
////                        newOqItem.setAmmount(0);
////                        arl.set(i, newOqItem);
////                    }
////                    arl.get(i).setAmmount(ammountPerPerson);
////                }
////
////            }
////
////        }
////
////    }
//
//
////    public static OqItem getEffectOqItem_FromCauseOqItem(OqItem oqItem) {
////
////        if (oqItem.getUidgavebnf() != null &&
////                oqItem.getUidreceivedbnf() != null) {
////
////        }
////
////    }
////
////
////    public static OqItem fillInfo(OqItem oqItem) {
////
////        if (oqItem.getUidgavebnf() != null &&
////                oqItem.getUidreceivedbnf() != null) {
////
////        }
////
////    }
////
////
////    @DebugLog
////    public static OqItem getCompensatingOqItem_FromBenefittingOqItem(
////            String uidGaveBnf,
////            OqItem benefittingOqItem) {
////
////        OqItem oqItem = new OqItem();
////
////
////        for (int i = 0; i < benefittingOqItems.size(); i++) {
////            oqItem.setAmmount(oqItem.getAmmount() + benefittingOqItems.get(i).getAmmount());
////        }
////
////        oqItem.setUidgettor(benefittingOqItems.get(0).getUidgavebnf());
////        oqItem.setUidpayer(null);
////        return oqItem;
////    }
//
//
////    @DebugLog
////    public static OqItem getCompensatingOqItem_FromBenefittingOqItems(
////            String uidGaveBnf,
////            ArrayList<OqItem> benefittingOqItems) {
////        OqItem oqItem = new OqItem();
////
////        for (int i = 0; i < benefittingOqItems.size(); i++) {
////            oqItem.setAmmount(oqItem.getAmmount() + benefittingOqItems.get(i).getAmmount());
////        }
////
////        oqItem.setUidgettor(benefittingOqItems.get(0).getUidgavebnf());
////        oqItem.setUidpayer(null);
////        return oqItem;
////    }
////
////    public static ArrayList<OqItem> getBenefittingOqItems_FromCompensatingOqItemFrom(
////            OqItem compensatingOqItem) {
////        ArrayList<OqItem> arl = new ArrayList<OqItem>();
////
////
////    }
//
//}
