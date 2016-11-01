package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQSumT;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.DiaFragAdapter;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;
import com.jackleeentertainment.oq.ui.widget.NumericKeyBoardTransformationMethod;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 30..
 */

public class MySpentItemDiaFrag extends android.support.v4.app.DialogFragment {

    ArrayList<ItemDiaFragList> arl = new ArrayList<>();

    static Context mContext;


    LinearLayout lo, loBtSMS;
    EditText etTitle, etAmmount;
    ImageView ivBtSMS;
    TextView tvBtSMS;
    TextView tvOk, tvCancel;
    String oqSumT = "";


    public static MySpentItemDiaFrag newInstance(Bundle bundle, Context context) {
        MySpentItemDiaFrag frag = new MySpentItemDiaFrag();

        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        theme = android.R.style.Theme_Material_Dialog_NoActionBar;
        frag.setStyle(style, theme);
        frag.setArguments(bundle);
        mContext = context;
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.diafrag_myspentitem, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lo = (LinearLayout) view.findViewById(R.id
                .lo);

        etAmmount = (EditText) view.findViewById(R.id
                .etAmmount);
        etAmmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        etAmmount.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        etTitle = (EditText) view.findViewById(R.id
                .etTitle);

        ivBtSMS = (ImageView) view.findViewById(R.id
                .ivBtSMS);

        tvBtSMS = (TextView
                ) view.findViewById(R.id
                .tvBtSMS);

        tvOk = (TextView) view.findViewById(R.id
                .tvOk__ro_diafrag_okcancel);
        tvCancel = (TextView) view.findViewById(R.id
                .tvCancel__ro_diafrag_okcancel);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Dialog");
        getDialog().setTitle(title);
//        // Show soft keyboard automatically and request focus to field
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        //ok
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send Data

                if (etTitle.getText().length() == 0 || etAmmount.getText().length() == 0) {
                    J.TOAST(R.string.input_title_and_ammount);
                }

                LBR.send(LBR.IntentFilterT.NewOQActivity_Frag0, oqSumT + "," + J.st
                        (etAmmount.getText().toString()));
                dismiss();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}