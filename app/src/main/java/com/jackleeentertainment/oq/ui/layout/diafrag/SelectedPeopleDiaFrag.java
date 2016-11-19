package com.jackleeentertainment.oq.ui.layout.diafrag;

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
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.PeopleActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;
import com.jackleeentertainment.oq.ui.widget.LoEtMoney;
import com.jackleeentertainment.oq.ui.widget.LoIvAvatarTvTitlesIvDelete;
import com.jackleeentertainment.oq.ui.widget.NumericKeyBoardTransformationMethod;

import java.util.ArrayList;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class SelectedPeopleDiaFrag extends android.support.v4.app.DialogFragment {


    static PeopleActivity mPeopleActivity;
    ArrayList<Profile> mArlSelectedProfile = new ArrayList<>();


    LinearLayout lo;
    LinearLayout loSelectedPeople;
    TextView tvOk, tvCancel;


    public static SelectedPeopleDiaFrag newInstance(Bundle bundle, Context context) {
        SelectedPeopleDiaFrag frag = new SelectedPeopleDiaFrag();
        frag.setArguments(bundle);
        mPeopleActivity = (PeopleActivity)context;
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Material_Light_Dialog_Alert;
        setStyle(style, theme);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.diafrag_selectedpeople, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getDialog().setTitle(JM.strById(R.string.selected_oponent));

        lo = (LinearLayout) view.findViewById(R.id.lo);

        loSelectedPeople = (LinearLayout) view.findViewById(R.id.loSelectedPeople);


        tvOk = (TextView) view.findViewById(R.id
                .tvOk__ro_diafrag_okcancel);
        tvCancel = (TextView) view.findViewById(R.id
                .tvCancel__ro_diafrag_okcancel);
        // Fetch arguments from bundle and set title
//        // Show soft keyboard automatically and request focus to field
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        //ok
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send Data
                mPeopleActivity.arlSelectedProfile = mArlSelectedProfile;
dismiss();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        String strJson = getArguments().getString("arlProfileJson");
        mArlSelectedProfile = ProfileUtil.getArlProfileFromJson(strJson);

        uiList(mArlSelectedProfile);



    }

    void uiList(ArrayList<Profile> arl){

        loSelectedPeople.removeAllViews();
        loSelectedPeople.removeAllViewsInLayout();

        for (final Profile p : mArlSelectedProfile) {

            LoIvAvatarTvTitlesIvDelete lo = new LoIvAvatarTvTitlesIvDelete(getActivity());
            lo.profile = p;

            JM.glideProfileThumb(
                    p,
                    lo.ivAvatar,
                    lo.tvAvatar,
                    mPeopleActivity
            );

            lo.tvName.setText(p.full_name);
            lo.tvEmail.setText(p.email);
            lo.ivDelete.setVisibility(View.VISIBLE);
            lo.ivDelete.setImageDrawable(
                    JM.tintedDrawable(
                            R.drawable.ic_delete_white_24dp,
                            R.color.material_grey500,
                            getActivity()
                    )
            );
            lo.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mArlSelectedProfile.remove(p);
                    uiList(mArlSelectedProfile);
                }
            });
            loSelectedPeople.addView(lo);
        }
    }

}