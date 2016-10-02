package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.ui.widget.CheckBoxJack;


/**
 * Created by Jacklee on 2016. 9. 17..
 */
public class AvatarNameChkViewHolder extends RecyclerView.ViewHolder {

    View mView;
    RelativeLayout ro_person_photo_48dip__lo_avatar_namechk;
    TextView tvTitle__lo_avatar_namechk   ;
    CheckBoxJack checkboxJack__lo_avatar_namechk;

    public AvatarNameChkViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ro_person_photo_48dip__lo_avatar_namechk =
                (RelativeLayout) mView
                        .findViewById(R.id.ro_person_photo_48dip__lo_avatar_namechk);
        tvTitle__lo_avatar_namechk =
                (TextView) mView
                        .findViewById(R.id.tvTitle__lo_avatar_namechk);
        checkboxJack__lo_avatar_namechk =
                (CheckBoxJack) mView
                        .findViewById(R.id.checkboxJack__lo_avatar_namechk);
    }


    public void setPersonPhoto(String personUid) {
        //Glide
    }

    public void setPersonName(String personName) {
        //tvTitle__lo_avatar_namedate_chatread.setText();
    }



}
