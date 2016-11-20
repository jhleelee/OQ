package com.jackleeentertainment.oq.ui.layout.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.activity.PeopleActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameEmailChkViewHolder;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class SearchProfileFrag extends ListFrag {

    boolean isContactItemExists = false;
    boolean isEmptyViewShown = false;
    boolean isProgressViewShown = true;



    String queryEmail = "";

    String TAG = this.getClass().getSimpleName();
    int numDone = 0;
    FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder>
            frvAdapterPub;

    View view;

    public SearchProfileFrag() {
        super();
    }

    @NonNull
    public static SearchProfileFrag newInstance() {
        return new SearchProfileFrag();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ro_empty_list.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);


    }


    @Override
    public void onResume() {
        super.onResume();
        queryGetPubProfiles(queryEmail);

    }

    @Override
    public void initUI() {
        super.initUI();
        searchView.setVisibility(View.VISIBLE);
        loEmpty.setBackgroundColor(JM.colorById(R.color.colorPrimaryDark));
        ivEmpty.setImageDrawable(JM.drawableById(R.drawable.ic_search_white_48dp));
        ivEmpty.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        tvEmptyTitle.setText(JM.strById(R.string.search_people));
        tvEmptyDetail.setText(JM.strById(R.string.search_people_long));
        tvEmptyLearnMore.setVisibility(View.GONE);

    }





    void initQueryByFullFirstLastNameOrEmail(String fullfirstlastnameOrEmail) {

        numDone = 0;
        final ArrayList<DataSnapshot> arlDataSnapshotOfProfiles = new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotAll) {
                if (dataSnapshotAll.exists()) {
                    if (dataSnapshotAll.hasChildren()) {
                        Iterator<DataSnapshot> it = dataSnapshotAll.getChildren().iterator();
                        while (it.hasNext()) {
                            DataSnapshot dItem = (DataSnapshot) it.next();
                            if (arlDataSnapshotOfProfiles.contains(dItem)) {
                                arlDataSnapshotOfProfiles.add(dItem);
                            }
                        }
                    }
                }
                numDone++;
                if (numDone == 3) {
                    LBR.send("initQueryByFullFirstLastNameOrEmail()", arlDataSnapshotOfProfiles);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //(1 - Full) Query by Full Name and Update
        Query queryFullName =
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .orderByChild("full_name")
                        .limitToFirst(16);
        queryFullName.addListenerForSingleValueEvent(valueEventListener);

        //(1 - Last) Query by Last Name and Update
        Query queryLastName =
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .orderByChild("last_name")
                        .limitToFirst(16);
        queryLastName.addListenerForSingleValueEvent(valueEventListener);

        //(1) Query by Last Name and Update
        Query queryEmail =
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .orderByChild("email")
                        .limitToFirst(32);
        queryEmail.addListenerForSingleValueEvent(valueEventListener);


    }

    ArrayList<Profile> arlProfileFromarlDataSnapshot(ArrayList<DataSnapshot> arlDS) {
        ArrayList<Profile> arlProfiles = new ArrayList<>();
        for (int i = 0; i < arlDS.size(); i++) {
            Profile profile = arlDS.get(i).getValue(Profile.class);
            profile.setUid(arlDS.get(i).getKey());
            arlProfiles.add(profile);
        }
        return arlProfiles;
    }


    void queryGetPubProfiles(String email) {

        if (queryEmail==null||queryEmail.length()==0){
            return;
        }


        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query queryEmail =
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .orderByChild("email")
                        .startAt(email)
                        .limitToFirst(50);

        frvAdapterPub = new FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder>
                (Profile.class,
                        R.layout.lo_avatar_titlesubtitle_chk,
                        AvatarNameEmailChkViewHolder.class,
                        queryEmail
                ) {


            public void populateViewHolder(
                    final AvatarNameEmailChkViewHolder avatarNameEmailChkViewHolder,
                    final Profile profile,
                    int position) {

                avatarNameEmailChkViewHolder.tvTitle__lo_avatartitlesubtitle_chk.setTextColor(
                        JM.colorById(R.color.text_black_87)
                );


                avatarNameEmailChkViewHolder.tvSubTitle__lo_avatartitlesubtitle_chk.setTextColor(
                        JM.colorById(R.color.text_black_54)
                );


                avatarNameEmailChkViewHolder.tvTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getFull_name()
                        );

                avatarNameEmailChkViewHolder.tvSubTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getEmail()
                        );

                //set Image

                //set Image
                JM.glideProfileThumb(
                        profile.getUid(),
                        profile.getFull_name(),
                        avatarNameEmailChkViewHolder.ro_person_photo_iv,
                        avatarNameEmailChkViewHolder.ro_person_photo_tv,
                        getActivity()
                );


                // if size is larger than 1, it is a problem.


                if (((PeopleActivity) getActivity()).arlSelectedProfile.contains(profile)) {
                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                            .setChecked(true);
                } else {
                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                            .setChecked(false);
                }

                avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (((PeopleActivity) getActivity()).arlSelectedProfile.contains
                                        (profile)) {
                                    ((PeopleActivity) getActivity()).arlSelectedProfile.remove(profile);
                                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk.setChecked(false);
                                } else {
                                    ((PeopleActivity) getActivity()).arlSelectedProfile.add(profile);
                                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk.setChecked(true);
                                }
                                ((PeopleActivity) getActivity()).bottomSheetControl((
                                        (PeopleActivity) getActivity()).arlSelectedProfile.size());
//                                notifyDataSetChanged();
                            }
                        });

                isContactItemExists = true;

                if (isProgressViewShown) {
                    roProgress.setVisibility(View.GONE);
                    isProgressViewShown = false;
                }

                if (isEmptyViewShown) {
                    ro_empty_list.setVisibility(View.GONE);
                    isEmptyViewShown = false;
                }
            }
        };

        recyclerView.setAdapter(frvAdapterPub);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isContactItemExists=false) {

                    if (frvAdapterPub.getItemCount() == 0) {

                        roProgress.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list.setVisibility(View.VISIBLE);
                        isEmptyViewShown = true;

                        isContactItemExists = false;


                    } else {
                        roProgress.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list.setVisibility(View.GONE);
                        isEmptyViewShown = false;

                        isContactItemExists = true;

                    }
                } else {
                    roProgress.setVisibility(View.GONE);
                    isProgressViewShown = false;

                    ro_empty_list.setVisibility(View.GONE);
                    isEmptyViewShown = true;

                }
            }
        }, 3000);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(frvAdapterPub!=null){
            frvAdapterPub.cleanup();
        }
    }


}
