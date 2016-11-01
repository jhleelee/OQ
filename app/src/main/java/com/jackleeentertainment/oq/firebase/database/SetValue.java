package com.jackleeentertainment.oq.firebase.database;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Chat;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;
import java.util.Map;

import static com.jackleeentertainment.oq.generalutil.ObjectUtil.getHashMapValueOfTrueFromArrayListOfString;

/**
 * Created by Jacklee on 2016. 9. 24..
 */

public class SetValue {

    public static void myPossibleContactsWithPhoneOrEmail(ArrayList<String> arlPhoneOrEmail,
                                                          Activity activity) {

        final Map<String, Object> map = getHashMapValueOfTrueFromArrayListOfString(arlPhoneOrEmail);

        App.fbaseDbRef
                .child(FBaseNode0.MyPContacts)
                .child(App.getUid(activity))
                .updateChildren(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        LBR.send(FBaseNode0.MyPContacts + LBR.SendSuffixT.SENT, map);

                    }
                })
        ;

    }


    /*
    Profile.class
    */


    public static void profile(final String FBaseNode0T,
                               final Profile myProfile,
                               final boolean toRamLBR,
                               Activity activity) {
        App.fbaseDbRef
                .child(FBaseNode0T)
                .child(App.getUid(activity))
                .setValue(myProfile, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            if (toRamLBR) {
                                Ram.myProfile = myProfile;
                                LBR.send(FBaseNode0T + LBR.SendSuffixT.SENT, Ram.myProfile);
                            }
                        }
                    }
                });
    }

    /*
    Post.class
    */

    public static void post(final Post post,
                            final boolean toRamLBR) {
        App.fbaseDbRef
                .child(FBaseNode0.Post)
                .child(post.getOid())
                .setValue(post, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            if (toRamLBR) {
                                LBR.send(FBaseNode0.Post + LBR.SendSuffixT.SENT, post);
                            }
                        }
                    }
                });
    }

    public static void postWithPushId(final Post post,
                                      final boolean toRamLBR) {
        String pushId = SetValueUtil.getPushKey(FBaseNode0.Post);
        post.setOid(pushId);
        App.fbaseDbRef
                .child(FBaseNode0.Post)
                .child(post.getOid())
                .setValue(post, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            if (toRamLBR) {
                                LBR.send(FBaseNode0.Post + LBR.SendSuffixT.SENT, post);
                            }
                        }
                    }
                });
    }

    public static void cache(final Chat chat,
                             final boolean toRamLBR) {

    }


        /*
    OQ
    */
        public static void myOQItems(final Post post,
                                final boolean toRamLBR) {
            App.fbaseDbRef
                    .child(FBaseNode0.MyOqItems)
                    .child(post.getOid())
                    .setValue(post, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                if (toRamLBR) {
                                    LBR.send(FBaseNode0.Post + LBR.SendSuffixT.SENT, post);
                                }
                            }
                        }
                    });
        }







}
