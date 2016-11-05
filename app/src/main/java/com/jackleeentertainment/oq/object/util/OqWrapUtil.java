package com.jackleeentertainment.oq.object.util;

import android.content.res.Resources;
import android.widget.ImageView;

import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqWrap;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.types.OqWrapT;

import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class OqWrapUtil {


    public static void ivTwoAvaRelation(ImageView iv, OqWrap oqWrap) {


        String t = getOqWrapT(oqWrap);


        if (t.equals(OqWrapT.ASentGetReq__BArgued)) {
            JM.BGD(iv, R.drawable.cir_requested_but_argued);
            JM.ID(iv, R.drawable.ic_arrow_forward_white_24dp);

        } else if (t.equals(OqWrapT.ASentGetReq__BNoArgued_NotPaidAll)) {
            JM.BGD(iv, R.drawable.cir_requested);
            JM.ID(iv, R.drawable.ic_arrow_forward_white_24dp);

        } else if (t.equals(OqWrapT.ASentGetReq__BNoArgued_ClaimPaidAll__AYetConfirmed)) {
            JM.BGD(iv, R.drawable.cir_requested_so_claimpaid);
            JM.ID(iv, R.drawable.ic_check_white_24dp);

        } else if (t.equals(OqWrapT.ASentGetReq__BNoArgued_ClaimPaidAll__ADidConfirmed)) {
            JM.BGD(iv, R.drawable.cir_requested_so_paid);
            JM.ID(iv, R.drawable.ic_check_white_24dp);

        } else if (t.equals(OqWrapT.ASentGetPastNoti__BArgued)) {
            // consider a/b avatar position shift?

        } else if (t.equals(OqWrapT.ASentGetPastNoti__BNoArgued)) {

        } else if (t.equals(OqWrapT.ASentPaySuggest__BArgued)) {

        } else if (t.equals(OqWrapT.ASentPaySuggest__BNoArgued_NotReceivedAll)) {

        } else if (t.equals(OqWrapT.ASentPaySuggest__BNoArgued_DidReceivedAll)) {

        } else if (t.equals(OqWrapT.ASentPayPastNoti__BYetConfirmed)) {

        } else if (t.equals(OqWrapT.ASentPayPastNoti__BDidConfirmed)) {

        }


    }


    public static String getOqWrapStr(OqWrap oqWrap) {
        Resources res = App.getContext().getResources();
        String t = getOqWrapT(oqWrap);
        String str = "";
        List<OqDo> listoqdo = oqWrap.getListoqdo();
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

                        //return OqWrapT.ASentGetReq__BNoArgued_ClaimPaidAll__ADidConfirmed;
                        return res.getString(
                                R.string.req_amtvar_whatphrasevar,
                                J.strAmt(firstOqdo.getAmmount()),
                                res.getString(R.string.whovar_all_paid_confirmed, firstOqdo
                                        .getNameb()));


                    } else {
                        //return OqWrapT.ASentGetReq__BNoArgued_ClaimPaidAll__AYetConfirmed;

                             return res.getString(
                                    R.string.req_amtvar_whatphrasevar,
                                    J.strAmt(firstOqdo.getAmmount()),
                                    res.getString(R.string.whovar_all_claimpaid,
                                            firstOqdo.getNameb()));


                    }


                } else { //not paid all (incl partially paid)
                    //return OqWrapT.ASentGetReq__BNoArgued_NotPaidAll;

                    if (sumListBaPayPast==0){
                        return res.getString(
                                R.string.req_amtvar_whatphrasevar,
                                J.strAmt(firstOqdo.getAmmount()),
                                res.getString(R.string.whovar_all_notpaid,
                                        firstOqdo.getNameb()));
                    } else {

                    }


                }


            } else if (firstOqdo.getAmmount() > sumListBaPayFuture) { // B does not agree with A's req
                return OqWrapT.ASentGetReq__BArgued;

            }

        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.GET) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaPayPast = OqDoUtil.getListBaPayPast(listoqdo);
            long sumListBaPayPast = OqDoUtil.getSumAmt(listBaPayPast);

            if (firstOqdo.getAmmount() == sumListBaPayPast) {
                return OqWrapT.ASentGetPastNoti__BNoArgued;

            } else {
                return OqWrapT.ASentGetPastNoti__BArgued;

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
                    return OqWrapT.ASentPaySuggest__BNoArgued_DidReceivedAll;

                } else {
                    return OqWrapT.ASentPaySuggest__BNoArgued_NotReceivedAll;

                }


            } else {
                return OqWrapT.ASentPaySuggest__BArgued;


            }


        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaGetPast = OqDoUtil.getListBaGetPast(listoqdo);
            long sumListBaGetPast = OqDoUtil.getSumAmt(listBaGetPast);

            if (firstOqdo.getAmmount() == sumListBaGetPast) {
                return OqWrapT.ASentPayPastNoti__BDidConfirmed;

            } else {
                return OqWrapT.ASentPayPastNoti__BYetConfirmed;

            }


        }

        return null;


    }


    public static String getOqWrapT(OqWrap oqWrap) {

        List<OqDo> listoqdo = oqWrap.getListoqdo();
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

                        return OqWrapT.ASentGetReq__BNoArgued_ClaimPaidAll__ADidConfirmed;

                    } else { //not paid all (incl partially paid)
                        return OqWrapT.ASentGetReq__BNoArgued_ClaimPaidAll__AYetConfirmed;

                    }


                } else { //not paid all (incl partially paid)
                    return OqWrapT.ASentGetReq__BNoArgued_NotPaidAll;

                }


            } else if (firstOqdo.getAmmount() > sumListBaPayFuture) { // B does not agree with A's req
                return OqWrapT.ASentGetReq__BArgued;

            }

        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.GET) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaPayPast = OqDoUtil.getListBaPayPast(listoqdo);
            long sumListBaPayPast = OqDoUtil.getSumAmt(listBaPayPast);

            if (firstOqdo.getAmmount() == sumListBaPayPast) {
                return OqWrapT.ASentGetPastNoti__BNoArgued;

            } else {
                return OqWrapT.ASentGetPastNoti__BArgued;

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
                    return OqWrapT.ASentPaySuggest__BNoArgued_DidReceivedAll;

                } else {
                    return OqWrapT.ASentPaySuggest__BNoArgued_NotReceivedAll;

                }


            } else {
                return OqWrapT.ASentPaySuggest__BArgued;


            }


        }

        if (firstOqdo.getOqwhat().equals(OQT.DoWhat.PAY) &&
                firstOqdo.getOqwhat().equals(OQT.DoWhen.PAST)) {


            List<OqDo> listBaGetPast = OqDoUtil.getListBaGetPast(listoqdo);
            long sumListBaGetPast = OqDoUtil.getSumAmt(listBaGetPast);

            if (firstOqdo.getAmmount() == sumListBaGetPast) {
                return OqWrapT.ASentPayPastNoti__BDidConfirmed;

            } else {
                return OqWrapT.ASentPayPastNoti__BYetConfirmed;

            }


        }

        return null;

    }


}
