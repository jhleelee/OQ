package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.ui.layout.activity.MainActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.DiaFragAdapter;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 25..
 */

public class MyProfileBackgroundPhotoDiaFrag extends BaseDiaFrag {

    DiaFragAdapter diaFragAdapter;
    ArrayList<ItemDiaFragList> arl = new ArrayList<>();

    public static MyProfileBackgroundPhotoDiaFrag newInstance(Bundle bundle, Context context) {
        MyProfileBackgroundPhotoDiaFrag frag = new MyProfileBackgroundPhotoDiaFrag();
        frag.setArguments(bundle);
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

        getDialog().setTitle(JM.strById(R.string.change_photo));

        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.V(lv__lo_diafragwithiconlist);
        JM.G(et__lo_diafrag);
        JM.G(ro_diafrag_okcancel__lo_diafragwithiconlist);

        //Items
        ItemDiaFragList iProfilePhoto = new ItemDiaFragList();
        iProfilePhoto.setText(JM.strById(R.string.change_profile_photo));
        iProfilePhoto.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_person_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iProfilePhoto.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );

        iProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).startActivityForResultPhotoGalleryToPROFILECHANGE();
                dismiss();

            }
        });

        ItemDiaFragList iBg = new ItemDiaFragList();
        iBg.setText(JM.strById(R.string.change_background_photo));
        iBg.setDrawableIco(
                JM.tintedDrawable(
                        R.drawable.ic_photo_library_white_48dp,
                        R.color.colorPrimary,
                        getActivity()
                )
        );
        iBg.setDrawableBg(
                JM.drawableById(
                        R.drawable.btn_transparent
                )
        );
        iBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        arl.add(iProfilePhoto);
        arl.add(iBg);

        diaFragAdapter = new DiaFragAdapter(arl, mContext);
        lv__lo_diafragwithiconlist.setAdapter(diaFragAdapter);
    }
}
