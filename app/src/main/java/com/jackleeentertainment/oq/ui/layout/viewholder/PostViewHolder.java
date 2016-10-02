package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.ui.widget.FavoriteBoxJack;


/**
 * Created by Jacklee on 2016. 9. 16..
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    View mView;

    RelativeLayout ro_media, ro_person_photo_48dip__lo_avatartitlesubtitle;
    LinearLayout lo_feed_alarm, lo_avatartitlesubtitle, lo_feed_action, lo_feed_supportingtext,
            lo_liked__lo_feed_action, lo_commented__lo_feed_action,
            lo_shared__lo_feed_action;
    ImageButton ibAlarm__lo_feed_alarm, ibComment__lo_feed_action, ibShare__lo_feed_action;
    ImageView ivBackGround__ro_media, ivIco__lo_liked, ivIco__lo_commented,ivIco__lo_shared;
    View vBackGround__ro_media, v_scrim_gradient_bottom__ro_media,
            v_scrim_solid__ro_media;
    TextView tvTitle__lo_avatartitlesubtitle,
            tvTitle__lo_feed_alarm, tvSubTitle__lo_feed_alarm, tv__lo_feed_supportingtext,
            tvLikedNum__lo_liked, tvCommentNum__lo_commented,tvSharedNum__lo_shared;
    TextView tvSubTitle__lo_avatartitlesubtitle;
    VideoView vvBackGround__ro_media;
    FavoriteBoxJack favoriteboxjack__lo_feed_action;

    public PostViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        lo_avatartitlesubtitle = (LinearLayout) mView.findViewById(R.id.lo_avatartitlesubtitle);
        ro_person_photo_48dip__lo_avatartitlesubtitle =
                (RelativeLayout) lo_avatartitlesubtitle.findViewById(R.id.ro_person_photo_48dip__lo_avatartitlesubtitle);
        tvTitle__lo_avatartitlesubtitle =
                (TextView) lo_avatartitlesubtitle.findViewById(R.id.tvTitle__lo_avatartitlesubtitle);
        tvSubTitle__lo_avatartitlesubtitle =
                (TextView) lo_avatartitlesubtitle.findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle);

        ro_media = (RelativeLayout) mView.findViewById(R.id.ro_media);
        vBackGround__ro_media = (View) ro_media.findViewById(R.id.vBackGround__ro_media);
        vvBackGround__ro_media = (VideoView) ro_media.findViewById(R.id.vvBackGround__ro_media);
        ivBackGround__ro_media = (ImageView) ro_media.findViewById(R.id.ivBackGround__ro_media);
        v_scrim_gradient_bottom__ro_media = (View) ro_media.findViewById(R.id.v_scrim_gradient_bottom__ro_media);
        v_scrim_solid__ro_media = (View) ro_media.findViewById(R.id.v_scrim_solid__ro_media);

        lo_feed_alarm = (LinearLayout) mView.findViewById(R.id.lo_feed_alarm);
        tvTitle__lo_feed_alarm = (TextView) lo_feed_alarm.findViewById(R.id.tvTitle__lo_feed_alarm);
        tvSubTitle__lo_feed_alarm = (TextView) lo_feed_alarm.findViewById(R.id.tvSubTitle__lo_feed_alarm);
        ibAlarm__lo_feed_alarm = (ImageButton) lo_feed_alarm.findViewById(R.id.ibAlarm__lo_feed_alarm);

        lo_feed_supportingtext = (LinearLayout) mView.findViewById(R.id.lo_feed_supportingtext);
        tv__lo_feed_supportingtext = (TextView) lo_feed_alarm.findViewById(R.id.tv__lo_feed_supportingtext);

        lo_feed_action = (LinearLayout) mView.findViewById(R.id.lo_feed_action);
        lo_liked__lo_feed_action = (LinearLayout) lo_feed_action.findViewById(R.id.lo_liked__lo_feed_action);
        lo_commented__lo_feed_action = (LinearLayout) lo_feed_action.findViewById(R.id.lo_commented__lo_feed_action);
        lo_shared__lo_feed_action = (LinearLayout) lo_feed_action.findViewById(R.id.lo_shared__lo_feed_action);
        ivIco__lo_liked = (ImageView) lo_liked__lo_feed_action.findViewById(R.id.ivIco__lo_liked);
        tvLikedNum__lo_liked = (TextView) lo_liked__lo_feed_action.findViewById(R.id.tvLikedNum__lo_liked);
        ivIco__lo_commented = (ImageView) lo_commented__lo_feed_action.findViewById(R.id.ivIco__lo_commented);
        tvCommentNum__lo_commented = (TextView) lo_commented__lo_feed_action.findViewById(R.id.tvCommentNum__lo_commented);
        ivIco__lo_shared = (ImageView) lo_shared__lo_feed_action.findViewById(R.id.ivIco__lo_shared);
        tvSharedNum__lo_shared= (TextView) lo_shared__lo_feed_action.findViewById(R.id.tvSharedNum__lo_shared);

        favoriteboxjack__lo_feed_action = (FavoriteBoxJack) mView.findViewById(R.id.favoriteboxjack__lo_feed_action);
        ibComment__lo_feed_action = (ImageButton) mView.findViewById(R.id.ibComment__lo_feed_action);
        ibShare__lo_feed_action = (ImageButton) mView.findViewById(R.id.ibShare__lo_feed_action);


    }
       /*
        Poster (Profile)
         */

    public void setPosterPhoto(String posterUid) {
        //Glide
    }

       /*
        Poster (Profile) & PostBody
         */


    public void setPosterNameAndDeed(String posterName, String deed) {
        //tvTitle__lo_avatartitlesubtitle.setText();
    }

         /*
        PostBody
         */

    public void setPostBackgroundMedia(Post post) {

    }

    public void setPostSupportingText(String supportingText) {
        tv__lo_feed_supportingtext.setText(supportingText);
    }

    public void setLikeNum(int likeNum) {
        tvLikedNum__lo_liked.setText(String.valueOf(likeNum));
    }

    public void setCommentNum(int commentNum) {
        tvCommentNum__lo_commented.setText(String.valueOf(commentNum));
    }

    public void setSharedNum(int sharedNum) {
        tvSharedNum__lo_shared.setText(String.valueOf(sharedNum));
    }
    public void setFavorite() {
        //
    }

         /*
        PostBody re Alarm
         */

    public void setAlarmTitleAndContent(Post post) {
        //tvTitle__lo_avatartitlesubtitle.setText(title);
        //tvSubTitle__lo_avatartitlesubtitle;
    }



}
