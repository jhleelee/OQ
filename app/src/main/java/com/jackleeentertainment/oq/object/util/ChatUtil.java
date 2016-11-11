package com.jackleeentertainment.oq.object.util;

import com.jackleeentertainment.oq.App;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jacklee on 2016. 11. 8..
 */

public class ChatUtil {

    public static String createRidWith2Ids(String uidA, String uidB){
        ArrayList<String> arl = new ArrayList<String>();
        arl.add(uidA);
        arl.add(uidB);
        Collections.sort(arl);
        String rid = arl.get(0) + "__" + arl.get(1);
        return rid;

    }

}
