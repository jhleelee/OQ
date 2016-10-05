package com.jackleeentertainment.oq.generalutil;

import android.content.Context;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.types.PostT;
import com.jackleeentertainment.oq.object.util.ProfileUtil;

import java.util.ArrayList;


/**
 * Created by Jacklee on 2016. 9. 10..
 */
public class StringGenerator {


    public static String xWantToXAboutMoney(String name, String wantToDoSomething) {

        // get locale
        if (name != null) {

            if (wantToDoSomething.equals(OQT.WantT.GET)) {
                return name + "님은 " + JM.strById(R.string.wanttoget_sentence);
            } else if (wantToDoSomething.equals(OQT.WantT.PAY)) {
                return name + "님은 " + JM.strById(R.string.wanttoget_sentence);
            }


        }
        return null;

    }


    public static String xNeedToXAboutMoney(String nameAndNumPeople, String wantToDoSomething) {

        // get locale
        if (nameAndNumPeople != null) {

            if (wantToDoSomething.equals(OQT.WantT.GET)) {
                return nameAndNumPeople + "에게 " + JM.strById(R.string.requesttopay_sentence);
            } else if (wantToDoSomething.equals(OQT.WantT.PAY)) {
                return nameAndNumPeople + "에게 " + JM.strById(R.string.requesttoget_sentence);
            }

        }
        return null;

    }


    public static String xPeople(ArrayList<Profile> arl) {

        if (arl != null && arl.size() > 0) {
            return arl.get(0).getFull_name() + JM.strById(R.string.count_ppl);
        }

        return null;

    }


    public static String xAndXPeople(ArrayList<Profile> arl) {

        if (arl != null && arl.size() > 0) {

            if (arl.size() == 1) {
                return arl.get(0).getFull_name() + "님";
            } else if (arl.size() == 2) {
                return arl.get(0).getFull_name() + "님" + "," + arl.get(1).getFull_name() + "님";
            } else {
                return arl.get(0).getFull_name() + "님 외 " + J.st(arl.size() - 1) + "명";
            }
        }

        return null;

    }


    public static String xPeopleColonPeopleNamesWithComma(ArrayList<Profile> arl) {

        ArrayList<String> arlNames = ProfileUtil.getArlName(arl);
        String namesComma = J.stFromArlWithComma(arlNames);
        if (arl != null && arl.size() > 0) {
            return xPeople(arl) + " : " + namesComma;
        }
        return null;
    }


    public static String xSentMessage(String name, int msgNum, Context context) {

        // get locale

        if (msgNum == 1) {
            return name + " " + JM.strById(R.string.sent, context) + " " + JM.strById(R.string.message, context) + ".";
        } else {
            return name + " " + JM.strById(R.string.sent, context) + " " + J.st(msgNum) + " " + JM.strById(R.string.message, context) + "s.";
        }

    }


    public static String postedx(Post post) {

        // get locale

        return JM.strById(R.string.posted) + " " + postT(post) + ".";
    }


    public static String xPostedx(String name, Post post, int postNum, Context context) {

        // get locale

        if (postNum == 1) {
            return name + " " + JM.strById(R.string.posted, context) + " " + postT(post) + ".";
        } else {
            return name + " " + JM.strById(R.string.posted, context) + " " + J.st(postNum) + " " + postT(post) + "s.";
        }

    }


    /**
     * Sentence
     */

    public static String xPaidAAToQAsTheRepOfAll_Sentence(String nameX, String nameQ, int numAll, double moneyAll) {

        //[A]가 [식당]에 3인 모두의 대표로 합계 [XX]원을 지출하였습니다.

        return nameX + "님은 " + nameQ + "에게 " +
                J.st(numAll) + "인 모두의 대표로서 " +
                "합계 " + J.st(moneyAll) + "원" + "을 지출하였습니다.";
    }

    public static String thatAAWasUsedForXYXForBBEachSoXWantsToGetBBFromEachOfXZ(
            String nameX, ArrayList<String> arlNameYZ, double moneyAll, double moneyEach) {

        //위 [XX]원은 [A],[B],[C] 각각을 위해 [YY]원씩 사용되었기 때문에, [A]는 [B],[C]에게서 각각 [YY]씩 받기를 원합니다. (선택 후 금액 변경 가능)
        return "위 " + J.st(moneyAll) + "은 " +
                nameX + "," + J.stFromArlWithComma(arlNameYZ) + " " +
                "각각을 위해 " +
                J.st(moneyEach) + "원씩 사용되었기 때문에" + ", " +
                nameX + "은 " +
                J.stFromArlWithComma(arlNameYZ) + "에게서 " +
                "각각 + " + J.st(moneyEach) + "원씩 " +
                "받기를 원합니다 ";

    }


