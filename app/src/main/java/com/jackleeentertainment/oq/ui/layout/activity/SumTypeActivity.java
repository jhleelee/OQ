package com.jackleeentertainment.oq.ui.layout.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQSumT;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag0_OQItems;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag1_Feeds;
import com.jackleeentertainment.oq.ui.layout.fragment.MainFrag2_ChatroomList;
import com.jackleeentertainment.oq.ui.layout.fragment.SumTypePageFrag;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameViewHolder;
import com.konifar.fab_transformation.FabTransformation;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 26..
 */

public class SumTypeActivity extends AppCompatActivity {

    ViewPager.OnPageChangeListener onPCListener_SoIWantToGETFromYou;
    ViewPager.OnPageChangeListener onPCListener_SoIWantToGETFromYouGuys;
    ViewPager.OnPageChangeListener onPCListener_SoIWantToPAY;
    ArrayList<Profile> arlOppoProfile = new ArrayList<>();
    Toolbar toolbar;
    RelativeLayout roClose;
    TextView tvToolbarTitle;
    TextView tvAmmount;
    EditText etAmmount;
    ViewPager viewPager;
    Button bt__lo_lefttext_rightoneaction;
    String OQSumT;
    String OQTWantT_Future;
    long ammountAsStandard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumtype);

        arlOppoProfile = ProfileUtil.getArlProfileFromJson(getIntent().getStringExtra("alreadySelected"));
        ammountAsStandard = getIntent().getLongExtra("ammountAsStandard", 0);
        OQSumT = getIntent().getStringExtra("OQSumT");
        OQTWantT_Future = getIntent().getStringExtra("OQTWantT_Future");
        initUI();
        setSupportActionBar(toolbar);
        initPageChangeListenerCandidates();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initUIAdapter();

    }

    void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        roClose = (RelativeLayout) findViewById(R.id.roClose);
        tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        tvAmmount = (TextView) findViewById(R.id.tvAmmount);
        etAmmount = (EditText) findViewById(R.id.etAmmount);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bt__lo_lefttext_rightoneaction = (Button) findViewById(R.id.bt__lo_lefttext_rightoneaction);
    }


    void initUIData() {

    }


    void initUIAdapter() {

        if (OQTWantT_Future.equals(OQT.WantT.GET)) {
            if (arlOppoProfile != null) {
                if (arlOppoProfile.size() == 1) {
                    viewPager.addOnPageChangeListener(onPCListener_SoIWantToGETFromYou);
                    viewPager.setAdapter(new SumTypePagerAdapter_SoIWantToGETFromYou(getSupportFragmentManager()));

                } else if (arlOppoProfile.size() > 1) {
                    viewPager.addOnPageChangeListener(onPCListener_SoIWantToGETFromYouGuys);
                    viewPager.setAdapter(new SumTypePagerAdapter_SoIWantToGETFromYouGuys
                            (getSupportFragmentManager()));
                }
            }

        } else if (OQTWantT_Future.equals(OQT.WantT.PAY)) {
            viewPager.addOnPageChangeListener(onPCListener_SoIWantToPAY);
            viewPager.setAdapter(new SumTypePagerAdapter_SoIWantToPAY(getSupportFragmentManager()));

        }


    }


    /**
     * A simple pager frvAdapterAllMyContact that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class SumTypePagerAdapter_SoIWantToGETFromYou extends FragmentPagerAdapter {
        public SumTypePagerAdapter_SoIWantToGETFromYou(FragmentManager fm) {
            super(fm);
        }

        String TAG = "SumTypePA_IWntGETFrYou";

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "position : " + String.valueOf(position));
            switch (position) {
                case 0:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou.I_PAID_FOR_YOU);
                case 1:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou
                            .I_PAID_FOR_YOU_AND_ME);
                case 2:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYou.N_ANYWAY);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    private class SumTypePagerAdapter_SoIWantToGETFromYouGuys extends FragmentPagerAdapter {
        public SumTypePagerAdapter_SoIWantToGETFromYouGuys(FragmentManager fm) {
            super(fm);
        }

        String TAG = "SumTypePA_IWntGETFrYouG";

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "position : " + String.valueOf(position));
            switch (position) {
                case 0:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys.I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME);
                case 1:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys
                            .I_PAID_FOR_ALL_INCLUDING_YOU__BUT_ME);
                case 2:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToGETFromYouGuys.N_ANYWAY);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    private class SumTypePagerAdapter_SoIWantToPAY extends FragmentPagerAdapter {
        public SumTypePagerAdapter_SoIWantToPAY(FragmentManager fm) {
            super(fm);
        }

        String TAG = "SumTypePA_IWntPayYou";

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "position : " + String.valueOf(position));
            switch (position) {
                case 0:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY.YOU_PAID_FOR_ME);
                case 1:
                    return SumTypePageFrag.newInstance(com.jackleeentertainment.oq.object.types.OQSumT.SoIWantToPAY.ANYWAY);

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    void initPageChangeListenerCandidates() {
  onPCListener_SoIWantToGETFromYou
                = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;

                    case 2:

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };


 onPCListener_SoIWantToGETFromYouGuys
                = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;

                    case 2:

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

       onPCListener_SoIWantToPAY
                = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        break;
                    case 1:

                        break;

                    case 2:

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

    }

}
