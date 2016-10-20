package com.jackleeentertainment.oq.ui.layout.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.Query;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.OqItemSumForPerson;
import com.jackleeentertainment.oq.ui.adapter.MyOqItemSumPerPersonRVAdapter;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameDetailViewHolder;

import hugo.weaving.DebugLog;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag0_OQItems extends ListFrag {
    String TAG = this.getClass().getSimpleName();
    View view;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String searchKey = intent.getStringExtra("data");


            if (searchKey.equals("nav_choose_from_list_get")||
                    searchKey.equals("nav_choose_from_list_pay")||
                    searchKey.equals("nav_list_i_am_master")||
                    searchKey.equals("nav_list_i_am_member")
                    ){

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

    @DebugLog
    @Override
    void initAdapter() {
        super.initAdapter();

        //nullpointerexception
        Query query = App.fbaseDbRef
                .child(FBaseNode0.MyOqItemSums)
                .child(App.getUid(getActivity()))
                .orderByChild("ts");
        firebaseRecyclerAdapter = new MyOqItemSumPerPersonRVAdapter(
                OqItemSumForPerson.class,
                R.layout.lo_avatar_titlesubtitle,
                AvatarNameDetailViewHolder.class,
                query
        );
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("com.jackleeentertainment.oq." + LBR.IntentFilterT.MainActivityDrawerMenu));
    }
}
