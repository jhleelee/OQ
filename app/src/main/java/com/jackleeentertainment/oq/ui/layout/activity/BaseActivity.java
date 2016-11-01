package com.jackleeentertainment.oq.ui.layout.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Receipt;
import com.jackleeentertainment.oq.ui.layout.diafrag.ChatroomAttrDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.layout.diafrag.EasyInputDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.GalleryOrCameraDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.MyProfileBackgroundPhotoDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.MySpentItemDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.OneLineInputDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.ReceiptBreakdownDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.SelectedFriendsAndMoreDiaFrag;
import com.jackleeentertainment.oq.ui.layout.diafrag.TransactChatOrShowProfileDiaFrag;
import com.soundcloud.android.crop.Crop;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class BaseActivity extends AppCompatActivity {

    public final static int RESULT_ACTION_PICK = 90;
    public final static int RESULT_ACTION_IMAGE_CAPTURE = 91;
    Uri croppedUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUILayoutOnCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUIDataOnResume();
        initOnClickListenerOnResume();
    }


    void initUILayoutOnCreate() {

    }


    void initUIDataOnResume() {

    }

    void initOnClickListenerOnResume() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {

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
            galleryOrCameraDiaFrag.show(fm, "ReceiptBreakdown");
        } else if (diaFragT.equals(DiaFragT.ReceiptBreakdown)) {
            Receipt receipt = (Receipt) bundle.getSerializable("Receipt");
            ReceiptBreakdownDiaFrag receiptBreakdownDiaFrag = ReceiptBreakdownDiaFrag
                    .newInstance(
                            bundle, this);
            receiptBreakdownDiaFrag.show(fm, "ReceiptBreakdownDiaFrag");

        } else if (diaFragT.equals(DiaFragT.TransactChatOrShowProfile)) {
            TransactChatOrShowProfileDiaFrag frag =
                    TransactChatOrShowProfileDiaFrag
                            .newInstance(
                                    bundle, this);
            frag.show(fm, "TransactChatOrShowProfileDiaFrag");

        } else if (diaFragT.equals(DiaFragT.MyProfileBackgroundPhoto)) {
            MyProfileBackgroundPhotoDiaFrag frag =
                    MyProfileBackgroundPhotoDiaFrag
                            .newInstance(
                                    bundle, this);
            frag.show(fm, "MyProfileBackgroundPhotoDiaFrag");

        } else if (diaFragT.equals(DiaFragT.MySpentItem)) {
            MySpentItemDiaFrag frag =
                    MySpentItemDiaFrag
                            .newInstance(
                                    bundle, this);
            frag.show(fm, "MySpentItemDiaFrag");

        } else if (diaFragT.equals(DiaFragT.EasyInput)) {


            EasyInputDiaFrag frag =
                    EasyInputDiaFrag
                            .newInstance(
                                    bundle, this);
            frag.show(fm, "EasyInputDiaFrag");

        }


    }


    public void showAlertDialogWithOnlyOk(
            int messageStrId
    ) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(JM.strById(messageStrId));

        alertDialogBuilder.setPositiveButton(JM.strById(R.string.ok_korean),
                new
                        DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(JM.colorById(R.color.colorPrimary));
        alertDialog.show();
    }


    public void showAlertDialogWithOkCancel(
            int messageStrId,
            final AsyncTask asyncTask

    ) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(JM.strById(messageStrId));

        alertDialogBuilder.setPositiveButton(
                JM.strById(R.string.ok_korean),
                new
                        DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                asyncTask.execute();

                            }
                        });

        alertDialogBuilder.setNegativeButton(JM.strById(R.string.cancel_korean),
                new
                        DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
//        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//        nbutton.setTextColor(JM.colorById(R.color.text_black_54));
//        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        pbutton.setTextColor(JM.colorById(R.color.colorPrimary));
        alertDialog.show();
    }


    public void showAlertDialogWithOkCancel(
            int messageStrId,
            final DialogInterface
                    .OnClickListener dialogOcl

    ) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(JM.strById(messageStrId));

        alertDialogBuilder.setPositiveButton(
                JM.strById(R.string.ok_korean),
                dialogOcl);

        alertDialogBuilder.setNegativeButton(JM.strById(R.string.cancel_korean),
                new
                        DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });


        AlertDialog alertDialog = alertDialogBuilder.create();

        if (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            Button nbutton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            nbutton.setTextColor(JM.colorById(R.color.text_black_54));
        }
        if (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            Button pbutton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            pbutton.setTextColor(JM.colorById(R.color.colorPrimary));
        }
        alertDialog.show();
    }


    public void showAlertDialogWithOkCancel(
            String messageStr,
            final DialogInterface
                    .OnClickListener dialogOcl

    ) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(messageStr);

        alertDialogBuilder.setPositiveButton(
                JM.strById(R.string.ok_korean),
                dialogOcl)
                .setNegativeButton(JM.strById(R.string.cancel_korean),
                        new DialogInterface
                                .OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        if (alertDialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            Button pbutton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            pbutton.setTextColor(JM.colorById(R.color.colorPrimary));
        }
        alertDialog.show();
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
