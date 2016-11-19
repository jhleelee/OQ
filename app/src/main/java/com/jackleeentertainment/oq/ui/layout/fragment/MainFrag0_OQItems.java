package com.jackleeentertainment.oq.ui.layout.fragment;

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
import com.jackleeentertainment.oq.ui.layout.viewholder.Ava2RelationDtlVHolder;

import java.util.ArrayList;
import java.util.Calendar;
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
    Resources res = App.getContext().getResources();
    boolean isQueryAComplete = false;
    boolean isQueryBComplete = false;
    boolean isQueryAEmpty = false;
    boolean isQueryBEmpty = false;


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


    void uiIsEmpty() {
        if (isQueryAEmpty==true){
            if (isQueryBEmpty==true){
                ro_empty_list.setVisibility(View.VISIBLE);
                return;
            }
        }


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

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.VISIBLE);
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
                <MyOqPerson, Ava2RelationDtlVHolder>
                (MyOqPerson.class,
                        R.layout.item_mainfrag0,
                        Ava2RelationDtlVHolder.class,
                        query

                ) {

            public void populateViewHolder(
                    final Ava2RelationDtlVHolder twoAvatarsWithRelationDtlVHolder,
                    final MyOqPerson oqPerson,
                    final int position) {


                JM.G(twoAvatarsWithRelationDtlVHolder.ivMore);


                if (oqPerson != null) {


                    //get content data
                    //"my_oqperson" - [uid] - {MyOqPerson(ts,{Profile})}

                    final List<OqDo> oqDoList = new ArrayList<>();


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
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        Iterable<DataSnapshot> i = dataSnapshot.getChildren();

                                        for (DataSnapshot d : i) {

                                            OqDo oqDo = d.getValue(OqDo.class);
                                            oqDoList.add(oqDo);
                                        }

                                        isQueryAComplete = true;
                                        if (isQueryBComplete) {
                                            uiOqDos(twoAvatarsWithRelationDtlVHolder, oqPerson,
                                                    oqDoList);
                                        }
                                    } else {
                                        isQueryAEmpty = true;
                                        uiIsEmpty();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                    queryB
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        Iterable<DataSnapshot> i = dataSnapshot.getChildren();

                                        for (DataSnapshot d : i) {

                                            OqDo oqDo = d.getValue(OqDo.class);

                                            oqDoList.add(oqDo);

                                        }
                                        isQueryBComplete = true;
                                        if (isQueryAComplete) {
                                            uiOqDos(twoAvatarsWithRelationDtlVHolder, oqPerson, oqDoList);
                                        }
                                    } else {
                                        isQueryBEmpty = true;
                                        uiIsEmpty();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    twoAvatarsWithRelationDtlVHolder.mView.setOnClickListener(
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

            void uiOqDos(Ava2RelationDtlVHolder twoAvatarsWithRelationDtlVHolder, MyOqPerson
                    oqPerson,
                         List<OqDo> oqDoList) {
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


                twoAvatarsWithRelationDtlVHolder
                        .tvContent
                        .setTextColor(JM.colorById(R.color.text_black_54));


                if (J.isLarger(amtAbGetFutureAgreed - amtBaPayPastAgreed,
                        amtBaGetFutureAgreed - amtAbPayPastAgreed) == 1
                        ) {


                    final String uida = App.getUid(getActivity());
                    final String unamea = App.getUname(getActivity());
                    final String uidb = oqPerson.getProfile().getUid();
                    final String unameb = oqPerson.getProfile().getFull_name();


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


                    long amtToTv = (amtAbGetFutureAgreed -
                            amtBaPayPastAgreed) - (
                            amtBaGetFutureAgreed - amtAbPayPastAgreed);
                    twoAvatarsWithRelationDtlVHolder.tvOppoName.setText(
                            J.shortenName(unameb)
                    );


                    Log.d(TAG, "amtToTv : " + J.st(amtToTv));


//                                                String strToTv =
//                                                        res.getString(
//                                                                R.string.req_amtvar_whatphrasevar,
//                                                                J.strAmt(amtToTv),
//                                                                res.getString(R.string.whovar_all_notpaid,
//                                                                        unameb));

                    twoAvatarsWithRelationDtlVHolder.tvContent.setText(
                            JT.str(oqPerson.getTs()) + "가장 최근 날짜 및 이벤트");

                    twoAvatarsWithRelationDtlVHolder.tvResultAmmount
                            .setText("+" + J.strAmt(amtToTv) + "원");
                    twoAvatarsWithRelationDtlVHolder.tvResultAmmount
                            .setTextColor(JM.colorById(R.color
                                    .getPrimaryDark));

                    //a : user , b : friend

                } else if (J.isLarger(amtAbGetFutureAgreed - amtBaPayPastAgreed,
                        amtBaGetFutureAgreed - amtAbPayPastAgreed) == 2) {

                    //b : user , a : friend


                    final String uidb = App.getUid(getActivity());
                    final String unameb = App.getUname(getActivity());
                    final String uida = oqPerson.getProfile().getUid();
                    final String unamea = oqPerson.getProfile()
                            .getFull_name();


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
                    long amtToTv = (
                            amtBaGetFutureAgreed -
                                    amtAbPayPastAgreed) - (amtAbGetFutureAgreed -
                            amtBaPayPastAgreed);

                    twoAvatarsWithRelationDtlVHolder.tvOppoName.setText(
                            J.shortenName(unamea)
                    );

                    twoAvatarsWithRelationDtlVHolder.tvContent.setText(
                            JT.str(oqPerson.getTs()) + "가장 최근 날짜 및 이벤트");


                    twoAvatarsWithRelationDtlVHolder.tvResultAmmount.setText(JT.str(oqPerson.getTs()));
                    twoAvatarsWithRelationDtlVHolder.tvResultAmmount
                            .setText("-" + J.strAmt(amtToTv) + "원");
                    twoAvatarsWithRelationDtlVHolder.tvResultAmmount
                            .setTextColor(JM.colorById(R.color
                                    .payPrimaryDark));
                } else {  //the same


                    final String uida = App.getUid(getActivity());
                    final String unamea = App.getUname(getActivity());
                    final String uidb = oqPerson.getProfile().getUid();
                    final String unameb = oqPerson.getProfile().getFull_name();


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

                    twoAvatarsWithRelationDtlVHolder.tvOppoName.setText(
                            J.shortenName(unameb) + "  " + JM.strById
                                    (R.string
                                            .no_amt_to_settle)
                    );

                    twoAvatarsWithRelationDtlVHolder.tvContent.setText(
                            JT.str(oqPerson.getTs()) + "가장 최근 날짜 및 " +
                                    "이벤트");

                    twoAvatarsWithRelationDtlVHolder.tvResultAmmount
                            .setText(JM.strById
                                    (R.string
                                            .no_amt_to_settle));
                    twoAvatarsWithRelationDtlVHolder.tvResultAmmount
                            .setTextColor(JM.colorById(R.color
                                    .dark_grey));
                }
            }

            ;


        };
        recyclerView.setAdapter(firebaseRecyclerAdapterMyOqPerson);
    }

}