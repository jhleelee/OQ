package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.os.Bundle;

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
}
