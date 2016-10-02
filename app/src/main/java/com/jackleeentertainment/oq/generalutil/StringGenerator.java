package com.jackleeentertainment.oq.generalutil;

import android.content.Context;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.types.PostT;

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


    public static String xAndXPeople(ArrayList<Profile> arl) {

        if (arl!=null && arl.size()>0){

            if (arl.size()==1){
                return arl.get(0).getFull_name() + "님";
            } else if (arl.size()==2){
                return arl.get(0).getFull_name() + "님" + "," + arl.get(1).getFull_name() + "님";
            } else {
                return arl.get(0).getFull_name() + "님 외 " +J.st(arl.size()-1) + "명";
            }
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
