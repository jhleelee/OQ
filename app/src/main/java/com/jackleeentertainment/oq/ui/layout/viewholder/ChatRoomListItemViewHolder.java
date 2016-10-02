package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jackleeentertainment.oq.R;

import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 17..
 */
public class ChatRoomListItemViewHolder extends RecyclerView.ViewHolder{

    View mView;
    RelativeLayout ro_person_photo_48dip__lo_avatar_namedate_chatread;
    TextView tvTitle__lo_avatar_namedate_chatread,
            tvSubTitle__lo_avatar_namedate_chatread,
            tvDate__lo_avatar_namedate_chatread;
    TextView tvUnread__lo_avatar_namedate_chatread;

    public ChatRoomListItemViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ro_person_photo_48dip__lo_avatar_namedate_chatread =
                (RelativeLayout) mView
                .findViewById(R.id.ro_person_photo_48dip__lo_avatar_namedate_chatread);
        tvTitle__lo_avatar_namedate_chatread =
                (TextView) mView
                        .findViewById(R.id.tvTitle__lo_avatar_namedate_chatread);
        tvSubTitle__lo_avatar_namedate_chatread =
                (TextView) mView
                        .findViewById(R.id.tvSubTitle__lo_avatar_namedate_chatread);
        tvDate__lo_avatar_namedate_chatread =
                (TextView) mView
                        .findViewById(R.id.tvDate__lo_avatar_namedate_chatread);
        tvUnread__lo_avatar_namedate_chatread =
                (TextView) mView
                        .findViewById(R.id.tvUnread__lo_avatar_namedate_chatread);
    }

         /*
        OpponentProfile
         */

    public void setOpponentPhotos(List<String> opponentUids) {
        //Glide
    }

    public void setOpponentNames(List<String> opponentNames) {
        //tvTitle__lo_avatar_namedate_chatread.setText();
    }


        /*
        LastChat
         */


    public void setLastChatDate() {
        //
    }

    public void setLastChatUnRead() {
        //
    }




}
