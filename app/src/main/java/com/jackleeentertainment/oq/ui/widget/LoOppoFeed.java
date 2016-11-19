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
 * Created by Jacklee on 2016. 11. 16..
 */

public class LoOppoFeed extends LinearLayout {


    public RelativeLayout roAvatar;
    public ImageView ivAvatar;
    public TextView tvAvatar;
    public TextView tvName, tvAmmount,   tvDeed;


    public LoOppoFeed(Context context) {
        this(context, null);
    }

    public LoOppoFeed(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.i_2ava_oqdo_small_infeed, this);



        roAvatar = (RelativeLayout) view.findViewById(R.id
                .roRelationTwoAva);

        ivAvatar = (ImageView) roAvatar.findViewById(R.id
                .ivAva);
        tvAvatar = (TextView) roAvatar.findViewById(R.id
                .tvAva);


        tvName = (TextView) view.findViewById(R.id
                .tvName);
        tvAmmount = (TextView) view.findViewById(R.id
                .tvAmmount);


        tvDeed = (TextView) view.findViewById(R.id
                .tvDeed);






    }


}

