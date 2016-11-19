package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.ContactsUtil;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ChatUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.BaseActivity;
import com.jackleeentertainment.oq.ui.layout.activity.ChatActivity;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.activity.PeopleActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.DiaFragAdapter;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jacklee on 2016. 11. 14..
 */

public class UpdateContactsDiaFrag extends BaseDiaFrag {

    DiaFragAdapter diaFragAdapter;
    ArrayList<ItemDiaFragList> arl = new ArrayList<>();
    static String mActivityT = "";

    class ActivityT{
        public final static String MainActivity = "a";
        public final static String PeopleActivity = "b";
    }

    public static UpdateContactsDiaFrag newInstance(Bundle bundle, Context context, String
            activityT) {
        UpdateContactsDiaFrag frag = new UpdateContactsDiaFrag();
        frag.setArguments(bundle);
        mContext = context;
        mActivityT = activityT;
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle(JM.strById(R.string.update_contact));

        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.V(lv__lo_diafragwithiconlist);
        JM.G(et__lo_diafrag);
        JM.G(viewForMarginBelow);

        JM.G(ro_diafrag_okcancel__lo_diafragwithiconlist);

        //Items



        //item 0

        ItemDiaFragList iPhone = new ItemDiaFragList();
        iPhone.setText(JM.strById(R.string.sync_phone));

        iPhone.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_contact_phone_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iPhone.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );


        iPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mActivityT.equals(ActivityT.MainActivity)) {
                    try {
                        ((MainActivity) mContext).isToStartPhoneSync = true;
                    } catch (Exception e) {

                    }
                }

                if (mActivityT.equals(ActivityT.PeopleActivity)) {
                    try {
                        ((PeopleActivity) mContext).isToStartPhoneSync = true;
                    } catch (Exception e) {

                    }
                }

                dismiss();


            }
        });



        //item 1

        ItemDiaFragList iEmail = new ItemDiaFragList();
        iEmail.setText(JM.strById(R.string.contacts_email_long));

        iEmail.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_contact_mail_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iEmail.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );
        iEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mActivityT.equals(ActivityT.MainActivity)) {
                    try {
                        ((MainActivity) mContext).isToStartEmailSync = true;
                    } catch (Exception e) {

                    }
                }

                if (mActivityT.equals(ActivityT.PeopleActivity)) {
                    try {
                        ((PeopleActivity) mContext).isToStartEmailSync = true;
                    } catch (Exception e) {

                    }
                }

                dismiss();



            }
        });


        //item 2


        ItemDiaFragList iSNS = new ItemDiaFragList();
        iSNS.setText(JM.strById(R.string.contacts_facebook_long));

        iSNS.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_facebook,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iSNS.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );

        iSNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        arl.add(iPhone);
        arl.add(iEmail);
//        arl.add(iSNS);

        diaFragAdapter = new DiaFragAdapter(arl, mContext);
        lv__lo_diafragwithiconlist.setAdapter(diaFragAdapter);
    }
}
