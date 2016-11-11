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
import com.jackleeentertainment.oq.object.util.ChatUtil;
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
 * Created by Jacklee on 2016. 11. 6..
 */

public class TransactOrChatDiaFrag extends BaseDiaFrag {

    DiaFragAdapter diaFragAdapter;
    ArrayList<ItemDiaFragList> arl = new ArrayList<>();
    static Profile profile;

    public static TransactOrChatDiaFrag newInstance(Bundle bundle, Context context) {
        TransactOrChatDiaFrag frag = new TransactOrChatDiaFrag();
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
        getDialog().setTitle(profile.getFull_name() + "님에 대하여");


        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.V(lv__lo_diafragwithiconlist);
        JM.G(et__lo_diafrag);
        JM.G(viewForMarginBelow);

        JM.G(ro_diafrag_okcancel__lo_diafragwithiconlist);

        //Items
        ItemDiaFragList iTranGet = new ItemDiaFragList();
        iTranGet.setText(JM.strById(R.string.transaction_i_get));

        iTranGet.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_create_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iTranGet.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );


        iTranGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tranIntent = new Intent(getActivity(), NewOQActivity.class);
                tranIntent.putExtra("Profile", profile);
                OqDo oqdo = new OqDo();
                tranIntent.putExtra("OqDo", oqdo);
                ((BaseActivity) mContext).startActivity(tranIntent);
            }
        });

        ItemDiaFragList iTranPay = new ItemDiaFragList();
        iTranPay.setText(JM.strById(R.string.transaction_i_pay));


        iTranPay.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_create_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iTranPay.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );
        iTranPay.setOnClickListener(new View.OnClickListener() {
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
                String rid = ChatUtil.createRidWith2Ids(App.getUid(getActivity()), profile.getUid());

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


        arl.add(iTranGet);
        arl.add(iTranPay);
        arl.add(iChat);

        diaFragAdapter = new DiaFragAdapter(arl, mContext);
        lv__lo_diafragwithiconlist.setAdapter(diaFragAdapter);
    }
}
