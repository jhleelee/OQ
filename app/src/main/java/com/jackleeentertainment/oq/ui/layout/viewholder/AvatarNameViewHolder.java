package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;


/**
 * Created by Jacklee on 2016. 9. 17..
 */
public class AvatarNameViewHolder extends RecyclerView.ViewHolder{


    View mView;
    RelativeLayout ro_person_photo_48dip__lo_avatar_name;
    TextView tvTitle__lo_avatar_name   ;

    public AvatarNameViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ro_person_photo_48dip__lo_avatar_name =
                (RelativeLayout) mView
                        .findViewById(R.id.ro_person_photo_48dip__lo_avatar_name);
        tvTitle__lo_avatar_name =
                (TextView) mView
                        .findViewById(R.id.tvTitle__lo_avatar_name);
    }


    public void setPersonPhoto(String personUid) {
        //Glide
    }

    public void setPersonName(String personName) {
        tvTitle__lo_avatar_name.setText(personName);
    }




}
