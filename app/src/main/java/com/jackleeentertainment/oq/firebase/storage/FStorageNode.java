package com.jackleeentertainment.oq.firebase.storage;

import android.content.Context;
import android.net.Uri;


import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;

import hugo.weaving.DebugLog;

/**
 * Created by Jacklee on 2016. 9. 25..
 */

public class FStorageNode {

    public static class PrefixT {
        public final static String LISTVIEWITEM = "l__";
        public final static String THUMBNAIL = "t__";
        public final static String EMPTY = "";
    }


    public static class FirstT {
        public final static String PROFILE_PHOTO = "profile_photo";
        public final static String PROFILE_PHOTO_THUMB = "profile_photo_thumb";

        public final static String PROFILE_VIDEO = "profile_video";
        public final static String PROFILE_VIDEO_THUMB = "profile_video_thumb";

        public final static String POST_PHOTO = "post_photo";
        public final static String POST_PHOTO_THUMB = "post_photo_thumb";

        public final static String POST_VIDEO = "post_video";
        public final static String POST_VIDEO_THUMB = "post_video_thumb";

        public final static String CHATATTACH_PHOTO = "chatatch_photo";
        public final static String CHATATTACH_PHOTO_THUMB = "chatatch_photo_thumb";
        public final static String CHATATTACH_VIDEO = "chatatch_video";
        public final static String CHATATTACH_VIDEO_THUMB = "chatatch_video_thumb";
    }

    public static class Suffix_PROFILE_PHOTO_THUMB_T {
        public final static String EMPTY = "";
        public final static String px36 = "__px036";
        public final static String px48 = "__px048";
        public final static String px72 = "__px072";
        public final static String px96 = "__px096";
        public final static String px144 = "__px144";
    }


    public static class Suffix_POST_PHOTO_THUMB_T {
        public final static String EMPTY = "";
        public final static String px120 = "__px120";
        public final static String px160 = "__px160";
        public final static String px240 = "__px240";
        public final static String px320 = "__px320";
        public final static String px480 = "__px480";
    }


    public static class Suffix_CHATATTACH_PHOTO_THUMB_T {
        public final static String EMPTY = "";
        public final static String px60 = "__px060";
        public final static String px80 = "__px080";
        public final static String px120 = "__px120";
        public final static String px160 = "__px160";
        public final static String px240 = "__px240";
    }


    @DebugLog
    public static String createFileNameToUpload(Uri fileUri, Context context) {
        String dotExtention = fileUri.toString().substring(fileUri.toString().lastIndexOf("."));
        String filename = App.getUID() + "_" + J.st(System.currentTimeMillis()) + dotExtention;
        return filename;
    }


    @DebugLog
    public static String createFileName(
                                        String firstPart,
                                        String secondPart,
                                        String suffix
    ) {


        if (firstPart.equals(FirstT.PROFILE_PHOTO) ||
                firstPart.equals(FirstT.POST_PHOTO) ||
                firstPart.equals(FirstT.CHATATTACH_PHOTO)
                ) {
            return
                     firstPart + "__" //firstNode
                    + secondPart +  //uid or oid

                    ".jpg";
        } else if (firstPart.equals(FirstT.PROFILE_VIDEO) ||
                firstPart.equals(FirstT.POST_VIDEO) ||
                firstPart.equals(FirstT.CHATATTACH_VIDEO)
                ) {
            return
                     firstPart + "__" //firstNode
                    + secondPart + //uid

                    ".mp4";
        } else if (firstPart.equals(FirstT.PROFILE_PHOTO_THUMB) ||
                firstPart.equals(FirstT.PROFILE_VIDEO_THUMB) ||
                firstPart.equals(FirstT.POST_PHOTO_THUMB) ||
                firstPart.equals(FirstT.POST_VIDEO_THUMB) ||
                firstPart.equals(FirstT.CHATATTACH_PHOTO_THUMB) ||
                firstPart.equals(FirstT.CHATATTACH_VIDEO_THUMB)
                ) {

            return
                     firstPart + "__" //firstNode
                    + secondPart + "__"  //uid
                    + JM.getSuffixOfImgWithDeviceDpi(firstPart) +
                    ".jpg";
        }


        return null;
    }


    @DebugLog
    public static String createPostFileName(String prefix,
                                            String firstPart,
                                            String secondPart,
                                            long timeStamp,
                                            String suffix
    ) {


        if (firstPart.equals(FirstT.POST_PHOTO)) {
            return
                     firstPart + "__" //firstNode
                    + secondPart + "__"  //uid
                    + J.st(timeStamp) //ts
                    + ".jpg";
        } else if (firstPart.equals(FirstT.POST_PHOTO)) {
            return
                     firstPart + "__" //firstNode
                    + secondPart + "__"  //uid
                    + J.st(timeStamp) //ts
                    + ".jpg";
        }

        return null;
    }


    @DebugLog
    public static String getFileName(String fileNameAndExtention) {
        int pos = fileNameAndExtention.lastIndexOf(".");
        if (pos > 0) {
            return fileNameAndExtention.substring(0, pos);
        }
        return null;
    }

    @DebugLog
    public static String getFileExtention(String fileNameAndExtention) {
        String filenameArray[] = fileNameAndExtention.split("\\.");
        return filenameArray[filenameArray.length - 1];
    }

}
