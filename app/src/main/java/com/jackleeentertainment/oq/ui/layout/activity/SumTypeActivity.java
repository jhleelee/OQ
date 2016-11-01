//package com.jackleeentertainment.oq.ui.layout.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.jackleeentertainment.oq.R;
//import com.jackleeentertainment.oq.generalutil.JM;
//import com.jackleeentertainment.oq.object.Profile;
//import com.jackleeentertainment.oq.object.types.OQT;
//import com.jackleeentertainment.oq.object.util.ProfileUtil;
//import com.jackleeentertainment.oq.ui.layout.fragment.SumTypePageFrag;
//
//import java.util.ArrayList;
//
///**
// * Created by Jacklee on 2016. 10. 26..
// */
//
//public class SumTypeActivity extends AppCompatActivity {
//
//    ViewPager.OnPageChangeListener onPCListener_SoIWantToGETFromYou;
//    ViewPager.OnPageChangeListener onPCListener_SoIWantToGETFromYouGuys;
//    ViewPager.OnPageChangeListener onPCListener_SoIWantToPAY;
//    ArrayList<Profile> arlOppoProfile = new ArrayList<>();
//    Toolbar toolbar;
//    RelativeLayout roClose;
//    TextView tvToolbarTitle;
//    TextView tvAmmount;
//    EditText etAmmount;
//    ViewPager viewPager;
//    Button bt__lo_lefttext_rightoneaction;
//    String OQSumT;
//    String OQTWantT_Future;
//    long ammountAsStandard;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sumtype);
//
//        arlOppoProfile = ProfileUtil.getArlProfileFromJson(getIntent().getStringExtra("alreadySelected"));
//        ammountAsStandard = getIntent().getLongExtra("ammountAsStandard", 0);
//        OQSumT = getIntent().getStringExtra("OQSumT");
//        OQTWantT_Future = getIntent().getStringExtra("OQTWantT_Future");
//        initUI();
//        setSupportActionBar(toolbar);
//        initPageChangeListenerCandidates();
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        intiUIListener();
//        initUIAdapter();
//
//    }
//
//    void initUI() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        roClose = (RelativeLayout) findViewById(R.id.roClose);
//        tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
//        tvAmmount = (TextView) findViewById(R.id.tvAmmount);
//        etAmmount = (EditText) findViewById(R.id.etAmmount);
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        bt__lo_lefttext_rightoneaction = (Button) findViewById(R.id.bt__lo_lefttext_rightoneaction);
//    }
//
//
//    void intiUIListener(){
//        roClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
//        bt__lo_lefttext_rightoneaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent returnIntent = new Intent();
//                if (OQSumT!=null) {
//                    returnIntent.putExtra("OQSumT", OQSumT);
//                }
//                if (ammountAsStandard!=0){
//                    returnIntent.putExtra("ammountAsStandard", ammountAsStandard);
//                }
//                setResult(Activity.RESULT_OK,returnIntent);
//                finish();
//            }
//        });
//    }
//
//
//    void initUIAdapter() {
//
//        if (OQTWantT_Future.equals(OQT.WantT.GET)) {
//            if (arlOppoProfile != null) {
//                if (arlOppoProfile.size() == 1) {
//                    viewPager.addOnPageChangeListener(onPCListener_SoIWantToGETFromYou);
//                    viewPager.setAdapter(new SumTypePagerAdapter_SoIWantToGETFromYou(getSupportFragmentManager()));
//
//                } else if (arlOppoProfile.size() > 1) {
//                    viewPager.addOnPageChangeListener(onPCListener_SoIWantToGETFromYouGuys);
//                    viewPager.setAdapter(new SumTypePagerAdapter_SoIWantToGETFromYouGuys
//                            (getSupportFragmentManager()));
//                }
//            }
//
//        } else if (OQTWantT_Future.equals(OQT.WantT.PAY)) {
//            viewPager.addOnPageChangeListener(onPCListener_SoIWantToPAY);
//            viewPager.setAdapter(new SumTypePagerAdapter_SoIWantToPAY(getSupportFragmentManager()));
//
//        }
//
//
//    }
//
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
//            switch (position) {
//                case 0:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU);
//                case 1:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
//                            .I_PAID_FOR_YOU_AND_ME);
//                case 2:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou.ANYWAY);
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
//            switch (position) {
//                case 0:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME);
//                case 1:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
//                            .I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME);
//                case 2:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys.N_ANYWAY);
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
//        @Override
//        public Fragment getItem(int position) {
//            Log.d(TAG, "position : " + String.valueOf(position));
//            switch (position) {
//                case 0:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY.YOU_PAID_FOR_ME);
//                case 1:
//                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY.ANYWAY);
//
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
//    void initPageChangeListenerCandidates() {
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
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
//                                .I_PAID_FOR_YOU_AND_ME;
//                        tvAmmount.setText(JM.strById(R.string.money_sum));
//                        break;
//                    case 1:
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
//                                .I_PAID_FOR_YOU;
//                        tvAmmount.setText(JM.strById(R.string.money_individual));
//
//                        break;
//
//                    case 2:
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
//                                .ANYWAY;
//                        tvAmmount.setText(JM.strById(R.string.money_individual));
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
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
//                                .I_PAID_FOR_ALL_INCLUDING_YOU__INCLUDING_ME;
//                        tvAmmount.setText(JM.strById(R.string.money_sum));
//                        break;
//                    case 1:
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
//                                .I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME;
//                        tvAmmount.setText(JM.strById(R.string.money_sum));
//                        break;
//
//                    case 2:
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
//                                .N_ANYWAY;
//                        tvAmmount.setText(JM.strById(R.string.money_individual));
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
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY
//                                .YOU_PAID_FOR_ME;
//                        tvAmmount.setText(JM.strById(R.string.money_ammount_neatural));
//                        break;
//                    case 1:
//
//                        OQSumT = com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY
//                                .ANYWAY;
//                        tvAmmount.setText(JM.strById(R.string.money_ammount_neatural));
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
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(0, 0);
//    }
//}
