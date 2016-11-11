package com.jackleeentertainment.oq.ui.layout.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.jackleeentertainment.oq.ui.layout.diafrag.TransactOrChatDiaFrag;

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

        } else if (diaFragT.equals(DiaFragT.TransactOrChat)){
            TransactOrChatDiaFrag frag =
                    TransactOrChatDiaFrag
                            .newInstance(
                                    bundle, this);
            frag.show(fm, "TransactOrChatDiaFrag");
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
        alertDialog.show();
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(JM.colorById(R.color.colorPrimary));
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
        showAlertDialogWithOkCancel(JM.strById(messageStrId), dialogOcl);

    }


    public void showAlertDialogWithOkCancel(
            String messageStr,
            final DialogInterface
                    .OnClickListener dialogOcl

    ) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(messageStr);
        builder.setNegativeButton(JM.strById(R.string.cancel_korean), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(JM.strById(R.string.ok_korean), dialogOcl);
        AlertDialog dialog = builder.create();


        if(dialog == null)
            return;
        dialog.show();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(JM.colorById(R.color.text_black_54));
        Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(JM.colorById(R.color.colorPrimary));

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }




}
