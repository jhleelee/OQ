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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.OqWrap;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.object.util.OqWrapUtil;
import com.jackleeentertainment.oq.ui.layout.viewholder.TwoAvatarsWithRelationDtlVHolder;
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

    }


    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();
        if (!isMe) {

            tvTitle_DrawerHeader.setText(profile.getFull_name());
            tvSubTitle_DrawerHeader.setText(profile.getEmail());

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


    FirebaseRecyclerAdapter recyclerAdapter;

    void initRVAdapter() {
        checkIfFirebaseListIsEmpty(mActivity);

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        rvOQ.setHasFixedSize(true);
        rvOQ.setLayoutManager(new LinearLayoutManager(mActivity));


        recyclerAdapter = new FirebaseRecyclerAdapter<OqWrap,
                TwoAvatarsWithRelationDtlVHolder
                >
                (OqWrap.class,
                        R.layout.lo_twoavatars_relation_names_explain_date_detail,
                        TwoAvatarsWithRelationDtlVHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyOqWraps)
                                .child(App.getUid(mActivity))
                                .child(profile.getUid())
                ) {

            public void populateViewHolder(
                    final TwoAvatarsWithRelationDtlVHolder twoAvatarsWithRelationDtlVHolder,
                    final OqWrap oqWrap,
                    final int position) {

                List<OqDo> oqDoList = oqWrap.getListoqdo();
                long ts = oqWrap.getTs();
                String gid = oqWrap.getGid();
                String qid = oqWrap.getQid();
                String wid = oqWrap.getWid();

                if (oqDoList != null && oqDoList.size() > 0) {
                    OqDoUtil.sortList(oqDoList);
                    OqDo oqDoFirst = oqDoList.get(0);

                    JM.glideProfileThumb(
                            oqDoFirst.getUida(),
                            oqDoFirst.getNamea(),
                            twoAvatarsWithRelationDtlVHolder.ivAvaLeft,
                            twoAvatarsWithRelationDtlVHolder.tvAvaLeft,
                            mActivity
                    );

                    JM.glideProfileThumb(
                            oqDoFirst.getUidb(),
                            oqDoFirst.getNameb(),
                            twoAvatarsWithRelationDtlVHolder.ivAvaRight,
                            twoAvatarsWithRelationDtlVHolder.tvAvaRight,
                            mActivity
                    );

                    OqWrapUtil.ivTwoAvaRelation(
                            twoAvatarsWithRelationDtlVHolder.ivRelation,
                            oqWrap);


                    twoAvatarsWithRelationDtlVHolder.tvTwoName.setText(
                            oqDoFirst.getNamea() + " • " + oqDoFirst.getNameb()
                    );

                    twoAvatarsWithRelationDtlVHolder.tvDate.setText(JT.str(oqWrap.getTs()));

                    twoAvatarsWithRelationDtlVHolder.tvContent.setText(OqWrapUtil.getOqWrapStr(
                            oqWrap));


                }


//                JM.tvAmtTextBgAboutMuOppo(twoAvatarsWithRelationDtlVHolder.tvAmtConfirmed, myOppo, 0);
//                JM.tvAmtTextBgAboutMuOppo(twoAvatarsWithRelationDtlVHolder.tvAmtArgued, myOppo, 1);
//                JM.tvAmtTextBgAboutMuOppo(twoAvatarsWithRelationDtlVHolder.tvAmtDone, myOppo, 2);


            }
        };

        rvOQ.setAdapter(recyclerAdapter);
    }


    void checkIfFirebaseListIsEmpty(Activity activity) {

        JM.V(roProgress);

        App.fbaseDbRef
                .child(FBaseNode0.MyOqWraps)
                .child(App.getUid(activity))
                .child(profile.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            JM.G(roProgress);
                            JM.V(ro_empty_list);
                        } else {
                            JM.G(roProgress);
                            JM.G(ro_empty_list);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        JM.G(roProgress);
                        JM.V(ro_empty_list);
                    }
                });

    }


}
