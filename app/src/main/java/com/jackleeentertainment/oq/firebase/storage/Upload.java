package com.jackleeentertainment.oq.firebase.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;


/**
 * Created by Jacklee on 16. 5. 23..
 */
public class Upload {
    static String TAG = "Upload";

    public static void uploadBitmap(Bitmap bitmap,
                                    final String firstpath,
                                    final String secondpathAkaMyUid,
                                    final String filenameAkaResolution) {

        UploadTask uploadTask = App.fbaseStorageRef
                .child(firstpath)
                .child(secondpathAkaMyUid)
                .child(filenameAkaResolution + ".jpg")
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
                                  String filenameAkaResolution,
                                  Uri fileUri,
                                  Context context) {

        UploadTask uploadTask =
                App.fbaseStorageRef
                        .child(firstpath)
                        .child(secondpathAkaMyUid)
                        .child(filenameAkaResolution + ".jpg")
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
                });
    }



    public static void logTaskSnapshot(UploadTask.TaskSnapshot taskSnapshot) {
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getBytesTransferred() " + J.st(taskSnapshot.getBytesTransferred()));
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getTotalByteCount() " + J.st(taskSnapshot.getTotalByteCount()));
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getDownloadUrl() " + J.st(taskSnapshot.getDownloadUrl()));
        Log.d(TAG + ":" + "logTaskSnapshot()", "taskSnapshot.getUploadSessionUri() " + J.st(taskSnapshot.getUploadSessionUri()));
    }


}
