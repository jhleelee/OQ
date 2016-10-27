package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.adapter.ProfileArlRecentAndContactRVAdapter;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameDetailViewHolder;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameEmailChkViewHolder;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameViewHolder;

import java.util.ArrayList;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class RecentAndContactProfileFrag extends Fragment {
    View view;

    ArrayList<Profile> arlProfileIHavePhoneOrEmail;
    ArrayList<Profile> arlProfileMyRecent;
    String TAG = "RtCtctProfileFrag";

    RecyclerView rvAllMyContacts;
    RecyclerView rvRecent;
    TextView tvAllMyContacts, tvRecent;
    FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder> frvAdapterMyRecent,
            frvAdapterAllMyContact;

    RelativeLayout vProgress, vEmpty;


    @NonNull
    public static RecentAndContactProfileFrag newInstance() {
        return new RecentAndContactProfileFrag();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_recentcontactprofile, container, false);
        initUI();
        return view;
    }

    void initUI() {
        rvAllMyContacts = (RecyclerView) view.findViewById(R.id.rvAllMyContacts);
        rvRecent = (RecyclerView) view.findViewById(R.id.rvRecent);
        tvAllMyContacts = (TextView) view.findViewById(R.id.tvAllMyContacts);
        tvRecent = (TextView) view.findViewById(R.id.tvRecent);
        vProgress = (RelativeLayout) view.findViewById(R.id.vProgress);
        vEmpty = (RelativeLayout) view.findViewById(R.id.vEmpty);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkIfFirebaseListIsEmpty(getActivity());

        rvAllMyContacts.setHasFixedSize(true);
        rvAllMyContacts.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvRecent.setHasFixedSize(true);
        rvRecent.setLayoutManager(new LinearLayoutManager(getActivity()));

        frvAdapterAllMyContact = new FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder>
                (Profile.class,
                        R.layout.lo_avatar_titlesubtitle_chk,
                        AvatarNameEmailChkViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyContacts)
                                .child(App.getUid(getActivity()))
                ) {
            public void populateViewHolder(AvatarNameEmailChkViewHolder avatarNameEmailChkViewHolder,
                                           Profile profile,
                                           int position) {

                avatarNameEmailChkViewHolder.tvTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getFull_name()
                        );

                avatarNameEmailChkViewHolder.tvSubTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getEmail()
                        );

                //set Image
                Glide.with(getActivity())
                        .using(new FirebaseImageLoader())
                        .load(App.fbaseStorageRef
                                .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                .child(profile.getUid())
                                .child(FStorageNode.createMediaFileNameToDownload(
                                        FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                        profile.getUid()
                                )))
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(avatarNameEmailChkViewHolder.ro_person_photo_iv);
                ;

                avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //

                            }
                        });
            }
        };

        rvAllMyContacts.setAdapter(frvAdapterAllMyContact);


        frvAdapterMyRecent = new FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder>
                (Profile.class,
                        R.layout.lo_avatar_name,
                        AvatarNameEmailChkViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyRecent)
                                .child(App.getUid(getActivity()))
                ) {
            public void populateViewHolder(
                    AvatarNameEmailChkViewHolder avatarNameEmailChkViewHolder,
                    final Profile profile,
                    int position
            ) {


                avatarNameEmailChkViewHolder.tvTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getFull_name()
                        );

                avatarNameEmailChkViewHolder.tvSubTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getEmail()
                        );

                //set Image
                if (profile.getUid() != null) {
                    Glide.with(getActivity())
                            .using(new FirebaseImageLoader())
                            .load(App.fbaseStorageRef
                                    .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                    .child(profile.getUid())
                                    .child(FStorageNode.createMediaFileNameToDownload(
                                            FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                            profile.getUid()
                                    )))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(avatarNameEmailChkViewHolder.ro_person_photo_iv);
                }

                avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //

                            }
                        });


            }
        };
        rvRecent.setAdapter(frvAdapterMyRecent);
    }


    void checkIfFirebaseListIsEmpty(Activity activity) {


        App.fbaseDbRef
                .child(FBaseNode0.MyRecent)
                .child(App.getUid(activity))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            JM.G(rvRecent);
                            JM.G(tvRecent);
                        } else {
                            JM.V(rvRecent);
                            JM.V(tvRecent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        JM.G(rvRecent);
                        JM.G(tvRecent);
                    }
                });

        App.fbaseDbRef
                .child(FBaseNode0.MyContacts)
                .child(App.getUid(activity))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            JM.G(rvRecent);
                            JM.G(tvRecent);
                            JM.G(rvAllMyContacts);
                            JM.G(tvAllMyContacts);
                        } else {
                             JM.V(rvAllMyContacts);
                            JM.V(tvAllMyContacts);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        JM.G(rvRecent);
                        JM.G(tvRecent);
                        JM.G(rvAllMyContacts);
                        JM.G(tvAllMyContacts);
                    }
                });

    }

}
