package com.jackleeentertainment.oq.ui.layout.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;

/**
 * Created by jaehaklee on 2016. 10. 4..
 */

public class TwoAvatarsWithRelationDtlVHolder extends RecyclerView.ViewHolder {
    View mView;
    LinearLayout lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail;
    ListView lv__lo_twoavatars_relation_names_explain_date_detail;
    LinearLayout lo_twoavatars_relation__lo_twoavatars_relation_names_explain_date;
    TextView tvTitle__lo_twoavatars_relation_names_explain_date;
    TextView tvDate__lo_twoavatars_relation_names_explain_date;
    TextView tvSubTitle__lo_twoavatars_relation_names_explain_date;
    ImageView ivMore__lo_twoavatars_relation_names_explain_date;

    public TwoAvatarsWithRelationDtlVHolder(View itemView) {
        super(itemView);
        mView = itemView;
        lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail
                =
                (LinearLayout) mView
                        .findViewById(R.id.lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail);
        //lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail

        lo_twoavatars_relation__lo_twoavatars_relation_names_explain_date =
                (LinearLayout) lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail
                        .findViewById(R.id.lo_twoavatars_relation__lo_twoavatars_relation_names_explain_date);

        tvTitle__lo_twoavatars_relation_names_explain_date =
                (TextView) lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail
                        .findViewById(R.id.tvTitle__lo_twoavatars_relation_names_explain_date);

        tvDate__lo_twoavatars_relation_names_explain_date =
                (TextView) lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail
                        .findViewById(R.id.tvDate__lo_twoavatars_relation_names_explain_date);

        tvSubTitle__lo_twoavatars_relation_names_explain_date =
                (TextView) lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail
                        .findViewById(R.id.tvSubTitle__lo_twoavatars_relation_names_explain_date);

        ivMore__lo_twoavatars_relation_names_explain_date =
                (ImageView) lo_twoavatars_relation_names_explain_date__lo_twoavatars_relation_names_explain_date_detail
                        .findViewById(R.id.ivMore__lo_twoavatars_relation_names_explain_date);
        lv__lo_twoavatars_relation_names_explain_date_detail
                =
                (ListView) mView
                        .findViewById(R.id.lv__lo_twoavatars_relation_names_explain_date_detail);
    }

         /*
        OpponentProfile
         */

}
