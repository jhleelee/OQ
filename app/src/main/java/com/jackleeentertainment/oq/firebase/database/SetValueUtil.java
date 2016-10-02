package com.jackleeentertainment.oq.firebase.database;


import com.jackleeentertainment.oq.App;

/**
 * Created by Jacklee on 2016. 9. 24..
 */

public class SetValueUtil {

    public static String getPushKey(String firstNode) {
        return App.fbaseDbRef
                .child(firstNode)
                .push()
                .getKey();
    }

    public static String getPushKey(String firstNode, String secondNode) {
        return App.fbaseDbRef
                .child(firstNode)
                .child(secondNode)
                .push()
                .getKey();
    }

    public static String getPushKey(String firstNode, String secondNode, String thirdNode) {
        return App.fbaseDbRef
                .child(firstNode)
                .child(secondNode)
                .child(thirdNode)
                .push()
                .getKey();
    }
}
