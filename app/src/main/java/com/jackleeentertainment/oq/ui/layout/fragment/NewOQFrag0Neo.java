package com.jackleeentertainment.oq.ui.layout.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.OqItemUtil;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.widget.LoIvAvatarTvNameSmallEtAmountLargeIvBtns;
import com.jackleeentertainment.oq.ui.widget.LoIvAvatarTvNameSmallEtAmountLargeIvBtnsAtchSpent;
import com.jackleeentertainment.oq.ui.widget.NumericKeyBoardTransformationMethod;
import com.jackleeentertainment.oq.ui.widget.SimpleTextWatcher;
import com.jackleeentertainment.oq.ui.widget.SimpleTextWatcher2;

/**
 * Created by Jacklee on 2016. 10. 30..
 */

public class NewOQFrag0Neo extends Fragment {

    String TAG = this.getClass().getSimpleName();
    String OQTWantT_Future;
    NewOQFrag0Neo mFragment;

    /**
     * UI
     */
    View view;
    CardView cvPeople;
    TextView tvToGetOrPay;
    LinearLayout loBtAddPeople;
    ImageView ivBtAddPeople;
    TextView tvBtAddPeople;
    LinearLayout loSelectedPeople;
    public TextView tvSumOqItems;
    Button btEasyInput;

