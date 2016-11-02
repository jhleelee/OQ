package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.jackleeentertainment.oq.R;


/**
 * Created by Jacklee on 2016. 9. 16..
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public    RelativeLayout roMedia, roAvatar;
    public  ImageView ivAvatar;
    public  TextView tvAvatar, tvName, tvDate, tvDeed;
    public  ImageView ivMedia;
    public  VideoView vvMedia;
    public  TextView tvSupportingText;
    public  LinearLayout loOqOppo, loCommentOne;

    public PostViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        roAvatar= (RelativeLayout) mView.findViewById(R.id.roAvatar);
        tvAvatar= (TextView) mView.findViewById(R.id.ro_person_photo_tv);
        ivAvatar= (ImageView) mView.findViewById(R.id.ro_person_photo_iv);
                tvName= (TextView) mView.findViewById(R.id.tvName);
        tvDate= (TextView) mView.findViewById(R.id.tvDate);
        tvDeed= (TextView) mView.findViewById(R.id.tvDeed);
        roMedia = (RelativeLayout) mView.findViewById(R.id.roMedia);

        vvMedia = (VideoView) mView.findViewById(R.id.videoViewMedia);
        ivMedia = (ImageView) mView.findViewById(R.id.imageViewMedia);
        tvSupportingText = (TextView) mView.findViewById(R.id.tvSupportingText);
        loOqOppo = (LinearLayout) mView.findViewById(R.id.loOqOppo);
        loCommentOne = (LinearLayout) mView.findViewById(R.id.loCommentOne);



    };





}
