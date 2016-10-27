package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.ui.layout.activity.SelectedPhotoListActivity;
import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class NewOQFrag1 extends Fragment {

    String TAG = this.getClass().getSimpleName();
    final int REQ_PHOTO_GALLERY = 99;
    final int REQ_SELECTED_PHOTO_LIST = 98;
    View view;
    ImageView ivPhotoMain, ivPhotoSub;
    TextView tvPhotoSubNum;
    EditText etContent;
    TextView tv_done, tvPhotoMainEmpty;
    ArrayList<Uri> arlUri = new ArrayList<>();

    String content = "";

    public NewOQFrag1() {
        super();
    }

    @NonNull
    public static NewOQFrag1 newInstance() {
        return new NewOQFrag1();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_newoq_1, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initClickListener();
    }


    void initUI() {
        ivPhotoMain = (ImageView) view.findViewById(R.id.ivPhotoMain);
        ivPhotoSub = (ImageView) view.findViewById(R.id.ivPhotoSub);
        tvPhotoSubNum = (TextView) view.findViewById(R.id.tvPhotoSubNum);
        etContent = (EditText) view.findViewById(R.id.etContent);
        tv_done = (TextView) view.findViewById(R.id.tv_done);
        tvPhotoMainEmpty = (TextView) view.findViewById(R.id.tvPhotoMainEmpty);
        uiPhotoData();
    }


    void initClickListener() {
        View.OnClickListener onClickListenerPhotoGetOrEdit = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (arlUri != null || arlUri.size() == 0) {
                    startActivityForResultGallery();
                } else if (arlUri.size() >= 1) {
                    startActivityForResultSelectedPhotoList(arlUri);
                }
            }
        };
        ivPhotoSub.setOnClickListener(onClickListenerPhotoGetOrEdit);
        ivPhotoMain.setOnClickListener(onClickListenerPhotoGetOrEdit);
        tvPhotoSubNum.setOnClickListener(onClickListenerPhotoGetOrEdit);
        tvPhotoMainEmpty.setOnClickListener(onClickListenerPhotoGetOrEdit);


    }



    void uiPhotoData() {
        if (arlUri == null || arlUri.size() == 0) {
            JM.V(tvPhotoMainEmpty);
            JM.G(ivPhotoSub);
        } else if (arlUri.size() == 1) {
            JM.G(tvPhotoMainEmpty);
            JM.G(ivPhotoSub);
        } else if (arlUri.size() == 2) {
            JM.G(tvPhotoMainEmpty);
            JM.V(ivPhotoSub);
        } else {
            JM.G(tvPhotoMainEmpty);
            JM.V(ivPhotoSub);
            JM.V(tvPhotoSubNum);
            tvPhotoSubNum.setText("+" + J.st(arlUri.size() - 2));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case REQ_PHOTO_GALLERY:
                    String result = data.getStringExtra("result");
                    arlUri.add(Uri.parse(result));
                    uiPhotoData();
                    break;
            }


        }
    }

    void startActivityForResultGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= 18) { //API18 and above
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_PHOTO_GALLERY);
    }

    void startActivityForResultSelectedPhotoList(ArrayList<Uri> arlUri) {
        Intent intent = new Intent(getActivity(), SelectedPhotoListActivity.class);
        startActivityForResult(intent, REQ_SELECTED_PHOTO_LIST);
    };


}
