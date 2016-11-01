package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 10. 29..
 */

public class LoIvAvatarTvTitlesIvDelete extends LinearLayout {
    public TextView tvAvatar;
    public ImageView ivAvatar, ivDelete;
    public TextView tvName, tvEmail;

    public LoIvAvatarTvTitlesIvDelete(Context context) {
        this(context, null);
    }

    public LoIvAvatarTvTitlesIvDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_avatar_titlesubtitle_delete, this);

        tvAvatar = (TextView) view.findViewById(R.id.ro_person_photo_tv);
        ivAvatar = (ImageView) view.findViewById(R.id.ro_person_photo_iv);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        tvName = (TextView) view.findViewById(R.id.tvTitle__lo_avatartitlesubtitle_delete);
        tvEmail = (TextView) view.findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle_delete);

    }

    public void setOnClickListenerForIvDelete(OnClickListener l) {
        ivDelete.setOnClickListener(l);
    }

}


