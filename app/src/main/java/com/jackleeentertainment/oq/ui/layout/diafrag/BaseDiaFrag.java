package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class BaseDiaFrag extends android.support.v4.app.DialogFragment {

    String TAG = getClass().getSimpleName();
    static Context mContext;
    static String mAId;

    //UI
    LinearLayout lo_numselectedprofiles_add__lo_diafrag;
    ListView lv__lo_diafragwithiconlist;
    RelativeLayout ro_diafrag_okcancel__lo_diafragwithiconlist;
    TextView tvOk__ro_diafrag_okcancel, tvCancel__ro_diafrag_okcancel;
    TextView tvNumSelectedProfiles__lo_numselectedprofiles_add;
    ImageButton ibAdd__lo_numselectedprofiles_add;
    EditText et__lo_diafrag;

    public BaseDiaFrag() {
        super();
    }


    public static BaseDiaFrag newInstance(Bundle bundle, Context context) {
        BaseDiaFrag frag = new BaseDiaFrag();
        frag.setArguments(bundle);
        mContext = context;
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.diafrag, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lo_numselectedprofiles_add__lo_diafrag= (LinearLayout) view.findViewById(R.id
                .lo_numselectedprofiles_add__lo_diafrag);

        tvNumSelectedProfiles__lo_numselectedprofiles_add = (TextView)lo_numselectedprofiles_add__lo_diafrag.findViewById(R.id.tvNumSelectedProfiles__lo_numselectedprofiles_add);
        ibAdd__lo_numselectedprofiles_add= (ImageButton)lo_numselectedprofiles_add__lo_diafrag.findViewById(R.id.ibAdd__lo_numselectedprofiles_add);

        lv__lo_diafragwithiconlist = (ListView) view.findViewById(R.id
                .lv__lo_diafrag);


        et__lo_diafrag= (EditText) view.findViewById(R.id.et__lo_diafrag);
        ro_diafrag_okcancel__lo_diafragwithiconlist= (RelativeLayout) view.findViewById(R.id.ro_diafrag_okcancel__lo_diafrag);
        tvOk__ro_diafrag_okcancel = (TextView )ro_diafrag_okcancel__lo_diafragwithiconlist
                .findViewById(R.id.tvOk__ro_diafrag_okcancel);
        tvOk__ro_diafrag_okcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOk();
            }
        });
        tvCancel__ro_diafrag_okcancel = (TextView )ro_diafrag_okcancel__lo_diafragwithiconlist
                .findViewById(R.id.tvCancel__ro_diafrag_okcancel);
        tvCancel__ro_diafrag_okcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Dialog");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }


    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    void onClickOk(){

    }
}
