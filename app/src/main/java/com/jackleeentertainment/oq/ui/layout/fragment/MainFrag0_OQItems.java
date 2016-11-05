package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.object.MyOppo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.OppoAvatarNameAmtTsViewHolder;

import hugo.weaving.DebugLog;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag0_OQItems extends ListFrag {
    String TAG = this.getClass().getSimpleName();
    View view;
    Fragment mFragment = this;
    FirebaseRecyclerAdapter<MyOppo, OppoAvatarNameAmtTsViewHolder> firebaseRecyclerAdapterOppo;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String searchKey = intent.getStringExtra("data");


            if (searchKey.equals("nav_choose_from_list_get") ||
                    searchKey.equals("nav_choose_from_list_pay") ||
                    searchKey.equals("nav_list_i_am_master") ||
                    searchKey.equals("nav_list_i_am_member")
                    ) {

                //query

            }
        }
    };


    public MainFrag0_OQItems() {
        super();
    }

    @NonNull
    public static MainFrag0_OQItems newInstance() {
        return new MainFrag0_OQItems();
    }


    @Override
    public void initUI() {
        super.initUI();


        tvEmptyTitle.setText(JM.strById(R.string.begin_transaction));
        tvEmptyDetail.setText(JM.strById(R.string.begin_transaction_long));
        tvEmptyLearnMore.setText(JM.strById(R.string.learn_more));
        ivEmpty.setImageDrawable(JM.drawableById(R.drawable.bg_hand0));

    }

    @DebugLog
    @Override
    void initAdapterOnResume() {
        super.initAdapterOnResume();

        if (App.fbaseDbRef != null) {

            initRVAdapter();


        } else {
            J.TOAST("App.fbaseDbRef!=null");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchView.setVisibility(View.GONE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(LBR.IntentFilterT.MainActivityDrawerMenu));
    }


    void initRVAdapter() {
        checkIfFirebaseListIsEmpty(getActivity());

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        firebaseRecyclerAdapterOppo = new FirebaseRecyclerAdapter<MyOppo, OppoAvatarNameAmtTsViewHolder>
                (MyOppo.class,
                        R.layout.i_oppo,
                        OppoAvatarNameAmtTsViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyOppoList)
                                .child(App.getUid(getActivity()))
                ) {

            public void populateViewHolder(
                    final OppoAvatarNameAmtTsViewHolder oppoAvatarNameAmtTsViewHolder,
                    final MyOppo myOppo,
                    final int position) {

                if (myOppo.getUid()!=null) {


                    JM.glideProfileThumb(
                            myOppo.getUid(),
                            myOppo.getUname(),
                            oppoAvatarNameAmtTsViewHolder.ivAvatar,
                            oppoAvatarNameAmtTsViewHolder.tvAvatar,
                            mFragment
                    );


                }

                oppoAvatarNameAmtTsViewHolder.tvName.setText(myOppo.getUname());
                oppoAvatarNameAmtTsViewHolder.tvDate.setText(JT.str(myOppo.getTs()));
                oppoAvatarNameAmtTsViewHolder.tvDeed.setText(StringGenerator.deed(myOppo));

                JM.tvAmtTextBgAboutMuOppo(oppoAvatarNameAmtTsViewHolder.tvAmtConfirmed, myOppo, 0);
                JM.tvAmtTextBgAboutMuOppo(oppoAvatarNameAmtTsViewHolder.tvAmtArgued, myOppo, 1);
                JM.tvAmtTextBgAboutMuOppo(oppoAvatarNameAmtTsViewHolder.tvAmtDone, myOppo, 2);

                oppoAvatarNameAmtTsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                                .child(myOppo.getUid())
                                .addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()){
                                                    Profile profile = dataSnapshot.getValue
                                                            (Profile.class);
                                                    Intent intent = new Intent(
                                                            mFragment.getActivity(),
                                                            ProfileActivity.class);
                                                    intent.putExtra("Profile",profile);
                                                    intent.putExtra("isMe",false);
                                                    startActivity(intent);
                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        }
                                );


                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapterOppo);
    }


    void checkIfFirebaseListIsEmpty(Activity activity) {

        JM.V(roProgress);

        App.fbaseDbRef
                .child(FBaseNode0.MyOppoList)
                .child(App.getUid(activity))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            JM.G(roProgress);
                            JM.V(ro_empty_list);
                        } else {
                            JM.G(roProgress);
                            JM.G(ro_empty_list);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        JM.G(roProgress);
                        JM.V(ro_empty_list);
                    }
                });

    }

}
