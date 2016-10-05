package com.jackleeentertainment.oq.ui.layout.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.database.SetValue;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.firebase.storage.Upload;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;

import java.io.File;

import static com.jackleeentertainment.oq.generalutil.ContactsUtil.getArlPhoneNumFromMyDevice;
import static com.jackleeentertainment.oq.generalutil.ContactsUtil.getArlEmailFromMyDevice;

/**
 * Created by jaehaklee on 2016. 10. 3..
 */

public class FirstVisitActivity extends BaseActivity {



    void finallyNewUserEnters(Profile profileToMe, Uri uri){

        // show progress dialog

        SetValue.profile(
                FBaseNode0.ProfileToPublic,
                ProfileUtil.getProfileToPublic(profileToMe),
                true);

        SetValue.profile(
                FBaseNode0.ProfileToMe,
                profileToMe,
                true);

        uploadMyProfileImagesToFirebaseStorage(uri);

        SetValue.myPossibleContactsWithPhoneOrEmail(
                getArlPhoneNumFromMyDevice()
        );

        SetValue.myPossibleContactsWithPhoneOrEmail(
                getArlEmailFromMyDevice()
        );

        // end showing dialog
    }




    void createMyProfileAtFirebaseDatabase(Profile profile) {

        App.fbaseDbRef
                .child(FBaseNode0.ProfileToMe)
                .child(App.getUID())
                .setValue(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

        Profile profile2 = new Profile();
        profile2.setFull_name(profile.getFull_name());
        profile2.setMiddle_name(profile.getMiddle_name());
        profile2.setFirst_name(profile.getFirst_name());
        profile2.setEmail(profile.getEmail());

        App.fbaseDbRef
                .child(FBaseNode0.ProfileToPublic)
                .child(App.getUID())
                .setValue(profile2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }




    void uploadMyProfileImagesToFirebaseStorage(Uri uri) {

        File file = new File(uri.getPath());

        Bitmap thumbnail_320 =
                ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeFile(
                                file.getPath()), 320, 320);

        Bitmap thumbnail_036 =
                ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeFile(
                                file.getPath()), 36, 36);

        Bitmap thumbnail_048 =
                ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeFile(
                                file.getPath()), 48, 48);

        Bitmap thumbnail_072 =
                ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeFile(
                                file.getPath()), 72, 72);

        Bitmap thumbnail_096 =
                ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeFile(
                                file.getPath()), 96, 96);

        Bitmap thumbnail_144 =
                ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeFile(
                                file.getPath()), 144, 144);


        Upload.uploadBitmap(
                thumbnail_320,
                FStorageNode.FirstT.PROFILE_PHOTO,
                App.getUID(),
                null
        );

        Upload.uploadBitmap(
                thumbnail_036,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUID(),
                FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px36
        );


        Upload.uploadBitmap(
                thumbnail_048,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUID(),
                FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px48
        );


        Upload.uploadBitmap(
                thumbnail_072,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUID(),
                FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px72
        );


        Upload.uploadBitmap(
                thumbnail_096,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUID(),
                FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px96
        );


        Upload.uploadBitmap(
                thumbnail_144,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUID(),
                FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px144
        );
    }

}