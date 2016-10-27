package com.jackleeentertainment.oq.ui.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.Receipt;
import com.jackleeentertainment.oq.ui.layout.activity.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 22..
 */

public class ReceiptPhotoRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static String TAG = "ReceiptPhotoRVAdapter";

    //Tesser
    private TessBaseAPI mTess; //Tess API reference
    String lanuageDataPath = ""; //path to folder containing language data file
    String ocrResult = null;

    //Ui
    Fragment mFragment;
    Activity mActivity;
    ArrayList<Receipt> mArrayListReceipt = new ArrayList<>();

    /**
     * ViewHolderReceipt
     */
    class ViewHolderReceipt extends RecyclerView.ViewHolder {

          CardView cardview__cardview_receiptocr;
        ImageView ivReceipt__cardview_receiptocr;
        TextView tvTitle__cardview_receiptocr;
        TextView tvSubTitle__cardview_receiptocr;
        ImageButton ibDelete__lo_receipt_action;
        ImageButton ibManual__lo_receipt_action;

        public ViewHolderReceipt(View itemView) { // root : cardview_titlesubtitlemediatwoactionbtn
            super(itemView);
            this. cardview__cardview_receiptocr = (CardView) itemView.findViewById(R.id
                     .cardview__cardview_receiptocr);
            this.      ivReceipt__cardview_receiptocr = (ImageView) itemView.findViewById(R.id.ivReceipt__cardview_receiptocr);
            this.     tvTitle__cardview_receiptocr = (TextView) itemView.findViewById(R.id.tvTitle__cardview_receiptocr);
            this.     tvSubTitle__cardview_receiptocr = (TextView) itemView.findViewById(R.id.tvSubTitle__cardview_receiptocr);
            this.    ibDelete__lo_receipt_action = (ImageButton) itemView.findViewById(R.id
                    .ibDelete__lo_receipt_action);
            this.  ibManual__lo_receipt_action = (ImageButton) itemView.findViewById(R.id.ibManual__lo_receipt_action);


        }




    }


    public ReceiptPhotoRVAdapter() {
        super();
    }


    public ReceiptPhotoRVAdapter(ArrayList<Receipt> arl, Activity activity) {
        super();
        this.mArrayListReceipt = arl;
        this.mActivity = activity;
        tessInit();
        Log.d(TAG, "ReceiptPhotoRVAdapter()");
     }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_receiptocr, parent, false);



       return new ViewHolderReceipt(itemView);
    }

    /**
     * Getter & Setter
     */


    /**
     * Usual
     */


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount()");

        if (mArrayListReceipt != null) {
            Log.d(TAG, "getItemCount() " + J.st(mArrayListReceipt.size()));
            return mArrayListReceipt.size();
        } else {
            return 0;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        Receipt receipt = mArrayListReceipt.get(position);
        Log.d(TAG, "onBindViewHolder receipt.uri "+receipt.uri.toString());

        ((ViewHolderReceipt) viewHolder).ivReceipt__cardview_receiptocr
                .setImageBitmap(
                        getBitmapData(receipt.uri)
                );

        ((ViewHolderReceipt) viewHolder).tvSubTitle__cardview_receiptocr
                .setText(
                        getOcrFromBitmap(
                                getBitmapData(receipt.uri)
                        )
                );

        ((ViewHolderReceipt) viewHolder).ibDelete__lo_receipt_action
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mActivity).showAlertDialogWithOkCancel(
                                R.string.delete_receipt_long,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mArrayListReceipt.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }
                        );
                    }
                });

        ((ViewHolderReceipt) viewHolder).ibManual__lo_receipt_action
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        // add data

                        ((BaseActivity) mActivity)
                                .showDialogFragment(bundle);
                    }
                });
    }


    /**
     * Tesser
     */


    Bitmap getBitmapData(Uri uri) {
        Log.d(TAG, "getBitmapData(Uri uri) " );

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore
                    .Images
                    .Media
                    .getBitmap(App.getContext().getContentResolver(), uri);

        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

        return bitmap;
    }


    public String getOcrFromBitmap(Bitmap bitmap) {
        Log.d(TAG, "getOcrFromBitmap(Bitmap bitmap)" );

        mTess.setImage(bitmap);
        return mTess.getUTF8Text();
    }

    public void tessInit() {
        Log.d(TAG, "tessInit()" );

        try {
            lanuageDataPath = App.getContext().getFilesDir() + "/tesseract/";

            //initialize Tesseract API
            String lang = "kor";
            mTess = new TessBaseAPI();
            checkFile(new File(lanuageDataPath + "tessdata/"));
            boolean isSuccess =   mTess.init(lanuageDataPath, lang);
            if(isSuccess){
                notifyDataSetChanged();
            }

        } catch (Exception e) {
            J.TOAST("Error fetching the file");
        }
    }


    private void checkFile(File dir) {
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles();
        }
        if (dir.exists()) {
            String datafilepath = lanuageDataPath + "/tessdata/kor.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = lanuageDataPath + "/tessdata/kor.traineddata";
            AssetManager assetManager = App.getContext().getAssets();

            InputStream instream = assetManager.open("tessdata/kor.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}