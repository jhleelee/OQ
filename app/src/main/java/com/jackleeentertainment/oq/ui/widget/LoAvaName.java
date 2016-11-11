package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 11. 7..
 */

public class LoAvaName extends LinearLayout {


    public TextView tvAvatar;
    public ImageView ivAvatar ;
    public TextView tvName;



     public LoAvaName(Context context) {
        this(context, null);
    }

    public LoAvaName(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_avatar_name, this);
        common(view);
   }

    public LoAvaName(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.lo_avatar_name, this);
        common(view);

    }


    void common(View view){

        tvAvatar = (TextView) view.findViewById(R.id.tvAva);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAva);
         tvName = (TextView) view.findViewById(R.id.tvTitle__lo_avatar_name);

    }



}

