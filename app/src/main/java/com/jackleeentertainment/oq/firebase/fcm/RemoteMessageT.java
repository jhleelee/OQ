package com.jackleeentertainment.oq.firebase.fcm;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Jacklee on 2016. 9. 20..
 */

public class RemoteMessageT {
    final static int NONE=0;
    final static int POST_OR_ALARM=1;
    final static int CHAT=2;
    final static int FRIENDSHIP=3;

    public static int getT(RemoteMessage remoteMessage){

        String tag = remoteMessage.getNotification().getTag();

        if (tag.equals("post")||tag.equals("alarm")){

            return POST_OR_ALARM;
        } else if (tag.equals("chat")){

            return CHAT;
        } else if (tag.equals("friend")){

            return FRIENDSHIP;
        } else {

            return NONE;
        }


    }
}
