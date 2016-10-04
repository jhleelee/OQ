package com.jackleeentertainment.oq.ui.layout.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.ui.layout.activity.FirstVisitActivity;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class FirstVisitFrag0_PhotoEmailName extends Fragment
        implements View.OnClickListener {

    String TAG = this.getClass().getSimpleName();

    FirstVisitActivity firstVisitActivity;
    TextView tvWelcome;
    ImageView ivProfile;
    TextView tvPhotoChange;
    TextView tvEmail;
    TextView tvName;
    TextView tvNameChange;

    View view;
    Bundle bundleForDiaFrag;
    Bitmap bmpProfile;
    String strName, strEmail;


    @NonNull
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {

            Log.d(TAG, "onReceive");
            Gson gson = new Gson();

            if (intent.getStringExtra("origin").equals("com.jackleeentertainment.oq." + "Crop.REQUEST_CROP")) {
                String jsonData = intent.getStringExtra("data");
                Uri uri = gson.fromJson(jsonData, Uri.class);
                ivProfile.setImageURI(uri);
            } else if (intent.getStringExtra("origin").equals("com.jackleeentertainment.oq." + "OneLineInputDiaFrag")) {
                String jsonData = intent.getStringExtra("data");
                String strName = gson.fromJson(jsonData, String.class);
                tvName.setText(strName);
            }

        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_firstvisit0_photonameemail, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firstVisitActivity = ((FirstVisitActivity) getActivity());
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        initUiData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivProfile:
            case R.id.tvPhotoChange:
                bundleForDiaFrag = new Bundle();
                bundleForDiaFrag.putString("diaFragT", DiaFragT.GalleryOrCamera);
                firstVisitActivity.showDialogFragment(bundleForDiaFrag);
                break;

            case R.id.tvNameChange:
                bundleForDiaFrag = new Bundle();
                bundleForDiaFrag.putString("diaFragT", DiaFragT.OneLineInput);
                firstVisitActivity.showDialogFragment(bundleForDiaFrag);
                break;

        }
    }

    void initUI() {
        tvWelcome = (TextView) view.findViewById(R.id.tvWelcome);
        ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
        tvPhotoChange = (TextView) view.findViewById(R.id.tvPhotoChange);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvNameChange = (TextView) view.findViewById(R.id.tvNameChange);
    }

    void initUiData() {
        ivProfile.setImageBitmap(bmpProfile);
        tvName.setText(strName);
        tvEmail.setText(strEmail);
    }

}
