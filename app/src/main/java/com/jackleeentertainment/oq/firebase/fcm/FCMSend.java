package com.jackleeentertainment.oq.firebase.fcm;

 import com.jackleeentertainment.oq.object.Chat;
 import com.jackleeentertainment.oq.object.ChatM;
 import com.jackleeentertainment.oq.object.ChatR;

/**
 * Created by Jacklee on 2016. 9. 27..
 */

public class FCMSend {



    public static void send(Chat chat){
        FcmMetaData fcmMetaData = new FcmMetaData();
        fcmMetaData.msgType = FcmMetaDataMsgTypeT.CHATSingleId;

        new FCMATask(
                chat.getRid(),
                fcmMetaData,
                chat.getTxt()
        ).execute();
    }


    public static void send(ChatR chat){
        FcmMetaData fcmMetaData = new FcmMetaData();
        fcmMetaData.msgType = FcmMetaDataMsgTypeT.CHATRoomId;

        new FCMATask(
                chat.getRoomid(),
                fcmMetaData,
                chat.getTxt()
        ).execute();
    }


    public static void send(ChatM chat){
        FcmMetaData fcmMetaData = new FcmMetaData();
        fcmMetaData.msgType = FcmMetaDataMsgTypeT.CHATMultiId;

        new FCMATask(
                chat.getArlrids(),
                fcmMetaData,
                chat.getTxt()
        ).execute();
    }


}
