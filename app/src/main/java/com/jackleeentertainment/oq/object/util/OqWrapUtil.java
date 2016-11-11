package com.jackleeentertainment.oq.object.util;

import android.content.res.Resources;
import android.widget.ImageView;

import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqWrap;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.types.OqWrapT;

import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class OqWrapUtil {


    public static void ivTwoAvaRelation(ImageView iv, OqWrap oqWrap) {


        String t = getOqWrapT(oqWrap);
        OqDoUtil.ivTwoAvaRelation(iv, oqWrap.getListoqdo());


    }


    public static String getOqWrapStr(OqWrap oqWrap) {
        List<OqDo> listoqdo = oqWrap.getListoqdo();
       return OqDoUtil.getOqDoListStr(listoqdo);
    }


    public static String getOqWrapT(OqWrap oqWrap) {

        List<OqDo> listoqdo = oqWrap.getListoqdo();
        return OqDoUtil.getOqDoListT(listoqdo);


    }


}
