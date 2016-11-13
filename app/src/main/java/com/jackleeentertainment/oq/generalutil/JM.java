package com.jackleeentertainment.oq.generalutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
 import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.OqDoPair;
import com.jackleeentertainment.oq.object.OqWrap;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.OqDoUtil;

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

        if (fStorageNodeT.equals(FStorageNode.FirstT.PROFILE_PHOTO)) {
            switch (getDeviceDpi()) {
                case DisplayMetrics.DENSITY_LOW:
                    return FStorageNode.pxProfileT.px36;

                case DisplayMetrics.DENSITY_DEFAULT:
                    return FStorageNode.pxProfileT.px48;

                case DisplayMetrics.DENSITY_HIGH:
                case DisplayMetrics.DENSITY_280:

                    return FStorageNode.pxProfileT.px72;

                case DisplayMetrics.DENSITY_XHIGH:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:

                    return FStorageNode.pxProfileT.px96;

                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_560:

                    return FStorageNode.pxProfileT.px144;

                case DisplayMetrics.DENSITY_XXXHIGH:
                    return FStorageNode.pxProfileT.px144;

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


    public static int getHalfProfileSizeOfImgWithDeviceDpi() {
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


    public static Drawable drawableById(int intId) {
        return ContextCompat.getDrawable(App.getContext(), intId);
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
    public static void uiTextViewPosiNega(TextView textView, boolean bool) {
        if (bool) {
            BGC(textView, R.color.colorAccent);
            TC(textView, R.color.colorPrimary);
        } else {
            BGC(textView, R.color.material_grey500);
            TC(textView, R.color.text_black_87);
        }
    }

    public static void btEnable(Button btEnable, boolean bool) {
        btEnable.setClickable(bool);
        btEnable.setEnabled(bool);
        if (!bool) {
            btEnable.setTextColor(JM.colorById(R.color.text_black_54));
        } else {
            btEnable.setTextColor(JM.colorById(R.color.colorPrimary));
        }

    }


//    public static void tvAmtTextBgAboutMuOppo(TextView tvAmt, MyOppo myOppo, int
//            zeroNormalOneArguedTwoDone
//    ) {
//        if (zeroNormalOneArguedTwoDone == 0) {
//
//            long val = myOppo.getAmticlaim() - myOppo.getAmtheclaim();
//
//            if (val > 0) {
//                tvAmt.setText(J.st(val));
//                JM.BGD(tvAmt, R.drawable.tv_oppo_normal_iplus);
//            } else if (val == 0) {
//                tvAmt.setVisibility(View.GONE);
////                tvAmt.setText(JM.strById(R.string.none));
////                JM.BGD(tvAmt,R.drawable.tv_oppo_confirmed_zero);
//            } else if (val < 0) {
//                tvAmt.setText(J.st(-val));
//                JM.BGD(tvAmt, R.drawable.tv_oppo_normal_iminus);
//            }
//
//        } else if (zeroNormalOneArguedTwoDone == 1) {
//
//            long val = myOppo.getAmticlaimarg() - myOppo.getAmtheclaimarg();
//
//            if (val > 0) {
//                tvAmt.setText(JM.strById(R.string.symbol_krw) + J.st(val) + JM.strById(R.string
//                        .request));
//                JM.BGD(tvAmt, R.drawable.tv_oppo_argued_iplus);
//                JM.TC(tvAmt, R.color.getPrimary);
//
//            } else if (val == 0) {
//                tvAmt.setVisibility(View.GONE);
////                tvAmt.setText(JM.strById(R.string.none));
////                JM.BGD(tvAmt,R.drawable.tv_oppo_yet_zero);
//            } else if (val < 0) {
//                tvAmt.setText(JM.strById(R.string.symbol_krw) + J.st(-val) + JM.strById(R.string
//                        .request));
//                JM.BGD(tvAmt, R.drawable.tv_oppo_argued_iminus);
//                JM.TC(tvAmt, R.color.payPrimary);
//
//            }
//
//        } else if (zeroNormalOneArguedTwoDone == 2) {
//            long val = myOppo.getAmticlaimdone() - myOppo.getAmtheclaimdone();
//
//            if (val > 0) {
//                tvAmt.setText(JM.strById(R.string.symbol_krw) + J.st(val) + JM.strById(R.string
//                        .request));
//                JM.TC(tvAmt, R.color.getPrimary);
//            } else if (val == 0) {
//                tvAmt.setText(JM.strById(R.string.none));
//
//            } else if (val < 0) {
//                tvAmt.setText(JM.strById(R.string.symbol_krw) + J.st(-val) + JM.strById(R.string
//                        .request));
//                JM.TC(tvAmt, R.color.payPrimary);
//            }
//        }
//    }


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
                                .child(FStorageNode.FirstT.PROFILE_PHOTO)
                                .child(uid)
                                .child(FStorageNode.createMediaFileNameToDownload(
                                        FStorageNode.FirstT.PROFILE_PHOTO,
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

            } catch (Exception e) {

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

    public static Drawable tintedDrawable(int drawableId, int colorId, Context context) {
        Drawable normalDrawable = context.getResources().getDrawable(drawableId);
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(colorId));
        return wrapDrawable;
    }

    /********************************
     * Pixel Calc
     ********************************/

    public static float dpFromPx(@NonNull final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpFromPx(final float px) {
        return px / App.getContext().getResources().getDisplayMetrics().density;
    }


    public static int pxFromDp(@NonNull final Context context, final float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }


    public static int pxFromDp(final float dp) {
        return Math.round(dp * App.getContext().getResources().getDisplayMetrics().density);
    }


    /********************************
     * Glide
     ********************************/


    public static void glideProfileThumb(final String uid,
                                         final String uname,
                                         final ImageView iv,
                                         final TextView tv,
                                         final Activity
                                                 mActivity) {
        //set Image
        Glide.with(mActivity)
                .using(new FirebaseImageLoader())
                .load(App.fbaseStorageRef
                        .child(FStorageNode.FirstT.PROFILE_PHOTO)
                        .child(JM.getSuffixOfImgWithDeviceDpi(FStorageNode.FirstT.PROFILE_PHOTO))
                        .child(uid))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        iv.setVisibility(View.GONE);
                        tv.setVisibility(View.VISIBLE);
                        tv.setText(uname.substring(0, 1));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        iv.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(iv);
    }

    public static void glideProfileThumb(final String uid,
                                         final String uname,
                                         final ImageView iv, final TextView tv,
                                         final Fragment mFragment) {
        //set Image
        Glide.with(mFragment)
                .using(new FirebaseImageLoader())
                .load(App.fbaseStorageRef
                        .child(FStorageNode.FirstT.PROFILE_PHOTO)
                        .child(JM.getSuffixOfImgWithDeviceDpi(FStorageNode.FirstT.PROFILE_PHOTO))
                        .child(uid))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        iv.setVisibility(View.GONE);
                        tv.setVisibility(View.VISIBLE);
                        tv.setText(uname.substring(0, 1));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        iv.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(iv);
    }






    public static void glideProfileOrig(final String uid,
                                         final ImageView iv, final TextView tv,
                                         final Activity
                                                 mActivity) {
        //set Image
        Glide.with(mActivity)
                .using(new FirebaseImageLoader())
                .load(App.fbaseStorageRef
                        .child(FStorageNode.FirstT.PROFILE_PHOTO)
                        .child("orig")
                        .child(uid))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        iv.setVisibility(View.GONE);
                        tv.setVisibility(View.VISIBLE);
                        tv.setText(JM.strById(R.string.file_not_found));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        iv.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(iv);
    }










   public static void  ivTwoAvaRelation(ImageView iv, OqWrap oqWrap){

       List<OqDo> listoqdo = oqWrap.getListoqdo();
       OqDoUtil.sortList(listoqdo);

       OqDo firstOqdo = listoqdo.get(0);

       if (firstOqdo.getOqwhat().equals(OQT.DoWhat.GET)&&
               firstOqdo.getOqwhen().equals(OQT.DoWhen.FUTURE)){

           List<OqDo> listBaPayFuture = OqDoUtil.getListBaPayFuture(listoqdo);
           long sumListBaPayFuture = OqDoUtil.getSumAmt(listBaPayFuture);

           List<OqDo> listAbGetPast = OqDoUtil.getListAbGetPast(listoqdo);
           long sumListAbGetPast = OqDoUtil.getSumAmt(listAbGetPast);

           List<OqDo> listBaPayPast = OqDoUtil.getListBaPayPast(listoqdo);
           long sumListBaPayPast = OqDoUtil.getSumAmt(listBaPayPast);


           if (firstOqdo.getAmmount()==sumListBaPayFuture){ //Basic - B agrees with A's req

               if (firstOqdo.getAmmount()==sumListBaPayPast){ // B claims he paid to A. is it true?

                   if (firstOqdo.getAmmount()==sumListAbGetPast){ // A admits is is paid all

                       JM.BGD(iv, R.drawable.cir_requested_so_paid);
                       JM.ID(iv, R.drawable.ic_check_white_24dp);

                   } else { //not paid all (incl partially paid)
                       JM.BGD(iv, R.drawable.cir_requested_so_claimpaid);
                       JM.ID(iv, R.drawable.ic_check_white_24dp);
                   }


               } else { //not paid all (incl partially paid)
                   JM.BGD(iv, R.drawable.cir_requested);
                   JM.ID(iv, R.drawable.ic_arrow_forward_white_24dp);
               }


           } else if (firstOqdo.getAmmount()>sumListBaPayFuture){ // B does not agree with A's req
               JM.BGD(iv, R.drawable.cir_requested_but_argued);
               JM.ID(iv, R.drawable.ic_arrow_forward_white_24dp);
           }

       }

   }







}
