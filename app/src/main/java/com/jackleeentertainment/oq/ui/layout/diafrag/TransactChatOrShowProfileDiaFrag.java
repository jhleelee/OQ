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
import com.jackleeentertainment.oq.object.OqItem;
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


        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.V(lv__lo_diafragwithiconlist);
        JM.G(et__lo_diafrag);
        JM.G(ro_diafrag_okcancel__lo_diafragwithiconlist);

        //Items
        ItemDiaFragList itemDiaFragListTransGet = new ItemDiaFragList();
        itemDiaFragListTransGet.setText(JM.strById(R.string.transaction_i_get));

        itemDiaFragListTransGet.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_play_for_work_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        itemDiaFragListTransGet.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );


        itemDiaFragListTransGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tranIntent = new Intent(getActivity(), NewOQActivity.class);
                tranIntent.putExtra("Profile", profile);
                OqItem oqItem = new OqItem();
                tranIntent.putExtra("OQItem", oqItem);
                ((BaseActivity) mContext).startActivity(tranIntent);
            }
        });

        ItemDiaFragList itemDiaFragListTransPay = new ItemDiaFragList();
        itemDiaFragListTransPay.setText(JM.strById(R.string.transaction_i_pay));


        itemDiaFragListTransGet.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_play_for_work_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        itemDiaFragListTransGet.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );
        itemDiaFragListTransPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tranIntent = new Intent(getActivity(), NewOQActivity.class);
                tranIntent.putExtra("Profile", profile);
                OqItem oqItem = new OqItem();
                tranIntent.putExtra("OQItem", oqItem);
                ((BaseActivity) mContext).startActivity(tranIntent);
            }
        });


        ItemDiaFragList itemDiaFragListChat = new ItemDiaFragList();
        itemDiaFragListChat.setText(JM.strById(R.string.do_chat));

        itemDiaFragListTransGet.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_chat_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        itemDiaFragListTransGet.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );

        itemDiaFragListChat.setOnClickListener(new View.OnClickListener() {
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


        ItemDiaFragList itemDiaFragListProfile = new ItemDiaFragList();
        itemDiaFragListProfile.setText(JM.strById(R.string.show_profile));

        itemDiaFragListTransGet.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_person_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        itemDiaFragListTransGet.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );


        itemDiaFragListProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
                profileIntent.putExtra("Profile", profile);
                ((BaseActivity) mContext).startActivity(profileIntent);
            }
        });

        arl.add(itemDiaFragListTransGet);
        arl.add(itemDiaFragListTransPay);
        arl.add(itemDiaFragListChat);
        arl.add(itemDiaFragListProfile);

        diaFragAdapter = new DiaFragAdapter(arl, mContext);
        lv__lo_diafragwithiconlist.setAdapter(diaFragAdapter);
    }
}
