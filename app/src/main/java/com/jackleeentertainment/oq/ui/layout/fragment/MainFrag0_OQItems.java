package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.MyOqPerson;
import com.jackleeentertainment.oq.object.OqWrap;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.Ava2RelationDtlVHolder;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag0_OQItems extends ListFrag {
    String TAG = this.getClass().getSimpleName();
    View view;
    Fragment mFragment = this;
    FirebaseRecyclerAdapter<MyOqPerson, Ava2RelationDtlVHolder> firebaseRecyclerAdapterMyOqPerson;

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

    Resources res = App.getContext().getResources();

    void initRVAdapter() {
        checkIfFirebaseListIsEmpty(getActivity());

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query = App.fbaseDbRef
                .child(FBaseNode0.MyOqPerson)
                .child(App.getUid(getActivity()))
                .orderByChild("ts");

        /*
        rts
         */

        firebaseRecyclerAdapterMyOqPerson = new FirebaseRecyclerAdapter<MyOqPerson,
                Ava2RelationDtlVHolder>
                (MyOqPerson.class,
                        R.layout.lo_twoavatars_relation_names_explain_date_detail,
                        Ava2RelationDtlVHolder.class,
                        query

                ) {

            public void populateViewHolder(
                    final Ava2RelationDtlVHolder twoAvatarsWithRelationDtlVHolder,
                    final MyOqPerson oqPerson,
                    final int position) {

                JM.G(twoAvatarsWithRelationDtlVHolder.ivMore);


                if (oqPerson != null) {


                    final String uida = App.getUid(getActivity());
                    final String unamea = App.getUname(getActivity());
                    final String uidb = oqPerson.getProfile().getUid();
                    final String unameb = oqPerson.getProfile().getFull_name();


                    //get content data
                    App.fbaseDbRef
                            .child(FBaseNode0.MyOqWraps)
                            .child(App.getUid(getActivity()))
                            .addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dUidBWidOqWrap) {

                                            Log.d(TAG, "onDataChange()");

                                            Iterable<DataSnapshot> i = dUidBWidOqWrap.getChildren();
                                            Log.d(TAG, "dUidBWidOqWrap.getChildrenCount() : " + J.st
                                                    (dUidBWidOqWrap.getChildrenCount()));
                                            List<OqDo> oqDoList = new ArrayList<>();

                                            for (DataSnapshot dWidOqWrap : i) {

                                                Iterable<DataSnapshot> i2 = dWidOqWrap
                                                        .getChildren();
                                                Log.d(TAG, "dWidOqWrap.getChildrenCount() : " + J.st
                                                        (dWidOqWrap.getChildrenCount()));

                                                for (DataSnapshot dOqWrap : i2) {
                                                    OqWrap oqWrap = dOqWrap.getValue(OqWrap.class);
                                                    if (oqWrap != null && oqWrap.getListoqdo() != null) {
                                                        Log.d(TAG, "oqWrap.getListoqdo().size() : " +
                                                                J.st(oqWrap.getListoqdo().size()));
                                                        oqDoList.addAll(oqWrap.getListoqdo());
                                                    }

                                                }
                                            }

                                            //(0)

                                            long sumAmtGetListAbGetFutureOqList = OqDoUtil
                                                    .getSumAmt(OqDoUtil
                                                            .getListAbGetFuture(oqDoList));

                                            long sumAmtGetListBaPayFutureOqList = OqDoUtil
                                                    .getSumAmt(OqDoUtil
                                                            .getListBaPayFuture(oqDoList));

                                            long amtAbGetFutureAgreed = J.getSmallerLong(
                                                    sumAmtGetListAbGetFutureOqList,
                                                    sumAmtGetListBaPayFutureOqList
                                            );
                                            Log.d(TAG, "amtAbGetFutureAgreed : " +
                                                    J.st
                                                            (amtAbGetFutureAgreed));

                                            //(1)
                                            long sumAmtGetListBaGetFutureOqList = OqDoUtil.getSumAmt(OqDoUtil.getListBaGetFuture
                                                    (oqDoList));

                                            long sumAmtGetListAbPayFutureOqList = OqDoUtil.getSumAmt(OqDoUtil.getListAbPayFuture
                                                    (oqDoList));


                                            long amtBaGetFutureAgreed = J.getSmallerLong(
                                                    sumAmtGetListBaGetFutureOqList,
                                                    sumAmtGetListAbPayFutureOqList
                                            );

                                            Log.d(TAG, "amtBaGetFutureAgreed : " + J.st(amtAbGetFutureAgreed));

                                            //(2)

                                            long sumAmtGetListAbPayPastOqList = OqDoUtil.getSumAmt(OqDoUtil.getListAbPayPast
                                                    (oqDoList));

                                            long sumAmtGetListBaGetPastOqList = OqDoUtil.getSumAmt
                                                    (OqDoUtil.getListBaGetPast
                                                            (oqDoList));

                                            long amtAbPayPastAgreed = J.getSmallerLong(

                                                    sumAmtGetListAbPayPastOqList,
                                                    sumAmtGetListBaGetPastOqList
                                            );

                                            Log.d(TAG, "amtAbPayPastAgreed : " + J.st(amtAbGetFutureAgreed));


                                            //(3)

                                            long sumAmtGetListBaPayPastOqList = OqDoUtil.getSumAmt(
                                                    OqDoUtil.getListBaPayPast
                                                            (oqDoList));

                                            long sumAmtGetListAbGetPastOqList = OqDoUtil.getSumAmt
                                                    (OqDoUtil.getListAbGetPast
                                                            (oqDoList));

                                            long amtBaPayPastAgreed = J.getSmallerLong(

                                                    sumAmtGetListBaPayPastOqList,
                                                    sumAmtGetListAbGetPastOqList
                                            );

                                            Log.d(TAG, "amtBaPayPastAgreed : " + J.st(amtAbGetFutureAgreed));


                                            if (J.isLarger(amtAbGetFutureAgreed - amtBaPayPastAgreed,
                                                    amtBaGetFutureAgreed - amtAbPayPastAgreed) == 1
                                                    ) {


                                                JM.glideProfileThumb(
                                                        uida,
                                                        unamea,
                                                        twoAvatarsWithRelationDtlVHolder.ivAvaLeft,
                                                        twoAvatarsWithRelationDtlVHolder.tvAvaLeft,
                                                        mFragment
                                                );

                                                JM.glideProfileThumb(
                                                        uidb,
                                                        unameb,
                                                        twoAvatarsWithRelationDtlVHolder.ivAvaRight,
                                                        twoAvatarsWithRelationDtlVHolder.tvAvaRight,
                                                        mFragment
                                                );

                                                twoAvatarsWithRelationDtlVHolder.tvTwoName.setText(
                                                        unamea + " • " + unameb
                                                );


                                                long amtToTv = (amtAbGetFutureAgreed -
                                                        amtBaPayPastAgreed) - (
                                                        amtBaGetFutureAgreed - amtAbPayPastAgreed);
                                                Log.d(TAG, "amtToTv : " + J.st(amtToTv));


                                                String strToTv =
                                                        res.getString(
                                                                R.string.req_amtvar_whatphrasevar,
                                                                J.strAmt(amtToTv),
                                                                res.getString(R.string.whovar_all_notpaid,
                                                                        unameb));

                                                twoAvatarsWithRelationDtlVHolder.tvContent.setText(
                                                        strToTv);
                                                //a : user , b : friend

                                            } else if (J.isLarger(amtAbGetFutureAgreed - amtBaPayPastAgreed,
                                                    amtBaGetFutureAgreed - amtAbPayPastAgreed) == 2) {

                                                //b : user , a : friend
                                                JM.glideProfileThumb(
                                                        uidb,
                                                        unameb,
                                                        twoAvatarsWithRelationDtlVHolder.ivAvaLeft,
                                                        twoAvatarsWithRelationDtlVHolder.tvAvaLeft,
                                                        mFragment
                                                );

                                                JM.glideProfileThumb(
                                                        uida,
                                                        unamea,
                                                        twoAvatarsWithRelationDtlVHolder.ivAvaRight,
                                                        twoAvatarsWithRelationDtlVHolder.tvAvaRight,
                                                        mFragment
                                                );

                                                twoAvatarsWithRelationDtlVHolder.tvTwoName.setText(
                                                        unameb + " • " + unamea
                                                );
                                                long amtToTv = (
                                                        amtBaGetFutureAgreed -
                                                                amtAbPayPastAgreed) - (amtAbGetFutureAgreed -
                                                        amtBaPayPastAgreed);
                                                String strToTv =
                                                        res.getString(
                                                                R.string.req_amtvar_whatphrasevar,
                                                                J.strAmt(amtToTv),
                                                                res.getString(R.string.whovar_all_notpaid,
                                                                        unamea));
                                                twoAvatarsWithRelationDtlVHolder.tvContent.setText(
                                                        strToTv);
                                            } else {  //the same

                                                JM.glideProfileThumb(
                                                        uida,
                                                        unamea,
                                                        twoAvatarsWithRelationDtlVHolder.ivAvaLeft,
                                                        twoAvatarsWithRelationDtlVHolder.tvAvaLeft,
                                                        mFragment
                                                );

                                                JM.glideProfileThumb(
                                                        uidb,
                                                        unameb,
                                                        twoAvatarsWithRelationDtlVHolder.ivAvaRight,
                                                        twoAvatarsWithRelationDtlVHolder.tvAvaRight,
                                                        mFragment
                                                );

                                                twoAvatarsWithRelationDtlVHolder.tvTwoName.setText(
                                                        unamea + " • " + unameb
                                                );

                                                twoAvatarsWithRelationDtlVHolder.tvContent.setText(
                                                        JM.strById(R.string.no_amt_to_settle));
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.d(TAG, "onCancelled");
                                        }
                                    }
                            );


                    twoAvatarsWithRelationDtlVHolder.tvDate.setText(JT.str(oqPerson.getTs()));


                    twoAvatarsWithRelationDtlVHolder.mView.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    App.fbaseDbRef
                                            .child(FBaseNode0.ProfileToPublic)
                                            .child(uidb)
                                            .addListenerForSingleValueEvent(
                                                    new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.exists()) {
                                                                Profile profile = dataSnapshot.getValue
                                                                        (Profile.class);
                                                                Intent intent = new Intent(
                                                                        mFragment.getActivity(),
                                                                        ProfileActivity.class);
                                                                intent.putExtra("Profile", profile);
                                                                intent.putExtra("isMe", false);
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


            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapterMyOqPerson);
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
