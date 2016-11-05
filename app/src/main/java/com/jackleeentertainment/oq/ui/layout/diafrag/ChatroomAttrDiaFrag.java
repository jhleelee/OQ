package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class ChatroomAttrDiaFrag extends BaseDiaFrag{

     public static ChatroomAttrDiaFrag newInstance(Bundle bundle, Context context) {
        ChatroomAttrDiaFrag frag = new ChatroomAttrDiaFrag();
        frag.setArguments(bundle);
        mContext = context;
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int  theme = android.R.style.Theme_Material_Light_Dialog_Alert;
        setStyle(style, theme);

    }

}
