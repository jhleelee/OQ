package com.jackleeentertainment.oq.generalutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.object.Profile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class JM {
    static boolean VERBOSE = false;

    /********************************
     * Machine Configuration
     ********************************/


    public static int getDeviceDpi() {
        DisplayMetrics metrics = App.getContext().getResources().getDisplayMetrics();
        return metrics.densityDpi;
    }

    public static String getSuffixOfImgWithDeviceDpi(String fStorageNodeT) {

        if (fStorageNodeT.equals(FStorageNode.FirstT.CHATATTACH_PHOTO_THUMB)) {
            switch (getDeviceDpi()) {
                case DisplayMetrics.DENSITY_LOW:
                    return FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px36;

                case DisplayMetrics.DENSITY_DEFAULT:
                    return FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px48;

                case DisplayMetrics.DENSITY_HIGH:
                case DisplayMetrics.DENSITY_280:

                    return FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px72;

                case DisplayMetrics.DENSITY_XHIGH:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:

                    return FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px96;

                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_560:

                    return FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px144;

                case DisplayMetrics.DENSITY_XXXHIGH:
                    return FStorageNode.Suffix_PROFILE_PHOTO_THUMB_T.px144;

                default:
                    return null;
            }
        } else if (fStorageNodeT.equals(FStorageNode.FirstT.POST_PHOTO_THUMB)) {

            switch (getDeviceDpi()) {
                case DisplayMetrics.DENSITY_LOW:
                    return FStorageNode.Suffix_POST_PHOTO_THUMB_T.px120;

                case DisplayMetrics.DENSITY_DEFAULT:
                    return FStorageNode.Suffix_POST_PHOTO_THUMB_T.px160;

                case DisplayMetrics.DENSITY_HIGH:
                case DisplayMetrics.DENSITY_280:

                    return FStorageNode.Suffix_POST_PHOTO_THUMB_T.px240;

                case DisplayMetrics.DENSITY_XHIGH:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:

                    return FStorageNode.Suffix_POST_PHOTO_THUMB_T.px320;

                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_560:

                    return FStorageNode.Suffix_POST_PHOTO_THUMB_T.px480;

                case DisplayMetrics.DENSITY_XXXHIGH:
                    return FStorageNode.Suffix_POST_PHOTO_THUMB_T.px480;

                default:
                    return null;
            }


        } else if (fStorageNodeT.equals(FStorageNode.FirstT.CHATATTACH_PHOTO_THUMB)) {

            switch (getDeviceDpi()) {
                case DisplayMetrics.DENSITY_LOW:
                    return FStorageNode.Suffix_CHATATTACH_PHOTO_THUMB_T.px60;

                case DisplayMetrics.DENSITY_DEFAULT:
                    return FStorageNode.Suffix_CHATATTACH_PHOTO_THUMB_T.px80;

                case DisplayMetrics.DENSITY_HIGH:
                case DisplayMetrics.DENSITY_280:

                    return FStorageNode.Suffix_CHATATTACH_PHOTO_THUMB_T.px120;

                case DisplayMetrics.DENSITY_XHIGH:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:

                    return FStorageNode.Suffix_CHATATTACH_PHOTO_THUMB_T.px160;

                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_560:

                    return FStorageNode.Suffix_CHATATTACH_PHOTO_THUMB_T.px240;

                case DisplayMetrics.DENSITY_XXXHIGH:
                    return FStorageNode.Suffix_CHATATTACH_PHOTO_THUMB_T.px240;

                default:
                    return null;
            }


        }

        return null;
    }


    public static int getHalfProfileSizeOfImgWithDeviceDpi(){
        switch (getDeviceDpi()) {
            case DisplayMetrics.DENSITY_LOW:
                return 18;

            case DisplayMetrics.DENSITY_DEFAULT:
                return 24;

            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_280:

                return 36;

            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:

                return 48;

            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_560:

                return 72;

            case DisplayMetrics.DENSITY_XXXHIGH:
                return 72;

            default:
                return 72;
        }
    }



    /********************************
     * Getting Resources Id
     ********************************/

    public static String strById(int intId) {
        return App.getContext().getResources().getString(intId);
    }

    public static String strById(int intId, Context context) {
        return context.getResources().getString(intId);
    }


    public static int colorById(int intId) {
        return ContextCompat.getColor(App.getContext(), intId);
    }

    public static int colorById(int intId, Context context) {
        return ContextCompat.getColor(context, intId);
    }

    public static Drawable drawableById(int intId, Context context) {
        return ContextCompat.getDrawable(context, intId);
    }


    /********************************
     * Pulling Resources
     ********************************/

    public static void BGC(View view, int intId) {

        view.setBackgroundColor(
                ContextCompat.getColor(App.getContext(), intId)
        );

    }


    public static void BGD(View view, int intId) {

        view.setBackgroundDrawable(
                null
        );

        view.setBackgroundDrawable(
                ContextCompat.getDrawable(App.getContext(), intId)
        );

    }


    public static void ID(ImageView iv, int intId) {

        iv.setImageDrawable(
                null
        );

        iv.setImageDrawable(
                ContextCompat.getDrawable(App.getContext(), intId)
        );

    }

    public static void ID(ImageView iv, int intIdtrue, int intIdfalse, boolean bool) {

        iv.setImageDrawable(
                null
        );
        if (bool) {
            ID(iv, intIdtrue);
        } else {
            ID(iv, intIdfalse);
        }
    }


    public static void BGD(ImageView iv, int intId) {

        iv.setBackground(
                null
        );

        iv.setBackground(
                ContextCompat.getDrawable(App.getContext(), intId)
        );
    }


    public static void BGD(ImageView iv, int intIdtrue, int intIdfalse, boolean bool) {

        iv.setImageDrawable(
                null
        );
        if (bool) {
            BGD(iv, intIdtrue);
        } else {
            BGD(iv, intIdfalse);
        }
    }


    public static void TC(TextView view, int intId) {

        view.setTextColor(
                ContextCompat.getColor(App.getContext(), intId)
        );

    }

    public static void TT(TextView tv, int intId) {

        tv.setText(
                App.getContext().getResources().getString(intId)
        );

    }

    /********************************
     * Ui
     ********************************/
    public static void uiTextViewPosiNega(TextView textView, boolean bool){
        if (bool){
            BGC(textView, R.color.colorAccent);
            TC(textView, R.color.white);
        } else {
            BGC(textView, R.color.material_grey500);
            TC(textView, R.color.text_black_87);
        }
    }



    /********************************
     * data manipulation for Bitmap
     ********************************/

    public static ByteArrayInputStream getByteArrayInputStreamFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] barBitmap = bos.toByteArray();
        return new ByteArrayInputStream(barBitmap);
    }


    /********************************
     * Image manipulation
     ********************************/
    static int IMAGELOADER_PROGRESS_COUNTER = 0;

    public static void loadMultipleProfilePhotoFromFbase(
            final Context context,
            final List<Profile> arlProfiles,
            final ImageView imageView,
            int pixel) {

        final ArrayList<Bitmap> arlBitmap = new ArrayList<>();


        IMAGELOADER_PROGRESS_COUNTER = 0;

        for (int i = 0; i < arlProfiles.size(); i++) {

            String uid = arlProfiles.get(i).getUid();

            //set Image
            try {
                Bitmap bitmap = Glide.with(context)
                        .using(new FirebaseImageLoader())
                        .load(App.fbaseStorageRef
                                .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                .child(uid)
                                .child(FStorageNode.createMediaFileNameToDownload(
                                        FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                        uid

                                )))
                        .asBitmap()
                        .into(JM.getHalfProfileSizeOfImgWithDeviceDpi(), JM.getHalfProfileSizeOfImgWithDeviceDpi())
                        .get();
                arlBitmap.add(bitmap);

                IMAGELOADER_PROGRESS_COUNTER++;
                if (IMAGELOADER_PROGRESS_COUNTER == arlBitmap.size()) {
                    showMultipleProfilePhotoWithBitmap(arlBitmap, imageView);
                }

            } catch (Exception e){

            }



        }


    }

    public static void showMultipleProfilePhotoWithBitmap(ArrayList<Bitmap> arlBitmap, ImageView iv) {

        ArrayList<Bitmap> arlCroppedBitmap = new ArrayList<>();

        if (arlBitmap.size() == 1) {
            iv.setImageBitmap(arlBitmap.get(0));

        } else {
            if (arlBitmap.size() == 2) {

                for (int i = 0; i < 2; i++) {
                    arlCroppedBitmap.add(cropBitmapCenterWidth50percent(arlBitmap.get(i)));
                }
            } else if (arlBitmap.size() == 3) {

                arlCroppedBitmap.add(cropBitmapCenterWidth50percent(arlBitmap.get(0)));
                arlCroppedBitmap.add(cropBitmapCenterWidth25Height25percent(arlBitmap.get(1)));
                arlCroppedBitmap.add(cropBitmapCenterWidth25Height25percent(arlBitmap.get(2)));

            } else {
                for (int i = 0; i < 4; i++) {
                    arlCroppedBitmap.add(cropBitmapCenterWidth25Height25percent(arlBitmap.get(i)));
                }
            }
            iv.setImageBitmap(createSingleImageFromMultipleImages(arlCroppedBitmap));
        }

    }


    private static Bitmap createSingleImageFromMultipleImages(ArrayList<Bitmap> arlBitmap) {

        Bitmap result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        switch (arlBitmap.size()) {
            case 2:
                canvas.drawBitmap(arlBitmap.get(0), 0f, 0f, null);
                canvas.drawBitmap(arlBitmap.get(1), 100f, 0f, null);
                return result;

            case 3:

                canvas.drawBitmap(arlBitmap.get(0), 0f, 0f, null);
                canvas.drawBitmap(arlBitmap.get(1), 100f, 0f, null);
                canvas.drawBitmap(arlBitmap.get(2), 100f, 100f, null);

                return result;

            case 4:
                canvas.drawBitmap(arlBitmap.get(0), 0f, 0f, null);
                canvas.drawBitmap(arlBitmap.get(1), 100f, 0f, null);
                canvas.drawBitmap(arlBitmap.get(2), 0f, 100f, null);
                canvas.drawBitmap(arlBitmap.get(3), 100f, 100f, null);

                return result;
            default:
                return null;
        }


    }


    public static Bitmap cropBitmapIntoSquare(Bitmap srcBmp) {
        Bitmap dstBmp;

        if (srcBmp.getWidth() > srcBmp.getHeight()) {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );
            return dstBmp;
        } else if (srcBmp.getWidth() < srcBmp.getHeight()) {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
            return dstBmp;
        } else {
            return srcBmp;
            // EQUALS
        }

    }


    public static Bitmap cropBitmapCenterWidth25Height25percent(Bitmap srcBmp) {

        Bitmap squareBmp = cropBitmapIntoSquare(srcBmp);
        // now, this is an rectangle

        return Bitmap.createBitmap(squareBmp, squareBmp.getWidth() / 4, squareBmp.getWidth() / 4,
                squareBmp.getWidth() / 2, squareBmp.getWidth() / 2);
    }

    public static Bitmap cropBitmapCenterWidth50percent(Bitmap srcBmp) {

        Bitmap squareBmp = cropBitmapIntoSquare(srcBmp);
        // now, this is an rectangle

        return Bitmap.createBitmap(squareBmp, squareBmp.getWidth() / 4, 0, squareBmp.getWidth() / 2, squareBmp.getWidth());
    }


    /********************************
     * visibility
     ********************************/

    public static void visibleOrGone(View v, boolean b) {
        if (b) {
            JM.V(v);
        } else {
            JM.G(v);
        }
    }

    public static void V(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    public static void IV(@NonNull View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void G(@NonNull View view) {
        view.setVisibility(View.GONE);
    }


    /********************************
     * tint
     ********************************/


    public static void tint(ImageView iv, int intId) {
        iv.setColorFilter(ContextCompat.getColor(
                App.getContext(), intId),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }


    public static void tint(ImageView iv, int intId, Context context) {
        iv.setColorFilter(ContextCompat.getColor(
                context, intId),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }


    public static void tint(ImageView iv, int intTrueColor, int intFalseColor, boolean bool, Context context) {
        if (bool) {
            iv.setColorFilter(ContextCompat.getColor(context, intTrueColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            iv.setColorFilter(ContextCompat.getColor(context, intFalseColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

}
