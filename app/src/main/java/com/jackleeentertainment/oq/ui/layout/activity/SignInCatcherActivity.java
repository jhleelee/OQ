package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.database.GetValue;
import com.jackleeentertainment.oq.firebase.database.SetValue;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;


/**
 * Created by Jacklee on 16. 5. 24..
 */
public class SignInCatcherActivity extends AppCompatActivity {

    private final static String TAG = SignInCatcherActivity.class.getSimpleName();
    private RelativeLayout roRoot;
    private boolean flag = true;
    Activity mActivity = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_catcher);
        roRoot = (RelativeLayout)findViewById(R.id.roRoot);
        App.initFirebaseAuth();
        App.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (flag) {

                        // User is signed in
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                        App.initFirebaseUser(user);
                        App.initFbaseDatabase();
                        App.initFbaseDatabaseRef();
                        App.initFbaseStorage();

                        flag = false;
                        goMainActivity();

                        ifProfilePublicNameEmailNullThenUplaodIt();

                    }

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    App.nullifyUser();

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setTheme(R.style.FullscreenTheme)
                                    .setProviders(App.getSelectedProviders())
                                    .build(),
                            App.RC_SIGN_IN);
                }

                // ...
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        App.mAuth.addAuthStateListener(App.mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (App.mAuthListener != null)
            App.mAuth.removeAuthStateListener(App.mAuthListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }

        showSnackbar(R.string.trouble_signing_in);
    }

    @MainThread
    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(roRoot, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }
    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "SignIn - Successful");
            return;
        }

        if (resultCode == RESULT_CANCELED) {
            showSnackbar(R.string.trouble_signing_in);
            return;
        }

        showSnackbar(R.string.trouble_signing_in);
    }


    /**************************************************
     * Next
     **************************************************/
    private void goMainActivity(){
        Intent intent = new Intent(SignInCatcherActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    /**************************************************
     * UI - Activity Anim
     **************************************************/
    @Override public void finish() { super.finish(); overridePendingTransition(0, 0); }


    void  ifProfilePublicNameEmailNullThenUplaodIt(){

        App.fbaseDbRef
                .child(FBaseNode0.ProfileToPublic) // FBaseNode0T.ProfileToFriend ; FBaseNode0T.ProfileToPublic
                .child(App.getUid(this)) //targetUid
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (!dataSnapshot.exists()){
                                    Profile profile = new Profile();
                                    profile.setUid(App.getUid(mActivity));
                                    profile.setFull_name(App.getFirebaseUser(mActivity).getDisplayName());
                                    profile.setEmail(App.getFirebaseUser(mActivity).getEmail());
                                    SetValue.profile(
                                            FBaseNode0.ProfileToPublic,
                                            profile,
                                            false,
                                            mActivity
                                    );
                                }
                                                                                           }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(TAG, databaseError.toString());
                            }
                        }
                );



    }
}
