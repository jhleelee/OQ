package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 11. 15..
 */

public class LoComment extends LinearLayout {
    public   RelativeLayout roAvatar;
    public TextView tvAvatar, tvTs, tvMultilineTxt;
    public ImageView ivAvatar ;
    public TextView tvName;

    public LoComment(Context context) {
        this(context, null);
    }

    public LoComment(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_commentlayout, this);
        common(view);



    }

    public LoComment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.lo_commentlayout, this);
        common(view);

    }


    void common(View view){

        roAvatar= (RelativeLayout) view.findViewById(R.id.roAvatar);
        tvAvatar = (TextView) roAvatar.findViewById(R.id.tvAva);
        ivAvatar = (ImageView) roAvatar.findViewById(R.id.ivAva);
         tvName = (TextView) view.findViewById(R.id.tvName);
        tvTs = (TextView) view.findViewById(R.id.tvTs);
        tvMultilineTxt = (TextView) view.findViewById(R.id.tvMultilineTxt);



    }



}

