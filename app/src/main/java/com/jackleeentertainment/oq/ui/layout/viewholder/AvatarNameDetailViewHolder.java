package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.JM;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class AvatarNameDetailViewHolder extends RecyclerView.ViewHolder {

    View mView;
    RelativeLayout ro_person_photo_48dip__lo_avatartitlesubtitle;
    TextView tvTitle__lo_avatartitlesubtitle;
    TextView tvSubTitle__lo_avatartitlesubtitle;
    ImageView ro_person_photo_iv;

    public AvatarNameDetailViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ro_person_photo_48dip__lo_avatartitlesubtitle =
                (RelativeLayout) mView
                        .findViewById(R.id.ro_person_photo_48dip__lo_avatartitlesubtitle);
        ro_person_photo_iv =
                (ImageView) ro_person_photo_48dip__lo_avatartitlesubtitle
                        .findViewById(R.id.ro_person_photo_iv);
        tvTitle__lo_avatartitlesubtitle =
                (TextView) mView
                        .findViewById(R.id.tvTitle__lo_avatartitlesubtitle);
        tvSubTitle__lo_avatartitlesubtitle =
                (TextView) mView
                        .findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle);
    }

         /*
        OpponentProfile
         */



    public void setPersonName(String uname) {
        tvTitle__lo_avatartitlesubtitle.setText(uname);
    }


    public RelativeLayout getRo_person_photo_48dip__lo_avatartitlesubtitle() {
        return ro_person_photo_48dip__lo_avatartitlesubtitle;
    }

    public TextView getTvTitle__lo_avatartitlesubtitle() {
        return tvTitle__lo_avatartitlesubtitle;
    }

    public TextView getTvSubTitle__lo_avatartitlesubtitle() {
        return tvSubTitle__lo_avatartitlesubtitle;
    }

    public ImageView getRo_person_photo_iv() {
        return ro_person_photo_iv;
    }


}
