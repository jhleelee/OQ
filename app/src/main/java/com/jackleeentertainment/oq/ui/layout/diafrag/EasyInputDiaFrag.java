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
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;
import com.jackleeentertainment.oq.ui.widget.NumericKeyBoardTransformationMethod;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 30..
 */

public class EasyInputDiaFrag extends   android.support.v4.app.DialogFragment {

    ArrayList<ItemDiaFragList> arl = new ArrayList<>();
    static Profile profile;
    static Context mContext;

    static  String OQTWantT_Future = "";
    static  int selectedProfileNum = 0;

    LinearLayout lo;
    RadioGroup radioGroup;
    RadioButton r0, r1, r2;
    EditText etAmmount;

    TextView tvOk, tvCancel;
    String oqSumT = "";


    public static EasyInputDiaFrag  newInstance(Bundle bundle, Context context) {
        EasyInputDiaFrag frag = new EasyInputDiaFrag();

        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        theme = android.R.style.Theme_Material_Dialog_NoActionBar;
        frag.setStyle(style, theme);

        frag.setArguments(bundle);
        selectedProfileNum =   bundle.getInt("selectedProfileNum");
        OQTWantT_Future =   bundle.getString("OQTWantT_Future");
        mContext = context;
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.diafrag_easyinput, container);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lo= (LinearLayout) view.findViewById(R.id
                .lo);
        radioGroup= (RadioGroup) view.findViewById(R.id
                .radioGroup);
        r0= (RadioButton) view.findViewById(R.id
                .r0);
        r1= (RadioButton) view.findViewById(R.id
                .r1);
        r2= (RadioButton) view.findViewById(R.id
                .r2);
        etAmmount= (EditText) view.findViewById(R.id
                .etAmmount);
        etAmmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        etAmmount.setTransformationMethod(new NumericKeyBoardTransformationMethod());



        tvOk = (TextView) view.findViewById(R.id
                .tvOk__ro_diafrag_okcancel);
        tvCancel = (TextView) view.findViewById(R.id
                .tvCancel__ro_diafrag_okcancel);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Dialog");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);



        if (OQTWantT_Future !=null&& OQTWantT_Future.equals(OQT.WantT.GET)){

            if (selectedProfileNum==1){

                //(1)visibility
                JM.V(r0);
                JM.V(r1);
                JM.G(r2);

                //(2)setOnclickListener
                r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            oqSumT = OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU_AND_ME;
                        }

                    }
                });

                r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            oqSumT = OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU;
                        }

                    }
                });

                //(3)setText
                r0.setText(JM.strById(R.string.dummydate));
                r1.setText(JM.strById(R.string.dummydate));

                //(4)onTextWatcher - Edittext




            } else if (selectedProfileNum>1){

                JM.V(r0);
                JM.V(r1);
                JM.V(r2);

                r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            oqSumT = OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME;
                        }

                    }
                });

                r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            oqSumT = OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME;
                        }

                    }
                });

                r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            oqSumT = OQSumT.SoIWantToGETFromYouGuys.N_ANYWAY;
                        }

                    }
                });

                r0.setText(JM.strById(R.string.dummydate));
                r1.setText(JM.strById(R.string.dummydate));
                r2.setText(JM.strById(R.string.dummydate));
            }

        } else if (OQTWantT_Future !=null&& OQTWantT_Future.equals(OQT.WantT.PAY)){
            JM.V(r0);
            JM.V(r1);
            JM.G(r2);


            r0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked){
                        oqSumT = OQSumT.SoIWantToPAY.YOU_PAID_FOR_ME;
                    }

                }
            });

            r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked){
                        oqSumT = OQSumT.SoIWantToPAY.ANYWAY;
                    }

                }
            });

            r0.setText(JM.strById(R.string.dummydate));
            r1.setText(JM.strById(R.string.dummydate));

        }








        //ok
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send Data

                LBR.send(LBR.IntentFilterT.NewOQActivity_Frag0_EasyInput, oqSumT + "," + J.st
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
