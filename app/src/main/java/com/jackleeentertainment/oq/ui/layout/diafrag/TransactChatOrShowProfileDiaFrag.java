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
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.BaseActivity;
import com.jackleeentertainment.oq.ui.layout.activity.ChatActivity;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.DiaFragAdapter;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jacklee on 2016. 10. 24..
 */

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class TransactChatOrShowProfileDiaFrag extends BaseDiaFrag {

    DiaFragAdapter diaFragAdapter;
    ArrayList<ItemDiaFragList> arl = new ArrayList<>();
    static Profile profile;

    public static TransactChatOrShowProfileDiaFrag newInstance(Bundle bundle, Context context) {
        TransactChatOrShowProfileDiaFrag frag = new TransactChatOrShowProfileDiaFrag();
        frag.setArguments(bundle);
        profile = (Profile) bundle.getSerializable("Profile");
        mContext = context;
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

        JM.G(viewForMarginBelow);

        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.V(lv__lo_diafragwithiconlist);
        JM.G(et__lo_diafrag);
        JM.G(ro_diafrag_okcancel__lo_diafragwithiconlist);

        //Items
        ItemDiaFragList iGet = new ItemDiaFragList();
        iGet.setText(JM.strById(R.string.transaction_i_get));

        iGet.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_play_for_work_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iGet.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );


        iGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tranIntent = new Intent(getActivity(), NewOQActivity.class);
                tranIntent.putExtra("Profile", profile);
                OqDo oqdo = new OqDo();
                tranIntent.putExtra("OqDo", oqdo);
                ((BaseActivity) mContext).startActivity(tranIntent);
            }
        });

        ItemDiaFragList iPay = new ItemDiaFragList();
        iPay.setText(JM.strById(R.string.transaction_i_pay));


        iPay.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_play_for_work_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iPay.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );
        iPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tranIntent = new Intent(getActivity(), NewOQActivity.class);
                tranIntent.putExtra("Profile", profile);
                OqDo oqdo = new OqDo();
                tranIntent.putExtra("OqDo", oqdo);
                ((BaseActivity) mContext).startActivity(tranIntent);
            }
        });


        ItemDiaFragList iChat = new ItemDiaFragList();
        iChat.setText(JM.strById(R.string.do_chat));

        iChat.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_chat_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iChat.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );

        iChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //no need to check if rid exists because its one 2 one anyway
                ArrayList<String> arl = new ArrayList<String>();
                arl.add(App.getUid(getActivity()));
                arl.add(profile.getUid());
                Collections.sort(arl);
                String rid = arl.get(0) + "__" + arl.get(1);

                //create arl of jsons - its just one here
                Profile myProfile = ProfileUtil.getMyProfileWithUidNameEmail(getActivity());
                ArrayList<Profile> arlProfiles = new ArrayList<Profile>();
                arlProfiles.add(myProfile);
                arlProfiles.add(profile);
                ArrayList<String> arlJsonProfiles = ProfileUtil.getArlJsonProfiles
                        (arlProfiles);

                Intent chatIntent = new Intent(getActivity(), ChatActivity.class);
                chatIntent.putExtra("rid", rid);
                chatIntent.putStringArrayListExtra("arlJsonProfilesInChat", arlJsonProfiles);
                ((BaseActivity) mContext).startActivity(chatIntent);
            }
        });


        ItemDiaFragList iProfile = new ItemDiaFragList();
        iProfile.setText(JM.strById(R.string.show_profile));

        iProfile.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_person_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iProfile.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );


        iProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
                profileIntent.putExtra("Profile", profile);
                ((BaseActivity) mContext).startActivity(profileIntent);
            }
        });

        arl.add(iGet);
        arl.add(iPay);
        arl.add(iChat);
        arl.add(iProfile);

        diaFragAdapter = new DiaFragAdapter(arl, mContext);
        lv__lo_diafragwithiconlist.setAdapter(diaFragAdapter);
    }
}
