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

public class Lo2AvaSmall extends LinearLayout {
    public RelativeLayout roRelationTwoAva;
      public TextView tvName, tvAmmount, tvDeed;
    public RelativeLayout roAvaLeft, roAvaRight;
    public ImageView ivRelation;

    public ImageView ivAvaLeft;
    public ImageView ivAvaRight;
    public TextView tvAvaLeft;
    public TextView tvAvaRight;


    public Lo2AvaSmall(Context context) {
        this(context, null);
    }

    public Lo2AvaSmall(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.i_2ava_oqdo_small_infeed, this);

        roRelationTwoAva = (RelativeLayout)view.findViewById(R.id.roRelationTwoAva);
        roAvaLeft = (RelativeLayout)roRelationTwoAva.findViewById(R.id.roAvaLeft);
        roAvaRight = (RelativeLayout)roRelationTwoAva.findViewById(R.id.roAvaRight);
        ivRelation = (ImageView)roRelationTwoAva.findViewById(R.id.ivRelation);

        tvAvaLeft= (TextView)roAvaLeft.findViewById(R.id.tvAva);
        ivAvaLeft= (ImageView)roAvaLeft.findViewById(R.id.ivAva);
        tvAvaRight= (TextView)roAvaRight.findViewById(R.id.tvAva);
        ivAvaRight= (ImageView)roAvaRight.findViewById(R.id.ivAva);

        tvName= (TextView)view.findViewById(R.id.tvName);
        tvAmmount= (TextView)view.findViewById(R.id.tvAmmount);
        tvDeed= (TextView)view.findViewById(R.id.tvDeed);







    }


}


