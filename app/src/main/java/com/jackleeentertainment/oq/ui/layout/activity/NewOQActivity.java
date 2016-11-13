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
import android.widget.EditText;

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
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;

import com.jackleeentertainment.oq.object.MyOqPerson;
import com.jackleeentertainment.oq.object.MyOqPost;
import com.jackleeentertainment.oq.object.OQPost;
import com.jackleeentertainment.oq.object.OQPostPhoto;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqWrap;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.DeedT;
import com.jackleeentertainment.oq.object.types.OQPostT;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0Neo;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 10. 19..
 */

public class NewOQActivity extends BaseFragmentContainFullDialogActivity {

    NewOQActivity mActivity = this;
    String TAG = "NewOQActivity";
    final int REQ_PEOPLE = 99;
    final int REQ_PICK_IMAGE_FOR_FEED = 98;

    //temp data (1)
    public String OQTWantT_Future =
            null; //PAY/GET

    public ArrayList<OqDo> arlOqDo_Paid = new ArrayList<>();
    public ArrayList<OqDo> arlOqDo_Now = new ArrayList<>();
    public ArrayList<OqDo> arlOqDo_Future = new ArrayList<>();
    public ArrayList<OqWrap> arlOqWrap = new ArrayList<>();

    public ArrayList<Uri> arlUriPhoto = new ArrayList<>();

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
        OQTWantT_Future = (getIntent().getStringExtra("OQTWantT_Future"));
        Bundle bundle = new Bundle();
        bundle.putString("OQTWantT_Future", OQTWantT_Future);
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
        if (OQTWantT_Future != null && OQTWantT_Future.equals(OQT.DoWhat.GET)) {
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_get));
        } else if (OQTWantT_Future != null && OQTWantT_Future.equals(OQT.DoWhat.PAY)) {
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
        if (arlOqDo_Future != null && arlOqDo_Future.size() > 0) {
            ArrayList<String> arlUidClaimee = OqDoUtil.getArlUidBFromArlOqDo
                    (arlOqDo_Future);
            i.putExtra("beforeUids", new Gson().toJson(arlUidClaimee));
        }
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

    Profile profileMe;

    public void doUploadAll(final EditText etContent) {

        //MyOqPerson
        //OqWrap <- OqDo
        arlTask = new ArrayList<>();

        if (arlUriPhoto.size()>0) {
            arlTask.add("all," +
                    "setvalue_oqpostphoto");
        }
        arlTask.add
                ("all,setvalue_oqpost");
        arlTask.add
                ("my,setvalue_myoqpost");
        arlTask.add
                ("his,setvalue_myoqpost");
        arlTask.add
                ("his,setvalue_oqwrap");
        arlTask.add
                ("my,setvalue_oqwrap");
        arlTask.add
                ("his," + "setvalue_myoqperson");
        arlTask.add
                ("my," + "setvalue_myoqperson");


        final long ts = System.currentTimeMillis();
        profileMe = ProfileUtil
                .getMyProfileWithUidNameEmail(mActivity);

        final ArrayList<Profile> arlProfile = new ArrayList<Profile>();
//        final String qid = App.fbaseDbRef.child("push").push().getKey();
        final ArrayList<String> arlWids = new ArrayList<>();
        final String pid = App.fbaseDbRef.child("push").push().getKey();
        Log.d(TAG, "pid : " + pid);

        for (final OqDo oqDo : arlOqDo_Future) {

            final ArrayList<String> arlUidNotMe = OqDoUtil.getUidsNotMe(oqDo, this);

            if (arlUidNotMe != null && arlUidNotMe.size() == 1) {


                /**
                 * Check Oppo's Profile and Upload MyOqPerson (to Me) using it
                 */
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .child(arlUidNotMe.get(0))
                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {


                                            Profile profileHim = dataSnapshot.getValue(Profile
                                                    .class);
                                            if (profileHim != null) {
                                                /**
                                                 * Upload MyOqPerson - me
                                                 */
                                                MyOqPerson oqPersonHim = new MyOqPerson();
                                                oqPersonHim.setProfile(profileHim);
                                                oqPersonHim.setTs(ts);

                                                App.fbaseDbRef
                                                        .child(FBaseNode0.MyOqPerson)
                                                        .child(App.getUid(mActivity))
                                                        .child(oqPersonHim.getProfile().getUid())
                                                        .setValue(oqPersonHim)
                                                        .addOnCompleteListener(
                                                                new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        arlTask.remove
                                                                                ("my," +
                                                                                        "setvalue_myoqperson");
                                                                        uiCheckStatusShowAlertDialogWhenDone();
                                                                    }
                                                                }
                                                        );


                                                /**
                                                 * Upload MyOqPerson - his
                                                 */


                                                final MyOqPerson oqPersonMe = new MyOqPerson();
                                                oqPersonMe.setProfile(profileMe);
                                                oqPersonMe.setTs(ts);

                                                App.fbaseDbRef
                                                        .child(FBaseNode0.MyOqPerson)
                                                        .child(arlUidNotMe.get(0))
                                                        .child(App.getUid(mActivity))
                                                        .setValue(oqPersonMe)
                                                        .addOnCompleteListener(
                                                                new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        arlTask.remove
                                                                                ("his," +
                                                                                        "setvalue_myoqperson");
                                                                        uiCheckStatusShowAlertDialogWhenDone();

                                                                    }
                                                                }
                                                        );


                                                /**
                                                 * Upload OqWrap holding OqDo
                                                 */
                                                final String wid = App.fbaseDbRef.child("push").push().getKey();
                                                arlWids.add(wid);

                                                List<OqDo> oqDos = new ArrayList<>();
                                                final String oid = App.fbaseDbRef.child("push").push().getKey();
                                                oqDo.setOid(oid);
                                                oqDo.setReferoid(oid);
                                                oqDo.setWid(wid);
                                                oqDo.setTs(ts);
                                                oqDos.add(oqDo);

                                                //reverse OqDo
                                                OqDo oqDo1 = new OqDo();
                                                final String oid1 = App.fbaseDbRef.child("push")
                                                        .push().getKey();
                                                oqDo1.setOid(oid1);
                                                oqDo.setReferoid(oid);
                                                oqDo1.setWid(wid);
                                                oqDo1.setTs(ts);
                                                oqDo1.setUida(oqDo.getUidb());
                                                oqDo1.setNamea(oqDo.getNameb());
                                                oqDo1.setEmaila(oqDo.getEmailb());
                                                oqDo1.setUidb(oqDo.getUida());
                                                oqDo1.setNameb(oqDo.getNamea());
                                                oqDo1.setEmailb(oqDo.getEmaila());
                                                oqDo1.setAmmount(oqDo.getAmmount());
                                                oqDo1.setCurrency(oqDo.getCurrency());
                                                oqDo1.setOqwhen(oqDo.getOqwhen());

                                                if (oqDo.getOqwhat().equals(OQT.DoWhat.GET)) {
                                                    oqDo1.setOqwhat(OQT.DoWhat.PAY);
                                                } else if (oqDo.getOqwhat().equals(OQT.DoWhat.PAY)) {
                                                    oqDo1.setOqwhat(OQT.DoWhat.GET);
                                                }

                                                if (oqDo.getOqwhen().equals(OQT.DoWhen.FUTURE)) {
                                                    oqDo1.setOqwhen(OQT.DoWhen.FUTURE);
                                                } else if (oqDo.getOqwhen().equals(OQT.DoWhen
                                                        .PAST)) {
                                                    oqDo1.setOqwhen(OQT.DoWhen.PAST);
                                                }

                                                oqDos.add(oqDo1);

                                                /*
                                                String wid; //this's id;
                                                String gid; //groups'id;
                                                String qid; //appointment's id;
                                                long ts; //listopdo's;
                                                List<OqDo> listoqdo;
                                                 */
                                                OqWrap oqWrap = new OqWrap();
                                                oqWrap.setWid(wid);
                                                oqWrap.setTs(ts);
                                                oqWrap.setListoqdo(oqDos);
                                                oqWrap.setPid(pid);
                                                //gid

                                                //Set My OqWrap
                                                App.fbaseDbRef
                                                        .child(FBaseNode0.MyOqWraps)
                                                        .child(oqDo.getUida()) //me
                                                        .child(oqDo.getUidb()) //opponent
                                                        .child(wid)
                                                        .setValue(oqWrap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                arlTask.remove
                                                                        ("my,setvalue_oqwrap");
                                                                uiCheckStatusShowAlertDialogWhenDone();

                                                            }
                                                        });

                                                //Set His OqWrap
                                                App.fbaseDbRef
                                                        .child(FBaseNode0.MyOqWraps)
                                                        .child(oqDo.getUidb()) //opponent
                                                        .child(oqDo.getUida()) //me
                                                        .child(wid)
                                                        .setValue(oqWrap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                arlTask.remove
                                                                        ("his,setvalue_oqwrap");
                                                                uiCheckStatusShowAlertDialogWhenDone();
                                                            }
                                                        });


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
                                                                        ("his,setvalue_myoqpost");
                                                                uiCheckStatusShowAlertDialogWhenDone();
                                                            }
                                                        })
                                                ;
                                            }


                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.d(TAG, "onCancelled");
                                    }
                                }
                        );


            }


        }

        /**
         * Post Body
         */


        OQPost oqPost = new OQPost();
        oqPost.setPid(pid);
        oqPost.setUid(App.getUid(mActivity));
        oqPost.setUname(App.getUname(mActivity));
        oqPost.setUdeed(DeedT.RequesterSENT_IWantGet_REQ);
        oqPost.setTxt(etContent.getText()
                .toString());
        oqPost.setTs(ts);
        oqPost.setWids(arlWids);

        OQPostPhoto oqPostPhoto = new OQPostPhoto();
        oqPostPhoto.setPid(oqPost.getPid());
        oqPostPhoto.setPhotoids(new ArrayList<String>());

        if (arlUriPhoto == null ||
                arlUriPhoto.size()
                        == 0) {
            oqPost.setPosttype(OQPostT.NONE);

        } else if (arlUriPhoto != null &&
                (arlUriPhoto.size() > 0)) {
            oqPost.setPosttype(OQPostT.PHOTO);
            uploadFeedPhoto(arlUriPhoto, oqPostPhoto, App.getUid(mActivity));

        }


        App.fbaseDbRef
                .child(FBaseNode0.OQPost)
                .child(App.getUid(this)) // this is not myspace. for all. for classification.
                .child(pid)
                .setValue(oqPost)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        arlTask.remove
                                ("all,setvalue_oqpost");
                        uiCheckStatusShowAlertDialogWhenDone();
                    }
                });

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
                })
        ;
    }


    ArrayList<String> arlTask = new ArrayList<>();

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
            pbutton.setTextColor(JM.colorById(R.color.colorPrimary));        }

    }


    StorageReference mTempStorageRef;  //mTempStorageRef was previously used to transfer data.

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
                ArrayList<Profile> arlOppoProfile = ProfileUtil.getArlProfileFromJson(intent
                        .getStringExtra("result"));
                Log.d(TAG, "intent.getStringExtra(result) : " + str);
                Log.d(TAG, "arlOppoProfile.size() " + J.st(arlOppoProfile.size()));

                //create oqitems from it
                for (Profile profile : arlOppoProfile) {

                    OqDo oqDo = OqDoUtil.getOqDoWithUidB(arlOqDo_Future, profile
                            .getUid());
                    if (oqDo == null) {

                        OqDo oqDo1 = new OqDo();
                        if (OQTWantT_Future.equals(OQT.DoWhat.GET)) {
                            oqDo1 = OqDoUtil.getInstanceIClaimThatIGet(profile
                                    , this);
                        } else if (OQTWantT_Future.equals(OQT.DoWhat.PAY)) {
                            oqDo1 = OqDoUtil.getInstanceIClaimThatIPay(profile
                                    , this);
                        }
                        arlOqDo_Future.add(oqDo1);
                    }
                }
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


