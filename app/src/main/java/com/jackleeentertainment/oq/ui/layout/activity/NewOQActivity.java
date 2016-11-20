package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;

import com.jackleeentertainment.oq.object.MyOqPerson;
import com.jackleeentertainment.oq.object.MyOqPost;
import com.jackleeentertainment.oq.object.OQPostPhoto;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.progress.NewOQProgressActivity;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0Neo;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class NewOQActivity extends BaseFragmentContainFullDialogActivity {

    public class TempProAmt implements Serializable {
        public Profile profile;
        public long ammount;
    }

    public class TempSpent implements Serializable {
        public Profile profile;
        public long ammount;
    }


    public ArrayList<TempProAmt> tempArl = new ArrayList<>();
    public ArrayList<TempSpent> tempArlSpent = new ArrayList<>();

    NewOQActivity mActivity = this;
    String TAG = "NewOQActivity";
    final int REQ_PEOPLE = 99;
    final int REQ_PICK_IMAGE_FOR_FEED = 98;
    final int  REQ_PROGACTIVITY = 95;

    //temp data (1)


    public ArrayList<Uri> arlUriPhoto = new ArrayList<>();


    public String strPostText;

    //OqDo
    public String mCurrency = "KRW";
    public String mDoWhat = null; //PAY/GET




    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String data = intent.getStringExtra("data");


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoWhat = (getIntent().getStringExtra("mDoWhat"));
        Bundle bundle = new Bundle();
        bundle.putString("mDoWhat", mDoWhat);
        showFrag(NewOQFrag0Neo.newInstance(bundle), R.id.fr_content);

        //LBR
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver,
                new IntentFilter(LBR.IntentFilterT.NewOQActivity));
    }


    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        fab.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();
        //toolbar title
        if (mDoWhat != null && mDoWhat.equals(OQT.DoWhat.GET)) {
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_get));
        } else if (mDoWhat != null && mDoWhat.equals(OQT.DoWhat.PAY)) {
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_pay));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
    }

    public void startActivityForResultPeopleActivity() {
        Intent i = new Intent(this, PeopleActivity.class);

        String beforeProfiles = getIntent().getStringExtra("beforeProfiles");
        ArrayList<Profile> arlPro = new ArrayList<>();

        for (TempProAmt t : tempArl) {
            arlPro.add(t.profile);
        }
        i.putExtra("beforeProfiles", new Gson().toJson(arlPro));
        startActivityForResult(i, REQ_PEOPLE);
    }


    public void startActivityForResultPhotoGalleryForFeed() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.attach_photo)),
                REQ_PICK_IMAGE_FOR_FEED);
    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(TAG, "onActivityResult()");
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_PEOPLE) {

                //get it
                String str = intent.getStringExtra("result");
                ArrayList<Profile> arlPro = ProfileUtil.getArlProfileFromJson(intent
                        .getStringExtra("result"));
                for (Profile p : arlPro) {

                    boolean isExists = false;

                    for (TempProAmt tempProAmt : tempArl) {

                        if (tempProAmt.profile.equals(p)) {
                            isExists = true;
                        }
                        //Frag0:ui
                    }

                    if (!isExists) {
                        TempProAmt tempProAmt = new TempProAmt();
                        tempProAmt.profile = p;
                        tempProAmt.ammount = 0;
                        tempArl.add(tempProAmt);
                    }

                }


                Log.d(TAG, "intent.getStringExtra(result) : " + str);
                Log.d(TAG, "arlOppoProfile.size() " + J.st(arlPro.size()));

            }


            if (requestCode == REQ_PICK_IMAGE_FOR_FEED) {

                if (intent != null
                        && intent.getData() != null) {
                    Log.d(TAG, "data available");

                    if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                        //Intent.EXTRA_ALLOW_MULTIPLE
                        Log.d(TAG, "VERSION.SDK_INT >= 18 " + intent.getData().toString());
                        arlUriPhoto.add(intent.getData());

                    } else {
                        Log.d(TAG, "VERSION.SDK_INT < 18 " + intent.getData().toString());
                        arlUriPhoto.add(intent.getData());
                    }
                }
            }

            if (requestCode == REQ_PROGACTIVITY) {
                 finish();
            }
        }

    }




    public void uiLsnerFrag0() {
        ivClose.setImageDrawable(JM.drawableById(R.drawable
                .ic_close_white_48dp));
        View.OnClickListener ocl = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        };
        ivClose.setOnClickListener(ocl);
        roClose.setOnClickListener(ocl);
    }

    public void uiLsnerFrag1(final Bundle bundleFromFrag0) {
        ivClose.setImageDrawable(JM.drawableById(R.drawable
                .ic_arrow_back_white_48dp));

        View.OnClickListener onClickListenerBackToFrag0 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                backToFragment(NewOQFrag0Neo.newInstance
                                (bundleFromFrag0),
                        R.id.fr_content);
            }
        };
        ivClose.setOnClickListener(onClickListenerBackToFrag0);
        roClose.setOnClickListener(onClickListenerBackToFrag0);

    }


    public void startNewOQProgActivity(){
        Intent intent = new Intent(this, NewOQProgressActivity.class);
        intent.putExtra("tempArl", tempArl);
        intent.putExtra("tempArlSpent", tempArlSpent);
        intent.putExtra("arlUriPhoto", arlUriPhoto);
        intent.putExtra("strPostText", strPostText);
        intent.putExtra("mCurrency", mCurrency);
        intent.putExtra("mDoWhat", mDoWhat);

        startActivityForResult(intent, REQ_PROGACTIVITY);




    }
}
