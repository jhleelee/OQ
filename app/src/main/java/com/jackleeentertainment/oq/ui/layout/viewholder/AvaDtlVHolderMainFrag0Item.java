package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 11. 19..
 */

public class AvaDtlVHolderMainFrag0Item extends RecyclerView.ViewHolder {
    public View mView;
    public LinearLayout loWrap;
    public  RelativeLayout roAva;
    public   TextView tvAva;
    public   TextView tvName, tvResultAmmount, tvResultAmmount2, tvContent;
    public   ImageView ivAva;
    public   TextView tvTs;

    public AvaDtlVHolderMainFrag0Item(View itemView) {
        super(itemView);
        mView = itemView;
        loWrap = (LinearLayout) mView.findViewById(R.id.loWrap);
        roAva = (RelativeLayout) mView.findViewById(R.id.roAva);
        tvAva = (TextView) mView.findViewById(R.id.tvAva);
        ivAva = (ImageView) mView.findViewById(R.id.ivAva);
        tvName = (TextView) mView.findViewById(R.id.tvName);
        tvResultAmmount = (TextView) mView.findViewById(R.id.tvProfileActivityTs);
        tvResultAmmount2 = (TextView) mView.findViewById(R.id.tvResultAmmount2);
        tvContent = (TextView) mView.findViewById(R.id.tvContent);
        tvTs= (TextView) mView.findViewById(R.id.tvTs);

    }

         /*
        OpponentProfile
         */

}
