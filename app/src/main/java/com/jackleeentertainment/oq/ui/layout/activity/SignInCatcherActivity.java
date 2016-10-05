package com.jackleeentertainment.oq.ui.layout.activity;

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
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;


/**
 * Created by Jacklee on 16. 5. 24..
 */
public class SignInCatcherActivity extends AppCompatActivity {

    private final static String TAG = SignInCatcherActivity.class.getSimpleName();
    RelativeLayout roRoot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_catcher);
        roRoot = (RelativeLayout)findViewById(R.id.roRoot);
        App.initFirebaseAuth();
        App. mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    App.initFirebaseUser(user);
                    goMainActivity();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    App.nullifyUser();

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setTheme(R.style.AppTheme)
                                    .setProviders(App.getSelectedProviders())
                                    .setTosUrl(App.getSelectedTosUrl())
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
        App.mAuth.removeAuthStateListener(App.mAuthListener);

    }

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
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
//            startActivity(SignedInActivity.createIntent(this));
            Log.d(TAG, "SignIn - Successful");
            App.initFirebaseUser();
            App.initFbaseDatabaseRef();
            App.initFbaseStorage();
            goMainActivity();
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



}
