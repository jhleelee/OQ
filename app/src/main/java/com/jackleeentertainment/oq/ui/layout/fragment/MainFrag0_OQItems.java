package com.jackleeentertainment.oq.ui.layout.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.MyOqPerson;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvaDtlVHolderMainFrag0Item;

import java.util.ArrayList;
import java.util.Calendar;

import hugo.weaving.DebugLog;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag0_OQItems extends ListFrag {

    boolean isContactItemExists = false;
    boolean isEmptyViewShown = false;
    boolean isProgressViewShown = true;

    String TAG = this.getClass().getSimpleName();
    View view;
    Fragment mFragment = this;
    FirebaseRecyclerAdapter<MyOqPerson, AvaDtlVHolderMainFrag0Item>
            firebaseRecyclerAdapterMyOqPerson;
    Resources res = App.getContext().getResources();
    boolean isQueryAComplete = false;
    boolean isQueryBComplete = false;


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
    void uiProgressOrEmptyOnResume() {
        super.uiProgressOrEmptyOnResume();

        if (isContactItemExists ) {
                roProgress.setVisibility(View.GONE);
                isProgressViewShown = false;

                ro_empty_list.setVisibility(View.GONE);
                isEmptyViewShown = false;

        } else {
            roProgress.setVisibility(View.GONE);
            isProgressViewShown = false;

            ro_empty_list.setVisibility(View.VISIBLE);
            isEmptyViewShown = true;
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

        //testPurpose
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2100);
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long t = calendar.getTimeInMillis();
        //1479440361455 or 1479513451485
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = App.fbaseDbRef
                .child(FBaseNode0.MyOqPerson)
                .child(App.getUid(getActivity()))
                .orderByChild("ts");

        /*
        rts
         */

        firebaseRecyclerAdapterMyOqPerson = new FirebaseRecyclerAdapter
                <MyOqPerson, AvaDtlVHolderMainFrag0Item>
                (MyOqPerson.class,
                        R.layout.item_mainfrag0,
                        AvaDtlVHolderMainFrag0Item.class,
                        query

                ) {

            public void populateViewHolder(
                    final AvaDtlVHolderMainFrag0Item avaDtlVHolderMainFrag0Item,
                    final MyOqPerson oqPerson,
                    final int position) {


                if (oqPerson != null) {



                    //get content data
                    //"my_oqperson" - [uid] - {MyOqPerson(ts,{Profile})}

                    final ArrayList<OqDo> oqDoList = new ArrayList<>();


                    Query queryA =
                            App.fbaseDbRef
                                    .child(FBaseNode0.OqDo)
                                    .orderByChild("uidab")
                                    .equalTo(App.getUid(getActivity()) + ",," + oqPerson.profile.uid);

                    Query queryB =
                            App.fbaseDbRef
                                    .child(FBaseNode0.OqDo)
                                    .orderByChild("uidab")
                                    .equalTo(oqPerson.profile.uid
                                            + ",," + App.getUid(getActivity
                                            ()));

                    queryA
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dKeyDoValueActualKey) {
                                    if (dKeyDoValueActualKey.exists()) {

                                        for (DataSnapshot dataSnapshotActual : dKeyDoValueActualKey
                                                .getChildren()) {

                                            OqDo oqDo = dataSnapshotActual.getValue(OqDo.class);
                                            oqDoList.add(oqDo);

                                            isQueryAComplete = true;
                                            if (isQueryBComplete) {
                                                uiOqDos(avaDtlVHolderMainFrag0Item, oqPerson,
                                                        oqDoList);
                                            }
                                        }


                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                    queryB
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dKeyDoValueActualKey) {

                                    if (dKeyDoValueActualKey.exists()) {
                                        DataSnapshot mDataSnapshotActual;

                                        for (DataSnapshot dataSnapshotActual : dKeyDoValueActualKey
                                                .getChildren()) {


                                            OqDo oqDo = dataSnapshotActual.getValue(OqDo.class);
                                            oqDoList.add(oqDo);


                                            isQueryBComplete = true;
                                            if (isQueryAComplete) {
                                                uiOqDos(avaDtlVHolderMainFrag0Item, oqPerson,
                                                        oqDoList);
                                            }
                                        }


                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    avaDtlVHolderMainFrag0Item.mView.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(
                                            mFragment.getActivity(),
                                            ProfileActivity.class);
                                    intent.putExtra("Profile", oqPerson.profile);
                                    intent.putExtra("isMe", false);
                                    startActivity(intent);

                                }
                            });
                }


            }


            void uiOqDos(AvaDtlVHolderMainFrag0Item avaDtlVHolderMainFrag0Item, MyOqPerson
                    oqPerson,
                         ArrayList<OqDo> oqDoList) {


                isContactItemExists = true;

                if (isProgressViewShown) {
                    roProgress.setVisibility(View.GONE);
                    isProgressViewShown = false;
                }

                if (isEmptyViewShown) {
                    ro_empty_list.setVisibility(View.GONE);
                    isEmptyViewShown = false;
                }


                JM.glideProfileThumb(
                        oqPerson.profile,
                        avaDtlVHolderMainFrag0Item.ivAva,
                        avaDtlVHolderMainFrag0Item.tvAva,
                        mFragment
                );

                avaDtlVHolderMainFrag0Item.tvName.setText(
                        oqPerson.profile.full_name
                );

                avaDtlVHolderMainFrag0Item
                        .tvContent
                        .setTextColor(JM.colorById(R.color.text_black_54));

                JM.uiTvContentMainFrag0( avaDtlVHolderMainFrag0Item.tvContent, oqDoList,
                        getActivity());



                avaDtlVHolderMainFrag0Item.tvTs.setText(  JT.str(oqPerson.getTs()) );


                JM.uiTvResultAmmount(avaDtlVHolderMainFrag0Item.tvResultAmmount,
                        OqDoUtil.getSumOqDoAmmountsAgreedTwo(oqDoList, getActivity()));


                JM.uiTvResultAmmount2(avaDtlVHolderMainFrag0Item.tvResultAmmount2,
                        OqDoUtil.getSumOqDoAmmountsDisAgreed(oqDoList, getActivity()));


                ;


            }

            ;
        };

        recyclerView.setAdapter(firebaseRecyclerAdapterMyOqPerson);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isContactItemExists = false) {

                    if (firebaseRecyclerAdapterMyOqPerson.getItemCount() == 0) {

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
        if (firebaseRecyclerAdapterMyOqPerson != null) {
            firebaseRecyclerAdapterMyOqPerson.cleanup();
        }
    }

}