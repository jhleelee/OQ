package com.jackleeentertainment.oq.generalutil;

import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by Jacklee on 2016. 10. 20..
 */

public class JT {

    public static String str(long time){

        Calendar calNow = Calendar.getInstance();

        Calendar calTgt = Calendar.getInstance();
        calTgt.setTimeInMillis(time);

        if (calNow.get(Calendar.YEAR)!=calTgt.get(Calendar.YEAR)){
            return DateFormat.format("yyyy/MM/dd", calTgt).toString();
        } else {

            if (calNow.get(Calendar.DAY_OF_YEAR)!=calTgt.get(Calendar.DAY_OF_YEAR)){
                return DateFormat.format("MM/dd", calTgt).toString();
            } else {
                return DateFormat.format("aa HH:mm", calTgt).toString();
            }


        }


    }


}
