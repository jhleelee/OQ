package com.jackleeentertainment.oq.firebase.fcm;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.backendgcm.messagingEndpoint.MessagingEndpoint;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jacklee on 16. 6. 6..
 */
public class FCMATask extends AsyncTask<Void, Void, Void> {

    String TAG = "FCMATask";
    MessagingEndpoint messagingEndpoint = null;
    private Context context;
    String rid; //receiverUid
    ArrayList<String> arlReceiverUid = new ArrayList<>();
    FcmMetaData fcmMetaData;
    String txt;


    public FCMATask(
            String rid,
            FcmMetaData fcmMetaData,
            String txt
    ) {
        super();
        this.rid = rid;
        this.fcmMetaData = fcmMetaData;
        this.txt = txt;
    }


    public FCMATask(
            ArrayList<String> arlReceiverUid,
            FcmMetaData fcmMetaData,
            String txt
    ) {
        super();
        this.arlReceiverUid = arlReceiverUid;
        this.fcmMetaData = fcmMetaData;
        this.txt = txt;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (messagingEndpoint == null) {  // Only do this once
            MessagingEndpoint.Builder builder =
                    new MessagingEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("https://oqmoney-93ff2.appspot.com/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
            // end options for devappserver
            messagingEndpoint = builder.build();
        }




        if (App.getToken() != null) {

            if (rid != null &&
                    (arlReceiverUid == null || arlReceiverUid.size() == 0)) {


                try {
                    /**
                     @Named("token") final String token, // includes senderUid
                     @Named("rid") final String rid,     // includes receiverUid (single)
                     @Named("meta") final String meta,     // includes type
                     @Named("txt") final String txt       //includes only content
                     **/

                    messagingEndpoint.messagingEndpoint().checkTokenSendMessageToSingleReceiver(
                            App.getToken(),
                            rid,
                            getJsonMeta(fcmMetaData),
                            txt
                    ).execute();

                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }


            } else if (rid == null &&
                    (arlReceiverUid != null && arlReceiverUid.size() >0)){
                try {
                    /**
                     @Named("token") final String token, // includes senderUid
                     @Named("rid") final String rid,     // includes receiverUid (single)
                     @Named("meta") final String meta,     // includes type
                     @Named("txt") final String txt       //includes only content
                     **/

                    messagingEndpoint.messagingEndpoint().checkTokenSendMessageToMultipleReceiver(
                            App.getToken(),
                            getArlReceiverUid(arlReceiverUid),
                            getJsonMeta(fcmMetaData),
                            txt
                    ).execute();

                } catch (IOException e) {
                    Log.d(TAG, e.toString());
                }

            }
        } else {
            Log.d(TAG, "token is null!!!");
        }



        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "FCM Sent");

    }

    String getJsonMeta(FcmMetaData fcmMetaData) {
        Gson gson = new Gson();
        return gson.toJson(fcmMetaData);
    }


    String getArlReceiverUid(ArrayList<String> arlReceiverUid) {
        Gson gson = new Gson();
        return gson.toJson(arlReceiverUid);
    }


}