    CardView cvMySpent;
    TextView tvMySpent;
    LinearLayout loBtAddMySpent;
    ImageView ivBtAddMySpent;
    TextView tvBtAddMySpent;
    LinearLayout loMySpent;
    public TextView tvSumMySpent;
    Button btApplyMySpentToReq;
    TextView tvNext;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String data = intent.getStringExtra("data");


        }
    };


    public NewOQFrag0Neo() {
        super();
        mFragment = this;
    }

    @NonNull
    public static NewOQFrag0Neo newInstance(Bundle bundle) {
        NewOQFrag0Neo newOQFrag0Neo = new NewOQFrag0Neo();
        newOQFrag0Neo.setArguments(bundle);
        return newOQFrag0Neo;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_newoq_0_neo, container, false);
        OQTWantT_Future = getArguments().getString("OQTWantT_Future");
        initUI();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver,
                new IntentFilter(LBR.IntentFilterT.NewOQActivity_Frag0));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCosmetic();
        initVGView();
        initOnClickListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        cvProfileApplyArlSelectedProfilesToLo();
        cvMySpentApplyArlOqItemsPastToLo();
        tvNextEnableOrNot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);

    }

    void initOnClickListeners() {
        loBtAddPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewOQActivity) getActivity()).startActivityForResultPeopleActivity();
            }
        });

        btEasyInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((NewOQActivity) getActivity()).arlOQItem_Future != null &&
                        ((NewOQActivity) getActivity()).arlOQItem_Future.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("diaFragT", DiaFragT.EasyInput);
                    bundle.putString("OQTWantT_Future", OQTWantT_Future);
                    bundle.putInt("arlOQItem_FutureNum",
                            ((NewOQActivity) getActivity()).arlOQItem_Future.size());
                    ((NewOQActivity) getActivity()).showDialogFragment(bundle);
                }
            }
        });

        loBtAddMySpent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("diaFragT", DiaFragT.MySpentItem);
                bundle.putString("OQTWantT_Future", OQTWantT_Future);

                ((NewOQActivity) getActivity()).showDialogFragment(bundle);

            }
        });

        btApplyMySpentToReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int othersNum = ((NewOQActivity) getActivity()).arlOQItem_Future.size();
                //sumAmmountMySpent / (n+1)

                long div = OqItemUtil.getDivideByMeAndOthers(
                        OqItemUtil.getSumOqItemAmmounts(
                                ((NewOQActivity) getActivity()).arlOQItem_Paid
                        ),
                        othersNum);

                for (OqItem oqItem : ((NewOQActivity) getActivity()).arlOQItem_Future) {
                    oqItem.setAmmount(div);
                    //update view
                }

                long divSum = 0;
                for (OqItem oqItem : ((NewOQActivity) getActivity()).arlOQItem_Future) {
                    divSum += oqItem.getAmmount();
                }
                cvMySpentApplyArlOqItemsPastToLo();
                //update sum view

            }
        });

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ((NewOQActivity) getActivity()).goToFragment(
                        NewOQFrag1.newInstance(bundle),
                        R.id.fr_content
                );
            }
        });
    }


    /**
     * UI
     */


    public void cvProfileApplyArlSelectedProfilesToLo() {
        Log.d(TAG, "cvProfileApplyArlSelectedProfilesToLo()");

        loSelectedPeople.removeAllViews();

        if (((NewOQActivity) getActivity()).arlOQItem_Future == null ||
                ((NewOQActivity) getActivity
                ()).arlOQItem_Future.size() == 0) {
            JM.btEnable(btEasyInput, false);

        } else {
            JM.btEnable(btEasyInput, true);


            Log.d(TAG, "((NewOQActivity) getActivity()).arlOQItem_Future " +
                    J.st(((NewOQActivity) getActivity()).arlOQItem_Future.size()));

            for (final OqItem oqItem : ((NewOQActivity) getActivity()).arlOQItem_Future) {

                final LoIvAvatarTvNameSmallEtAmountLargeIvBtns lo = new
                        LoIvAvatarTvNameSmallEtAmountLargeIvBtns(getActivity());


                JM.glideProfileThumb(
                        oqItem.getUidclaimee(),
                        oqItem.getNameclaimee(),
                        lo.ivAvatar,
                        lo.tvAvatar,
                        this
                );


                lo.tvName.setText(oqItem.getNameclaimee());
                lo.setOnIvTocClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        long l = oqItem.getAmmount();
                        for (OqItem oqItem1 : ((NewOQActivity) getActivity()).arlOQItem_Future){
                            oqItem1.setAmmount(l);
                        }
                        cvProfileApplyArlSelectedProfilesToLo();
                    }
                });
                lo.setOnIvDeleteClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((NewOQActivity) getActivity()).arlOQItem_Future.remove(oqItem);
                        cvProfileApplyArlSelectedProfilesToLo();
                    }
                });

                lo.ivToc.setImageDrawable(
                        JM.tintedDrawable(
                                R.drawable.ic_toc_white_24dp,
                                R.color.text_black_54,
                                getActivity()
                        )
                );

                lo.ivDelete.setImageDrawable(
                        JM.tintedDrawable(
                                R.drawable.ic_delete_white_24dp,
                                R.color.text_black_54,
                                getActivity()
                        )
                );
                lo.etAmmount.setText(J.st(oqItem.getAmmount()));
                lo.etAmmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                lo.etAmmount.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                lo.etAmmount.addTextChangedListener(
                        new SimpleTextWatcher(
                                lo.etAmmount,
                                oqItem,
                                (NewOQActivity) getActivity(),
                                mFragment
                        ));


                loSelectedPeople.addView(lo);

                //update view
            }


        }


    }


    public void cvMySpentApplyArlOqItemsPastToLo() {
        Log.d(TAG, "cvMySpentApplyArlOqItemsPastToLo()");

        loMySpent.removeAllViews();

        if (((NewOQActivity) getActivity()).arlOQItem_Paid == null ||
                ((NewOQActivity) getActivity()).arlOQItem_Paid.size() == 0) {

            JM.btEnable(btApplyMySpentToReq, false);

        } else {

            JM.btEnable(btApplyMySpentToReq, true);

            for (final OqItem oqItem : ((NewOQActivity) getActivity()).arlOQItem_Paid) {

                final LoIvAvatarTvNameSmallEtAmountLargeIvBtnsAtchSpent lo = new
                        LoIvAvatarTvNameSmallEtAmountLargeIvBtnsAtchSpent(getActivity());


                lo.tvName.setText(oqItem.getNamegettor());
                lo.setOnIvTocClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("diaFragT", DiaFragT.MySpentItem);
                        bundle.putSerializable("oqItem", oqItem);
                        ((NewOQActivity) getActivity()).showDialogFragment(bundle);
                    }
                });
                lo.setOnIvDeleteClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((NewOQActivity) getActivity()).arlOQItem_Paid.remove(oqItem);
                        cvMySpentApplyArlOqItemsPastToLo();
                    }
                });

                lo.ivToc.setImageDrawable(
                        JM.tintedDrawable(
                                R.drawable.ic_mode_edit_white_24dp,
                                R.color.text_black_54,
                                getActivity()
                        )
                );

                lo.ivDelete.setImageDrawable(
                        JM.tintedDrawable(
                                R.drawable.ic_delete_white_24dp,
                                R.color.text_black_54,
                                getActivity()
                        )
                );

                lo. etAmmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                lo.  etAmmount.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                lo.etAmmount.addTextChangedListener(new
                        SimpleTextWatcher2(
                        lo.etAmmount,
                        oqItem,
                        (NewOQActivity) getActivity(),
                        mFragment
                ));


                loMySpent.addView(lo);

                //update view
            }


        }


    }


    public void tvNextEnableOrNot() {

        if (
                ((NewOQActivity) getActivity()).arlOQItem_Future == null ||
                        ((NewOQActivity) getActivity()).arlOQItem_Future.size() == 0 ||
                        OqItemUtil.isFalseOqItem(((NewOQActivity) getActivity()).arlOQItem_Future)
                ) {


            if (((NewOQActivity) getActivity()).arlOQItem_Future == null) {
                Log.d(TAG, "((NewOQActivity) getActivity()).arlOQItem_Future == null");
            }

            if (((NewOQActivity) getActivity()).arlOQItem_Future.size() == 0) {
                Log.d(TAG, "((NewOQActivity) getActivity()).arlOQItem_Future.size() == 0");
            }

            if (OqItemUtil.isFalseOqItem(((NewOQActivity) getActivity()).arlOQItem_Future)) {
                Log.d(TAG, "OqItemUtil.isFalseOqItem(((NewOQActivity) getActivity()).arlOQItem_Future");
            }

            Log.d(TAG, "tvNextEnableOrNot() false");
            JM.uiTextViewPosiNega(tvNext, false);
            tvNext.setClickable(false);
        } else {
            Log.d(TAG, "tvNextEnableOrNot() true");
            JM.uiTextViewPosiNega(tvNext, true);
            tvNext.setClickable(true);
        }

    }


    void initUI() {

        /**
         cvPERSON
         **/
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
        tvSumOqItems = (TextView) view.findViewById(R.id
                .tvSum);
        btEasyInput = (Button) view.findViewById(R.id
                .btEasyInput);

        cvMySpent = (CardView) view.findViewById(R.id
                .cvMySpent);
        tvMySpent = (TextView) view.findViewById(R.id
                .tvMySpent);
        loBtAddMySpent = (LinearLayout) view.findViewById(R.id
                .loBtAddMySpent);
        ivBtAddMySpent = (ImageView) view.findViewById(R.id
                .ivBtAddMySpent);
        tvBtAddMySpent = (TextView) view.findViewById(R.id
                .tvBtAddMySpent);
        loMySpent = (LinearLayout) view.findViewById(R.id
                .loMySpent);
        tvSumMySpent = (TextView) view.findViewById(R.id
                .tvSumMySpent);
        btApplyMySpentToReq = (Button) view.findViewById(R.id
                .btApplyMySpentToReq);
        tvNext = (TextView) view.findViewById(R.id.tvNext);
    }


    void initCosmetic() {

        if (OQTWantT_Future != null &&
                OQTWantT_Future.equals(OQT.DoWhat.GET)) {
            tvToGetOrPay.setText(JM.strById(R.string.request_detail));
        } else if (OQTWantT_Future != null &&
                OQTWantT_Future.equals(OQT.DoWhat.PAY)) {
            tvToGetOrPay.setText(JM.strById(R.string.getter));
        }
        tvMySpent.setText(JM.strById(R.string.myspent));

        ivBtAddPeople.setImageDrawable(JM.tintedDrawable(
                R.drawable.ic_add_white_48dp,
                R.color.colorPrimary,
                getActivity()
        ));
        ivBtAddMySpent.setImageDrawable(JM.tintedDrawable(
                R.drawable.ic_add_white_48dp,
                R.color.colorPrimary,
                getActivity()
        ));
    }


    void initVGView() {

        if (OQTWantT_Future != null &&
                OQTWantT_Future.equals(OQT.DoWhat.GET)) {
            JM.V(cvMySpent);
        } else if (OQTWantT_Future != null &&
                OQTWantT_Future.equals(OQT.DoWhat.PAY)) {
            JM.G(cvMySpent);
        }

    }

}
