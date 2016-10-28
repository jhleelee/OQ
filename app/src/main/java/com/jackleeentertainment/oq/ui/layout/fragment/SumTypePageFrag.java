package com.jackleeentertainment.oq.ui.layout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

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
        Log.d(TAG, "initUiData() OQSumT : "+ OQSumT);
        if (OQSumT == null) {
            return;
        }

        String myName = "";
        String hisName = "";
        int moneyStandard = 0;
        int moneyTarget = 0;

        if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
                .I_PAID_FOR_YOU)) {
            String str = String.format("%s님이 %s님을 위하여 %d원을 지출하였습니다.\n그러므로 %s님은 %s님으로부터 %s원을 받고자 합니다." +
                    "", myName, hisName, moneyStandard, myName, hisName, moneyTarget);
            tv1.setText(str);

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
                .I_PAID_FOR_YOU_AND_ME)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
                .ANYWAY)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToGETFromYouGuys.N_ANYWAY)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToPAY.YOU_PAID_FOR_ME)) {

        } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
                .SoIWantToPAY.ANYWAY)) {

        }
    }

}



