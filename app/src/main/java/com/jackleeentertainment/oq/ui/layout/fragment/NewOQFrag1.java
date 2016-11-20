package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
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
    TextView tv_done,  tvPhotoCommentTitle;
    LinearLayout loBtAddPhoto;
    ImageView ivBtAddPhoto;
    TextView tvBtAddPhoto;
    RelativeLayout roPhoto;
     Bundle bundleFromFrag0;

    public NewOQFrag1() {
        super();
    }

    @NonNull
    public static NewOQFrag1 newInstance(Bundle bundleFromFrag0) {
        NewOQFrag1 newOQFrag1 = new NewOQFrag1();
        newOQFrag1.setArguments(bundleFromFrag0);
        return newOQFrag1;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        bundleFromFrag0 = getArguments();
        view = inflater.inflate(R.layout.frag_newoq_1, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        uiPhotoData();
        initUiCosmetic();

        initClickListener();
        ((NewOQActivity)getActivity()).uiLsnerFrag1(bundleFromFrag0);

    }

    void initUI() {


        tvPhotoCommentTitle = (TextView) view.findViewById(R.id.tvPhotoCommentTitle);

        loBtAddPhoto = (LinearLayout) view.findViewById(R.id.loBtAddPhoto);
        ivBtAddPhoto = (ImageView) view.findViewById(R.id.ivBtAddPhoto);
        tvBtAddPhoto = (TextView) view.findViewById(R.id.tvBtAddPhoto);

        roPhoto= (RelativeLayout) view.findViewById(R.id.roPhoto);
        ivPhotoMain = (ImageView) view.findViewById(R.id.ivPhotoMain);
        ivPhotoSub = (ImageView) view.findViewById(R.id.ivPhotoSub);
        tvPhotoSubNum = (TextView) view.findViewById(R.id.tvPhotoSubNum);
        etContent = (EditText) view.findViewById(R.id.etContent);
        tv_done = (TextView) view.findViewById(R.id.tv_done);
    }

    void initUiCosmetic() {
        tvPhotoCommentTitle.setText(JM.strById(R.string.add_photo_comment));

    }


    void initClickListener() {


        loBtAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewOQActivity)getActivity()).startActivityForResultPhotoGalleryForFeed();
            }
        });



        // show selected photo list activity and allow deletion
//        View.OnClickListener onClickListenerPhotoGetOrEdit = new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (((NewOQActivity)getActivity()).arlUriPhoto != null ||
//                        ((NewOQActivity)getActivity()).arlUriPhoto.size() == 0) {
//                    ((NewOQActivity)getActivity()).startActivityForResultPhotoGalleryForFeed();
//
//                } else if (((NewOQActivity)getActivity()).arlUriPhoto.size() >= 1) {
//                    startActivityForResultSelectedPhotoList(((NewOQActivity)getActivity()).arlUriPhoto);
//                }
//            }
//        };
//
//        ivPhotoSub.setOnClickListener(onClickListenerPhotoGetOrEdit);
//        ivPhotoMain.setOnClickListener(onClickListenerPhotoGetOrEdit);
//        tvPhotoSubNum.setOnClickListener(onClickListenerPhotoGetOrEdit);
//        tvPhotoMainEmpty.setOnClickListener(onClickListenerPhotoGetOrEdit);





        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogInterface.OnClickListener ocl = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick which :" + J.st(which));

                        ((NewOQActivity)getActivity()).startNewOQProgActivity();

                    }
                };


                String s = "";

                if ((((NewOQActivity)getActivity()).arlUriPhoto == null ||
                        ((NewOQActivity)getActivity()).arlUriPhoto.size() == 0) &&
                        etContent.getText().length() == 0) {

                    ((NewOQActivity)getActivity()).strPostText = null;

                    s = JM.strById(R.string.newoq_nophoto_nowriting);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).tempArl
                            );
                } else if (((NewOQActivity)getActivity()).arlUriPhoto == null ||
                        ((NewOQActivity)getActivity()).arlUriPhoto.size() == 0) {

                    ((NewOQActivity)getActivity()).strPostText = etContent.getText().toString();


                    s = JM.strById(R.string.newoq_nophoto);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).tempArl
                            );
                } else if (etContent.getText().length() == 0) {

                    ((NewOQActivity)getActivity()).strPostText = null;

                    s = JM.strById(R.string.newoq_nowriting);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).tempArl
                            );
                } else {

                    ((NewOQActivity)getActivity()).strPostText = etContent.getText().toString();

                    s = JM.strById(R.string.newoq_send);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).tempArl
                            );
                }

                ((NewOQActivity) getActivity()).showAlertDialogWithOkCancel(s, ocl);

            }
        });
    }


    void uiPhotoData() {

        if (((NewOQActivity)getActivity()).arlUriPhoto == null || ((NewOQActivity)getActivity()).arlUriPhoto.size() == 0) {
            JM.G(roPhoto);

        } else if (((NewOQActivity)getActivity()).arlUriPhoto.size() == 1) {
            Log.d(TAG, "uiPhotoData()" + J.st(((NewOQActivity)getActivity()).arlUriPhoto.size()));
            JM.V(roPhoto);
            JM.G(ivPhotoSub);
            JM.G(tvPhotoSubNum);
            ivPhotoMain.setImageURI(((NewOQActivity)getActivity()).arlUriPhoto.get(0));

        } else if (((NewOQActivity)getActivity()).arlUriPhoto.size() == 2) {
            Log.d(TAG, "uiPhotoData()" + J.st(((NewOQActivity)getActivity()).arlUriPhoto.size()));
            JM.V(roPhoto);
            JM.V(ivPhotoSub);
            JM.G(tvPhotoSubNum);
            ivPhotoMain.setImageURI(((NewOQActivity)getActivity()).arlUriPhoto.get(0));
            ivPhotoSub.setImageURI(((NewOQActivity)getActivity()).arlUriPhoto.get(1));

        } else {
            Log.d(TAG, "uiPhotoData()" + J.st(((NewOQActivity)getActivity()).arlUriPhoto.size()));
            JM.V(roPhoto);
            JM.V(ivPhotoSub);
            JM.V(tvPhotoSubNum);

            ivPhotoMain.setImageURI(((NewOQActivity)getActivity()).arlUriPhoto.get(0));
            ivPhotoSub.setImageURI(((NewOQActivity)getActivity()).arlUriPhoto.get(1));
            tvPhotoSubNum.setText("+" + J.st(((NewOQActivity)getActivity()).arlUriPhoto.size() - 2));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case REQ_PHOTO_GALLERY:
                    String result = data.getStringExtra("result");
                    ((NewOQActivity)getActivity()).arlUriPhoto.add(Uri.parse(result));
                    uiPhotoData();
                    break;
            }


        }
    }






}