    public static String itIsBecauseXSpentToQBBAsRepOfAllNum(
            String nameX, String nameQ, ArrayList<String> arlNameYZ, double moneyAll) {

        //그 이유는 [A]가 [식당]에 3인 모두의 대표로 합계 [YY]원을 지출하였기 때문입니다.
        return
                "그 이유는 " +
                        nameX + "가 " +
                        nameQ + "에게 " +
                        J.st(arlNameYZ.size() + 1) + "인 모두의 대표로 " +
                        "합계 " + moneyAll + "원을 " +
                        "지출하였기 때문입니다. ";

    }


    public static String itIsBecauseXIsGoingToCollectHoebiFromYZ(
            String nameX, String nameQ, ArrayList<String> arlNameYZ, double moneyAll) {

        //그 이유는[A]가 총무로서[B],[C]에게서 회비를 걷으려고 하기 때문입니다.
        return
                "그 이유는 " +
                        nameX + "가 총무로서 " +
                        J.stFromArlWithComma(arlNameYZ) + "에게서 " +
                        "회비를 걷으려고 하기 때문입니다.";
    }


    public static String sumMoneyBBOfAllNumWillBeUsedForAll(
            String nameX, String nameQ, ArrayList<String> arlNameYZ, double moneyAll) {

        //3인분에 해당하는 합계 [YY]원은  [A],[B],[C] 모두를 위해 사용될 것입니다.
        return
                J.st(arlNameYZ.size() + 1) + "인분에 해당하는 " +
                        "합계 " + J.st(moneyAll) + "은 " +
                        nameX + "," + J.stFromArlWithComma(arlNameYZ) + " " +
                        "모두를 위해 사용될 것입니다.";
    }


    public static String xWantToPayAAToY(String xName, String yName, double moneyToPay) {

        // [A]는 [B]에게 [XX]원의 돈을 주기를 원합니다.
        return xName + "은 " +
                yName + "에게 " +
                J.st(moneyToPay) + "원을 " +
                "주기를 원합니다 ";
    }

    public static String itIsBecauseYSpentAAToX(String xName, String yName, double moneyToPay) {

        // 그 이유는 [B]가 [A]를 위하여 [XX]원을 대신 지출하였기 때문입니다.
        return "그 이유는 " +
                yName + "가 " +
                xName + "를 위하여 " +
                J.st(moneyToPay) + "원을 " +
                "대신 지출하였기 때문입니다.";
    }


    public static String itIsBecauseYIsGoingToCollectHoebiFromXOrElseMembers(String xName, String yName) {

        // 그 이유는 [B]가 총무로서 [A] 또는 그 외의 회원들으로부터  회비를 걷으려고 하기 때문입니다.
        return "그 이유는 " +
                yName + "가 총무로서" +
                xName + " 또는 그 외의 회원들로부터 " +
                "회비를 걷으려고 하기 때문입니다.";
    }

    public static String sumHoebiYIsGoingToCollectWillBeUsedForXYOrElseMembers(String xName, String yName) {

        //  [B]가 걷게 될 회비 총액은 [A],[B] 또는 그 외의 회원들 모두를 위해 사용될 것입니다.
        return yName + "가 걷게 될 회비 총액은 " +
                xName + "," + yName +
                " 또는 그 외의 회원들 모두를 위하여 " +
                "사용될 것입니다.";
    }


    /**
     * Phrase
     */

    public static String asTheRepresentativeOfNumAll_Phrase(int numAll) {
        return J.st(numAll) + "인 모두의 대표로 ";
    }


    public static String spentSumAA_Phrase(double moneyAll) {
        return "합계 " + J.st(moneyAll) + "원" + "을 지출하였습니다. ";
    }

    public static String becauseBBWasUsedEach(double money) {
        return J.st(money) + "원씩 사용되었기 때문에";
    }

    public static String xWantToGetEachAAFromYZ(String xName, ArrayList<String> arlYZName, double moneyEach) {
        return xName + "은 " +
                J.stFromArlWithComma(arlYZName) + "에게서 " +
                "각각 + " +
                J.st(moneyEach) + "원씩 " +
                "받기를 원합니다 ";
    }


    public static String postT(Post post) {
        switch (post.getPosttype()) {
            case PostT.NONE:
                return JM.strById(R.string.post);

            case PostT.PHOTO:
                return JM.strById(R.string.photo);

            case PostT.VIDEO:
                return JM.strById(R.string.video);

            default:
                return null;

        }
    }


}
