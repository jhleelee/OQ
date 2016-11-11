package com.jackleeentertainment.oq.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jackleeentertainment.oq.object.OqDo;
 import com.jackleeentertainment.oq.object.util.OqDoUtil;
 import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0Neo;

/**
 * Created by Jacklee on 2016. 10. 31..
 */

public class SimpleTextWatcher implements TextWatcher {
    String TAG  = "SimpleTextWatcher";
    private EditText mEditText;
    NewOQActivity mNewOQActivity;
    NewOQFrag0Neo mNewOQFrag0;
    OqDo mOqItem;

    public SimpleTextWatcher(EditText editText, OqDo oqDo,
                             NewOQActivity newOQActivity,
                             NewOQFrag0Neo newOQFrag0Neo) {
        mEditText = editText;
        mOqItem = oqDo;
        mNewOQActivity = newOQActivity;
        mNewOQFrag0= newOQFrag0Neo;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        Log.d(TAG, "afterTextChanged");

        mEditText.removeTextChangedListener(this);

//        if (s.equals("")){
//            mEditText.setText(JM.strById(R.string.symbol_krw) +" "+ "0");
//        } else
//
//        if (s.equals(JM.strById(R.string.symbol_krw) +" "+"0")){
//
//        } else {
//
//        }



        if (mNewOQActivity!=null){

            long l = 0;
            try {
                l = Long.parseLong(
                        mEditText.getText().toString());
                mOqItem.setAmmount(l);
                long sum = OqDoUtil.getSumOqDoAmmounts(
                        (mNewOQActivity).arlOqDo_Future
                );

                mNewOQFrag0.tvSumOqItems.setText(String.valueOf(sum));
                mNewOQFrag0.tvNextEnableOrNot();
            } catch (Exception e){
                Log.d(TAG, e.toString());

            }


        } else {
            Log.d(TAG, "mNewOQActivity!=null");
        }

        mEditText.addTextChangedListener(this);

    }
}
