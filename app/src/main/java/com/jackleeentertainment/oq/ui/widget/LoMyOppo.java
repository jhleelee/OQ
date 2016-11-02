package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.object.Profile;

/**
 * Created by Jacklee on 2016. 11. 1..
 */

public class LoMyOppo extends LinearLayout {


    public RelativeLayout ro_person_photo_48dip__i_oppo;
    public ImageView ivAvatar;
    public TextView tvAvatar;
    public TextView tvTitle__i_oppo, tvDate__i_oppo, tvAmtConfirmed, tvAmtYet, tvAmtDone, tvDeed;


    public LoMyOppo(Context context) {
        this(context, null);
    }

    public LoMyOppo(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.i_oppo, this);
        ro_person_photo_48dip__i_oppo = (RelativeLayout) view.findViewById(R.id
                .ro_person_photo_48dip__i_oppo);

        ivAvatar = (ImageView) view.findViewById(R.id
                .ro_person_photo_iv);
        tvAvatar = (TextView) view.findViewById(R.id
                .ro_person_photo_tv);
        tvTitle__i_oppo = (TextView) view.findViewById(R.id
                .tvTitle__i_oppo);
        tvDate__i_oppo = (TextView) view.findViewById(R.id
                .tvDate__i_oppo);
        tvAmtConfirmed = (TextView) view.findViewById(R.id
                .tvAmtConfirmed);
        tvAmtYet = (TextView) view.findViewById(R.id
                .tvAmtYet);
        tvAmtDone = (TextView) view.findViewById(R.id
                .tvAmtDone);
        tvDeed = (TextView) view.findViewById(R.id
                .tvDeed);

    }


}

