package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqDoPair;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.ChatUtil;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.ui.layout.viewholder.Ava2RelationDtlSmallVHolder;
import com.jackleeentertainment.oq.ui.layout.viewholder.Ava2RelationDtlVHolder;
import com.jackleeentertainment.oq.ui.widget.LoOppoFeed;
import com.konifar.fab_transformation.FabTransformation;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 10. 24..
 */

public class ProfileActivity extends BaseActivity {
    static String TAG = "ProfileActivity";
    Activity mActivity = this;
    final int REQ_PICK_IMAGE = 99;

    Profile profile;
    ImageView ivClose;
    RecyclerView rvOQ;
    LinearLayout nav_header_main;

    RelativeLayout ro_person_photo_48dip__lessmargin;
    TextView tvTitle_DrawerHeader;
    TextView tvSubTitle_DrawerHeader;
    TextView ro_person_photo_tv;
    ImageView ro_person_photo_iv;
    RelativeLayout roProgress, ro_empty_list;
    ImageView ivChat, ivAddTran;

    //FAB
    FloatingActionButton fab;
    Toolbar toolbarFooter;
    RelativeLayout roFootTab0, roFootTab1, roFootTab2;
    TextView tvFootTitle;
    ImageView ivFootTab0, ivFootTab1, ivFootTab2;
    View vScrim;


    boolean isMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profile = (Profile) getIntent().getSerializableExtra("Profile");
        isMe = getIntent().getBooleanExtra("isMe", false);

        setContentView(R.layout.activity_profile);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        rvOQ = (RecyclerView) findViewById(R.id.rvOQ);
        nav_header_main = (LinearLayout) findViewById(R.id.nav_header_main);
        ivChat = (ImageView) findViewById(R.id.ivChat);
        ivAddTran = (ImageView) findViewById(R.id.ivAddTran);
        ro_person_photo_48dip__lessmargin = (RelativeLayout) findViewById(R.id
                .ro_person_photo_48dip__lessmargin);

        ro_person_photo_tv = (TextView) ro_person_photo_48dip__lessmargin.
                findViewById(R.id.tvAva);
        ro_person_photo_iv = (ImageView) ro_person_photo_48dip__lessmargin.
                findViewById(R.id.ivAva);
        tvTitle_DrawerHeader = (TextView) findViewById(R.id.tvTitle_DrawerHeader);
        tvSubTitle_DrawerHeader = (TextView) findViewById(R.id.tvSubTitle_DrawerHeader);
        roProgress = (RelativeLayout) findViewById(R.id.roProgress);

        ro_empty_list = (RelativeLayout) findViewById(R.id.ro_empty_list);


        /**
         * FAB
         */
        toolbarFooter = (Toolbar) findViewById(R.id.toolbar_footer);
        vScrim = (View) findViewById(R.id.vScrim);


        tvFootTitle = (TextView) findViewById(R.id.tvFootTitle);
        ivFootTab0 = (ImageView) findViewById(R.id.ivFootTab0);
        ivFootTab1 = (ImageView) findViewById(R.id.ivFootTab1);
        ivFootTab2 = (ImageView) findViewById(R.id.ivFootTab2);

