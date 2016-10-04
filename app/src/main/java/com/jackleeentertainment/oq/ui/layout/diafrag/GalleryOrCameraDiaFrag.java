package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.ui.layout.activity.BaseActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.DiaFragAdapter;
import com.jackleeentertainment.oq.ui.layout.diafrag.list.ItemDiaFragList;

import java.util.ArrayList;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class GalleryOrCameraDiaFrag extends BaseDiaFrag {

    DiaFragAdapter diaFragAdapter;
    ArrayList<ItemDiaFragList> arl = new ArrayList<>();

    public static GalleryOrCameraDiaFrag newInstance(Bundle bundle, Context context) {
        GalleryOrCameraDiaFrag frag = new GalleryOrCameraDiaFrag();
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
        JM.G(lo_numselectedprofiles_add__lo_diafrag);
        JM.V(lv__lo_diafragwithiconlist);
        JM.G(et__lo_diafrag);
        JM.G(ro_diafrag_okcancel__lo_diafragwithiconlist);

        //Items
        ItemDiaFragList itemDiaFragListGallery = new ItemDiaFragList();
        itemDiaFragListGallery.setText(JM.strById(R.string.chooseatgallery));
        itemDiaFragListGallery.setIdDrawableIco(R.drawable.ic_photo_library_white_48dp);
        itemDiaFragListGallery.setIdDrawableBg(R.drawable.rec_radmd2_nostr_getprimary);
        itemDiaFragListGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((BaseActivity)mContext).startActivityForResult(intent,BaseActivity. RESULT_ACTION_PICK);
            }
        });

        ItemDiaFragList itemDiaFragListCamera = new ItemDiaFragList();
        itemDiaFragListCamera.setText(JM.strById(R.string.takephoto));
        itemDiaFragListGallery.setIdDrawableIco(R.drawable.ic_photo_camera_white_48dp);
        itemDiaFragListGallery.setIdDrawableBg(R.drawable.rec_radmd2_nostr_payprimary);
        itemDiaFragListGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                ((BaseActivity)mContext).startActivityForResult(cameraIntent, BaseActivity.RESULT_ACTION_IMAGE_CAPTURE);
            }
        });

        arl.add(itemDiaFragListGallery);
        arl.add(itemDiaFragListCamera);
        diaFragAdapter = new DiaFragAdapter(arl, mContext);
    }
}
