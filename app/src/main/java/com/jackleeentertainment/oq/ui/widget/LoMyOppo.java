package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 11. 1..
 */

public class LoMyOppo extends LinearLayout {


    public RelativeLayout roAvatar;
    public ImageView ivAvatar;
    public TextView tvAvatar;
    public TextView tvName, tvDate__i_oppo, tvAmtConfirmed, tvAmtYet, tvAmtDone, tvDeed;


    public LoMyOppo(Context context) {
        this(context, null);
    }

    public LoMyOppo(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.i_oppo, this);
        roAvatar = (RelativeLayout) view.findViewById(R.id
                .ro_person_photo_48dip__i_oppo);

        ivAvatar = (ImageView) view.findViewById(R.id
                .ivAva);
        tvAvatar = (TextView) view.findViewById(R.id
                .tvAva);
        tvName = (TextView) view.findViewById(R.id
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

