//package com.jackleeentertainment.oq.ui.layout.fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.CardView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.jackleeentertainment.oq.App;
//import com.jackleeentertainment.oq.R;
//import com.jackleeentertainment.oq.generalutil.J;
//import com.jackleeentertainment.oq.generalutil.JM;
//import com.jackleeentertainment.oq.object.OqItem;
//import com.jackleeentertainment.oq.object.Profile;
//import com.jackleeentertainment.oq.object.types.OQSumT;
//import com.jackleeentertainment.oq.object.types.OQT;
//import com.jackleeentertainment.oq.object.util.OqItemUtil;
//import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
//import com.jackleeentertainment.oq.ui.widget.ListViewUtil;
//
//import java.util.ArrayList;
//
///**
// * Created by Jacklee on 2016. 9. 14..
// */
//public class NewOQFrag0 extends Fragment implements View.OnClickListener {
//
//    //((NewOQActivity) getActivity()).goToFragment(new NewOQFrag1(), R.id.lofragmentlayout);
//    String TAG = this.getClass().getSimpleName();
//    final int REQ_PEOPLE = 99;
//    final int REQ_SUMTYPE = 98;
//    boolean hasSumTypeEdited = false;
//    View view;
//    CardView cvPERSON, cvSUMTYPE,
//            cvBREAKDOWN;
//    TextView tv_done;
//    SelectedPeopleListAdapter selectedPeopleListAdapter;
//
//    //PERSON Cardview
//    LinearLayout loMain_PERSON;
//    TextView tvTitle_PERSON, tvEmpty_cardview_PERSON,
//            tv__lo_lefttext_rightoneaction__PERSON;
//    ListView lv_PERSON;
//    LinearLayout lo_lefttext_rightoneaction_borderlesscolored_PERSON;
//    RelativeLayout roEmpty_cardview_PERSON;
//    Button bt__lo_lefttext_rightoneaction__PERSON;
//
//    //SUMTYPE Cardview
//    TextView tv_title__cardview_titleedittextillust;
//    TextView tvAmmount__cardview_titleedittextillust;
//    EditText etAmmount__cardview_titleedittextillust;
//    ViewPager viewPager__cardview_titleedittextillust;
//    ViewPager.OnPageChangeListener onPCListener_SoIWantToGETFromYou;
//    ViewPager.OnPageChangeListener onPCListener_SoIWantToGETFromYouGuys;
//    ViewPager.OnPageChangeListener onPCListener_SoIWantToPAY;
//
//    //BREAKDOWN Cardview
//    LinearLayout loMain_BREAKDOWN;
//    TextView tvTitle_BREAKDOWN, tv__lo_lefttext_rightoneaction__BREAKDOWN, tvEmptyBREAKDOWN;
//    ListView lvBREAKDOWN;
//    LinearLayout lo_lefttext_rightoneaction_borderlesscolored_BREAKDOWN;
//    RelativeLayout roEmptyBREAKDOWN;
//    Button bt__lo_lefttext_rightoneaction__BREAKDOWN;
//
//
//    public NewOQFrag0() {
//        super();
//    }
//
//    @NonNull
//    public static NewOQFrag0 newInstance() {
//        return new NewOQFrag0();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView ...");
//        view = inflater.inflate(R.layout.frag_newoq_0, container, false);
//        initUI();
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
////        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
////                new IntentFilter("com.jackleeentertainment.oq." + LBR.IntentFilterT.NewOQActivity_Frag0));
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume()");
//        Log.d(TAG, "onResume() : ((NewOQActivity) getActivity()).arlOppoProfile : " + J.st(((NewOQActivity) getActivity()).arlOppoProfile.size()));
//
//        uiDataCardViewExist(
//                ((NewOQActivity) getActivity()).arlOppoProfile,
//                ((NewOQActivity) getActivity()).ammountAsStandard,
//                ((NewOQActivity) getActivity()).OQSumT
//        );
//
//        uiDataCardViewPeopleList(
//                ((NewOQActivity) getActivity()).arlOppoProfile
//        );
//
//        uiDataCardViewSumType(
//                ((NewOQActivity) getActivity()).mDoWhat,
//                ((NewOQActivity) getActivity()).arlOppoProfile,
//                ((NewOQActivity) getActivity()).ammountAsStandard,
//                ((NewOQActivity) getActivity()).OQSumT
//        );
//        uiDataCardViewBreakDown(
//                ((NewOQActivity) getActivity()).arlOqDo_Paid,
//                ((NewOQActivity) getActivity()).arlOqDo_Now,
//                ((NewOQActivity) getActivity()).arlOqDo_Future
//        );
//
//        initViewPagerChangeListenerCandidates();
//        initViewPagerAdapter(
//                ((NewOQActivity) getActivity())
//                        .mDoWhat,
//                ((NewOQActivity) getActivity())
//                        .arlOppoProfile
//        );
//
//        etAmmount__cardview_titleedittextillust.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d(TAG, "beforeTextChanged(CharSequence s, int start, int count, int after) "
//                        + s.toString() + ", "
//                        + J.st(start) + ", "
//                        + J.st(count) + ", "
//                        + J.st(after + ", ")
//                );
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(TAG, "onTextChanged(CharSequence s, int start, int before, int count) "
//                        + s.toString() + ", "
//                        + J.st(start) + ", "
//                        + J.st(before) + ", "
//                        + J.st(count) + ", "
//                );
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                Log.d(TAG, "afterTextChanged(Editable s) " + s.toString());
//                if (s.length() == 0) {
//                    etAmmount__cardview_titleedittextillust.setText("0");
//                }
//                ;
//
//                try {
//                    ((NewOQActivity) getActivity()).ammountAsStandard
//                            = Long.parseLong(etAmmount__cardview_titleedittextillust.getText().toString());
//                } catch (Exception e) {
//                    Log.d(TAG, e.toString());
//                }
//
//                uiDataCardViewExist(
//                        ((NewOQActivity) getActivity()).arlOppoProfile,
//                        ((NewOQActivity) getActivity()).ammountAsStandard,
//                        ((NewOQActivity) getActivity()).OQSumT
//                );
//
//            }
//        });
//        ListViewUtil.setListViewHeightBasedOnChildren(lv_PERSON);
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.cv_people__frag_newoq_0:
//                break;
//
//
//            case R.id.cv_sumtype__frag_newoq_0:
//
////                if (!hasSumTypeEdited) {
////                    Intent intent_cardview_cause_sumtype__frag_newoq_0 = new Intent(getActivity(), SumTypeActivity.class);
////                    startActivityForResult(intent_cardview_cause_sumtype__frag_newoq_0, REQ_SUMTYPE);
////                }
//
//                break;
//
//            case R.id.cv_breakdown__frag_newoq_0:
//
//                // no, deirect edit..
//
//                break;
//
//
//            case R.id.tv_done__frag_newoq_0:
//                break;
//        }
//    }
//
//
//    void initUI() {
//
//        /**
//         cvPERSON
//         **/
//        cvPERSON = (CardView) view.findViewById(R.id
//                .cv_people__frag_newoq_0);
//        loMain_PERSON = (LinearLayout) cvPERSON
//                .findViewById(R.id.loMain);
//        tvTitle_PERSON = (TextView) cvPERSON
//                .findViewById(R.id.tv_title__cardview_titlelistaction);
//        lv_PERSON = (ListView) cvPERSON.findViewById(R.id
//                .lv__cardview_titlelistaction);
//        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
//        lo_lefttext_rightoneaction_borderlesscolored_PERSON = (LinearLayout)
//                cvPERSON.findViewById(R.id
//                        .lo_lefttext_rightoneaction_borderlesscolored__cardview_titlelistaction);
//
//        tv__lo_lefttext_rightoneaction__PERSON = (TextView)
//                lo_lefttext_rightoneaction_borderlesscolored_PERSON.findViewById(R.id
//                        .tv__lo_lefttext_rightoneaction);
//        tv__lo_lefttext_rightoneaction__PERSON.setVisibility(View.INVISIBLE);
//        bt__lo_lefttext_rightoneaction__PERSON = (Button)
//                lo_lefttext_rightoneaction_borderlesscolored_PERSON.findViewById(R.id
//                        .bt__lo_lefttext_rightoneaction);
//        bt__lo_lefttext_rightoneaction__PERSON.setText(JM.strById(R.string.delete_all));
//        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
//        roEmpty_cardview_PERSON = (RelativeLayout)
//                cvPERSON.findViewById(R.id
//                        .roEmpty__cardview_titlelistaction);
//        tvEmpty_cardview_PERSON = (TextView)
//                cvPERSON.findViewById(R.id
//                        .tvEmpty__cardview_titlelistaction);
//
//        /**
//         cvSUMTYPE
//         **/
//        cvSUMTYPE = (CardView) view.findViewById(R.id
//                .cv_sumtype__frag_newoq_0);
//
//
//        tv_title__cardview_titleedittextillust = (TextView) cvSUMTYPE
//                .findViewById(R.id.tv_title__cardview_titleedittextillust);
//        tvAmmount__cardview_titleedittextillust = (TextView) cvSUMTYPE
//                .findViewById(R.id.tvAmmount__cardview_titleedittextillust);
//        etAmmount__cardview_titleedittextillust = (EditText) cvSUMTYPE
//                .findViewById(R.id.etAmmount__cardview_titleedittextillust);
//        viewPager__cardview_titleedittextillust = (ViewPager) cvSUMTYPE
//                .findViewById(R.id.viewPager__cardview_titleedittextillust);
//
//        tv_title__cardview_titleedittextillust.setText(JM.strById(R.string.sumandtype));
//        /**
//         cvBREAKDOWN
//         **/
//
//        cvBREAKDOWN = (CardView) view
//                .findViewById(R.id.cv_breakdown__frag_newoq_0);
//        loMain_BREAKDOWN = (LinearLayout) cvBREAKDOWN
//                .findViewById(R.id.loMain);
//        tvTitle_BREAKDOWN = (TextView) cvBREAKDOWN
//                .findViewById(R.id.tv_title__cardview_titlelistaction);
//        tvTitle_BREAKDOWN.setText(JM.strById(R.string.request_detail));
//        lvBREAKDOWN = (ListView) cvBREAKDOWN
//                .findViewById(R.id.lv__cardview_titlelistaction);
//        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
//        lo_lefttext_rightoneaction_borderlesscolored_BREAKDOWN = (LinearLayout)
//                cvBREAKDOWN
//                        .findViewById(R.id.lo_lefttext_rightoneaction_borderlesscolored__cardview_titlelistaction);
//
//        tv__lo_lefttext_rightoneaction__BREAKDOWN = (TextView)
//                lo_lefttext_rightoneaction_borderlesscolored_BREAKDOWN
//                        .findViewById(R.id.tv__lo_lefttext_rightoneaction);
//
//        bt__lo_lefttext_rightoneaction__BREAKDOWN = (Button)
//                lo_lefttext_rightoneaction_borderlesscolored_BREAKDOWN
//                        .findViewById(R.id.bt__lo_lefttext_rightoneaction);
//        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
//        roEmptyBREAKDOWN = (RelativeLayout)
//                cvBREAKDOWN
//                        .findViewById(R.id.roEmpty__cardview_titlelistaction);
//        tvEmptyBREAKDOWN = (TextView)
//                cvBREAKDOWN
//                        .findViewById(R.id.tvEmpty__cardview_titlelistaction);
//
//
//        /**
//         tv_done
//         **/
//
//        tv_done = (TextView) view.findViewById(R.id.tv_done__frag_newoq_0);
//
//    }
//
//
//    private void uiDataCardViewPeopleList(ArrayList<Profile> arlProfiles) {
//        Log.d(TAG, "uiDataCardViewPeopleList()");
//
//        if (arlProfiles == null || arlProfiles.size() == 0) {
//            Log.d(TAG, "arlProfiles == null || arlProfiles.size() == 0");
//            JM.G(loMain_PERSON);
//            JM.V(roEmpty_cardview_PERSON);
//            View.OnClickListener onClickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((NewOQActivity) getActivity()).startActivityForResultPeopleActivity();
//                }
//            };
//            roEmpty_cardview_PERSON.setOnClickListener(onClickListener);
//            tvEmpty_cardview_PERSON.setOnClickListener(onClickListener);
//
//        } else {
//            Log.d(TAG, "else");
//            JM.G(roEmpty_cardview_PERSON);
//            JM.V(loMain_PERSON);
//
//            if (((NewOQActivity) getActivity())
//                    .mDoWhat.equals(
//                            OQT.WantT.GET)) {
//                Log.d(TAG, "GET");
//                tvTitle_PERSON.setText(JM.strById(R.string.payer));
//            } else if (((NewOQActivity) getActivity())
//                    .mDoWhat.equals(OQT.WantT
//                            .PAY)) {
//                Log.d(TAG, "PAY");
//
//                tvTitle_PERSON.setText(JM.strById(R.string.getter));
//            }
//
//            lv_PERSON.setAdapter(new SelectedPeopleListAdapter(
//                    arlProfiles,
//                    getActivity()));
//
//            bt__lo_lefttext_rightoneaction__PERSON.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((NewOQActivity) getActivity()).arlOppoProfile = new ArrayList<Profile>();
//                    uiDataCardViewPeopleList(((NewOQActivity) getActivity()).arlOppoProfile);
//                    uiDataCardViewExist(
//                            ((NewOQActivity) getActivity()).arlOppoProfile,
//                            ((NewOQActivity) getActivity()).ammountAsStandard,
//                            ((NewOQActivity) getActivity()).OQSumT
//                    );
//                    ListViewUtil.setListViewHeightBasedOnChildren(lv_PERSON);
//
//                }
//            });
//
//        }
//    }
//
//
//    void initViewPagerAdapter(String mDoWhat,
//                              ArrayList<Profile> arlOppoProfile) {
//        Log.d(TAG, "initViewPagerAdapter()");
//        if (mDoWhat.equals(OQT.WantT.GET)) {
//            if (arlOppoProfile != null) {
//                if (arlOppoProfile.size() == 1) {
//                    viewPager__cardview_titleedittextillust.addOnPageChangeListener(onPCListener_SoIWantToGETFromYou);
//                    viewPager__cardview_titleedittextillust.setAdapter(
//                            new SumTypePagerAdapter_SoIWantToGETFromYou(getFragmentManager()));
//                } else if (arlOppoProfile.size() > 1) {
//                    viewPager__cardview_titleedittextillust.addOnPageChangeListener(onPCListener_SoIWantToGETFromYouGuys);
//                    viewPager__cardview_titleedittextillust.setAdapter(
//                            new SumTypePagerAdapter_SoIWantToGETFromYouGuys(getFragmentManager()));
//                }
//            }
//
//        } else if (mDoWhat.equals(OQT.WantT.PAY)) {
//            viewPager__cardview_titleedittextillust.addOnPageChangeListener(onPCListener_SoIWantToPAY);
//            viewPager__cardview_titleedittextillust.setAdapter(
//                    new SumTypePagerAdapter_SoIWantToPAY(getFragmentManager()));
//        }
//    }
//
//
//    void initViewPagerChangeListenerCandidates() {
//        Log.d(TAG, "initViewPagerChangeListenerCandidates");
//
//        onPCListener_SoIWantToGETFromYou
//                = new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        ((NewOQActivity) getActivity()).OQSumT =
//                                com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
//                                        .I_PAID_FOR_YOU_AND_ME;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_sum));
//                        break;
//                    case 1:
//                        ((NewOQActivity) getActivity()).OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
//                                .I_PAID_FOR_YOU;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_individual));
//
//                        break;
//
//                    case 2:
//                        ((NewOQActivity) getActivity()).OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
//                                .ANYWAY;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_individual));
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        };
//
//
//        onPCListener_SoIWantToGETFromYouGuys
//                = new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        ((NewOQActivity) getActivity()).OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
//                                .I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_sum));
//                        break;
//                    case 1:
//                        ((NewOQActivity) getActivity()).OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
//                                .I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_sum));
//                        break;
//
//                    case 2:
//                        ((NewOQActivity) getActivity()).OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
//                                .N_ANYWAY;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_individual));
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        };
//
//        onPCListener_SoIWantToPAY
//                = new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        ((NewOQActivity) getActivity()).OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY
//                                .YOU_PAID_FOR_ME;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_ammount_neatural));
//                        break;
//                    case 1:
//
//                        ((NewOQActivity) getActivity()).OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY
//                                .ANYWAY;
//                        tvAmmount__cardview_titleedittextillust.setText(JM.strById(R.string.money_ammount_neatural));
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        };
//
//    }
//
//
//    void uiDataCardViewSumType(
//            String mDoWhat,
//            ArrayList<Profile> arlOppoProfile,
//            long ammountAsStandard,
//            String OQSumT
//    ) {
//        //set default OQSumT
//        if (OQSumT == null) {
//            if (((NewOQActivity) getActivity()).mDoWhat.equals(OQT.WantT.GET)) {
//
//                if (arlOppoProfile.size() == 1) {
//                    OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU_AND_ME;
//                } else if (arlOppoProfile.size() > 1) {
//                    OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME;
//                }
//
//            } else if (((NewOQActivity) getActivity()).mDoWhat.equals(OQT.WantT.PAY)) {
//                OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY.YOU_PAID_FOR_ME;
//            }
//        }
//
//
//    }
//
//
//    void uiDataCardViewBreakDownWithStandardAmmount(
//            ArrayList<Profile> arlSelectedProfiles,
//            long standardAmount,
//            String oqsumtype,
//            Activity activity
//    ) {
//        ArrayList<OqItem> arlOqItem = OqItemUtil.getInstances(
//                arlSelectedProfiles,
//                standardAmount,
//                oqsumtype,
//                activity
//        );
//
//
//    }
//
//
//    void uiDataCardViewBreakDown(
//            ArrayList<OqItem> arlOQItem_Past,
//            ArrayList<OqItem> arlOqDo_Now,
//            ArrayList<OqItem> arlOqDo_Future
//    ) {
//
//
//    }
//
//
//    void uiDataCardViewExist(
//            ArrayList<Profile> arlProfile,
//            long ammountAsStandard,
//            @NonNull String OQSumT
//    ) {
//        Log.d(TAG, "uiDataCardViewExist()");
//        if (arlProfile == null ||
//                arlProfile.size() == 0) {
//            Log.d(TAG, "uiDataCardViewExist() - arlProfile == null ||" +
//                    "arlProfile.size() == 0");
//            JM.V(cvPERSON);
//            JM.G(cvSUMTYPE);
//            JM.G(cvBREAKDOWN);
//            JM.G(tv_done);
//
////        } else if (ammountAsStandard == 0 || OQSumT == null) {
////            Log.d(TAG, "uiDataCardViewExist() - ammountAsStandard == 0 || OQSumT == null");
////            JM.V(cvPERSON);
////            JM.V(cvSUMTYPE);
////            JM.G(cvBREAKDOWN);
////            JM.G(tv_done);
//
//        } else {
//            Log.d(TAG, "uiDataCardViewExist() - else");
//            JM.V(cvPERSON);
//            JM.V(cvSUMTYPE);
//            JM.V(cvBREAKDOWN);
//            JM.V(tv_done);
//
//            if ((ammountAsStandard == 0 || OQSumT == null)) {
//                JM.uiTextViewPosiNega(tv_done, false);
//
//            } else {
//                JM.uiTextViewPosiNega(tv_done, true);
//            }
//
//
//        }
//
//
//    }
//
//
//    void uiTvDoneExist(
//            ArrayList<OqItem> oqItems
//    ) {
//        if (oqItems != null && oqItems.size() > 0) {
//            JM.V(tv_done);
//            tv_done.setText(J.st(oqItems.size()) + "건의 요청");
//        } else {
//            JM.G(tv_done);
//
//        }
//    }
//
//
//    /**
//     * Classes
//     */
//
//    class SelectedPeopleListAdapter extends BaseAdapter {
//
//        LayoutInflater mInflater;
//        Context mContext;
//        ArrayList<Profile> mArrayListProfile = new ArrayList<>();
//
//        public SelectedPeopleListAdapter(
//                ArrayList<Profile> mArrayListProfile,
//                Context context) {
//            super();
//            this.mArrayListProfile = mArrayListProfile;
//            this.mContext = context;
//            mInflater = LayoutInflater.from(this.mContext);
//        }
//
//
//        @Override
//        public int getCount() {
//            return mArrayListProfile.size();
//        }
//
//        @Override
//        public Profile getItem(int position) {
//
//
//            return mArrayListProfile.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            // create a ViewHolderReceipt reference
//            ProfileViewHolder holder;
//
//            //check to see if the reused view is null or not, if is not null then reuse it
//            if (convertView == null) {
//                holder = new ProfileViewHolder();
//                convertView = mInflater.inflate(R.layout.lo_avatar_titlesubtitle_delete, parent, false);
//
//                // get all views you need to handle from the cell and save them in the view holder
//
//                holder.ro_person_photo_48dip__lo_avatartitlesubtitle_delete =
//                        (RelativeLayout) convertView.findViewById(R.id
//                                .ro_person_photo_48dip__lo_avatartitlesubtitle_delete);
//                holder.ivAva = (ImageView)
//                        holder.ro_person_photo_48dip__lo_avatartitlesubtitle_delete
//                                .findViewById(R.id
//                                        .ivAva);
//                holder.tvTitle__lo_avatartitlesubtitle_delete = (TextView) convertView.findViewById(R.id.tvTitle__lo_avatartitlesubtitle_delete);
//                holder.tvSubTitle__lo_avatartitlesubtitle_delete = (TextView) convertView.findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle_delete);
//                holder.ibDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
//                holder.ibDelete.setImageDrawable(
//                        JM.tintedDrawable(
//                                R.drawable.ic_delete_white_24dp,
//                                R.color.material_grey800,
//                                mContext
//                        )
//                );
//                // save the view holder on the cell view to get it back latter
//                convertView.setTag(holder);
//            } else {
//                // the getTag returns the viewHolder object set as a tag to the view
//                holder = (ProfileViewHolder) convertView.getTag();
//            }
//
//            //get the string item from the position "position" from array list to put it on the TextView
//            Profile profile = mArrayListProfile.get(position);
//            if (profile != null) {
//                //set the item name on the TextView
//                //Glide
//                holder.tvTitle__lo_avatartitlesubtitle_delete.setText(profile.getFull_name());
//                holder.tvSubTitle__lo_avatartitlesubtitle_delete.setText(profile.getEmail());
//            }
//
//            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Profile profile1 = getItem(position);
//                    mArrayListProfile.remove(profile1);
//                    ((NewOQActivity) mContext).arlOppoProfile.remove(profile1);
//                    notifyDataSetChanged();
//                }
//            });
//
//            //this method must return the view corresponding to the data at the specified position.
//            return convertView;
//
//        }
//
//        public ArrayList<Profile> getmArrayListProfile() {
//            return mArrayListProfile;
//        }
//
//        public void setmArrayListProfile(ArrayList<Profile> mArrayListProfile) {
//            this.mArrayListProfile = mArrayListProfile;
//        }
//
//
//        class ProfileViewHolder {
//            RelativeLayout ro_person_photo_48dip__lo_avatartitlesubtitle_delete;
//            ImageView ivAva;
//            TextView tvTitle__lo_avatartitlesubtitle_delete,
//                    tvSubTitle__lo_avatartitlesubtitle_delete;
//            ImageView ibDelete;
//        }
//    }
//
//    /**
//     * A simple pager frvAdapterAllMyContact that represents 5 ScreenSlidePageFragment objects, in
//     * sequence.
//     */
//    private class SumTypePagerAdapter_SoIWantToGETFromYou extends FragmentPagerAdapter {
//        public SumTypePagerAdapter_SoIWantToGETFromYou(FragmentManager fm) {
//            super(fm);
//        }
//
//        String TAG = "SumTypePA_IWntGETFrYou";
//
//        @Override
//        public Fragment getItem(int position) {
//            Log.d(TAG, "position : " + String.valueOf(position));
//            /**
//             OQSumT = getArguments().getString("OQSumT");
//             myName = getArguments().getString("myName");
//             hisName = getArguments().getString("hisName");
//             moneyStandard = getArguments().getLong("moneyStandard");
//             moneyTarget = getArguments().getLong("moneyTarget");
//             */
//            Bundle bundle = new Bundle();
//            bundle.putString("myName",
//                    App.getName(getActivity()));
//            bundle.putString("hisName",
//                    ((NewOQActivity) getActivity()).arlOppoProfile.get(0).getFull_name());
//            switch (position) {
//
//                case 0:
//
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToGETFromYou
//                                    .I_PAID_FOR_YOU);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    return SumTypePageFrag.newInstance(bundle);
//
//
//                case 1:
//
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToGETFromYou
//                                    .I_PAID_FOR_YOU_AND_ME);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            OqItemUtil.getDivideByMeAndOthers(
//                                    ((NewOQActivity) getActivity()).ammountAsStandard,
//                                    2
//                            ));
//                    return SumTypePageFrag.newInstance(bundle);
//
//
//                case 2:
//
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToGETFromYou
//                                    .ANYWAY);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    return SumTypePageFrag.newInstance(bundle);
//
//
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//    }
//
//
//    private class SumTypePagerAdapter_SoIWantToGETFromYouGuys extends FragmentPagerAdapter {
//        public SumTypePagerAdapter_SoIWantToGETFromYouGuys(FragmentManager fm) {
//            super(fm);
//        }
//
//        String TAG = "SumTypePA_IWntGETFrYouG";
//
//        @Override
//        public Fragment getItem(int position) {
//            Log.d(TAG, "position : " + String.valueOf(position));
//            /**
//             OQSumT = getArguments().getString("OQSumT");
//             myName = getArguments().getString("myName");
//             hisName = getArguments().getString("hisName");
//             moneyStandard = getArguments().getLong("moneyStandard");
//             moneyTarget = getArguments().getLong("moneyTarget");
//             */
//            Bundle bundle = new Bundle();
//            bundle.putString("myName",
//                    App.getName(getActivity()));
//            bundle.putString("hisName",
//                    ((NewOQActivity) getActivity()).arlOppoProfile.get(0).getFull_name());
//
//            switch (position) {
//                case 0:
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToGETFromYouGuys
//                                    .I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            OqItemUtil.getDivideByMeAndOthers(
//                                    ((NewOQActivity) getActivity()).ammountAsStandard,
//                                    ((NewOQActivity) getActivity()).arlOppoProfile.size()
//                            ));
//                    return SumTypePageFrag.newInstance(bundle);
//
//
//                case 1:
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToGETFromYouGuys
//                                    .I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            OqItemUtil.getDivideByOthers(
//                                    ((NewOQActivity) getActivity()).ammountAsStandard,
//                                    ((NewOQActivity) getActivity()).arlOppoProfile.size()
//                            ));
//                    return SumTypePageFrag.newInstance(bundle);
//
//                case 2:
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToGETFromYouGuys
//                                    .N_ANYWAY);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    return SumTypePageFrag.newInstance(bundle);
//
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//    }
//
//
//    private class SumTypePagerAdapter_SoIWantToPAY extends FragmentPagerAdapter {
//        public SumTypePagerAdapter_SoIWantToPAY(FragmentManager fm) {
//            super(fm);
//        }
//
//        String TAG = "SumTypePA_IWntPayYou";
//
//
//        @Override
//        public Fragment getItem(int position) {
//            Log.d(TAG, "position : " + String.valueOf(position));
//
//            /**
//             OQSumT = getArguments().getString("OQSumT");
//             myName = getArguments().getString("myName");
//             hisName = getArguments().getString("hisName");
//             moneyStandard = getArguments().getLong("moneyStandard");
//             moneyTarget = getArguments().getLong("moneyTarget");
//             */
//            Bundle bundle = new Bundle();
//            bundle.putString("myName",
//                    App.getName(getActivity()));
//            bundle.putString("hisName",
//                    ((NewOQActivity) getActivity()).arlOppoProfile.get(0).getFull_name());
//
//
//            switch (position) {
//                case 0:
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToPAY
//                                    .YOU_PAID_FOR_ME);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            ((NewOQActivity) getActivity()).ammountAsStandard
//                    );
//                    return SumTypePageFrag.newInstance(bundle);
//
//                case 1:
//                    bundle.putString("OQSumT",
//                            OQSumT.SoIWantToPAY
//                                    .ANYWAY);
//                    bundle.putLong("moneyStandard",
//                            ((NewOQActivity) getActivity()).ammountAsStandard);
//                    bundle.putLong("moneyTarget",
//                            ((NewOQActivity) getActivity()).ammountAsStandard
//                    );
//                    return SumTypePageFrag.newInstance(bundle);
//                default:
//                    return null;
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//    }
//
//
//}
