package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;
import com.jackleeentertainment.oq.ui.widget.ListViewUtil;
import com.jackleeentertainment.oq.ui.widget.Lo2AvaSmall;
import com.jackleeentertainment.oq.ui.widget.LoEtMoney;
import com.jackleeentertainment.oq.ui.widget.NumericKeyBoardTransformationMethod;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 11. 20..
 */

public class PostOqDoListDiaFrag extends android.support.v4.app.DialogFragment {


    static Context mContext;
    LinearLayout lo, loOqDos;
    RelativeLayout roAvatar;
    TextView tvName, tvDate, tvDeed;
    ScrollView svOqDos;
    TextView tvAva;
    ImageView ivAva;

    public static PostOqDoListDiaFrag newInstance(Bundle bundle, Context context) {
        PostOqDoListDiaFrag frag = new PostOqDoListDiaFrag();
        frag.setArguments(bundle);
        mContext = context;
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Material_Light_Dialog_Alert;
        setStyle(style, theme);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.diafrag_postoqdolist, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //header
        Profile hProfile  = (Profile)getArguments().getSerializable("hProfile");
        String hDeed  = getArguments().getString("hDeed");
        String hTs  = getArguments().getString("hTs");






        //items

        ArrayList<OqDo> list  = ( ArrayList<OqDo>)getArguments().getSerializable("oqDos");

        //(1) classify by Person
        ArrayList<ArrayList<OqDo>> arlArlOqDoPerPeople =
                OqDoUtil
                        .getArlArlOqDoPerPeople(list, getActivity());



        lo = (LinearLayout) view.findViewById(R.id.lo);
        roAvatar = (RelativeLayout) view.findViewById(R.id.roAvatar);
        tvAva = (TextView) view.findViewById(R.id.tvAva);
        ivAva = (ImageView) view.findViewById(R.id.ivAva);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDeed = (TextView) view.findViewById(R.id.tvDeed);
        svOqDos = (ScrollView) view.findViewById(R.id.svOqDos);
        loOqDos = (LinearLayout) view.findViewById(R.id.loOqDos);

        JM.glideProfileThumb(
                hProfile,
                ivAva,
                tvAva,
                (Activity)mContext
        );

        tvName.setText(hProfile.full_name);
        tvDate.setText(hTs);
        tvDeed.setText(hDeed);



        for (ArrayList<OqDo> arlOqDoPerPeople :
                arlArlOqDoPerPeople) { //For
            // each Person

            OqDoUtil.sortList(arlOqDoPerPeople);

            OqDo mainOqDo = OqDoUtil.getOqDoOidTheSameReferOid
                    (arlOqDoPerPeople);






            Lo2AvaSmall lo2AvaSmall = new Lo2AvaSmall(getActivity());
            JM.glideProfileThumb(
                    mainOqDo.profilea,
                    lo2AvaSmall.ivAvaLeft,
                    lo2AvaSmall.tvAvaLeft,
                    (Activity)mContext
            );

            JM.glideProfileThumb(
                    mainOqDo.profileb,
                    lo2AvaSmall.ivAvaRight,
                    lo2AvaSmall.tvAvaRight,
                    (Activity)mContext
            );

            lo2AvaSmall.tvName.setText(
                    mainOqDo.profilea.full_name
                            + "•" +
                            mainOqDo.profileb.full_name

            );


            lo2AvaSmall.tvAmmount.setText(
                    J.st1000won(OqDoUtil.getSumOqDoAmmounts
                            (arlOqDoPerPeople))
            );
            lo2AvaSmall.tvDeed.setText(OqDoUtil
                    .getOqDoListStr(arlOqDoPerPeople));


            loOqDos.addView(lo2AvaSmall);

//            int loheight = loOqDos.getHeight();
//            if (JM.dpFromPx(loheight)<240){
//                svOqDos.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        loheight));
//            }

        }


    }
}