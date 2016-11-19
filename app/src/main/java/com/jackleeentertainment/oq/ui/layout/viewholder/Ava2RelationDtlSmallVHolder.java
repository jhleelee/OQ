package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 11. 6..
 */

public class Ava2RelationDtlSmallVHolder extends RecyclerView.ViewHolder {
    public View mView;
    public LinearLayout loTwoAvaWrap;
     public RelativeLayout roRelationTwoAva;
    public TextView tvDate;
    public TextView tvContent;
    public ImageView  ivRelation;
    public RelativeLayout roAvaLeft, roAvaRight;
    public ImageView ivAvaLeft, ivAvaRight;
    public TextView tvAvaLeft, tvAvaRight;

    public Ava2RelationDtlSmallVHolder(View itemView) {
        super(itemView);
        mView = itemView;


        //loTwoAvaWrap

        roRelationTwoAva =
                (RelativeLayout) mView
                        .findViewById(R.id.roRelationTwoAva); //incl
        roAvaLeft =
                (RelativeLayout) roRelationTwoAva
                        .findViewById(R.id.roAvaLeft);
        ivAvaLeft =
                (ImageView) roAvaLeft
                        .findViewById(R.id.ivAva);
        tvAvaLeft =
                (TextView) roAvaLeft
                        .findViewById(R.id.tvAva);


        roAvaRight =
                (RelativeLayout) roRelationTwoAva
                        .findViewById(R.id.roAvaRight);


        ivAvaRight =
                (ImageView) roAvaRight
                        .findViewById(R.id.ivAva);
        tvAvaRight =
                (TextView) roAvaRight
                        .findViewById(R.id.tvAva);
        ivRelation =
                (ImageView) roRelationTwoAva
                        .findViewById(R.id.ivRelation);


        tvDate =
                (TextView) loTwoAvaWrap
                        .findViewById(R.id.tvResultAmmount);

        tvContent =
                (TextView) loTwoAvaWrap
                        .findViewById(R.id.tvContentTwoAva);


    }

         /*
        OpponentProfile
         */

}
