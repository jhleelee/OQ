package com.jackleeentertainment.oq.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqDo;
 import com.jackleeentertainment.oq.object.util.OqDoUtil;
 import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0Neo;

/**
 * Created by Jacklee on 2016. 10. 31..
 */

public class SimpleTextWatcher2  implements TextWatcher {
    String TAG  = "SimpleTextWatcher";
    private EditText mEditText;
    NewOQActivity mNewOQActivity;
    NewOQFrag0Neo mNewOQFrag0;
    NewOQActivity.TempSpent mTempSpent;

    public SimpleTextWatcher2(EditText editText, NewOQActivity.TempSpent t,
                             NewOQActivity newOQActivity,
                             NewOQFrag0Neo newOQFrag0Neo) {
        mEditText = editText;
        mTempSpent = t;
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

        if (mNewOQActivity != null) {

            long l = 0;
            try {
                l = Long.parseLong(
                        mEditText.getText().toString());
                mTempSpent.ammount = l;

                long sum = 0;
                for (NewOQActivity.TempSpent t :
                        mNewOQActivity.tempArlSpent) {
                    sum += t.ammount;
                }

                mNewOQFrag0.tvSumMySpent.setText(String.valueOf(sum));
                mNewOQFrag0.tvNextEnableOrNot();

            } catch (Exception e) {
                Log.d(TAG, e.toString());

            }


        } else {
            Log.d(TAG, "mNewOQActivity!=null");
        }

        mEditText.addTextChangedListener(this);

    }
}
