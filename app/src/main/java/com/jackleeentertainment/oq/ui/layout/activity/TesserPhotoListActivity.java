package com.jackleeentertainment.oq.ui.layout.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Receipt;
import com.jackleeentertainment.oq.ui.layout.fragment.TesserPhotoListFrag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 21..
 */

public class TesserPhotoListActivity extends BaseFragmentContainFullDialogActivity {
    String TAG = "TesserPhotoListActivity";
    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uriPhoto = getIntent().getStringExtra("uriPhoto");
        uri = Uri.parse(uriPhoto);
        ArrayList<Receipt> arl = new ArrayList<>();
        Receipt receipt = new Receipt();
        receipt.uri = Uri.parse(uriPhoto);
        arl.add(receipt);
        goToFragment(  TesserPhotoListFrag.newInstance(arl), R.id.fr_content);
    }

    @Override
    void initUIDataOnResume() {
        super.initUIDataOnResume();
        tvToolbarTitle.setText(JM.strById(R.string.receipt_photo));
    }

    @Override
    void initUIOnCreate() {
        super.initUIOnCreate();
        tabLayout.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }
}


