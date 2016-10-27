package com.jackleeentertainment.oq.ui.layout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Receipt;
import com.jackleeentertainment.oq.ui.adapter.ReceiptPhotoRVAdapter;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 22..
 */

public class TesserPhotoListFrag extends ListFrag {


    //UI
    ReceiptPhotoRVAdapter receiptPhotoRVAdapter;
    TextView tvDone__ro_tv_done;
    ImageView ivChev__ro_tv_done;

    //Data
    public ArrayList<Receipt> arrayListReceipt = new ArrayList<>();


    @NonNull
    public static TesserPhotoListFrag newInstance(ArrayList<Receipt> _arrayListReceipt) {
        TesserPhotoListFrag tesserPhotoListFrag = new TesserPhotoListFrag();
        tesserPhotoListFrag.arrayListReceipt = _arrayListReceipt;
        return tesserPhotoListFrag;
    }

    /**
     * Frag Life
     */

    public TesserPhotoListFrag() {
        super();
    }


    @Override
    public  void initUI() {
        super.initUI();
        recyclerView.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);
        ro_tv_done.setVisibility(View.GONE);
    }

    @Override
    void initAdapterOnResume() {
        super.initAdapterOnResume();
        Log.d(TAG, "initAdapterOnResume() : " + J.st(arrayListReceipt.size()));
        receiptPhotoRVAdapter = new ReceiptPhotoRVAdapter(
                arrayListReceipt,
                getActivity()
        );
        recyclerView.setAdapter(receiptPhotoRVAdapter);
        uiBottomTvDone(arrayListReceipt.size());
    }

    void uiBottomTvDone(int i) {
        if (i == 0) {
            ro_tv_done.setVisibility(View.GONE);
        } else {
            String sumAmmount = "";
            ro_tv_done.setVisibility(View.VISIBLE);
            tvDone__ro_tv_done = (TextView) ro_tv_done.findViewById(R.id.tvDone__ro_tv_done);
            ivChev__ro_tv_done = (ImageView) ro_tv_done.findViewById(R.id.ivChev__ro_tv_done);
            tvDone__ro_tv_done.setText(JM.strById(R.string.money_sum) + sumAmmount + "원"
                    + " ");
            ivChev__ro_tv_done.setImageDrawable(
                    JM.tintedDrawable(
                            R.drawable.ic_chevron_right_white_24dp,
                            R.color.colorPrimary,
                            getActivity()
                    )
            );
        }
    }


}
