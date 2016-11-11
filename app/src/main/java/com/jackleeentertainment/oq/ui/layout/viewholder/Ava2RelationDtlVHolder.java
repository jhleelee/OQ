package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by jaehaklee on 2016. 10. 4..
 */

public class Ava2RelationDtlVHolder extends RecyclerView.ViewHolder {
    public   View mView;
 public   LinearLayout loTwoAvaWrap;
    public  LinearLayout lolvTwoAvaHistory;
    public  RecyclerView rvSub;
    public  RelativeLayout roRelationTwoAva;
    public  TextView tvTwoName;
    public  TextView tvDate;
    public  TextView tvContent;
    public ImageView ivMore, ivRelation;
    public  RelativeLayout roAvaLeft, roAvaRight;
    public  ImageView ivAvaLeft, ivAvaRight;
    public  TextView tvAvaLeft, tvAvaRight;

    public Ava2RelationDtlVHolder(View itemView) {
        super(itemView);
        mView = itemView;
        loTwoAvaWrap
                =
                (LinearLayout) mView
                        .findViewById(R.id.loTwoAvaWrap);
        //loTwoAvaWrap

        roRelationTwoAva =
                (RelativeLayout) loTwoAvaWrap
                        .findViewById(R.id.roRelationTwoAva);

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


        tvTwoName =
                (TextView) loTwoAvaWrap
                        .findViewById(R.id.tvNameTwoAva);

        tvDate =
                (TextView) loTwoAvaWrap
                        .findViewById(R.id.tvDateTwoAva);

        tvContent =
                (TextView) loTwoAvaWrap
                        .findViewById(R.id.tvContentTwoAva);

        ivMore =
                (ImageView) loTwoAvaWrap
                        .findViewById(R.id.ivMoreTwoAvaHistory);

        lolvTwoAvaHistory =
                (LinearLayout) mView
                        .findViewById(R.id.lolvTwoAvaHistory);
        rvSub
                =
                (RecyclerView) mView
                        .findViewById(R.id.rvSub);
    }

         /*
        OpponentProfile
         */

}
