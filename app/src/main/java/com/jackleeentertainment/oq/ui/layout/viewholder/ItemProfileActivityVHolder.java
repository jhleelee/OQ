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

public class ItemProfileActivityVHolder extends RecyclerView.ViewHolder {
    public   View mView;
 public   LinearLayout loTwoAvaWrap;
    public  LinearLayout lolvTwoAvaHistory;
     public  RelativeLayout roRelationTwoAva;
    public  TextView tvOppoName;
    public  TextView tvResultAmmount;
    public  TextView tvResultAmmount2;

    public  TextView tvContent;
    public ImageView ivMore, ivRelation;
    public  RelativeLayout roAvaLeft, roAvaRight;
    public  ImageView ivAvaLeft, ivAvaRight;
    public  TextView tvAvaLeft, tvAvaRight;

    public ItemProfileActivityVHolder(View itemView) {
        super(itemView);
        mView = itemView;









        roRelationTwoAva =
                (RelativeLayout) mView
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







        tvOppoName =
                (TextView) mView
                        .findViewById(R.id.tvNameTwoAva);

        tvResultAmmount =
                (TextView) mView
                        .findViewById(R.id.tvResultAmmount);


        tvResultAmmount2=
                (TextView) mView
                        .findViewById(R.id.tvResultAmmount2);

        tvContent =
                (TextView) mView
                        .findViewById(R.id.tvContentTwoAva);

        ivMore =
                (ImageView) mView
                        .findViewById(R.id.ivMoreTwoAvaHistory);

        lolvTwoAvaHistory =
                (LinearLayout) mView
                        .findViewById(R.id.loBreakDown);

    }

         /*
        OpponentProfile
         */

}
