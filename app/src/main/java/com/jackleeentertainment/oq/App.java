package com.jackleeentertainment.oq;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.annotation.StyleRes;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;

/**
 * Application class responsible for initializing singletons and other common components.
 */
public class App extends Application {

    private final static String TAG = Application.class.getSimpleName();
    private static Context appContext;
    private String firebaseStorageSecondNodeForDpi;

    //User Profile
    static Profile myProfile;
    public static Uri UrlFbaseBasicPhoto;

    //User Login
    public static String token;

    //Firebase Ref
    public static FirebaseAuth mAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference fbaseDbRef;
    public static FirebaseStorage firebaseStorage;
    public static StorageReference fbaseStorageRef;

    public static boolean GIVEN_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGES = false;
    public static int CURRENTFRAGMENT_MAINACTIVITY = 0;




    public static Application getInstance() {
        return new App();
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "Application.onCreate - Initializing application...");
        super.onCreate();
        appContext = getApplicationContext();
        myProfile = new Profile();
        FirebaseApp.initializeApp(appContext);

        initFbaseDatabase();

        //Facebook Logger
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        initApplicationLifecycleHandler();
        Log.d(TAG, "Application.onCreate - Application initialized OK");
    }

    public static void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static final int RC_SIGN_IN = 100;

    @MainThread
    @StyleRes
    private static int getSelectedTheme() {
//        if (mUseDefaultTheme.isChecked()) {
//            return AuthUI.getDefaultTheme();
//        }

//        if (mUsePurpleTheme.isChecked()) {
//            return R.style.PurpleTheme;
//        }
//
        return AuthUI.getDefaultTheme();
    }

    @MainThread
    public static String[] getSelectedProviders() {
        ArrayList<String> selectedProviders = new ArrayList<>();

        selectedProviders.add(AuthUI.EMAIL_PROVIDER);

        selectedProviders.add(AuthUI.FACEBOOK_PROVIDER);

        selectedProviders.add(AuthUI.GOOGLE_PROVIDER);

        return selectedProviders.toArray(new String[selectedProviders.size()]);
    }

    @MainThread
    public static String getSelectedTosUrl() {
//        if (mUseGoogleTos.isChecked()) {
//            return GOOGLE_TOS_URL;
//        }

        return FIREBASE_TOS_URL;
    }

    private static final String FIREBASE_TOS_URL =
            "https://www.firebase.com/terms/terms-of-service.html";


    public static void initFbaseDatabase() {
        Log.d(TAG, "initFbaseDatabase()");
        if (firebaseDatabase==null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
    }

    public static void initFbaseDatabaseRef() {
        Log.d(TAG, "initFbaseDatabaseRef()");
        fbaseDbRef = firebaseDatabase.getReference();
        fbaseDbRef.keepSynced(true);
    }

    public static void initFbaseStorage() {
        firebaseStorage = FirebaseStorage.getInstance();
        fbaseStorageRef = firebaseStorage.getReferenceFromUrl("gs://oqmoney-93ff2.appspot.com");
        Log.d(TAG, "firebaseStorage.getApp().getMname() :"+firebaseStorage.getApp().getName());

    }


    public static Context getContext() {
        return appContext;
    }



    public static void initFREFifNull() {
        if (fbaseDbRef == null) {
            fbaseDbRef = firebaseDatabase.getReference();
        }
    }


    private void initApplicationLifecycleHandler() {
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);
    }

    public static FirebaseUser firebaseUser;

    public static void initFirebaseUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.


            Task<GetTokenResult> taskGetTokenResult = firebaseUser.getToken(false);

            taskGetTokenResult.addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    Log.d(TAG, "onSuccess() token : "+ getTokenResult.getToken());

                   token = getTokenResult.getToken();
                }
            });

            firebaseUserToMyProfile(firebaseUser);

            // Name, email address, and profile photo Url
             UrlFbaseBasicPhoto = firebaseUser.getPhotoUrl();

        }
    }








    public static FirebaseUser getFirebaseUser(Activity activity){
        Log.d(TAG, "getFirebaseUser()");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null) {
            return user;
        } else {
            nullifyUser();

            activity.startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setTheme(R.style.FullscreenTheme)
                            .setProviders(App.getSelectedProviders())
                            .build(),
                    App.RC_SIGN_IN);
            return null;
        }
    }






    public static String getUid(Activity activity){
        Log.d(TAG, "getUid()");
        if (App.firebaseUser!=null&&
                App.firebaseUser.getUid()!=null){
            Log.d(TAG, "return " + App.firebaseUser.getUid());
            return  App.firebaseUser.getUid();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "return " + user.getUid());
                return user.getUid();
            } else {
                // No user is signed in
                nullifyUser();

                activity.startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setTheme(R.style.FullscreenTheme)
                                .setProviders(App.getSelectedProviders())
                                .build(),
                        App.RC_SIGN_IN);
                Log.d(TAG, "return " + "nullvalue");
                return "nullvalue";
            }
        }
    }


    public static String getUname(Activity activity){


        if (App.firebaseUser!=null&&
                App.firebaseUser.getUid()!=null){
            return  App.firebaseUser.getDisplayName();

        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in
                return user.getDisplayName();
            } else {
                // No user is signed in
                nullifyUser();

                activity.startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setTheme(R.style.FullscreenTheme)
                                .setProviders(App.getSelectedProviders())
                                .build(),
                        App.RC_SIGN_IN);

                return null;
            }
        }
    }

    public static String getUemail(Activity activity){


        if (App.firebaseUser!=null&&
                App.firebaseUser.getUid()!=null){
            return  App.firebaseUser.getEmail();

        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in
                return user.getEmail();
            } else {
                // No user is signed in
                nullifyUser();

                activity.startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setTheme(R.style.FullscreenTheme)
                                .setProviders(App.getSelectedProviders())
                                .build(),
                        App.RC_SIGN_IN);

                return null;
            }
        }
    }
    /*
    User
     */

    public static void initFirebaseUser(FirebaseUser firebaseUser) {

        Log.d(TAG, "initFirebaseUser()");
        App.firebaseUser = firebaseUser;

        if (firebaseUser != null) {
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.

            Task<GetTokenResult> taskGetTokenResult = firebaseUser.getToken(false);

            taskGetTokenResult.addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    Log.d(TAG, "onSuccess() token : "+ getTokenResult.getToken());
                    token = getTokenResult.getToken();
                }
            });

            firebaseUserToMyProfile(firebaseUser);
            UrlFbaseBasicPhoto = firebaseUser.getPhotoUrl();
        }
    }


    public static void firebaseUserToMyProfile(FirebaseUser firebaseUser){
        Log.d(TAG, firebaseUser.getUid());
        if (myProfile == null)
            myProfile = new Profile();
        myProfile.setUid(firebaseUser.getUid());
        myProfile.setFull_name(firebaseUser.getDisplayName());
        myProfile.setEmail(firebaseUser.getEmail());
    }



    public static void nullifyUser() {

        firebaseUser = null;
        myProfile = null;
        token = null;
        UrlFbaseBasicPhoto = null;
    }


    /*
    Getter and Setter
     */
    public String getFirebaseStorageSecondNodeForDpi() {
        return firebaseStorageSecondNodeForDpi;
    }

    public void setFirebaseStorageSecondNodeForDpi(String fStorageSecondNodeForDpi) {
        this.firebaseStorageSecondNodeForDpi = fStorageSecondNodeForDpi;
    }

    public Profile getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(Profile myProfile) {
        this.myProfile = myProfile;
    }



}
