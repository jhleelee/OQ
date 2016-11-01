package com.jackleeentertainment.oq.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.object.Profile;

/**
 * Created by Jacklee on 2016. 10. 29..
 */

public class LoIvAvatarTvNameSmallEtAmountLargeIvBtns extends LinearLayout {
    public TextView tvAvatar;
    public ImageView ivAvatar, ivToc, ivDelete;
    public TextView tvName;
    public EditText etAmmount;

    public LoIvAvatarTvNameSmallEtAmountLargeIvBtns(Context context) {
        this(context, null);
    }

    public LoIvAvatarTvNameSmallEtAmountLargeIvBtns(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_avatar_smallnamelargeamount_btns, this);

        tvAvatar = (TextView) view.findViewById(R.id.ro_person_photo_tv);
        ivAvatar = (ImageView) view.findViewById(R.id.ro_person_photo_iv);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        tvName = (TextView) view.findViewById(R.id.tvTitle__lo_avatar_smallnamelargeamount_btns);
        ivToc = (ImageView) view.findViewById(R.id.ivToc);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        etAmmount = (EditText) view.findViewById(R.id.etAmount__lo_avatar_smallnamelargeamount_btns);



    }


    public LoIvAvatarTvNameSmallEtAmountLargeIvBtns(Context context, AttributeSet attrs, Profile
            profile) {
        super(context, attrs);
        View view = inflate(context, R.layout.lo_avatar_smallnamelargeamount_btns, this);

        tvAvatar = (TextView) view.findViewById(R.id.ro_person_photo_tv);
        ivAvatar = (ImageView) view.findViewById(R.id.ro_person_photo_iv);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        tvName = (TextView) view.findViewById(R.id.tvTitle__lo_avatar_smallnamelargeamount_btns);
        ivToc = (ImageView) view.findViewById(R.id.ivToc);
        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        etAmmount = (EditText) view.findViewById(R.id.etAmount__lo_avatar_smallnamelargeamount_btns);

//        Glide ;

        tvName.setText(profile.getFull_name());
    }


    public void setOnIvTocClickListener(OnClickListener l) {
        ivToc.setOnClickListener(l);
    }


    public void setOnIvDeleteClickListener(OnClickListener l) {
        ivDelete.setOnClickListener(l);
    }

}

