package com.jackleeentertainment.oq.ui.layout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class FirstVisitFrag1_PhoneNum extends Fragment implements View.OnClickListener {

    String TAG = this.getClass().getSimpleName();

    View view;

    LinearLayout loInputPhoneNum;
    EditText etNCode;
    TextView tvNName;
    EditText etPhoneNum;
    Button btDoSms;
    LinearLayout loInputVerifyCode;
    EditText etVerifyNum0;
    Button btTryCode;
    TextView tvTimer;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_firstvisit1_phonenum, container, false);
        initUI();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNName:

                break;

            case R.id.btDoSms:

                break;

            case R.id.btTryCode:

                break;
        }
    }

    void initUI() {

        loInputPhoneNum = (LinearLayout) view.findViewById(R.id.loInputPhoneNum);
        etNCode = (EditText) view.findViewById(R.id.etNCode);
        tvNName = (TextView) view.findViewById(R.id.tvNName);
        etPhoneNum = (EditText) view.findViewById(R.id.etPhoneNum);
        btDoSms = (Button) view.findViewById(R.id.btDoSms);
        loInputVerifyCode = (LinearLayout) view.findViewById(R.id.loInputVerifyCode);
        etVerifyNum0 = (EditText) view.findViewById(R.id.etVerifyNum0);
        btTryCode = (Button) view.findViewById(R.id.btTryCode);
        tvTimer = (TextView) view.findViewById(R.id.tvTimer);


    }

}
