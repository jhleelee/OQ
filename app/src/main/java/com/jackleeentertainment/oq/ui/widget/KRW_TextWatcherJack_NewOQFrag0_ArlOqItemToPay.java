//package com.jackleeentertainment.oq.ui.widget;
//
//import android.text.Editable;
//import android.util.Log;
//import android.widget.EditText;
//
//import com.jackleeentertainment.oq.object.OqItem;
//import com.jackleeentertainment.oq.object.util.OqItemUtil;
//import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
//import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0Neo;
//
///**
// * Created by Jacklee on 2016. 10. 30..
// */
//
//public class KRW_TextWatcherJack_NewOQFrag0_ArlOqItemToPay extends KRW_TextWatcherJack {
//    NewOQActivity mNewOQActivity;
//    NewOQFrag0Neo mNewOQFrag0;
//    OqItem mOqItem;
//
//    public KRW_TextWatcherJack_NewOQFrag0_ArlOqItemToPay() {
//        super();
//    }
//
//    public KRW_TextWatcherJack_NewOQFrag0_ArlOqItemToPay(EditText editText) {
//        super(editText);
//    }
//
//    public KRW_TextWatcherJack_NewOQFrag0_ArlOqItemToPay(EditText editText, OqItem oqItem,
//                                                         NewOQActivity newOQActivity,
//                                                         NewOQFrag0Neo newOQFrag0Neo) {
//        super(editText);
//        mOqItem = oqItem;
//        mNewOQActivity = newOQActivity;
//        mNewOQFrag0= newOQFrag0Neo;
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        super.beforeTextChanged(s, start, count, after);
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        super.onTextChanged(s, start, before, count);
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//        super.afterTextChanged(s);
//
//        if (mNewOQActivity!=null){
//
//            long l = 0;
//            try {
//                l = Long.parseLong(
//                mEditText.getText().toString());
//                mOqItem.setAmmount(l);
//                long sum = OqItemUtil.getSumOqItemAmmounts(
//                        (mNewOQActivity).arlOqDo_Future
//                );
//
//                mNewOQFrag0.tvSumOqItems.setText(String.valueOf(sum));
//                mNewOQFrag0.tvNextEnableOrNot();
//            } catch (Exception e){
//
//            }
//
//
//        } else {
//            Log.d(TAG, "mNewOQActivity!=null");
//        }
//    }
//}