//            if (requestCode == REQ_SUMTYPE) {
//
//                OQSumT = intent.getStringExtra("OQSumT");
//                ammountAsStandard = intent.getLongExtra("ammountAsStandard", 0);
//
//                if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYou.I_PAID_FOR_YOU_AND_ME)) {
//
//                    /**
//                     *    //id
//                     String oid; //this object's id
//                     String gid; //human group's id (if any)
//                     String qid; //oqitem group's id (in the case of case oqitem)
//
//                     //people
//                     String uidpayer;
//                     String uidgettor;
//
//                     //ammount
//                     long ammount;
//
//                     //point - future=obligation, now=paying, past=paid
//                     String oqtype;
//
//                     //ts
//                     long ts;
//
//                     String currency;
//                     String duedate;
//                     */
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(App.getUid(this));
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.DoWhen.PAST);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOqDo_Paid.set(0, oqItemPaid);
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setUidgettor(App.getUid(this));
//                    oqItemToPay.setAmmount(ammountAsStandard / 2);
//                    oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOqDo_Future.set(0, oqItemToPay);
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYou.I_PAID_FOR_YOU)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(App.getUid(this));
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.DoWhen.PAST);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOqDo_Paid.set(0, oqItemPaid);
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setUidgettor(App.getUid(this));
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOqDo_Future.set(0, oqItemToPay);
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYou.ANYWAY)) {
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setUidgettor(App.getUid(this));
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOqDo_Future.set(0, oqItemToPay);
//
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(App.getUid(this));
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.DoWhen.PAST);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOqDo_Paid.set(0, oqItemPaid);
//
//                    int pplNumWithoputMe = arlOppoProfile.size();
//                    long eachBurden = ammountAsStandard / (pplNumWithoputMe + 1);
//
//                    for (int i = 0; i < arlOppoProfile.size(); i++) {
//                        OqItem oqItemToPay = new OqItem();
//                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
//                        oqItemToPay.setUidgettor(App.getUid(this));
//                        oqItemToPay.setAmmount(eachBurden);
//                        oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                        oqItemToPay.setDuedate(null);
//                        arlOqDo_Future.set(i, oqItemToPay);
//                    }
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(App.getUid(this));
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.DoWhen.PAST);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOqDo_Paid.set(0, oqItemPaid);
//
//                    int pplNumWithoputMe = arlOppoProfile.size();
//                    long eachBurden = ammountAsStandard / (pplNumWithoputMe);
//
//                    for (int i = 0; i < arlOppoProfile.size(); i++) {
//                        OqItem oqItemToPay = new OqItem();
//                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
//                        oqItemToPay.setUidgettor(App.getUid(this));
//                        oqItemToPay.setAmmount(eachBurden);
//                        oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                        oqItemToPay.setDuedate(null);
//                        arlOqDo_Future.set(i, oqItemToPay);
//                    }
//
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYouGuys.N_ANYWAY)) {
//
//                    int pplNumWithoputMe = arlOppoProfile.size();
//                    long eachBurden = ammountAsStandard / (pplNumWithoputMe);
//
//                    for (int i = 0; i < arlOppoProfile.size(); i++) {
//                        OqItem oqItemToPay = new OqItem();
//                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
//                        oqItemToPay.setUidgettor(App.getUid(this));
//                        oqItemToPay.setAmmount(eachBurden);
//                        oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                        oqItemToPay.setDuedate(null);
//                        arlOqDo_Future.set(i, oqItemToPay);
//                    }
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToPAY.YOU_PAID_FOR_ME)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.DoWhen.PAST);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOqDo_Paid.set(0, oqItemPaid);
//
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(App.getUid(this));
//                    oqItemToPay.setUidgettor(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOqDo_Future.set(0, oqItemToPay);
//
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToPAY.ANYWAY)) {
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(App.getUid(this));
//                    oqItemToPay.setUidgettor(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.DoWhen.FUTURE);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOqDo_Future.set(0, oqItemToPay);
//
//
//                }
//            }

        }

    }

    int unloadPhotoCounter = 0;

    public void uploadFeedPhoto(
            final ArrayList<Uri> arluri,
            final OQPostPhoto oqPostPhoto,
            String uid
    ) {

        unloadPhotoCounter = 0;

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
                            unloadPhotoCounter++;
                            if (unloadPhotoCounter == arluri.size()) {
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
                            unloadPhotoCounter++;
                            if (unloadPhotoCounter == arluri.size()) {
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
                .child(FBaseNode0.OQPostPhoto)
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

    public void uiLsnerFrag0(){
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

    public void uiLsnerFrag1(final Bundle bundleFromFrag0){
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
