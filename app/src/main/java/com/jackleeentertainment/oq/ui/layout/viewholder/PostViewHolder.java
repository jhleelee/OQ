package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.CardView;
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
    public CardView cardView;
    public    RelativeLayout roMedia, roAvatar;
    public  ImageView ivAvatar;
    public  TextView tvAvatar, tvName, tvDate, tvDeed;

    public  TextView tvSupportingText;
    public  LinearLayout loOqOppo, loCommentOne;
    public VideoView vvPhotoMain;
    public  ImageView ivPhotoMain;
    public  ImageView ivPhotoSub;
    public  TextView tvPhotoSubNum;
    public RelativeLayout roPhotoSub;
    public TextView tvAddComment;

    public PostViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        cardView= (CardView) mView.findViewById(R.id.cardview);
        roAvatar= (RelativeLayout) mView.findViewById(R.id.roAvatar);
        tvAvatar= (TextView) mView.findViewById(R.id.tvAva);
        ivAvatar= (ImageView) mView.findViewById(R.id.ivAva);
                tvName= (TextView) mView.findViewById(R.id.tvName);
        tvDate= (TextView) mView.findViewById(R.id.tvDate);
        tvDeed= (TextView) mView.findViewById(R.id.tvDeed);
        roMedia = (RelativeLayout) mView.findViewById(R.id.roMedia);
        vvPhotoMain = (VideoView) mView.findViewById(R.id.vvPhotoMain);
        ivPhotoMain = (ImageView) mView.findViewById(R.id.ivPhotoMain);
        roPhotoSub= (RelativeLayout) mView.findViewById(R.id.roPhotoSub);
        ivPhotoSub = (ImageView) mView.findViewById(R.id.ivPhotoSub);
        tvPhotoSubNum= (TextView) mView.findViewById(R.id.tvPhotoSubNum);

         tvSupportingText = (TextView) mView.findViewById(R.id.tvSupportingText);
        loOqOppo = (LinearLayout) mView.findViewById(R.id.loOqOppo);
        loCommentOne = (LinearLayout) mView.findViewById(R.id.loCommentOne);
        tvAddComment= (TextView) mView.findViewById(R.id.tvAddComment);
    };





}
