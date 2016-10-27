/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package com.jackleeentertainment.oq.backendgcm;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Named;

import static com.jackleeentertainment.oq.backendgcm.OfyService.ofy;

/**
 * An endpoint to send messages to devices registered with the backend
 * <p>
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 * <p>
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(
        name = "messagingEndpoint",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backendgcm.oq.jackleeentertainment.com",
                ownerName = "backendgcm.oq.jackleeentertainment.com",
                packagePath = ""
        )
)
public   class MessagingEndpoint {
    private   final Logger log = Logger.getLogger(MessagingEndpoint.class.getName());

    /**
     * Api Keys can be obtained from the google cloud console
     */
    private   final String API_KEY = System.getProperty("gcm.api.key");

    @ApiMethod
    public   void checkTokenSendMessageToSingleReceiver(
            @Named("token") final String token, // includes senderUid
            @Named("rid") final String rid,     // includes receiverUid (single)
            @Named("meta") final String meta,     // includes type
            @Named("txt") final String txt       //includes only content
    ) throws IOException {

        // (0) check if there is meta, txt
        if (meta == null || meta.trim().length() == 0) {
            log.warning("Not sending message because meta is empty");
            return;
        }

        if (txt == null || txt.trim().length() == 0) {
            log.warning("Not sending message because txt is empty");
            return;
        }


        // (1) check token and get uid, uname
        FirebaseAuth.getInstance().verifyIdToken(token)
                .addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        String senderUid = decodedToken.getUid();
                        String senderName = decodedToken.getName();

                        // (2) get receiver's uid, regId
                        UserObj receiverUserObj = ofy()
                                .deadline(2.0)
                                .cache(true)
                                .load()
                                .type(UserObj.class)
                                .id(rid)
                                .now();

                        if (receiverUserObj == null) {
                            return;
                        } else {
                            // (3) build a message instance
                            Message msg = new Message.Builder()
                                    .addData("sid", senderUid) //SenderId
                                    .addData("sname", senderName) //SenderName
                                    .addData("meta", meta) //SenderName
                                    .addData("txt", cropText(txt, 1000)) //Text
                                    .build();

                            // (4) send it!
                            trySendMessage(msg, receiverUserObj);

                        }
                    }
                });
    }


    private   void trySendMessage(Message msg, UserObj receiverUserObj) {

        Sender sender = new Sender(API_KEY);

        try {
            Result result = sender.send(
                    msg,
                    receiverUserObj.regId,
                    0);

            if (result.getCanonicalRegistrationId() != null) {
                updateReceiverRegId(result, receiverUserObj);
            }
            ;

        } catch (Exception e) {
            log.warning("Exception when Message sening : " + e.toString());
        }

    }

    private   void trySendMessage(Message msg, Map<String, UserObj> mapReciverUidUserObj) {

        Sender sender = new Sender(API_KEY);
        ArrayList<UserObj> arlReceiverUserObj = new ArrayList<>();
        ArrayList<String> arlReceiverRegId = new ArrayList<>();

        Iterator it = mapReciverUidUserObj.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            arlReceiverUserObj.add((UserObj) pair.getValue());
            arlReceiverRegId.add(((UserObj) pair.getValue()).regId);
            it.remove(); // avoids a ConcurrentModificationException
        }

        try {
            MulticastResult multicastResult = sender.send(
                    msg,
                    arlReceiverRegId,
                    0);

            if (multicastResult.getResults() != null) {
                updateReceiverRegId(multicastResult, arlReceiverUserObj);
            }
            ;

        } catch (Exception e) {
            log.warning("Exception when Message sening : " + e.toString());
        }

    }


    @ApiMethod
    public   void checkTokenSendMessageToMultipleReceiver(
            @Named("token") final String token, // includes senderUid
            @Named("gsonrids") final String gsonrids,     // includes receiverUid (single)
            @Named("meta") final String meta,     // includes type
            @Named("txt") final String txt       //includes type, content
    ) throws IOException {

        // (0) check if there is meta, txt
        if (meta == null || meta.trim().length() == 0) {
            log.warning("Not sending message because meta is empty");
            return;
        }

        if (txt == null || txt.trim().length() == 0) {
            log.warning("Not sending message because txt is empty");
            return;
        }


        // (1) check token and get uid, uname
        FirebaseAuth.getInstance().verifyIdToken(token)
                .addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
                    @Override
                    public void onSuccess(FirebaseToken decodedToken) {
                        String senderUid = decodedToken.getUid();
                        String senderName = decodedToken.getName();

                        // (2) get receivers' uid, regId
                        Gson gson = new Gson();
                        String[] arReceiverUids = gson.fromJson(gsonrids, new
                                TypeToken<String[]>() {
                                }.getType());

                        Map<String, UserObj> mapReciverUidUserObj = ofy()
                                .load()
                                .type(UserObj.class)
                                .ids(arReceiverUids);

                        if (mapReciverUidUserObj == null || mapReciverUidUserObj.size() == 0) {
                            return;
                        } else {
                            // (3) build a message instance
                            Message msg = new Message.Builder()
                                    .addData("sid", senderUid) //SenderId
                                    .addData("sname", senderName) //SenderName
                                    .addData("meta", meta) //SenderName
                                    .addData("txt", cropText(txt, 1000)) //Text
                                    .build();

                            // (4) send it!
                            trySendMessage(msg, mapReciverUidUserObj);

                        }
                    }
                });
    }


    private   void updateReceiverRegId(final Result result, final UserObj receiverUserObjOld
    ) {

        if (result.getMessageId() != null) {
            log.info("Message sent to " + receiverUserObjOld.uid);

            final String canonicalRegId = result.getCanonicalRegistrationId(); //JHE : of Receiver
            if (canonicalRegId != null && !receiverUserObjOld.regId.equals(canonicalRegId)) {
                // if the regId changed, we have to update the datastore
                log.info("Registration Id changed for " + receiverUserObjOld.uid + " updating to " +
                        canonicalRegId);
                UserObj userObj = new UserObj();
                userObj.uid = receiverUserObjOld.uid;
                userObj.regId = canonicalRegId;
                ofy()
                        .deadline(2.0)
                        .save()
                        .entity(userObj); // asynchronous
            }
        } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                log.warning("Registration Id " + receiverUserObjOld.uid + " no longer registered with GCM, removing from datastore");
                // if the device is no longer registered with Gcm, remove it from the datastore
                ofy().delete().type(UserObj.class).id(receiverUserObjOld.uid); // asynchronous
            } else {
                log.warning("Error when sending message : " + error); //InvalidRegistration
            }
        }
    }


    private   void updateReceiverRegId(final MulticastResult multicastResult,
                                     ArrayList<UserObj> arlReceiverUserObj
    ) {

    }

    private   String cropText(String tx, int limit) {

        String _tx = "";

        if (tx.length() > limit) {
            _tx = tx.substring(0, limit) + "[...]";
        } else {
            _tx = tx;
        }

        return _tx;
    }
}

