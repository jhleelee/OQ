package com.jackleeentertainment.oq.ui.layout.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Group;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQSumT;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 27..
 */

public class SumTypePageFrag extends Fragment {

    static String TAG = "SumTypePageFrag";
    View view;
    String OQSumT = "";
    TextView tv1, tv2;

    @NonNull
    public static SumTypePageFrag newInstance(String OQSumT) {
        Bundle bundle = new Bundle();
        Log.d(TAG, "newInstance() OQSumT" + OQSumT);
        bundle.putString("OQSumT", OQSumT);
        SumTypePageFrag sumTypeItemFrag = new SumTypePageFrag();
        sumTypeItemFrag.setArguments(bundle);
        return sumTypeItemFrag;
    }


    public SumTypePageFrag() {
        super();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_sumtypepages, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUiData();

    }

    @Override
    public void onResume() {
        super.onResume();
        OQSumT = getArguments().getString("OQSumT");
        initUiData();
    }

    void initUI() {
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
    }


    void initUiData() {

        if (OQSumT == null) {
            return;
        }

        if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
                .I_PAID_FOR_YOU)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
                .I_PAID_FOR_YOU_AND_ME)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
                .N_ANYWAY)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToGETFromYouGuys.N_ANYWAY)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToPAY.YOU_PAID_FOR_ME)){

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToPAY.ANYWAY)){

        }
    }

}



