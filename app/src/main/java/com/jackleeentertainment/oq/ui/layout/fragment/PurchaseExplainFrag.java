package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.activity.SelectedPhotoListActivity;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 31..
 */

public class PurchaseExplainFrag extends Fragment {


    String TAG = this.getClass().getSimpleName();

    View view;


    public PurchaseExplainFrag() {
        super();
    }

    @NonNull
    public static PurchaseExplainFrag newInstance(Bundle bundleFromFrag0) {
        PurchaseExplainFrag newOQFrag1 =   new PurchaseExplainFrag();
        newOQFrag1.setArguments(bundleFromFrag0);
        return newOQFrag1;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
         view = inflater.inflate(R.layout.ro_empty_list, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initClickListener();
    }


    void initUI() {

    }

    void initUiCosmetic(){
        ((NewOQActivity)getActivity()).ivClose.setImageDrawable(JM.drawableById(R.drawable.ic_arrow_back_white_48dp));
    }



    void initClickListener() {

    }








}
