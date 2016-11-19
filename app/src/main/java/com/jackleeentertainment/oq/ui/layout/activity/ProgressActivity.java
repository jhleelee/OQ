package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.ContactsUtil;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 11. 17..
 */

public class ProgressActivity extends AppCompatActivity {
    Activity mActivity = this;
    Profile myProfile = ProfileUtil.getMyProfileWithUidNameEmail(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUILayoutOnCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initOnResume();
    }


    void initUILayoutOnCreate() {
        setContentView(R.layout.activity_progress);
    }


    void initOnResume() {


        final String progressT = getIntent().getStringExtra("progressT");

        if (progressT.equals(ProgressT.UPDATE_CONTACT_PHONE)) {

            ArrayList<String> arl = ContactsUtil.getArlPhoneNumFromMyDevice();

            //send to backend : get -> set to my and send to his;

            for (final String strNum : arl) {
                getNumNumTrueAndMore(strNum);
            }

        } else if (progressT.equals(ProgressT.UPDATE_CONTACT_EMAIL)) {

            ArrayList<String> arl = ContactsUtil.getArlEmailFromMyDevice();

            //send to backend : get -> set to my and send to his;

            for (final String strEmail : arl) {
                getEmailEmailTrueAndMore(strEmail);
            }
        }

    }


    void getNumNumTrueAndMore(final String strNum) {
        App.fbaseDbRef
                .child(FBaseNode0.NumNumTrue)
                .child(strNum)
                .child(App.getUphone(this))
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {
                                    boolean bool = dataSnapshot.getValue(Boolean.class);
                                    if (bool) {
                                        getNumProfileAndMore(strNum);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }


    void getNumProfileAndMore(String strNum) {
        //get Profile
        App.fbaseDbRef
                .child(FBaseNode0.NumProfile)
                .child(strNum)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Profile profile = dataSnapshot.getValue(Profile.class);
                                    if (profile!=null) {
                                        setHisProfileToMyContact(profile);
                                        setMyProfileToHisContact(profile);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }


    void setMyProfileToHisContact(Profile hisProfile){
        App.fbaseDbRef
                .child(FBaseNode0.MyContacts)
                .child(hisProfile.uid)
                .child(myProfile.uid)
                .setValue(myProfile)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        }
                );
    }


    void setHisProfileToMyContact(Profile hisProfile) {

            App.fbaseDbRef
                    .child(FBaseNode0.MyContacts)
                    .child(App.getUid(mActivity))
                    .child(hisProfile.uid)
                    .setValue(hisProfile)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            }
                    );

    }




    void getEmailEmailTrueAndMore(final String strEmail) {
        App.fbaseDbRef
                .child(FBaseNode0.EmailEmailTrue)
                .child(strEmail)
                .child(App.getUemail(this))
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {
                                    boolean bool = dataSnapshot.getValue(Boolean.class);
                                    if (bool) {
                                        getEmailProfileAndMore(strEmail);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }


    void getEmailProfileAndMore(String strEmail) {
        //get Profile
        App.fbaseDbRef
                .child(FBaseNode0.EmailProfile)
                .child(strEmail)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Profile profile = dataSnapshot.getValue(Profile.class);
                                    if (profile!=null) {
                                        setHisProfileToMyContact(profile);
                                        setMyProfileToHisContact(profile);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }


}
