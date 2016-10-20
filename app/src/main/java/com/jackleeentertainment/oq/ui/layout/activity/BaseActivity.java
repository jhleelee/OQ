package com.jackleeentertainment.oq.ui.layout.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.ui.layout.diafrag.ChatroomAttrDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.layout.diafrag.GalleryOrCameraDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.OneLineInputDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.SelectedFriendsAndMoreDiaFrag;
import com.soundcloud.android.crop.Crop;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class BaseActivity extends AppCompatActivity {

    public final static int RESULT_ACTION_PICK = 90;
    public final static int RESULT_ACTION_IMAGE_CAPTURE = 91;
    Uri croppedUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULT_OK) {

            switch (requestCode) {
                case RESULT_ACTION_PICK:
                    if (null != data) {
                        Uri uri = data.getData();
                        Crop.of(uri, croppedUri).asSquare().start(this);
                    }
                    break;

                case RESULT_ACTION_IMAGE_CAPTURE:
                    if (null != data) {
                        Uri uri = data.getData();
                        Crop.of(uri, croppedUri).asSquare().start(this);
                    }
                    break;

                case Crop.REQUEST_CROP:
                    if (null != data) {
                        Uri uri = data.getData();
                        LBR.send("Crop.REQUEST_CROP", uri);
                    }
                    break;


                default:
                    break;
            }
        }
    }


    public void showDialogFragment(Bundle bundle) {
        String diaFragT = bundle.getString("diaFragT");
        FragmentManager fm = getSupportFragmentManager();

        if (diaFragT.equals(DiaFragT.ChatroomAttr_atChatRoomList)) {
            ChatroomAttrDiaFrag chatroomAttrDiaFrag = ChatroomAttrDiaFrag.newInstance(
                    bundle, this);
            chatroomAttrDiaFrag.show(fm, "chatroomAttrDiaFrag");
        } else if (diaFragT.equals(DiaFragT.SelectedFriendsAndMore)) {
            SelectedFriendsAndMoreDiaFrag selectedFriendsAndMoreDiaFrag = SelectedFriendsAndMoreDiaFrag
                    .newInstance(
                            bundle, this);
            selectedFriendsAndMoreDiaFrag.show(fm, "selectedFriendsAndMoreDiaFrag");
        } else if (diaFragT.equals(DiaFragT.OneLineInput)) {
            OneLineInputDiaFrag oneLineInputDiaFrag = OneLineInputDiaFrag
                    .newInstance(
                            bundle, this);
            oneLineInputDiaFrag.show(fm, "oneLineInputDiaFrag");
        } else if (diaFragT.equals(DiaFragT.GalleryOrCamera)) {
            GalleryOrCameraDiaFrag galleryOrCameraDiaFrag = GalleryOrCameraDiaFrag
                    .newInstance(
                            bundle, this);
            galleryOrCameraDiaFrag.show(fm, "galleryOrCameraDiaFrag");
        }

    }


    /********************************
     * UI - Fragment
     *******************************/

    public void goToFragment(Fragment fragment, int layoutId) {
        //Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enterfromright, R.anim.exittoleft, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(layoutId, fragment, fragment.getClass().getSimpleName());
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void backToFragment(Fragment fragment, int layoutId) {
        //Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enterfromleft, R.anim.exittoright, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(layoutId, fragment, fragment.getClass().getSimpleName());
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showFrag(Fragment frag, int layoutId) {

        android.support.v4.app.FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(layoutId, frag, frag.getClass().getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
