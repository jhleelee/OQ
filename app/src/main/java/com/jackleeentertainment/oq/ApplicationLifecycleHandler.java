package com.jackleeentertainment.oq;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Jacklee on 15. 11. 13..
 */
public class ApplicationLifecycleHandler implements android.app.Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {


    private static final String TAG = ApplicationLifecycleHandler.class.getSimpleName();
    private static boolean isInBackground = false;


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted");

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed");
        initFirebaseMyZListener();
        setFirebaseIamHere_NoNeedPush();

        if (isInBackground) {
            Log.d(TAG, "appBarLayout went to foreground");
            isInBackground = false;
            setFirebaseIamHere_NoNeedPush();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused");

    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped");

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.d(TAG, "onActivitySaveInstanceState");

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed");

    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        Log.d(TAG, "onConfigurationChanged");

    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory");

    }

    @Override
    public void onTrimMemory(int i) {
        Log.d(TAG, "onTrimMemory");

        if (i == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Log.d(TAG, "appBarLayout went to background");
            isInBackground = true;
            setFirebaseIamLeaving_DoNeedPush();
            quitFirebaseMyZListener();
        }
    }


    private void setFirebaseIamHere_NoNeedPush() {
        Log.d(TAG, "setFirebaseIamHere_NoNeedPush");
        if (App.fbaseDbRef != null && App.getUID() != null)
            App.fbaseDbRef.child("p").child(App.getUID()).setValue(true);
    }

    private void setFirebaseIamLeaving_DoNeedPush() {
        Log.d(TAG, "setFirebaseIamLeaving_DoNeedPush");
        if (App.fbaseDbRef != null && App.getUID() != null)

            App.fbaseDbRef.child("p").child(App.getUID()).setValue(null);
    }

    ChildEventListener cELMyZ;

    private void initFirebaseMyZListener() {
        Log.d(TAG, "initFirebaseMyZListener()");

        if (App.fbaseDbRef != null && App.getUID() != null) {
            Log.d(TAG, "Application.fbaseDbRef != null && Application.UID != null");

            /********
             * EventListenerObj
             */
            if (cELMyZ == null) {
                Log.d(TAG, "cELMyZ == null");

                cELMyZ = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        Log.d(TAG, "dataSnapshot " + dataSnapshot.getKey()); //JHE : PushKey
                        Log.d(TAG, "dataSnapshot " + dataSnapshot.getValue().toString());
                        //{i=Jp25BJbpc0, n=BB, t=ㅇㄱ, r=NEMCaVaB2z, p=1447160223082}

//                        FbaseSet.set_Z_MyUid_PushKey_Null(dataSnapshot.getKey());
//                        DealWithReceivedMsg.deal(dataSnapshot);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {

                    }
                };
            }
            zref = App.fbaseDbRef.child("z").child(App.getUID());
            queryZref = zref.orderByKey();

            if (!isQueryZrefEVAttached) {
                queryZref.addChildEventListener(cELMyZ);
                isQueryZrefEVAttached = true;
                zref.keepSynced(true);
            }
        }
    }

    DatabaseReference zref;
    Query queryZref;
    boolean isQueryZrefEVAttached = false;

    private void quitFirebaseMyZListener() {
        Log.d(TAG, "quitFirebaseMyZListener()");
        if (isQueryZrefEVAttached) {
            queryZref.removeEventListener(cELMyZ);
            isQueryZrefEVAttached = false;
            zref.keepSynced(false);

        }
    }





}
