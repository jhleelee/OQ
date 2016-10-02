package com.jackleeentertainment.oq.firebase.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.object.Profile;


/**
 * Created by Jacklee on 2016. 9. 24..
 */

public class GetValue {

    /*
    Profile.class
     */

    public static void myProfileCls(final boolean isRamLBR) {
        App.fbaseDbRef
                .child(FBaseNode0.ProfileToMe)
                .child(App.getUID())
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (isRamLBR) {
                                    Ram.myProfile = dataSnapshot.getValue(Profile.class);
                                    LBR.send(FBaseNode0.ProfileToMe + LBR.SendSuffixT.RECEIVED, Ram
                                            .myProfile);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }


    public static void profileCls(final String FBaseNode0T,
                                  final String uidAsSecondNode,
                                 final boolean isRamLBR) {
         App.fbaseDbRef
                .child(FBaseNode0T) // FBaseNode0T.ProfileToFriend ; FBaseNode0T.ProfileToPublic
                .child(uidAsSecondNode) //targetUid
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (isRamLBR) {
                                    Profile profile = dataSnapshot.getValue(Profile.class);
                                    profile.setUid(uidAsSecondNode);
                                    Ram.addProfile(uidAsSecondNode, profile);
                                    LBR.send(FBaseNode0T + LBR.SendSuffixT.RECEIVED, profile);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }

        /*
    Post.class
     */

    public static void postCls(
                                  final String oid,
                                  final boolean isRamLBR) {
        App.fbaseDbRef
                .child(FBaseNode0.Post)
                .child(oid)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (isRamLBR) {
                                    Post post = dataSnapshot.getValue(Post.class);
                                    post.setOid(oid);
                                    Ram.addPost(oid, post);
                                    LBR.send(FBaseNode0.Post + LBR.SendSuffixT.RECEIVED, post);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }



}
