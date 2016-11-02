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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.UploadTask;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.database.SetValue;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.object.MyOppo;
import com.jackleeentertainment.oq.object.OQPost;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.types.DeedT;
import com.jackleeentertainment.oq.object.types.OQPostT;
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
    TextView tv_done, tvPhotoMainEmpty, tvPhotoCommentTitle;
    LinearLayout loBtAddPhoto;
    ImageView ivBtAddPhoto;
    TextView tvBtAddPhoto;

    String content = "";
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
        initUiCosmetic();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initClickListener();
    }


    @Override
    public void onResume() {
        super.onResume();
        uiPhotoData();

    }

    void initUI() {


        tvPhotoCommentTitle = (TextView) view.findViewById(R.id.tvPhotoCommentTitle);

        loBtAddPhoto = (LinearLayout) view.findViewById(R.id.loBtAddPhoto);
        ivBtAddPhoto = (ImageView) view.findViewById(R.id.ivBtAddPhoto);
        tvBtAddPhoto = (TextView) view.findViewById(R.id.tvBtAddPhoto);


        ivPhotoMain = (ImageView) view.findViewById(R.id.ivPhotoMain);
        ivPhotoSub = (ImageView) view.findViewById(R.id.ivPhotoSub);
        tvPhotoSubNum = (TextView) view.findViewById(R.id.tvPhotoSubNum);
        etContent = (EditText) view.findViewById(R.id.etContent);
        tv_done = (TextView) view.findViewById(R.id.tv_done);
        tvPhotoMainEmpty = (TextView) view.findViewById(R.id.tvPhotoMainEmpty);
    }

    void initUiCosmetic() {
        tvPhotoCommentTitle.setText(JM.strById(R.string.add_photo_comment));
        ((NewOQActivity) getActivity()).ivClose.setImageDrawable(null);
        ((NewOQActivity) getActivity()).ivClose.setImageDrawable(JM.drawableById(R.drawable.ic_arrow_back_white_48dp));

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

        View.OnClickListener onClickListenerBackToFrag0 = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ((NewOQActivity) getActivity()).backToFragment(NewOQFrag0Neo.newInstance
                                (bundleFromFrag0),
                        R.id.fr_content);
            }
        };
        ((NewOQActivity) getActivity()).ivClose.setOnClickListener(onClickListenerBackToFrag0);
        ((NewOQActivity) getActivity()).roClose.setOnClickListener(onClickListenerBackToFrag0);




        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogInterface.OnClickListener ocl = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick which :" + J.st(which));

                        ((NewOQActivity)getActivity()).doUploadAll(etContent);

                    }
                };


                String s = "";

                if ((((NewOQActivity)getActivity()).arlUriPhoto == null ||
                        ((NewOQActivity)getActivity()).arlUriPhoto.size() == 0) &&
                        etContent.getText().length() == 0) {
                    s = JM.strById(R.string.newoq_nophoto_nowriting);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).arlOQItem_Future
                            );
                } else if (((NewOQActivity)getActivity()).arlUriPhoto == null ||
                        ((NewOQActivity)getActivity()).arlUriPhoto.size() == 0) {
                    s = JM.strById(R.string.newoq_nophoto);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).arlOQItem_Future
                            );
                } else if (etContent.getText().length() == 0) {
                    s = JM.strById(R.string.newoq_nowriting);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).arlOQItem_Future
                            );
                } else {
                    s = JM.strById(R.string.newoq_send);
                    s += "\n";
                    s += StringGenerator
                            .xAndXPeopleOqItemClaimee(
                                    ((NewOQActivity) getActivity()).arlOQItem_Future
                            );
                }

                ((NewOQActivity) getActivity()).showAlertDialogWithOkCancel(s, ocl);

            }
        });
    }


    void uiPhotoData() {
        if (((NewOQActivity)getActivity()).arlUriPhoto == null || ((NewOQActivity)getActivity()).arlUriPhoto.size() == 0) {
            JM.V(tvPhotoMainEmpty);
            JM.G(ivPhotoSub);
        } else if (((NewOQActivity)getActivity()).arlUriPhoto.size() == 1) {
            JM.G(tvPhotoMainEmpty);
            JM.G(ivPhotoSub);
        } else if (((NewOQActivity)getActivity()).arlUriPhoto.size() == 2) {
            JM.G(tvPhotoMainEmpty);
            JM.V(ivPhotoSub);
        } else {
            JM.G(tvPhotoMainEmpty);
            JM.V(ivPhotoSub);
            JM.V(tvPhotoSubNum);
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



    void startActivityForResultSelectedPhotoList(ArrayList<Uri> arlUri) {
        Intent intent = new Intent(getActivity(), SelectedPhotoListActivity.class);
        startActivityForResult(intent, REQ_SELECTED_PHOTO_LIST);
    }

    ;


}