        roFootTab0 = (RelativeLayout) findViewById(R.id.roFootTab0);
        roFootTab1 = (RelativeLayout) findViewById(R.id.roFootTab1);
        roFootTab2 = (RelativeLayout) findViewById(R.id.roFootTab2);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_add_white_48dp,
                        R.color.colorPrimary,
                        this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabTransformation.with(fab)
                        .duration(200)
                        .transformTo(toolbarFooter);
                vScrim.setVisibility(View.VISIBLE);
            }
        });

        vScrim.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FabTransformation.with(fab)
                        .duration(100)
                        .transformFrom(toolbarFooter);
                v.setVisibility(View.GONE);
                return false;
            }
        });

        ivFootTab0.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_photo_camera_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );


        ivFootTab1.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_credit_card_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );

        // upto provider
        ivFootTab2.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_create_white_24dp,
                        R.color.colorPrimary,
                        this
                )
        );


        roFootTab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResultToTakeReceipt();
            }
        });
        roFootTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResultToLoadSMS();
            }
        });
        roFootTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, NewOQActivity.class);
                intent.putExtra("mDoWhat", OQT.DoWhat.GET);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiDecoration(this);
        setOcl();
    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();
        if (!isMe) {

            tvTitle_DrawerHeader.setText(profile.getFull_name());
            tvSubTitle_DrawerHeader.setText(profile.getEmail());
            View.OnClickListener oclViewPhoto = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ViewPhotoActivity.class);
                    intent.putExtra("Profile", profile);
                    startActivity(intent);
                }
            };
            ro_person_photo_48dip__lessmargin.setOnClickListener(oclViewPhoto);
            ro_person_photo_tv.setOnClickListener(oclViewPhoto);
            ro_person_photo_iv.setOnClickListener(oclViewPhoto);

        } else {

            tvTitle_DrawerHeader.setText(App.getUname(this));
            tvSubTitle_DrawerHeader.setText(App.getUemail(this));
            View.OnClickListener oclPicGallery = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResultPhotoGalleryToPROFILECHANGE();
                }
            };
            ro_person_photo_48dip__lessmargin.setOnClickListener(oclPicGallery);
            ro_person_photo_tv.setOnClickListener(oclPicGallery);
            ro_person_photo_iv.setOnClickListener(oclPicGallery);

        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //set Image

        JM.glideProfileThumb(
                profile.getUid(),
                profile.getFull_name(),
                ro_person_photo_iv,
                ro_person_photo_tv,
                this
        );


        initRVAdapter();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQ_PICK_IMAGE) {

                if (data != null
                        && data.getData() != null) {
                    Log.d(TAG, "data available");
                    Uri imageUri = data.getData();

                    if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                        //Intent.EXTRA_ALLOW_MULTIPLE
                        Log.d(TAG, "VERSION.SDK_INT >= 18 " + data.getData().toString());
                        CropImage.activity(imageUri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(this);


                    } else {
                        Log.d(TAG, "VERSION.SDK_INT < 18 " + data.getData().toString());
                        CropImage.activity(imageUri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(this);
                    }
                }


            }


        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                if (profile.getUid().equals(App.getUid(this))) {
                    uploadProfPhoto(resultUri, App.getUid(this));
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d(TAG, error.toString());

            }
        }


    }

    final int REQ_PICK_IMAGE_FOR_PROFILECHANGE = 95;

    public void startActivityForResultPhotoGalleryToPROFILECHANGE() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.change_profile_photo)),
                REQ_PICK_IMAGE_FOR_PROFILECHANGE);
    }


    StorageReference mTempStorageRefORIG;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx36;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx48;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx72;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx96;  //mTempStorageRef was previously used to transfer data.
    StorageReference mTempStorageRefpx144;  //mTempStorageRef was previously used to transfer data.
    ArrayList<StorageReference> arlRef = new ArrayList<>();


    public void uploadProfPhoto(
            final Uri uri,
            String uid
    ) {
        Log.d(TAG, "uploadProfPhoto()");

        /**
         * get Bitmap
         */

        try {

            ArrayList<Bitmap> arlBmp = new ArrayList<>();
            ArrayList<String> arlStr = new ArrayList<>();


            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            arlBmp.add(bitmap);
            arlStr.add(FStorageNode.pxProfileT.ORIG);
            //use bitmap and create thumnail file
            Bitmap thumbnail_036 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 36, 36);
            arlStr.add(FStorageNode.pxProfileT.px36);
            arlBmp.add(thumbnail_036);

            Bitmap thumbnail_048 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 48, 48);
            arlStr.add(FStorageNode.pxProfileT.px48);
            arlBmp.add(thumbnail_048);

            Bitmap thumbnail_072 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 72, 72);
            arlBmp.add(thumbnail_072);
            arlStr.add(FStorageNode.pxProfileT.px72);

            Bitmap thumbnail_096 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 96, 96);
            arlBmp.add(thumbnail_096);
            arlStr.add(FStorageNode.pxProfileT.px96);

            Bitmap thumbnail_144 =
                    ThumbnailUtils.extractThumbnail(
                            bitmap, 144, 144);
            arlBmp.add(thumbnail_144);
            arlStr.add(FStorageNode.pxProfileT.px144);

            arlRef = new ArrayList<>();
            arlRef.add(mTempStorageRefORIG);
            arlRef.add(mTempStorageRefpx36);
            arlRef.add(mTempStorageRefpx48);
            arlRef.add(mTempStorageRefpx72);
            arlRef.add(mTempStorageRefpx96);
            arlRef.add(mTempStorageRefpx144);


            for (int i = 0; i < 6; i++) {

                Log.d(TAG, "uploadProfPhoto() loop " + J.st(i));


                ByteArrayOutputStream os = new ByteArrayOutputStream();

                if (arlBmp.get(i).compress(Bitmap.CompressFormat.JPEG, 100, os)) {
                    byte[] bytes = os.toByteArray();


                    /**
                     metaData
                     **/
                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .setCustomMetadata("uid", uid)
                            .build();
                    /**
                     path
                     **/
                    arlRef.set(i,
                            App.fbaseStorageRef
                                    .child(FStorageNode.FirstT.PROFILE_PHOTO)
                                    .child(arlStr.get(i))
                                    .child(uid));

                    /**
                     main
                     **/
                    UploadTask uploadTask =
                            arlRef.get(i)
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

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Handle successful uploads on complete
                            Log.d(TAG, "onSuccess");

                            Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
                        }
                    });


                }
            }


        } catch (FileNotFoundException e) {
            Log.d(TAG, e.toString());
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }
    }


    ArrayList<ArrayList<OqDo>> arlArlOqDoPerReferOid;
    boolean isQueryAComplete = false;
    boolean isQueryBComplete = false;


    void uiOqDos(ArrayList<OqDo> oqDoList) {

        OqDoUtil.sortList(oqDoList);
        arlArlOqDoPerReferOid = OqDoUtil.getArlArlOqDoPerReferOid
                (oqDoList,
                        this);
        rvOQ.setAdapter(oqDoRVAdapter);

    }

    public class OqDoRVAdapter extends RecyclerView.Adapter<Ava2RelationDtlVHolder> {

        public ArrayList<ArrayList<OqDo>> arlArlOqDoPerReferOid = new ArrayList();

        public OqDoRVAdapter(ArrayList<ArrayList<OqDo>> arl) {
            super();
            arlArlOqDoPerReferOid = arl;
        }

        @Override
        public Ava2RelationDtlVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(App.getContext())
                    .inflate(R.layout.item_mainfrag0, parent, false);
            return new Ava2RelationDtlVHolder(view);
        }

        @Override
        public void onBindViewHolder(final Ava2RelationDtlVHolder holder,
                                     int position,
                                     List<Object>
                                             payloads) {
            super.onBindViewHolder(holder, position, payloads);

            final ArrayList<OqDo> listOqDo = arlArlOqDoPerReferOid.get(position);
            OqDoUtil.sortList(listOqDo);


            JM.glideProfileThumb(
                    listOqDo.get(0).profilea,
                    holder.ivAvaLeft,
                    holder.tvAvaLeft,
                    mActivity
            );

            JM.glideProfileThumb(
                    listOqDo.get(0).profileb,
                    holder.ivAvaRight,
                    holder.tvAvaRight,
                    mActivity
            );

            OqDoUtil.ivTwoAvaRelation(
                    holder.ivRelation,
                    listOqDo
            );


            JM.uiTvResultAmmount(
                    holder.tvResultAmmount,
                    OqDoUtil.getSumOqDoAmmountsAgreed(listOqDo, mActivity)
            );


            JM.uiTvResultAmmount2(
                    holder.tvResultAmmount2,
                    OqDoUtil.getSumOqDoAmmountsDisAgreed(listOqDo, mActivity)
            );

            holder.tvContent.setText(OqDoUtil.getOqDoListMostRecentStr(listOqDo));

            holder.ivMore.setImageDrawable(
                    JM.tintedDrawable(
                            R.drawable.ic_expand_more_white_48dp,
                            R.color.text_black_54,
                            mActivity
                    ));

            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.lolvTwoAvaHistory
                            .getVisibility() == View.GONE) {
                        holder.lolvTwoAvaHistory
                                .setVisibility(View.VISIBLE);

                        holder.ivMore.setImageDrawable(
                                JM.tintedDrawable(
                                        R.drawable.ic_expand_less_white_24dp,
                                        R.color.text_black_54,
                                        mActivity
                                ));

                        holder.lolvTwoAvaHistory.removeAllViews();
                        holder.lolvTwoAvaHistory.removeAllViewsInLayout();

                        for (OqDo oqDo : listOqDo) {

                            LoOppoFeed loMyOppo = new LoOppoFeed(mActivity);

                            JM.glideProfileThumb(
                                    oqDo.profilea,
                                    loMyOppo.ivAvatar,
                                    loMyOppo.tvAvatar,
                                    mActivity
                            );

                            loMyOppo.tvName.setText(oqDo.profilea.full_name);

                            loMyOppo.tvAmmount.setText(J.st1000(oqDo.ammount));

                            loMyOppo.tvDeed.setText(
                                    OqDoUtil.getOqDoDeedStr(oqDo)
                            );

                            holder.lolvTwoAvaHistory.addView(loMyOppo);
                        }


                        rvOQ.requestLayout();
                    } else if (holder.lolvTwoAvaHistory
                            .getVisibility() == View.VISIBLE) {
                        holder.lolvTwoAvaHistory
                                .setVisibility(View.GONE);
                        holder.ivMore.setImageDrawable(
                                JM.tintedDrawable(
                                        R.drawable.ic_expand_more_white_48dp,
                                        R.color.text_black_54,
                                        mActivity
                                ));
                        rvOQ.requestLayout();

                    }

                }
            });


        }


        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }


        @Override
        public void onBindViewHolder(Ava2RelationDtlVHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return arlArlOqDoPerReferOid.size();
        }
    }

    OqDoRVAdapter oqDoRVAdapter;
    void initRVAdapter() {

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        rvOQ.setHasFixedSize(true);
        rvOQ.setLayoutManager(new LinearLayoutManager(mActivity));
          oqDoRVAdapter = new OqDoRVAdapter(arlArlOqDoPerReferOid);
        rvOQ.setAdapter(oqDoRVAdapter);



        final ArrayList<OqDo> oqDoList = new ArrayList<>();

        Query queryA =
                App.fbaseDbRef
                        .child(FBaseNode0.OqDo)
                        .orderByChild("uidab")
                        .equalTo(App.getUid(this) + ",," + profile.uid);

        Query queryB =
                App.fbaseDbRef
                        .child(FBaseNode0.OqDo)
                        .orderByChild("uidab")
                        .equalTo(profile.uid + ",," + App.getUid(this));

        queryA
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            Iterable<DataSnapshot> i = dataSnapshot.getChildren();

                            for (DataSnapshot d : i) {

                                OqDo oqDo = d.getValue(OqDo.class);
                                oqDoList.add(oqDo);
                            }

                            isQueryAComplete = true;
                            if (isQueryBComplete) {
                                uiOqDos(oqDoList);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        queryB
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            Iterable<DataSnapshot> i = dataSnapshot.getChildren();

                            for (DataSnapshot d : i) {

                                OqDo oqDo = d.getValue(OqDo.class);

                                oqDoList.add(oqDo);

                            }
                            isQueryBComplete = true;
                            if (isQueryAComplete) {
                                uiOqDos(oqDoList);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




    }


    int REQ_PICK_SMS = 98;

    public void startActivityForResultToLoadSMS() {
        Intent intentLoadSMS = new Intent(this, SMSListActivity.class);
        startActivityForResult(intentLoadSMS, REQ_PICK_SMS);
    }

    private void uiDecoration(Activity activity) {
        ivChat.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_chat_white_48dp,
                        R.color.colorPrimary,
                        activity
                )
        );
        ivAddTran.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_add_white_48dp,
                        R.color.colorPrimary,
                        activity
                )
        );
    }


    void setOcl() {
        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //rid
                String rid = ChatUtil.createRidWith2Ids(App.getUid(mActivity), profile.getUid());

                //arlJsonProfilesInChat
                String strProfile = new Gson().toJson(profile);
                ArrayList<String> arlJsonProfilesInChat = new ArrayList();
                arlJsonProfilesInChat.add(strProfile);

                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtra("rid", rid);
                intent.putStringArrayListExtra
                        ("arlJsonProfilesInChat", arlJsonProfilesInChat);
                startActivity(intent);

            }
        });


        ivAddTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
