package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.ui.widget.CheckBoxJack;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class AvatarNameEmailChkViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public RelativeLayout ro_person_photo_48dip__lo_avatartitlesubtitle_chk;
    public ImageView ro_person_photo_iv;
    public TextView ro_person_photo_tv;

    public TextView tvTitle__lo_avatartitlesubtitle_chk;
    public TextView tvSubTitle__lo_avatartitlesubtitle_chk;
    public CheckBoxJack checkboxJack__lo_avatartitlesubtitle_chk;

    public AvatarNameEmailChkViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ro_person_photo_48dip__lo_avatartitlesubtitle_chk =
                (RelativeLayout) mView
                        .findViewById(R.id.ro_person_photo_48dip__lo_avatartitlesubtitle_chk);
        ro_person_photo_iv =
                (ImageView) ro_person_photo_48dip__lo_avatartitlesubtitle_chk
                        .findViewById(R.id.ivAva);
        ro_person_photo_tv =
                (TextView) ro_person_photo_48dip__lo_avatartitlesubtitle_chk
                        .findViewById(R.id.tvAva);
        tvTitle__lo_avatartitlesubtitle_chk =
                (TextView) mView
                        .findViewById(R.id.tvTitle__lo_avatartitlesubtitle_chk);
        tvSubTitle__lo_avatartitlesubtitle_chk =
                (TextView) mView
                        .findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle_chk);
        checkboxJack__lo_avatartitlesubtitle_chk =
                (CheckBoxJack) mView
                        .findViewById(R.id.checkboxJack__lo_avatartitlesubtitle_chk);
    }
}
