package com.jackleeentertainment.oq.firebase.database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Chat;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 9. 24..
 */

public class SetValue {

    public static void myPossibleContactsWithPhoneOrEmail(ArrayList<String> arlPhoneOrEmail) {

        //arl or HashMap? or UpdateChildren?

        App.fbaseDbRef
                .child(FBaseNode0.MyPContacts)
                .child(App.getUID())
                .setValue(arlPhoneOrEmail, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {


                            LBR.send(FBaseNode0.MyPContacts + LBR.SendSuffixT.SENT, null);

                        }
                    }
                });
    }


    /*
    Profile.class
    */

    public static void myProfile(final Profile myProfile,
                                 final boolean toRamLBR) {
        App.fbaseDbRef
                .child(FBaseNode0.ProfileToMe)
                .child(App.getUID())
                .setValue(myProfile, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            if (toRamLBR) {
                                Ram.myProfile = myProfile;
                                LBR.send(FBaseNode0.ProfileToMe + LBR.SendSuffixT.SENT, Ram.myProfile);
                            }
                        }
                    }
                });
    }

    public static void profile(final String FBaseNode0T,
                               final Profile myProfile,
                               final boolean toRamLBR) {
        App.fbaseDbRef
                .child(FBaseNode0T)
                .child(App.getUID())
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
}
