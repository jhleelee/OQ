package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;

/**
 * Created by jaehaklee on 2016. 10. 4..
 */

public class ItemProfileActivityVHolder extends RecyclerView.ViewHolder {
    public   View mView;
 public   LinearLayout loTwoAvaWrap;
    public  LinearLayout lolvTwoAvaHistory;
     public  RelativeLayout roRelationTwoAva;
    public  TextView tvMainContent;
    public  TextView tvTs;
    public  TextView tvResultAmmount2;

    public  TextView tvAfterallContent;
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







        tvMainContent =
                (TextView) mView
                        .findViewById(R.id.tvMainContent);

        tvAfterallContent =
                (TextView) mView
                        .findViewById(R.id.tvAfterContent);



        tvTs =
                (TextView) mView
                        .findViewById(R.id.tvProfileActivityTs);


        tvResultAmmount2=
                (TextView) mView
                        .findViewById(R.id.tvResultAmmount2);





        lolvTwoAvaHistory =
                (LinearLayout) mView
                        .findViewById(R.id.loBreakDown);
        ivMore =
                (ImageView) mView
                        .findViewById(R.id.ivMoreTwoAvaHistory);
        ivMore.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_more_vert_white_48dp,
                        R.color.colorPrimary,
                        App.getContext()
                )
        );

        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visi = lolvTwoAvaHistory.getVisibility();
                if (visi==View.GONE){
                    lolvTwoAvaHistory.setVisibility(View.VISIBLE);
                } else if (visi==View.VISIBLE){
                    lolvTwoAvaHistory.setVisibility(View.GONE);
                }

            }
        });
    }

         /*
        OpponentProfile
         */

}
