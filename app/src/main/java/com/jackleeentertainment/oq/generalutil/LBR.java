package com.jackleeentertainment.oq.generalutil;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;

/**
 * Created by Jacklee on 16. 5. 9..
 */
public class LBR {
   static String TAG = "LBR";

  public   static class SendSuffixT {
       public  final static String SENT = "s";
      public final static String RECEIVED = "r";

  }



    public static void send(String reason) {
        Log.d(TAG, "send()");

        /**
         * Create an intent with a given action.  All other fields (data, type,
         * class) are null.  Note that the action <em>must</em> be in a
         * namespace because Intents are used globally in the system -- for
         * example the system VIEW action is android.intent.action.VIEW; an
         * application's custom action would be something like
         * com.google.app.myapp.CUSTOM_ACTION.
         *
         * @param action The Intent action, such as ACTION_VIEW.
         */
        Intent intent = new Intent();
        intent.putExtra("origin", "com.jackleeentertainment.oq." + reason);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    public static void send(String reason, Object object) {
        Log.d(TAG, "send()");

        /**
         * Create an intent with a given action.  All other fields (data, type,
         * class) are null.  Note that the action <em>must</em> be in a
         * namespace because Intents are used globally in the system -- for
         * example the system VIEW action is android.intent.action.VIEW; an
         * application's custom action would be something like
         * com.google.app.myapp.CUSTOM_ACTION.
         *
         * @param action The Intent action, such as ACTION_VIEW.
         */

        Intent intent = new Intent();
        intent.putExtra("origin", "com.jackleeentertainment.oq." + reason);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(object);
        intent.putExtra("data", jsonStr);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }


}
