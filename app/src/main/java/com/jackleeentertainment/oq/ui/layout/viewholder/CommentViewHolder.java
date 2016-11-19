package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;


/**
 * Created by Jacklee on 2016. 9. 17..
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {


    View mView;
  public   RelativeLayout roAvatar;
    public  ImageView ivAvatar;
    public  TextView tvAvatar,tvName,
             tvMultiline,
            tvTs;



    public CommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        roAvatar =
                (RelativeLayout) mView
                        .findViewById(R.id.roAvatar);

        ivAvatar =
                (ImageView) mView
                        .findViewById(R.id.ivAva);


        tvAvatar =
                (TextView) mView
                        .findViewById(R.id.tvAva);
        tvName = (TextView) mView.findViewById(R.id
                .tvName);
        tvMultiline =
                (TextView) mView
                        .findViewById(R.id.tvMultilineTxt);

        tvTs =
                (TextView) mView
                        .findViewById(R.id.tvTs);


    }



}
