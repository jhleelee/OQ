package com.jackleeentertainment.oq.object.util;

import android.app.Activity;

import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.generalutil.J;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Jacklee on 2016. 11. 8..
 */

public class ChatUtil {

    public static String createRidWith2Ids(String uidA, String uidB) {
        ArrayList<String> arl = new ArrayList<String>();
        arl.add(uidA);
        arl.add(uidB);
        Collections.sort(arl);
        String rid = arl.get(0) + "__" + arl.get(1);
        return rid;

    }

    public static String getOppoUidFromRidWith2Ids(String rid, Activity activity) {


        if (rid != null) {
            ArrayList<String> arl = new ArrayList<String>(Arrays.asList(rid.split("__")));
            arl.remove(App.getUid(activity));
            return arl.get(0);
        } else {
            return null;
        }


    }


}
