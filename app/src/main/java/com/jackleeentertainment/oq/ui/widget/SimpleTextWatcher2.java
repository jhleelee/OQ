package com.jackleeentertainment.oq.ui.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.util.OqItemUtil;
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
    OqItem mOqItem;

    public SimpleTextWatcher2(EditText editText, OqItem oqItem,
                             NewOQActivity newOQActivity,
                             NewOQFrag0Neo newOQFrag0Neo) {
        mEditText = editText;
        mOqItem = oqItem;
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
        if (mNewOQActivity!=null){
            OqItem oqItem = OqItemUtil.getOqItemWithUidclaimee(
                    (mNewOQActivity).arlOQItem_Paid,
                    mOqItem.getUidgettor());
            long l = 0;
            try {
                l = Long.parseLong(
                        mEditText.getText().toString());
                oqItem.setAmmount(l);
                long sum = OqItemUtil.getSumOqItemAmmounts(
                        (mNewOQActivity).arlOQItem_Paid
                );
                mNewOQFrag0.tvSumOqItems.setText(JM.strById(R.string.symbol_krw)+String.valueOf(sum));

            } catch (Exception e){
                Log.d(TAG, e.toString());
            }


        }
    }
}
