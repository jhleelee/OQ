package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class OneLineInputDiaFrag  extends BaseDiaFrag {

    public static OneLineInputDiaFrag newInstance(Bundle bundle, Context context) {
        OneLineInputDiaFrag frag = new OneLineInputDiaFrag();
        frag.setArguments(bundle);
        mContext = context;
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.G(lv__lo_diafragwithiconlist);
        JM.V(et__lo_diafrag);
        JM.V(ro_diafrag_okcancel__lo_diafragwithiconlist);
        tvOk__ro_diafrag_okcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LBR.send("OneLineInputDiaFrag"  , et__lo_diafrag.getText().toString());
                dismiss();
            }
        });
    }
}
