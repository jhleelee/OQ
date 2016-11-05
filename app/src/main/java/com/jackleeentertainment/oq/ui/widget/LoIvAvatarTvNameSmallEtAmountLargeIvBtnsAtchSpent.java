package com.jackleeentertainment.oq.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 10. 30..
 */




public class LoIvAvatarTvNameSmallEtAmountLargeIvBtnsAtchSpent extends LinearLayout {
    public TextView tvAvatar;
    public ImageView ivAvatar, ivToc, ivDelete;
    public TextView tvName;
    public EditText etAmmount;
    public TextView tvSms;
    public ImageView ivReceipt;

    public LoIvAvatarTvNameSmallEtAmountLargeIvBtnsAtchSpent(Context context) {
        this(context, null);
    }

    public LoIvAvatarTvNameSmallEtAmountLargeIvBtnsAtchSpent(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_avatar_smallnamelargeamount_btns, this);

        tvAvatar = (TextView) view.findViewById(R.id.tvAva);
        ivAvatar = (ImageView) view.findViewById(R.id.ivAva);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        tvName = (TextView) view.findViewById(R.id.tvTitle__lo_avatar_smallnamelargeamount_btns);
        ivToc = (ImageView) view.findViewById(R.id.ivToc);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        etAmmount = (EditText) view.findViewById(R.id.etAmount__lo_avatar_smallnamelargeamount_btns);
        tvSms = (TextView) view.findViewById(R.id.tvSms);
        ivReceipt = (ImageView) view.findViewById(R.id.ivReceipt);

    }




    public void setOnIvTocClickListener(OnClickListener l) {
        ivToc.setOnClickListener(l);
    }


    public void setOnIvDeleteClickListener(OnClickListener l) {
        ivDelete.setOnClickListener(l);
    }

}

