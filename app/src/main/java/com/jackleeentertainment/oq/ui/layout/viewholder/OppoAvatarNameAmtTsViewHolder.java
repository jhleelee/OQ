package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 10. 31..
 */

public class OppoAvatarNameAmtTsViewHolder extends RecyclerView.ViewHolder {

    public View mView;
  public   RelativeLayout roAvatar;
    public   ImageView ivAvatar;
    public  TextView tvAvatar;

    public TextView tvName, tvDate;
    public TextView tvAmtConfirmed, tvAmtArgued, tvAmtDone, tvDeed;


    public OppoAvatarNameAmtTsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        roAvatar =
                (RelativeLayout) mView
                        .findViewById(R.id.ro_person_photo_48dip__i_oppo);


        ivAvatar =
                (ImageView) mView
                        .findViewById(R.id.ivAva);

        tvAvatar =
                (TextView) mView
                        .findViewById(R.id.tvAva);

        tvName =
                (TextView) mView
                        .findViewById(R.id.tvTitle__i_oppo);


        tvDate =
                (TextView) mView
                        .findViewById(R.id.tvDate__i_oppo);


        tvAmtConfirmed =
                (TextView) mView
                        .findViewById(R.id.tvAmtConfirmed);


        tvAmtArgued =
                (TextView) mView
                        .findViewById(R.id.tvAmtYet);

        tvAmtDone=
                (TextView) mView
                        .findViewById(R.id.tvAmtDone);
        tvDeed = (TextView) mView
                .findViewById(R.id.tvDeed);

    }



}
