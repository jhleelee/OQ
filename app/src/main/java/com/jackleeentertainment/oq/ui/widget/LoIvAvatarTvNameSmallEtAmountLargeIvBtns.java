package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.activity.uiobj.TempProAmt;

/**
 * Created by Jacklee on 2016. 10. 29..
 */

public class LoIvAvatarTvNameSmallEtAmountLargeIvBtns extends LinearLayout {
    public TextView tvAvatar;
    public ImageView ivAvatar, ivToc, ivDelete;
    public TextView tvName;
    public LoEtMoney loetmomey;
    public TempProAmt tempProAmt;


    public LoIvAvatarTvNameSmallEtAmountLargeIvBtns(Context context) {
        this(context, null);
    }

    public LoIvAvatarTvNameSmallEtAmountLargeIvBtns(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_avatar_smallnamelargeamount_btns, this);
        common(view);



    }

    public LoIvAvatarTvNameSmallEtAmountLargeIvBtns(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.lo_avatar_smallnamelargeamount_btns, this);
        common(view);

    }


    void common(View view){

        tvAvatar = (TextView) view.findViewById(R.id.tvAva);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAva);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        tvName = (TextView) view.findViewById(R.id.tvTitle__lo_avatar_smallnamelargeamount_btns);
        ivToc = (ImageView) view.findViewById(R.id.ivToc);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        loetmomey = (LoEtMoney) view.findViewById(R.id.loetmomey);

    }

    public void setOnIvTocClickListener(OnClickListener l) {
        ivToc.setOnClickListener(l);
    }


    public void setOnIvDeleteClickListener(OnClickListener l) {
        ivDelete.setOnClickListener(l);
    }

}

