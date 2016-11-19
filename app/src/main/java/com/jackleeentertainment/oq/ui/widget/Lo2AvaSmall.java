package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 11. 16..
 */

public class Lo2AvaSmall extends LinearLayout {
    public TextView tvAvatar;
    public ImageView ivAvatar, ivDelete;
    public TextView tvName, tvEmail;

    public Lo2AvaSmall(Context context) {
        this(context, null);
    }

    public Lo2AvaSmall(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.i_2ava_oqdo_small_infeed, this);

        tvAvatar = (TextView) view.findViewById(R.id.tvAva);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAva);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        tvName = (TextView) view.findViewById(R.id.tvTitle__lo_avatartitlesubtitle_delete);
        tvEmail = (TextView) view.findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle_delete);

    }

    public void setOnClickListenerForIvDelete(OnClickListener l) {
        ivDelete.setOnClickListener(l);
    }

}


