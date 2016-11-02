package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.database.SetValue;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.firebase.storage.Upload;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.MyOppo;
import com.jackleeentertainment.oq.object.OQPost;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.DeedT;
import com.jackleeentertainment.oq.object.types.OQPostT;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.OqItemUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.fragment.NewOQFrag0Neo;

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
    public String OQTWantT_Future = null; //PAY/GET

    public ArrayList<OqItem> arlOQItem_Paid = new ArrayList<>();
    public ArrayList<OqItem> arlOQItem_Now = new ArrayList<>();
    public ArrayList<OqItem> arlOQItem_Future = new ArrayList<>();

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
        if (OQTWantT_Future != null && OQTWantT_Future.equals(OQT.WantT.GET)) {
            tvToolbarTitle.setText(JM.strById(R.string.transaction_i_get));
        } else if (OQTWantT_Future != null && OQTWantT_Future.equals(OQT.WantT.PAY)) {
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
        if (arlOQItem_Future != null && arlOQItem_Future.size() > 0) {

            ArrayList<String> arlUidClaimee = OqItemUtil.getArlUidClaimeeFromArlOqItem
                    (arlOQItem_Future);
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


    public void doUploadAll(final EditText etContent) {

        final long ts = System.currentTimeMillis();
        final ArrayList<MyOppo> arlMyOppo = new ArrayList<MyOppo>();

        for (final OqItem oqItem : arlOQItem_Future) {
            final String oid = App.fbaseDbRef.child("push").push().getKey();
            oqItem.setOid(oid);
            oqItem.setTs(ts);
            uploadMyOqItemThenMyOppo(oqItem, oid, ts, arlMyOppo);
            uploadHisOqItemThenHisOppo(oqItem, oid, ts);
            SetValue.updateMyRecentProfilesWithOppo(arlMyOppo, mActivity);
        }

        final String pid = App.fbaseDbRef.child("push").push().getKey();
        OQPost oqPost = new OQPost();
        oqPost.setPid(pid);
        oqPost.setUid(App.getUid(mActivity));
        oqPost.setUname(App.getUname(mActivity));
        oqPost.setUdeed(DeedT.SENT_GETREQ);
        oqPost.setTxt(etContent.getText()
                .toString());
        oqPost.setTs(ts);

        if (arlUriPhoto == null ||
                arlUriPhoto.size()
                        == 0) {
            oqPost.setPosttype(OQPostT.NONE);

        } else if (arlUriPhoto != null &&
                (arlUriPhoto.size() > 0)) {
            oqPost.setPosttype(OQPostT.PHOTO);
            for (Uri uri : arlUriPhoto) {
                uploadFeedPhoto(pid);
            }
        }
        oqPost.setMyOppos(arlMyOppo);

        App.fbaseDbRef
                .child(FBaseNode0.MyPosts)
                .child(App.getUid(mActivity))
                .child(pid)
                .setValue(oqPost)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                })
        ;


        for (final OqItem oqItem : arlOQItem_Future) {
            App.fbaseDbRef
                    .child(FBaseNode0.MyPosts)
                    .child(oqItem.getUidclaimee())
                    .child(pid)
                    .setValue(oqPost)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    })
            ;
        }


    }

    void uploadMyOqItemThenMyOppo(final OqItem oqItem,
                                  final String oid,
                                  final long ts,
                                  final ArrayList<MyOppo> arlMyOppo) {

        App.fbaseDbRef
                .child(FBaseNode0.MyOqItems)
                .child(App.getUid(mActivity))
                .child(oqItem.getUidclaimee())
                .child(oid)
                .setValue(oqItem)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        App.fbaseDbRef
                                .child(FBaseNode0.MyOqItems)
                                .child(App.getUid(mActivity))
                                .child(oqItem.getUidclaimee())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {

                                            long amtIClaimToHim = 0;

                                            Iterable<DataSnapshot> i = dataSnapshot.getChildren();

                                            for (DataSnapshot d : i) {

                                                if ((d.getValue
                                                        (OqItem.class))
                                                        .getUidclaimer()
                                                        .equals(App.getUid
                                                                (mActivity))) {
                                                    amtIClaimToHim += (d.getValue
                                                            (OqItem.class))
                                                            .getAmmount();
                                                }
                                            }


                                            MyOppo myOppo = new MyOppo();
                                            myOppo.setUid(oqItem.getUidclaimee());
                                            myOppo.setUname(oqItem.getNameclaimee
                                                    ());
                                            myOppo.setAmticlaim(amtIClaimToHim);
                                            myOppo.setTs(ts);
                                            App.fbaseDbRef
                                                    .child(FBaseNode0.MyOppoList)
                                                    .child(App.getUid(mActivity))
                                                    .child(oqItem.getUidclaimee())
                                                    .setValue(myOppo)
                                                    .addOnCompleteListener
                                                            (new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                }
                                                            });

                                            arlMyOppo.add(myOppo);


                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                    }
                });
    }

    void uploadHisOqItemThenHisOppo(final OqItem oqItem,
                                    final String oid,
                                    final long ts) {

        App.fbaseDbRef
                .child(FBaseNode0.MyOqItems)
                .child(oqItem.getUidclaimee())
                .child(App.getUid(mActivity))
                .child(oid)
                .setValue(oqItem)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        App.fbaseDbRef
                                .child(FBaseNode0.MyOqItems)
                                .child(oqItem.getUidclaimee())
                                .child(App.getUid(mActivity))
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {

                                            long amtHeClaimMe = 0;

                                            Iterable<DataSnapshot> i = dataSnapshot.getChildren();

                                            for (DataSnapshot d : i) {

                                                if ((d.getValue
                                                        (OqItem.class))
                                                        .getUidclaimee()
                                                        .equals(oqItem
                                                                .getUidclaimee())) {

                                                    amtHeClaimMe += (d.getValue
                                                            (OqItem.class))
                                                            .getAmmount();
                                                }
                                            }


                                            MyOppo myOppo = new MyOppo();
                                            myOppo.setUid(App.getUid(mActivity));
                                            myOppo.setUname(App.getUname(mActivity));
                                            myOppo.setAmtheclaim
                                                    (amtHeClaimMe);
                                            myOppo.setTs(ts);


                                            App.fbaseDbRef
                                                    .child(FBaseNode0.MyOppoList)
                                                    .child(oqItem
                                                            .getUidclaimee())
                                                    .child(App.getUid(mActivity))
                                                    .setValue(myOppo)
                                                    .addOnCompleteListener
                                                            (new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                }
                                                            });


                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                    }
                });

    }

    void uploadFeedPhoto(String pid) {

        for (Uri uri : arlUriPhoto) {
            Upload.uploadFile(
                    FStorageNode.FirstT.POST_PHOTO,
                    App.getUid(mActivity), // for security and maintainence
                    pid,
                    uri,
                    mActivity                    );
        }
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

                    OqItem oqItem = OqItemUtil.getOqItemWithUidclaimee(arlOQItem_Future, profile
                            .getUid());
                    if (oqItem == null) {
                        OqItem oqItem1 = new OqItem();
                        if (OQTWantT_Future.equals(OQT.WantT.GET)) {
                            oqItem1 = OqItemUtil.getInstanceIClaimThatIGet(profile
                                    , this);
                        } else if (OQTWantT_Future.equals(OQT.WantT.PAY)) {
                            oqItem1 = OqItemUtil.getInstanceIClaimThatIPay(profile
                                    , this);
                        }
                        arlOQItem_Future.add(oqItem1);
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
//                    oqItemPaid.setOqtype(OQT.PointT.PAID);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOQItem_Paid.set(0, oqItemPaid);
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setUidgettor(App.getUid(this));
//                    oqItemToPay.setAmmount(ammountAsStandard / 2);
//                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOQItem_Future.set(0, oqItemToPay);
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYou.I_PAID_FOR_YOU)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(App.getUid(this));
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.PointT.PAID);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOQItem_Paid.set(0, oqItemPaid);
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setUidgettor(App.getUid(this));
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOQItem_Future.set(0, oqItemToPay);
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYou.ANYWAY)) {
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setUidgettor(App.getUid(this));
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOQItem_Future.set(0, oqItemToPay);
//
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(App.getUid(this));
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.PointT.PAID);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOQItem_Paid.set(0, oqItemPaid);
//
//                    int pplNumWithoputMe = arlOppoProfile.size();
//                    long eachBurden = ammountAsStandard / (pplNumWithoputMe + 1);
//
//                    for (int i = 0; i < arlOppoProfile.size(); i++) {
//                        OqItem oqItemToPay = new OqItem();
//                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
//                        oqItemToPay.setUidgettor(App.getUid(this));
//                        oqItemToPay.setAmmount(eachBurden);
//                        oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                        oqItemToPay.setDuedate(null);
//                        arlOQItem_Future.set(i, oqItemToPay);
//                    }
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(App.getUid(this));
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.PointT.PAID);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOQItem_Paid.set(0, oqItemPaid);
//
//                    int pplNumWithoputMe = arlOppoProfile.size();
//                    long eachBurden = ammountAsStandard / (pplNumWithoputMe);
//
//                    for (int i = 0; i < arlOppoProfile.size(); i++) {
//                        OqItem oqItemToPay = new OqItem();
//                        oqItemToPay.setUidpayer(arlOppoProfile.get(i).getUid());
//                        oqItemToPay.setUidgettor(App.getUid(this));
//                        oqItemToPay.setAmmount(eachBurden);
//                        oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                        oqItemToPay.setDuedate(null);
//                        arlOQItem_Future.set(i, oqItemToPay);
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
//                        oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                        oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                        oqItemToPay.setDuedate(null);
//                        arlOQItem_Future.set(i, oqItemToPay);
//                    }
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToPAY.YOU_PAID_FOR_ME)) {
//
//                    OqItem oqItemPaid = new OqItem();
//                    oqItemPaid.setUidpayer(arlOppoProfile.get(0).getUid());
//                    oqItemPaid.setUidgettor(null);
//                    oqItemPaid.setAmmount(ammountAsStandard);
//                    oqItemPaid.setOqtype(OQT.PointT.PAID);
//                    oqItemPaid.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemPaid.setDuedate(null);
//                    arlOQItem_Paid.set(0, oqItemPaid);
//
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(App.getUid(this));
//                    oqItemToPay.setUidgettor(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOQItem_Future.set(0, oqItemToPay);
//
//
//                } else if (OQSumT.equals(com.jackleeentertainment.oq.object.types.OQSumT
//                        .SoIWantToPAY.ANYWAY)) {
//
//                    OqItem oqItemToPay = new OqItem();
//                    oqItemToPay.setUidpayer(App.getUid(this));
//                    oqItemToPay.setUidgettor(arlOppoProfile.get(0).getUid());
//                    oqItemToPay.setAmmount(ammountAsStandard);
//                    oqItemToPay.setOqtype(OQT.PointT.TOPAY);
//                    oqItemToPay.setCurrency(JM.strById(R.string.currency_code));
//                    oqItemToPay.setDuedate(null);
//                    arlOQItem_Future.set(0, oqItemToPay);
//
//
//                }
//            }

        }

    }
}
