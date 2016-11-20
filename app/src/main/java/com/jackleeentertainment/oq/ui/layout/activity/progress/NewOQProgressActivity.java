package com.jackleeentertainment.oq.ui.layout.activity.progress;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
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
import com.jackleeentertainment.oq.generalutil.ContactsUtil;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.MyOqPerson;
import com.jackleeentertainment.oq.object.MyOqPost;
import com.jackleeentertainment.oq.object.OQPostPhoto;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.BaseActivity;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.activity.ProgressT;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 19..
 */

public class NewOQProgressActivity extends BaseActivity {
    String TAG = "NewOQProgActivity";

    Activity mActivity = this;

    //Variables
    public ArrayList<NewOQActivity.TempProAmt> tempArl = new ArrayList<>();
    public ArrayList<NewOQActivity.TempSpent> tempArlSpent = new ArrayList<>();

    public ArrayList<Uri> arlUriPhoto = new ArrayList<>();
    public String strPostText;

    StorageReference mTempStorageRef;  //mTempStorageRef was previously used to transfer data.

    public String mCurrency = "KRW";
    public String mDoWhat = null; //PAY/GET

    OQPostPhoto oqPostPhoto;
    String photoId;

    //Counters
    int uploadPhotoCounter = 0;
    public int oqDosCounter = 0;
    ArrayList<String> arlTask = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUILayoutOnCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        tempArl= (ArrayList<NewOQActivity.TempProAmt> )getIntent().getSerializableExtra("tempArl");
        tempArlSpent= (ArrayList<NewOQActivity.TempSpent> )getIntent().getSerializableExtra("tempArlSpent");
        arlUriPhoto= (ArrayList<Uri> )getIntent().getParcelableExtra("arlUriPhoto");
        strPostText=getIntent().getStringExtra("strPostText");
        mCurrency=getIntent().getStringExtra("mCurrency");
        mDoWhat=getIntent().getStringExtra("mDoWhat");

        doUploadAll();
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
                            oqPostPhoto.getPhotoids().add(photoId);
                            uploadPhotoCounter++;
                            if (uploadPhotoCounter == arlUriPhoto.size()) {
                                uploadOqPostPhotoObj(oqPostPhoto);
                            }
                        }
                    }
            );
        }
    }


    void initUILayoutOnCreate() {
        setContentView(R.layout.activity_progress);
    }






    public void doUploadAll() {

        final long ts = System.currentTimeMillis();
        final String pid = App.fbaseDbRef.child("push").push().getKey();
        Log.d(TAG, "pid : " + pid);
        final ArrayList<OqDo> arlOqDo = getOqDosFromTempHolder(ts, pid);


        //MyOqPerson
        //OqWrap <- OqDo
        arlTask = new ArrayList<>();

        //common space

        if (arlUriPhoto.size() > 0) {
            arlTask.add("all,oqpostphoto");
        }

        if (strPostText != null && strPostText.length() > 0) {
            arlTask.add
                    ("all,oqposttext");
        }
        arlTask.add
                ("all,oqdo");

        arlTask.add
                ("my,myoqpost"); // create just profile, pid , ts




       Profile profileMe = ProfileUtil
                .getMyProfileWithUidNameEmail(mActivity);




        ArrayList<ArrayList<OqDo>> ArlArlOqDoPerPeople = OqDoUtil.getArlArlOqDoPerPeople
                (arlOqDo, mActivity);

        for (int upCounter = 0; upCounter < ArlArlOqDoPerPeople.size(); upCounter++) {

            //my space

            //myoqpost is done only once, so not here.
            arlTask.add
                    ("his,myoqpost" + J.st(upCounter)); // create just profile, pid , ts
            arlTask.add
                    ("my,myoqperson" + J.st(upCounter)); //update(by setting value) just ts
            arlTask.add
                    ("his,myoqperson" + J.st(upCounter)); //update(by setting value)  just ts
            arlTask.add
                    ("my,mycontact" + J.st(upCounter)); //update(by setting value) just ts
            arlTask.add
                    ("his,mycontact" + J.st(upCounter)); //update(by setting value)  just ts
        }



        for (int upCounter = 0; upCounter < ArlArlOqDoPerPeople.size(); upCounter++) {

            final int upct = upCounter;

            ArrayList<OqDo>   arlItem = ArlArlOqDoPerPeople.get(upCounter);
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
                                            ("my,myoqperson" + J.st(upct));
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
                                            ("his,myoqperson" + J.st(upct));
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
                                    ("his,myoqpost" + J.st(upct));
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
                                            ("my,mycontact" + J.st(upct)); //update(by setting value) just ts
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
                                            ("his,mycontact" + J.st(upct)); //update(by setting value)  just ts
                                    uiCheckStatusShowAlertDialogWhenDone();

                                }
                            }
                    );

        }

        oqDosCounter = 0;

        for (final OqDo oqDo : arlOqDo) {


            App.fbaseDbRef
                    .child(FBaseNode0.OqDo)
                    .child(oqDo.oid)
                    .setValue(oqDo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            oqDosCounter++;
                            if (arlOqDo.size() == oqDosCounter) {
                                arlTask.remove
                                        ("all,oqdo");
                            }
                            uiCheckStatusShowAlertDialogWhenDone();
                        }
                    });


        }

        /**
         * Post Body : text, photo repectively
         */
        if (strPostText != null && strPostText.length() > 0) {
            App.fbaseDbRef
                    .child(FBaseNode0.OqPostText)
                    .child(pid)
                    .setValue(strPostText)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            arlTask.remove
                                    ("all,oqposttext");
                            uiCheckStatusShowAlertDialogWhenDone();
                        }
                    });
        }

        oqPostPhoto = new OQPostPhoto();
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
                                ("my,myoqpost");
                        uiCheckStatusShowAlertDialogWhenDone();
                    }
                });


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
                    photoId = App.fbaseDbRef.child("push").push().getKey();

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
                                ("all,oqpostphoto");
                        uiCheckStatusShowAlertDialogWhenDone();
                    }
                })
        ;
    }


    //varvar
    public ArrayList<OqDo> getOqDosFromTempHolder(long ts, String pid) {

        Profile profileMe = ProfileUtil.getMyProfileWithUidNameEmail(mActivity);

        ArrayList<OqDo> oqDos = new ArrayList<>();

        if (mDoWhat.equals(OQT.DoWhat.GET)) {

            for (NewOQActivity.TempProAmt t : tempArl) {

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
                oqDo.setPid(pid);
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
                oqDo1.setPid(pid);

                oqDos.add(oqDo1);

            }
        }
        return oqDos;
    }

    ;




    void uiCheckStatusShowAlertDialogWhenDone() {

        if (arlTask == null || arlTask.size() == 0) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(JM.strById(R.string.sent));
            alertDialogBuilder.setPositiveButton(JM.strById(R.string.ok_korean),
                    new
                            DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();
                                    Intent returnIntent = new Intent();
                                    mActivity.setResult(Activity.RESULT_OK, returnIntent);
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
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
