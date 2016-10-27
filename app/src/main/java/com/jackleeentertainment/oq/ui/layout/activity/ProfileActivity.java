package com.jackleeentertainment.oq.ui.layout.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.firebase.storage.Upload;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.soundcloud.android.crop.Crop;

/**
 * Created by Jacklee on 2016. 10. 24..
 */

public class ProfileActivity extends BaseActivity {
    static String TAG = "ProfileActivity";
    Activity mActivity = this;
    final int REQ_PICK_IMAGE = 99;

    Uri outputCropUri;
    Profile profile;
    ImageView ivClose;
    RecyclerView rvOQ;
    LinearLayout nav_header_main;
    RelativeLayout ro_person_photo_48dip__lessmargin;
    TextView tvTitle_DrawerHeader;
    TextView tvSubTitle_DrawerHeader;
    TextView ro_person_photo_tv;
    ImageView ro_person_photo_iv;
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
                findViewById(R.id.ro_person_photo_tv);
        ro_person_photo_iv = (ImageView) ro_person_photo_48dip__lessmargin.
                findViewById(R.id.ro_person_photo_iv);
        tvTitle_DrawerHeader = (TextView) findViewById(R.id.tvTitle_DrawerHeader);
        tvSubTitle_DrawerHeader = (TextView) findViewById(R.id.tvSubTitle_DrawerHeader);


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

        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View.OnClickListener oclPicGallery = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResultPhotoGallery();
            }
        };
        ro_person_photo_48dip__lessmargin.setOnClickListener(oclPicGallery);
        ro_person_photo_tv.setOnClickListener(oclPicGallery);
        ro_person_photo_iv.setOnClickListener(oclPicGallery);

        //set Image
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(App.fbaseStorageRef
                        .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                        .child(App.getUid(this))
                        .child(FStorageNode.createMediaFileNameToDownload(
                                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                App.getUid(this)
                        )))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ro_person_photo_iv.setVisibility(View.GONE);
                        ro_person_photo_tv.setVisibility(View.VISIBLE);
                        ro_person_photo_tv.setText(App.getUname(mActivity).substring(0, 1));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ro_person_photo_iv.setVisibility(View.VISIBLE);
                        ro_person_photo_tv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ro_person_photo_iv)

        ;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQ_PICK_IMAGE) {

                if (data != null
                        && data.getData() != null) {
                    Log.d(TAG, "data available");

                    if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
                        //Intent.EXTRA_ALLOW_MULTIPLE
                        Log.d(TAG, "VERSION.SDK_INT >= 18 " + data.getData().toString());
                        Crop.of(data.getData(), outputCropUri).asSquare().start(this);


                    } else {
                        Log.d(TAG, "VERSION.SDK_INT < 18 " + data.getData().toString());
                        Crop.of(data.getData(), outputCropUri).asSquare().start(this);
                    }
                }


            } else if (requestCode == Crop.REQUEST_CROP){
                Upload.uploadMyProfileImagesToFirebaseStorage(outputCropUri, this);
            }

        }

    }


    void startActivityForResultPhotoGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                JM.strById(R.string.receipt_photo)),
                REQ_PICK_IMAGE);
    }
}
