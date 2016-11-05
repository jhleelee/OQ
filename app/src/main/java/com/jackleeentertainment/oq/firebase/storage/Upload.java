package com.jackleeentertainment.oq.firebase.storage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;

import java.io.File;


/**
 * Created by Jacklee on 16. 5. 23..
 */
public class Upload {
    static String TAG = "Upload";



    public static void logTaskSnapshot(UploadTask.TaskSnapshot taskSnapshot) {
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getBytesTransferred() " + J.st(taskSnapshot.getBytesTransferred()));
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getTotalByteCount() " + J.st(taskSnapshot.getTotalByteCount()));
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getDownloadUrl() " + J.st(taskSnapshot.getDownloadUrl()));
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getUploadSessionUri() " + J.st(taskSnapshot.getUploadSessionUri()));
    }





    public static void uploadBitmap(Bitmap bitmap,
                                    final String firstpath,
                                    final String secondpathAkaMyUid,
                                    final String suffix) {

        LBR.send(LBR.SendSuffixT.SENDING);

        UploadTask uploadTask = App.fbaseStorageRef
                .child(firstpath)
                .child(secondpathAkaMyUid)
                .child(FStorageNode.createMediaFileNameToUpload(firstpath, secondpathAkaMyUid, suffix))
                .putStream(JM.getByteArrayInputStreamFromBitmap(bitmap));

        uploadTask
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        logTaskSnapshot(taskSnapshot);
                        LBR.send(taskSnapshot.getDownloadUrl() + "," + LBR.SendSuffixT.SENT,
                                taskSnapshot.getMetadata());
                    }
                });

    }


    public static void uploadFile(final String firstpath,
                                  final String secondpathAkaMyUid,
                                  String filenameAkaResolutionAkaPostId,
                                  Uri fileUri,
                                  Activity activity) {

        Log.d(TAG,

                "uploadFile(final String firstpath,\n" + firstpath + "\n" +
                        "final String secondpathAkaMyUid,\n" + secondpathAkaMyUid + "\n" +
                        "String filenameAkaResolutionAkaPostId,\n" + filenameAkaResolutionAkaPostId + "\n" +
                        "Uri fileUri,\n" + fileUri.toString() + "\n" +
                        "Activity activity) "

        );

        UploadTask uploadTask =
                App.fbaseStorageRef
                        .child(firstpath)
                        .child(secondpathAkaMyUid)
                        .child(filenameAkaResolutionAkaPostId + ".jpg")
                        .putFile(fileUri);

        uploadTask
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        LBR.send(taskSnapshot.getDownloadUrl() + "," + LBR.SendSuffixT.SENT,
                                taskSnapshot.getMetadata());
                    }
                })
                .addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                System.out.println("Upload is " + progress + "% done");
                            }
                        })
                .addOnPausedListener(
                        new OnPausedListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                                System.out.println("Upload is paused");
                            }
                        })
        ;


    }


    public static void uploadMyProfileImagesToFirebaseStorage(Uri uri, Activity activity) {

        Log.d(TAG, "uploadMyProfileImagesToFirebaseStorage : " + uri.toString());

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


        Log.d(TAG, "uploadMyProfileImagesToFirebaseStorage : Bitmaps are Created "
        );


        Upload.uploadBitmap(
                thumbnail_320,
                FStorageNode.FirstT.PROFILE_PHOTO,
                App.getUid(activity),
                null
        );

        Upload.uploadBitmap(
                thumbnail_036,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUid(activity),
                FStorageNode.pxProfileT.px36
        );


        Upload.uploadBitmap(
                thumbnail_048,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUid(activity),
                FStorageNode.pxProfileT.px48
        );


        Upload.uploadBitmap(
                thumbnail_072,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUid(activity),
                FStorageNode.pxProfileT.px72
        );


        Upload.uploadBitmap(
                thumbnail_096,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUid(activity),
                FStorageNode.pxProfileT.px96
        );


        Upload.uploadBitmap(
                thumbnail_144,
                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                App.getUid(activity),
                FStorageNode.pxProfileT.px144
        );
    }

}
