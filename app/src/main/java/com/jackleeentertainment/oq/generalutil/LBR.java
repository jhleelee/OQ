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

    public static class SendSuffixT {
        public final static String SENDING = "g";

        public final static String SENT = "s";
        public final static String RECEIVED = "r";

    }

    public static class IntentFilterT {

        public final static String root =
                "com.jackleeentertainment.oq.";

        public final static String MainActivityDrawerMenu = root + "MainActivityDrawerMenu";
        public final static String NewGroupActivityTitlePhoto = root + "NewGroupActivityTitlePhoto";
        public final static String NewOQActivity =
                root + "NewOQActivity";


        public final static String NewOQActivity_Frag0 =
                root + "NewOQActivity_Fg0";
        public final static String NewOQActivity_Frag0_EasyInput = root + "NewOQActivity_Fg0_EI";
        public final static String
                NewOQActivity_ArlOppoProfile_Frag0 = root + "NewOQAct_ArlOpPro_Fg0";
        public final static String PeopleActivity =
                root + "PeopleActivity";
        public final static String PeopleActivity_Frag0 =
                root + "PeopleActivity_Fg0";
        public final static String PeopleActivity_Frag1 =
                root + "PeopleActivity_Fg1";
    }

    public static void send(String intentfilterT) {
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
        Intent intent = new Intent("com.jackleeentertainment.oq." + intentfilterT);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    public static void send(String intentfilterT, Object object) {
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
        intent.putExtra("origin", "com.jackleeentertainment.oq." + intentfilterT);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(object);
        intent.putExtra("data", jsonStr);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }

    public static void send(String intentfilterT, String str) {
        Log.d(TAG, "send()");
        Intent intent = new Intent();
        intent.putExtra("origin", "com.jackleeentertainment.oq." + intentfilterT);
        intent.putExtra("data", str);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }
}
