package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;


/**
 * Created by Jacklee on 2016. 9. 17..
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {


    View mView;
    RelativeLayout ro_person_photo_48dip__lo_avatar_namemultilinetext_lohourlikereply;
    TextView tvTitle__lo_avatar_namemultilinetext_lohourlikereply, tvMultiline__lo_avatar_namemultilinetext_lohourlikereply,
    tvHourAgo__lo_hourlikereply, tvFavorite__lo_hourlikereply, tvReply__lo_hourlikereply;
    LinearLayout lo_hourlikereply__lo_avatar_namemultilinetext_lohourlikereply;



    public CommentViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ro_person_photo_48dip__lo_avatar_namemultilinetext_lohourlikereply =
                (RelativeLayout) mView
                        .findViewById(R.id.ro_person_photo_48dip__lo_avatar_namemultilinetext_lohourlikereply);
        tvTitle__lo_avatar_namemultilinetext_lohourlikereply =
                (TextView) mView
                        .findViewById(R.id.tvTitle__lo_avatar_namemultilinetext_lohourlikereply);
        tvMultiline__lo_avatar_namemultilinetext_lohourlikereply =
                (TextView) mView
                        .findViewById(R.id.tvMultiline__lo_avatar_namemultilinetext_lohourlikereply);
        lo_hourlikereply__lo_avatar_namemultilinetext_lohourlikereply =
                (LinearLayout) mView
                        .findViewById(R.id.lo_hourlikereply__lo_avatar_namemultilinetext_lohourlikereply);

        tvHourAgo__lo_hourlikereply =
                (TextView) lo_hourlikereply__lo_avatar_namemultilinetext_lohourlikereply
                        .findViewById(R.id.tvHourAgo__lo_hourlikereply);
        tvFavorite__lo_hourlikereply =
                (TextView) lo_hourlikereply__lo_avatar_namemultilinetext_lohourlikereply
                        .findViewById(R.id.tvFavorite__lo_hourlikereply);
        tvReply__lo_hourlikereply =
                (TextView) lo_hourlikereply__lo_avatar_namemultilinetext_lohourlikereply
                        .findViewById(R.id.tvReply__lo_hourlikereply);

    }


    public void setCommentorPhoto(String personUid) {
        //Glide
    }

    public void setCommentorName(String personName) {
        //tvTitle__lo_avatar_namedate_chatread.setText();
    }

    public void setBodyContent(String content) {
        //
    }

    public void setBodyHourAgo(String hourAgo) {
        //
    }

    public void setBodyFavoriteNum(int favoriteNum) {
        //
    }

    public void setBodyReplyNum(int replyNum) {
        //
    }
}
