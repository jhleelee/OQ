package com.jackleeentertainment.oq.ui.layout.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Group;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.GroupT;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.ui.layout.activity.NewGroupActivity;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.activity.PeopleActivity;
import com.jackleeentertainment.oq.ui.widget.LoEtMoney;
import com.jackleeentertainment.oq.ui.widget.LoIvAvatarTvTitlesIvDelete;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 22..
 */

public class NewGroupFrag extends Fragment {

    final int REQ_PEOPLE = 99;
    String TAG = this.getClass().getSimpleName();
    ArrayList<Profile> mArrayListProfile = new ArrayList<>();
    View view;

    CardView cardviewTitlePhoto, cardviewAmmount, cvPeople;

    EditText etTitle;
    ImageView ivPhotoMain;
    ImageButton ibChangePhoto;
    RelativeLayout roEmpty;
    TextView tvEmpty;
    TextView tvFirstDate;
    Spinner spinnerPeriod;
    TextView tvToGetOrPay;
    RelativeLayout ro_tv_done;
    LinearLayout lo_invoice_multi;
    LinearLayout loBtAddPeople;
    ImageView ivBtAddPeople;
    TextView tvBtAddPeople;
    LinearLayout loSelectedPeople;
    LoEtMoney loetmomey;
    Switch sw;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String strUriTitlePhoto = intent.getStringExtra("data");
            if (strUriTitlePhoto != null) {
                ivPhotoMain.setImageURI(Uri.parse(strUriTitlePhoto));
            }
        }
    };


    public NewGroupFrag() {
        super();
    }

    @NonNull
    public static NewGroupFrag newInstance() {
        return new NewGroupFrag();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_newgroup, container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUiData();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("com.jackleeentertainment.oq." + LBR.IntentFilterT.NewGroupActivityTitlePhoto));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity()
                .getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        uiPhoto(((NewGroupActivity) getActivity()).photoUri);
        uiPeople(((NewGroupActivity) getActivity()).arlProfiles);
        initOCL();

    }

    void initUI() {

        /**
         cvPERSON
         **/

        cardviewTitlePhoto = (CardView) view.findViewById(R.id
                .cardviewTitlePhoto);
        ivPhotoMain = (ImageView) view.findViewById(R.id
                .ivPhotoMain);
        ibChangePhoto = (ImageButton) view.findViewById(R.id
                .ibChangePhoto);

        roEmpty = (RelativeLayout) view.findViewById(R.id
                .roEmpty);

        tvEmpty = (TextView) view.findViewById(R.id
                .tvEmpty);


        etTitle = (EditText) view.findViewById(R.id
                .etTitle);

        cvPeople = (CardView) view.findViewById(R.id
                .cvPeople);

        tvToGetOrPay = (TextView) view.findViewById(R.id
                .tvToGetOrPay);

        loBtAddPeople = (LinearLayout) view.findViewById(R.id
                .loBtAddPeople);
        ivBtAddPeople = (ImageView) view.findViewById(R.id
                .ivBtAddPeople);
        tvBtAddPeople = (TextView) view.findViewById(R.id
                .tvBtAddPeople);

        loSelectedPeople = (LinearLayout) view.findViewById(R.id
                .loSelectedPeople);

        cardviewAmmount = (CardView) view.findViewById(R.id
                .cardviewAmmount);

        loetmomey = (LoEtMoney) view.findViewById(R.id
                .loetmomey);

        lo_invoice_multi = (LinearLayout) view.findViewById(R.id
                .lo_invoice_multi);
        tvFirstDate = (TextView) view.findViewById(R.id
                .tvFirstDate);
        spinnerPeriod = (Spinner) view.findViewById(R.id
                .spinnerPeriod);

        sw = (Switch) view.findViewById(R.id.sw);
        ro_tv_done = (RelativeLayout) view.findViewById(R.id
                .ro_tv_done);

    }


    void initUiData() {




        etTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                if (etTitle.getText().length() > 0) {
//                    cardviewMember.animate()
//                            .translationY(-cardviewMember.getHeight())
//                            .alpha(1.0f)
//                            .setDuration(300)
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    cardviewMember.setVisibility(View.VISIBLE);
//                                }
//                            });
                } else {
//                    cardviewMember.animate()
//                            .translationY(cardviewMember.getHeight())
//                            .alpha(0.0f)
//                            .setDuration(100)
//                            .setListener(new AnimatorListenerAdapter() {
//                                @Override
//                                public void onAnimationEnd(Animator animation) {
//                                    super.onAnimationEnd(animation);
//                                    cardviewMember.setVisibility(View.GONE);
//                                }
//                            });
                }
            }
        });


    }


    void initOCL() {
        loBtAddPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewGroupActivity) getActivity()).startActivityForResultPeopleActivity();
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText(JM.strById(R.string.invoice_period));
                    ((NewGroupActivity)getActivity()).group.setReqtype(GroupT.ReqOnceOrRepeatT.INVOICE_REPEAT);
                    lo_invoice_multi.setVisibility(View.VISIBLE);
                } else {
                    buttonView.setText(JM.strById(R.string.invoice_once));
                    ((NewGroupActivity)getActivity()).group.setReqtype(GroupT.ReqOnceOrRepeatT.INVOICE_ONCE);
                    lo_invoice_multi.setVisibility(View.GONE);
                }
            }
        });

        String[] spinnerArray=
                new String[]{JM.strById(R.string.every_week),
                        JM.strById(R.string.every_month),
                        JM.strById(R.string.every_year)
                 };

        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_spinner_item,
                        spinnerArray);

        //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(spinnerArrayAdapter);

        tvFirstDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    void uiPhoto(Uri uri) {
        if (uri == null) {
            ivPhotoMain.setVisibility(View.GONE);
            ibChangePhoto.setVisibility(View.GONE);
            roEmpty.setVisibility(View.VISIBLE);
            roEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((NewGroupActivity) getActivity()).startActivityForResultPhotoGalleryForFeed
                            ();
                }
            });
        } else {
            ivPhotoMain.setVisibility(View.VISIBLE);
            ibChangePhoto.setVisibility(View.VISIBLE);
            roEmpty.setVisibility(View.GONE);
            ibChangePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((NewGroupActivity) getActivity()).startActivityForResultPhotoGalleryForFeed
                            ();
                }
            });
        }
    }

    void uiPeople(final ArrayList<Profile> arlProfile) {

        for (final Profile profile : arlProfile) {

            loSelectedPeople.removeAllViews();
            loSelectedPeople.removeAllViewsInLayout();
            LoIvAvatarTvTitlesIvDelete lo = new
                    LoIvAvatarTvTitlesIvDelete(getActivity());
            JM.glideProfileThumb(
                    profile.getUid(),
                    profile.getFull_name(),
                    lo.ivAvatar,
                    lo.tvAvatar,
                    getActivity()
            );
            lo.tvName.setText(profile.getFull_name());
            lo.tvEmail.setText(profile.getEmail());
            lo.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arlProfile.remove(profile);
                    uiPeople(arlProfile);
                }
            });
            loSelectedPeople.addView(lo);
        }
    }


    private void uiDataCardViewPeopleList(String jsonStrSelectedProfiles) {
        Gson gson = new Gson();
        ArrayList<Profile> arlProfile = gson.fromJson(jsonStrSelectedProfiles, new
                TypeToken<ArrayList<Profile>>() {
                }.getType());

    }


}
