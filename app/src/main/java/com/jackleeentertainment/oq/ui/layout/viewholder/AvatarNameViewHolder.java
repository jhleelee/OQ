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
public class AvatarNameViewHolder extends RecyclerView.ViewHolder{


    public View mView;
  public   RelativeLayout ro_person_photo_48dip__lo_avatar_name;
    public  TextView tvTitle__lo_avatar_name   ;
    public TextView ro_person_photo_tv;
    public ImageView ro_person_photo_iv;

    public AvatarNameViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ro_person_photo_48dip__lo_avatar_name =
                (RelativeLayout) mView
                        .findViewById(R.id.ro_person_photo_48dip__lo_avatar_name);
        ro_person_photo_tv =
                (TextView) ro_person_photo_48dip__lo_avatar_name
                        .findViewById(R.id.tvAva);
        ro_person_photo_iv =
                (ImageView) ro_person_photo_48dip__lo_avatar_name
                        .findViewById(R.id.ivAva);
        tvTitle__lo_avatar_name =
                (TextView) mView
                        .findViewById(R.id.tvTitle__lo_avatar_name);
    }



}
