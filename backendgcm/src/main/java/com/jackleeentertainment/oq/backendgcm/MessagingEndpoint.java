/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package com.jackleeentertainment.oq.backendgcm;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.OnSuccessListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
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
        name = "messaging",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backendgcm.oq.jackleeentertainment.com",
                ownerName = "backendgcm.oq.jackleeentertainment.com",
                packagePath = ""
        )
)
public class MessagingEndpoint {
    private static final Logger log = Logger.getLogger(MessagingEndpoint.class.getName());

    /**
     * Api Keys can be obtained from the google cloud console
     */
    private static final String API_KEY = System.getProperty("gcm.api.key");


    FirebaseApp firebaseApp = null;


    private void initFirebaseApp() {

        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setServiceAccount(
                            new ByteArrayInputStream(
                                    Constant.cred.getBytes())
                    )
                    .setDatabaseUrl("https://oqmoney-93ff2.firebaseio.com/")
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);

            log.info("initFirebaseApp() : done");

        } catch (Exception e) {
            log.warning("Exception initFirebaseApp : " + e.toString());

        }
    }

    /**
     * Send to the first 10 devices (You can modify this to send to any number of devices or a specific device)
     *
     * @param message The message to send
     */
    public void sendMessage(@Named("message") String message) throws IOException {
        if (message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", message).build();
        List<RegistrationRecord> records = ofy()
                .load()
                .type(RegistrationRecord.class)
                .limit(10).list();

        for (RegistrationRecord record : records) {

            Result result = sender.send(msg, record.getRegId(), 5);

            if (result.getMessageId() != null) {
                log.info("Message sent to " + record.getRegId());
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    // if the regId changed, we have to update the datastore
                    log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                    record.setRegId(canonicalRegId);
                    ofy().save().entity(record).now();
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    log.warning("Registration Id " + record.getRegId() + " no longer registered with GCM, removing from datastore");
                    // if the device is no longer registered with Gcm, remove it from the datastore
                    ofy().delete().entity(record).now();
                } else {
                    log.warning("Error when sending message : " + error);
                }
            }
        }
    }


    private void trySendSingleMessage(String rg, Message msg) {
        Sender sender = new Sender(API_KEY);

        try {

            Result result = sender.send(
                    msg,
                    rg,
                    1);
            updateReceiverRegId(result, rg);
        } catch (Exception e) {
            log.warning("Exception when Message sening : " + e.toString());
        }

    }


    private void updateReceiverRegId(final Result result, final String rg) {


        if (result.getMessageId() != null) {
            log.info("Message sent to " + rg);
            final String canonicalRegId = result.getCanonicalRegistrationId(); //JHE : of Receiver

            if (canonicalRegId != null) {
                // if the regId changed, we have to update the datastore
                log.info("Registration Id changed for " + rg + " updating to " + canonicalRegId);

                FirebaseDatabase.getInstance(firebaseApp).getReference()
                        .child("FCM")
                        .child(rg)
                        .addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String prevRg = dataSnapshot.getKey();
                                String Uid = dataSnapshot.getValue().toString();

                                log.info("onDataChange prevRg, Uid: " + prevRg + ", " + Uid);

                                //A
                                FirebaseDatabase.getInstance(firebaseApp).getReference()
                                        .child("P").child(Uid).child("rg").setValue(canonicalRegId);


                                //B
                                FirebaseDatabase.getInstance(firebaseApp).getReference()
                                        .child("FCM").child(canonicalRegId).setValue(Uid);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


            }
        } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                log.warning("Registration Id " + rg + " no longer registered with GCM, removing from datastore");
                // if the device is no longer registered with Gcm, remove it from the datastore

                FirebaseDatabase.getInstance(firebaseApp).getReference()
                        .child("FCM").child(rg).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String prevRg = dataSnapshot.getKey();
                        String Uid = dataSnapshot.getValue().toString();
                        log.info("onDataChange prevRg, Uid: " + prevRg + ", " + Uid);

                        //A
                        FirebaseDatabase.getInstance(firebaseApp).getReference()
                                .child("P").child(Uid).child("rg").setValue(null);


                        //B
                        FirebaseDatabase.getInstance(firebaseApp).getReference()
                                .child("FCM").child(prevRg).setValue(null);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                log.warning("Error when sending message : " + error); //InvalidRegistration
            }
        }


    }


    @ApiMethod
    public void sendSingleChat(
            @Named("rg") final String rg,
            @Named("nm") final String nm,
            @Named("tx") final String tx,
            @Named("token") final String token

    ) {

        log.info("rg : " + rg);
        log.info("nm : " + nm);
        log.info("tx : " + tx);
        log.info("token : " + token);


        if (firebaseApp == null) {
            initFirebaseApp();
        } else {

            FirebaseAuth.getInstance(firebaseApp).verifyIdToken(token)
                    .addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
                        @Override
                        public void onSuccess(FirebaseToken decodedToken) {

                            String snd = decodedToken.getUid();
                            log.info("onSuccess() senderUid : " + snd);


                            if (tx == null || tx.trim().length() == 0) {
                                log.warning("Not sending message because it is empty");
                                return;
                            }

                            Message msg = new Message.Builder()
                                    .addData("z", "c") //CONTEXT
                                    .addData("i", snd) //SenderId
                                    .addData("n", nm) //SenderName
                                    .addData("t", cropText(tx, 1000)) //Text
                                    .build();

                            trySendSingleMessage(rg, msg);
                        }
                    });

        }
    }

    private Message getMessageFromPOJO(Object obj) {


        Message msg = new Message.Builder()
                .setData(getMapWithStringedValueFromPOJO(obj))
                .addData("z", obj.getClass().getSimpleName())
                .build();
        return msg;
    }


    private void setMessageDataFromPOJO(Message msg, Object obj) {

        msg = new Message.Builder()
                .setData(getMapWithStringedValueFromPOJO(obj))
                .addData("z", obj.getClass().getSimpleName())
                .build();
    }


    public static Map<String, String> getMapWithStringedValueFromPOJO(Object obj) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                String dataType = field.get(obj).getClass().getSimpleName();
                map.put(field.getName().toString(), dataType.charAt(0) + "__" + String.valueOf(field.get(obj)));
            } catch (IllegalArgumentException e1) {
            } catch (IllegalAccessException e1) {
            }
        }
        return map;
    }
}
