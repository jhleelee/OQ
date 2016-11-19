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

    public class TempProAmt implements Serializable{
        public Profile profile;
        public long ammount;
    }

    public class TempSpent implements Serializable{
        public Profile profile;
        public long ammount;
    }


    public ArrayList<TempProAmt> tempArl = new ArrayList<>();
    public ArrayList<TempSpent> tempArlSpent = new ArrayList<>();

    NewOQActivity mActivity = this;
    String TAG = "NewOQActivity";
    final int REQ_PEOPLE = 99;
    final int REQ_PICK_IMAGE_FOR_FEED = 98;


    //temp data (1)


    public ArrayList<Uri> arlUriPhoto = new ArrayList<>();


    public String strPostText;
    public Profile profileMe;
    StorageReference mTempStorageRef;  //mTempStorageRef was previously used to transfer data.

    //OqDo
    public String mCurrency = "KRW";
    public String mDoWhat = null; //PAY/GET



    //Counters
    int uploadPhotoCounter = 0;
    public int oqDosCounter = 0;
    ArrayList<String> arlTask = new ArrayList<>();

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


    int upCounter = 0;

    //varvar
    public ArrayList<OqDo> getOqDosFromTempHolder(long ts) {

        ArrayList<OqDo> oqDos = new ArrayList<>();

        if (mDoWhat.equals(OQT.DoWhat.GET)) {

            for (TempProAmt t : tempArl) {

                final String oid = App.fbaseDbRef
                        .child("push").push().getKey();
                OqDo oqDo = new OqDo();
                oqDo.setOid(oid);
                oqDo.setReferoid(oid);
                oqDo.setUidab(profileMe.uid + ",," + t.profile.uid);
                oqDo.profilea = profileMe;
                oqDo.profileb = t.profile;
                oqDo.ammount = t.ammount;
                oqDo.currency = mCurrency;
                oqDo.setTs(ts);
                oqDo.setOqwhen(OQT.DoWhen.FUTURE);
                oqDo.setOqwhat(OQT.DoWhat.GET);
                oqDos.add(oqDo);

                final String oid1 = App.fbaseDbRef
                        .child("push").push().getKey();
                OqDo oqDo1 = new OqDo();
                oqDo1.setOid(oid1);
                oqDo1.setReferoid(oid);
                oqDo1.setUidab(t.profile.uid + ",," + profileMe.uid);
                oqDo1.profilea = t.profile;
                oqDo1.profileb = profileMe;
                oqDo1.setAmmount(t.ammount);
                oqDo1.currency = mCurrency;
                oqDo1.setTs(ts);
                oqDo1.setOqwhen(OQT.DoWhen.FUTURE);
                oqDo1.setOqwhat(OQT.DoWhat.PAY);
                oqDos.add(oqDo1);

            }
        }
        return oqDos;
    }

    ;


    public void doUploadAll() {

        final long ts = System.currentTimeMillis();

        ArrayList<OqDo> arlOqDo = getOqDosFromTempHolder(ts);


        //MyOqPerson
        //OqWrap <- OqDo
        arlTask = new ArrayList<>();

        //common space

        if (arlUriPhoto.size() > 0) {
            arlTask.add("all," +
                    "setvalue_oqpostphoto");
        }

        arlTask.add
                ("all,setvalue_oqposttext");

        arlTask.add
                ("all,setvalue_oqdo");

        arlTask.add
                ("my,setvalue_myoqpost"); // create just profile, pid , ts


        profileMe = ProfileUtil
                .getMyProfileWithUidNameEmail(mActivity);

        final String pid = App.fbaseDbRef.child("push").push().getKey();
        Log.d(TAG, "pid : " + pid);


        ArrayList<ArrayList<OqDo>> ArlArlOqDoPerPeople = OqDoUtil.getArlArlOqDoPerPeople
                (arlOqDo, mActivity);


        for (upCounter = 0; upCounter < ArlArlOqDoPerPeople.size(); upCounter++) {

            //my space

            //myoqpost is done only once, so not here.
            arlTask.add
                    ("his,setvalue_myoqpost" + J.st(upCounter)); // create just profile, pid , ts

            arlTask.add
                    ("my," + "setvalue_myoqperson" + J.st(upCounter)); //update(by setting value) just ts
            arlTask.add
                    ("his," + "setvalue_myoqperson" + J.st(upCounter)); //update(by setting value)  just ts

            arlTask.add
                    ("my," + "setvalue_mycontact" + J.st(upCounter)); //update(by setting value) just ts
            arlTask.add
                    ("his," + "setvalue_mycontact" + J.st(upCounter)); //update(by setting value)  just ts


            ArrayList<OqDo> arlItem = new ArrayList<>();

            Profile profileHim = OqDoUtil.getOppoProfileFromOqDo(arlItem.get(0), mActivity);





            /*
            MyOqPerson Update [MainFrag0]
             */

            //Me
            MyOqPerson oqPersonHim = new MyOqPerson();
            oqPersonHim.setProfile(profileHim);
            oqPersonHim.setTs(ts);

            App.fbaseDbRef
                    .child(FBaseNode0.MyOqPerson)
                    .child(App.getUid(mActivity))
                    .child(profileHim.uid)
                    .setValue(oqPersonHim)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    arlTask.remove
                                            ("my," +
                                                    "setvalue_myoqperson" + J.st(upCounter));
                                    uiCheckStatusShowAlertDialogWhenDone();
                                }
                            }
                    );


            //Him

            final MyOqPerson oqPersonMe = new MyOqPerson();
            oqPersonMe.setProfile(profileMe);
            oqPersonMe.setTs(ts);

            App.fbaseDbRef
                    .child(FBaseNode0.MyOqPerson)
                    .child(profileHim.uid)
                    .child(App.getUid(mActivity))
                    .setValue(oqPersonMe)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    arlTask.remove
                                            ("his," +
                                                    "setvalue_myoqperson" + J.st(upCounter));
                                    uiCheckStatusShowAlertDialogWhenDone();

                                }
                            }
                    );





                        /*
            MyOqPost Update [MainFrag1]
             */


            /**
             * Upload MyOqPost - his (my is at below , to
             * exec just once)
             */
            MyOqPost myOqPost = new MyOqPost();
            myOqPost.setPid(pid);
            myOqPost.setTs(ts);
            myOqPost.setProfile(profileMe);
            App.fbaseDbRef
                    .child(FBaseNode0.MyPosts)
                    .child(oqPersonHim.getProfile().getUid())
                    .child(pid)
                    .setValue(myOqPost)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            arlTask.remove
                                    ("his,setvalue_myoqpost" + J.st(upCounter));
                            uiCheckStatusShowAlertDialogWhenDone();
                        }
                    })
            ;



            /*
            Contact Update
             */

            //Me
            App.fbaseDbRef
                    .child(FBaseNode0.MyContacts)
                    .child(App.getUid(mActivity))
                    .child(profileHim.uid)
                    .setValue(profileHim)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    arlTask.remove
                                            ("my," + "setvalue_mycontact" + J.st(upCounter)); //update(by setting value) just ts
                                    uiCheckStatusShowAlertDialogWhenDone();
                                }
                            }
                    );

            //Him
            App.fbaseDbRef
                    .child(FBaseNode0.MyContacts)
                    .child(profileHim.uid)
                    .child(App.getUid(mActivity))
                    .setValue(profileMe)
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    arlTask.remove
                                            ("his," + "setvalue_mycontact" + J.st(upCounter)); //update(by setting value)  just ts
                                    uiCheckStatusShowAlertDialogWhenDone();

                                }
                            }
                    );

        }


        for (final OqDo oqDo : arlOqDo) {


            final ArrayList<OqDo> oqDos = new ArrayList<>();

            oqDosCounter = 0;
            for (OqDo d : oqDos) {

                App.fbaseDbRef
                        .child(FBaseNode0.OqDo)
                        .child(d.oid)
                        .setValue(d)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                oqDosCounter++;
                                if (oqDos.size() == oqDosCounter) {
                                    arlTask.remove
                                            ("all,setvalue_oqdo");
                                }
                                uiCheckStatusShowAlertDialogWhenDone();
                            }
                        });
            }


        }

        /**
         * Post Body : text, photo repectively
         */
        App.fbaseDbRef
                .child(FBaseNode0.OqPostText)
                .child(pid)
                .setValue(strPostText)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        arlTask.remove
                                ("all,setvalue_oqposttext");
                        uiCheckStatusShowAlertDialogWhenDone();
                    }
                });


        OQPostPhoto oqPostPhoto = new OQPostPhoto();
        oqPostPhoto.setPid(pid);
        oqPostPhoto.setPhotoids(new ArrayList<String>());

        if (arlUriPhoto != null &&
                (arlUriPhoto.size() > 0)) {
            uploadFeedPhoto(arlUriPhoto, oqPostPhoto, App.getUid(mActivity));

        }


        /**
         * Upload MyOqPost -my
         */
        MyOqPost myOqPost = new MyOqPost();
        myOqPost.setPid(pid);
        myOqPost.setTs(ts);
        myOqPost.setProfile(profileMe);
        App.fbaseDbRef
                .child(FBaseNode0.MyPosts)
                .child(App.getUid(mActivity))
                .child(pid)
                .setValue(myOqPost)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        arlTask.remove
                                ("my,setvalue_myoqpost");
                        uiCheckStatusShowAlertDialogWhenDone();
                    }
                });


    }


    void uiCheckStatusShowAlertDialogWhenDone() {

        if (arlTask == null || arlTask.size() == 0) {
            JM.G(ro_prog);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(JM.strById(R.string.sent));
            alertDialogBuilder.setPositiveButton(JM.strById(R.string.ok_korean),
                    new
                            DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();
                                    mActivity.finish();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(JM.colorById(R.color.colorPrimary));
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // If there's an upload in progress, save the reference so you can query it later
        if (mTempStorageRef != null) {
            outState.putString("reference", mTempStorageRef.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // If there was an upload in progress, get its reference and create a new StorageReference
        final String stringRef = savedInstanceState.getString("reference");
        if (stringRef == null) {
            return;
        }
        mTempStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);

        // Find all UploadTasks under this StorageReference (in this example, there should be one)
        List<UploadTask> tasks = mTempStorageRef.getActiveUploadTasks();
        if (tasks.size() > 0) {
            // Get the task monitoring the upload
            UploadTask task = tasks.get(0);

            // Add new listeners to the task using an Activity scope
            task.addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //handleSuccess(state);
                            // call a user defined function to
                            // handle the event.

                        }
                    }
            );
        }
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
                            isExists= true;
                        }
                        //Frag0:ui
                    }

                    if (!isExists){
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


        }

    }


    public void uploadFeedPhoto(
            final ArrayList<Uri> arluri,
            final OQPostPhoto oqPostPhoto,
            String uid
    ) {

        uploadPhotoCounter = 0;

        for (Uri uri : arluri) {


            /**
             * get Bitmap
             */

            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)) {
                    byte[] bytes = os.toByteArray();

                    /**
                     photoId
                     **/
                    final String photoId = App.fbaseDbRef.child("push").push().getKey();

                    /**
                     metaData
                     **/
                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .setCustomMetadata("uid", uid)
                            .build();
                    /**
                     metaData
                     **/
                    mTempStorageRef = App.fbaseStorageRef
                            .child(FStorageNode.FirstT.POST_PHOTO)
                            .child(photoId);

                    /**
                     main
                     **/
                    UploadTask uploadTask =
                            mTempStorageRef
                                    .putBytes(bytes, metadata);

                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            System.out.println("Upload is " + progress + "% done");
                        }
                    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload is paused");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Log.d(TAG, "onFailure");
                            Log.d(TAG, exception.toString());
                            uploadPhotoCounter++;
                            if (uploadPhotoCounter == arluri.size()) {
                                uploadOqPostPhotoObj(oqPostPhoto);
                            }

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Handle successful uploads on complete
                            Log.d(TAG, "onSuccess");

                            Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                            oqPostPhoto.getPhotoids().add(photoId);
                            uploadPhotoCounter++;
                            if (uploadPhotoCounter == arluri.size()) {
                                uploadOqPostPhotoObj(oqPostPhoto);
                            }
                        }
                    });
                }
                ;
            } catch (FileNotFoundException e) {
                Log.d(TAG, e.toString());
                arlTask.add
                        ("error," +
                                "FileNotFoundException");
                uiCheckStatusShowAlertDialogWhenDone();
            } catch (IOException e) {
                Log.d(TAG, e.toString());
                arlTask.add
                        ("error," +
                                "IOException");
                uiCheckStatusShowAlertDialogWhenDone();
            }

        }
    }

    void uploadOqPostPhotoObj(OQPostPhoto oqPostPhoto) {
        App.fbaseDbRef
                .child(FBaseNode0.OqPostPhoto)
                .child(oqPostPhoto.getPid())
                .setValue(oqPostPhoto)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        arlTask.remove
                                ("all," +
                                        "setvalue_oqpostphoto");
                        uiCheckStatusShowAlertDialogWhenDone();
                    }
                })
        ;
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


}
