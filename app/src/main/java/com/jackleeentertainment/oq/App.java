package com.jackleeentertainment.oq;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jackleeentertainment.oq.generalutil.JM;
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
        initFbaseDatabase();
        initApplicationLifecycleHandler();
        Log.d(TAG, "Application.onCreate - Application initialized OK");
    }

    public static void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }


    public static void initFirebaseAuthStateListener(final Activity context) {
        App.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    App.initFirebaseUser(user);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    App.nullifyUser();

                    context.startActivityForResult(
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setTheme(R.style.AppTheme)
                                    .setProviders(getSelectedProviders())
                                    .setTosUrl(getSelectedTosUrl())
                                    .build(),
                            RC_SIGN_IN);
                    context.finish();
                }

                // ...
            }
        };
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        Log.d(TAG, "firebaseDatabase.getApp().getMname() :"+firebaseDatabase.getApp().getName());
    }

    public static void initFbaseDatabaseRef() {
        Log.d(TAG, "initFbaseDatabaseRef()");
        fbaseDbRef = firebaseDatabase.getReference();
    }

    public static void initFbaseStorage() {
        firebaseStorage = FirebaseStorage.getInstance();
        fbaseStorageRef = firebaseStorage.getReferenceFromUrl("gs://project-5788136259447833395.appspot.com");
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


    public static String getUID(){

        if (App.myProfile.getUid()!=null){
            return  App.myProfile.getUid();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in

                myProfile.setUid( user.getUid());
                return user.getUid();
            } else {
                // No user is signed in
                nullifyUser();
                return null;
            }
        }

    }

    public static void setUID(String UID){
        myProfile.setUid(UID);

    }

    /*
    User
     */

    public static void initFirebaseUser(FirebaseUser firebaseUser) {

        Log.d(TAG, "initFirebaseUser()");
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