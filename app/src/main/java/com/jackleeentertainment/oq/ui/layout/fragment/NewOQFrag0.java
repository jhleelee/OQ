package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.NewOQActivity;
import com.jackleeentertainment.oq.ui.layout.activity.PeopleActivity;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class NewOQFrag0 extends Fragment implements View.OnClickListener {

    String TAG = this.getClass().getSimpleName();
    final int REQ_PEOPLE = 99;
    final int REQ_SUMTYPE = 98;
    boolean hasSumTypeEdited = false;

    View view;
    CardView cardview_cause_people__frag_newoq_0, cardview_cause_sumtype__frag_newoq_0,
            cardview_cause_breakdown__frag_newoq_0;
    TextView tv_done__frag_newoq_0;
    SelectedPeopleListAdapter selectedPeopleListAdapter;
    ArrayList<Profile> mArrayListProfile = new ArrayList<>();

    LinearLayout loLv__PERSON, loLv__SUMTYPE, loLv__BREAKDOWN;
    TextView tv_title__cardview_cause__PERSON, tvEmpty__cardview_cause__PERSON,
            tv__lo_lefttext_rightoneaction__PERSON, tv_title__cardview_cause__SUMTYPE, tv__lo_lefttext_rightoneaction__SUMTYPE, tvEmpty__cardview_cause__SUMTYPE, tv_title__cardview_cause__BREAKDOWN, tv__lo_lefttext_rightoneaction__BREAKDOWN, tvEmpty__cardview_cause__BREAKDOWN;
    ListView lv__cardview_cause__PERSON, lv__cardview_cause__SUMTYPE, lv__cardview_cause__BREAKDOWN;
    LinearLayout lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__PERSON,
            lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__SUMTYPE, lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__BREAKDOWN;
    RelativeLayout roEmpty__cardview_cause__PERSON, roEmpty__cardview_cause__SUMTYPE, roEmpty__cardview_cause__BREAKDOWN;
    Button bt__lo_lefttext_rightoneaction__PERSON, bt__lo_lefttext_rightoneaction__SUMTYPE, bt__lo_lefttext_rightoneaction__BREAKDOWN;

    OqItem oqItem = new OqItem();

    public NewOQFrag0() {
        super();
        oqItem.setAmmount(0);
    }

    @NonNull
    public static NewOQFrag0 newInstance() {
        return new NewOQFrag0();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_newoq_0, container, false);
        initUI();
        uiDependOnOQItemCardViewPeople(oqItem);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cardview_cause_people__frag_newoq_0:


                break;
            case R.id.cardview_cause_sumtype__frag_newoq_0:

                if (!hasSumTypeEdited) {
                    Intent intent_cardview_cause_sumtype__frag_newoq_0 = new Intent(getActivity(), PeopleActivity.class);
                    startActivityForResult(intent_cardview_cause_sumtype__frag_newoq_0, REQ_SUMTYPE);
                }

                break;

            case R.id.cardview_cause_breakdown__frag_newoq_0:

                // no, deirect edit..

                break;


            case R.id.tv_done__frag_newoq_0:

                ((NewOQActivity) getActivity()).goToFragment(new NewOQFrag1(), R.id.lofragmentlayout);


                break;

        }
    }


    void initUI() {

        /**
         cardview_cause_people__frag_newoq_0
         **/
        cardview_cause_people__frag_newoq_0 = (CardView) view.findViewById(R.id
                .cardview_cause_people__frag_newoq_0);
        loLv__PERSON = (LinearLayout) cardview_cause_people__frag_newoq_0
                .findViewById(R.id.loLv);
        tv_title__cardview_cause__PERSON = (TextView) cardview_cause_people__frag_newoq_0
                .findViewById(R.id.tv_title__cardview_cause);
        lv__cardview_cause__PERSON = (ListView) cardview_cause_people__frag_newoq_0.findViewById(R.id
                .lv__cardview_cause);
        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
        lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__PERSON = (LinearLayout)
                cardview_cause_people__frag_newoq_0.findViewById(R.id
                        .lo_lefttext_rightoneaction_borderlesscolored__cardview_cause);

        tv__lo_lefttext_rightoneaction__PERSON = (TextView)
                lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__PERSON.findViewById(R.id
                        .tv__lo_lefttext_rightoneaction);

        bt__lo_lefttext_rightoneaction__PERSON = (Button)
                lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__PERSON.findViewById(R.id
                        .bt__lo_lefttext_rightoneaction);
        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
        roEmpty__cardview_cause__PERSON = (RelativeLayout)
                cardview_cause_people__frag_newoq_0.findViewById(R.id
                        .roEmpty__cardview_cause);
        tvEmpty__cardview_cause__PERSON = (TextView)
                cardview_cause_people__frag_newoq_0.findViewById(R.id
                        .tvEmpty__cardview_cause);

        /**
         cardview_cause_sumtype__frag_newoq_0
         **/
        cardview_cause_sumtype__frag_newoq_0 = (CardView) view.findViewById(R.id
                .cardview_cause_sumtype__frag_newoq_0);
        loLv__SUMTYPE = (LinearLayout) cardview_cause_sumtype__frag_newoq_0
                .findViewById(R.id.loLv);
        tv_title__cardview_cause__SUMTYPE = (TextView) cardview_cause_sumtype__frag_newoq_0
                .findViewById(R.id.tv_title__cardview_cause);
        lv__cardview_cause__SUMTYPE = (ListView) cardview_cause_sumtype__frag_newoq_0
                .findViewById(R.id
                        .lv__cardview_cause);
        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
        lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__SUMTYPE = (LinearLayout)
                cardview_cause_sumtype__frag_newoq_0
                        .findViewById(R.id
                                .lo_lefttext_rightoneaction_borderlesscolored__cardview_cause);

        tv__lo_lefttext_rightoneaction__SUMTYPE = (TextView)
                lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__SUMTYPE.findViewById(R.id
                        .tv__lo_lefttext_rightoneaction);

        bt__lo_lefttext_rightoneaction__SUMTYPE = (Button)
                lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__SUMTYPE.findViewById(R.id
                        .bt__lo_lefttext_rightoneaction);
        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
        roEmpty__cardview_cause__SUMTYPE = (RelativeLayout)
                cardview_cause_sumtype__frag_newoq_0.findViewById(R.id
                        .roEmpty__cardview_cause);
        tvEmpty__cardview_cause__SUMTYPE = (TextView)
                cardview_cause_sumtype__frag_newoq_0.findViewById(R.id
                        .tvEmpty__cardview_cause);


        /**
         cardview_cause_breakdown__frag_newoq_0
         **/

        cardview_cause_breakdown__frag_newoq_0 = (CardView) view
                .findViewById(R.id.cardview_cause_breakdown__frag_newoq_0);
        loLv__BREAKDOWN = (LinearLayout) cardview_cause_breakdown__frag_newoq_0
                .findViewById(R.id.loLv);
        tv_title__cardview_cause__BREAKDOWN = (TextView) cardview_cause_breakdown__frag_newoq_0
                .findViewById(R.id.tv_title__cardview_cause);
        lv__cardview_cause__BREAKDOWN = (ListView) cardview_cause_breakdown__frag_newoq_0
                .findViewById(R.id.lv__cardview_cause);
        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
        lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__BREAKDOWN = (LinearLayout)
                cardview_cause_breakdown__frag_newoq_0
                        .findViewById(R.id.lo_lefttext_rightoneaction_borderlesscolored__cardview_cause);

        tv__lo_lefttext_rightoneaction__BREAKDOWN = (TextView)
                lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__BREAKDOWN
                        .findViewById(R.id.tv__lo_lefttext_rightoneaction);

        bt__lo_lefttext_rightoneaction__BREAKDOWN = (Button)
                lo_lefttext_rightoneaction_borderlesscolored__cardview_cause__BREAKDOWN
                        .findViewById(R.id.bt__lo_lefttext_rightoneaction);
        ////lo_lefttext_rightoneaction_borderlesscolored__cardview_cause
        roEmpty__cardview_cause__BREAKDOWN = (RelativeLayout)
                cardview_cause_breakdown__frag_newoq_0
                        .findViewById(R.id.roEmpty__cardview_cause);
        tvEmpty__cardview_cause__BREAKDOWN = (TextView)
                cardview_cause_breakdown__frag_newoq_0
                        .findViewById(R.id.tvEmpty__cardview_cause);


        /**
         tv_done__frag_newoq_0
         **/

        tv_done__frag_newoq_0 = (TextView) view.findViewById(R.id.tv_done__frag_newoq_0);

    }

    void initAdapterData() {
        uiDataCardViewPeopleList(mArrayListProfile);
    }


    void initAdapter() {
        selectedPeopleListAdapter = new SelectedPeopleListAdapter(getActivity());
    }

    void uiDependOnOQItemCardViewPeople(OqItem oqItemEffect) {
        if (oqItemEffect.getUidgettor() == null || oqItemEffect.getUidpayer() == null) {
            JM.V(cardview_cause_people__frag_newoq_0);
            JM.G(cardview_cause_sumtype__frag_newoq_0);
            JM.G(cardview_cause_breakdown__frag_newoq_0);

            JM.V(roEmpty__cardview_cause__PERSON);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), PeopleActivity.class);
                    startActivityForResult(i, REQ_PEOPLE);
                }
            };
            roEmpty__cardview_cause__PERSON.setOnClickListener(onClickListener);
            tvEmpty__cardview_cause__PERSON.setOnClickListener(onClickListener);
        } else{
            JM.G(roEmpty__cardview_cause__PERSON);
            if (oqItemEffect.getUidpayer().equals(App.getUid(getActivity()))){
                mArrayListProfile = ProfileUtil.getArlProfileFromJson(oqItemEffect.getUidgettor());
                initAdapter();
                initAdapterData();
            } else if (oqItemEffect.getUidgettor().equals(App.getUid(getActivity()))){
                mArrayListProfile =  ProfileUtil.getArlProfileFromJson(oqItemEffect.getUidpayer());
                initAdapter();
                initAdapterData();
            }
        }
    }


    void uiDependOnOQItemCardViewSumType(OqItem oqItemEffect) {

        if (oqItemEffect.getAmmount()==0 ||oqItemEffect.getOqgnltype()==null||
                oqItemEffect.getOqwnttype()==null) {

        } else{

        }

    }



    void uiDependOnOQItemCardViewBreakdown(OqItem oqItemEffect) {



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case REQ_PEOPLE:
                    String result = data.getStringExtra("result");
                    uiDataCardViewPeopleList(result);
                    break;


            }


        }
    }


    private void uiDataCardViewPeopleList(String jsonStrSelectedProfiles) {
        Gson gson = new Gson();
        ArrayList<Profile> arlProfile = gson.fromJson(jsonStrSelectedProfiles, new
                TypeToken<ArrayList<Profile>>() {
                }.getType());

    }

    private void uiDataCardViewPeopleList(ArrayList<Profile> arlProfiles) {
        if (arlProfiles == null || arlProfiles.size() == 0) {

        } else {

        }
    }

    static class SelectedPeopleListAdapter extends BaseAdapter {

        LayoutInflater mInflater;
        Context mContext;
        ArrayList<Profile> mArrayListProfile = new ArrayList<>();

        public SelectedPeopleListAdapter(Context context) {
            super();
            this.mContext = context;
            mInflater = LayoutInflater.from(this.mContext);
        }


        @Override
        public int getCount() {
            return mArrayListProfile.size();
        }

        @Override
        public Profile getItem(int position) {
            return mArrayListProfile.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // create a ViewHolder reference
            ProfileViewHolder holder;

            //check to see if the reused view is null or not, if is not null then reuse it
            if (convertView == null) {
                holder = new ProfileViewHolder();
                convertView = mInflater.inflate(R.layout.lo_avatar_titlesubtitle_delete, parent, false);

                // get all views you need to handle from the cell and save them in the view holder

                holder.ro_person_photo_48dip__lo_avatartitlesubtitle_delete = (RelativeLayout) convertView.findViewById(R.id
                        .ro_person_photo_48dip__lo_avatartitlesubtitle_delete);
                holder.ro_person_photo_iv = (ImageView)
                        holder.ro_person_photo_48dip__lo_avatartitlesubtitle_delete
                                .findViewById(R.id
                                        .ro_person_photo_iv);
                holder.tvTitle__lo_avatartitlesubtitle_delete = (TextView) convertView.findViewById(R.id.tvTitle__lo_avatartitlesubtitle_delete);
                holder.tvSubTitle__lo_avatartitlesubtitle_delete = (TextView) convertView.findViewById(R.id.tvSubTitle__lo_avatartitlesubtitle_delete);
                holder.ibDelete = (ImageButton) convertView.findViewById(R.id.ibDelete);

                // save the view holder on the cell view to get it back latter
                convertView.setTag(holder);
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (ProfileViewHolder) convertView.getTag();
            }

            //get the string item from the position "position" from array list to put it on the TextView
            Profile profile = mArrayListProfile.get(position);
            if (profile != null) {
                //set the item name on the TextView
                //Glide
                holder.tvTitle__lo_avatartitlesubtitle_delete.setText(profile.getEmail());
                holder.tvSubTitle__lo_avatartitlesubtitle_delete.setText(profile.getFull_name());
            }

            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mArrayListProfile.remove(position);
                    notifyDataSetChanged();
                }
            });

            //this method must return the view corresponding to the data at the specified position.
            return convertView;

        }

        public ArrayList<Profile> getmArrayListProfile() {
            return mArrayListProfile;
        }

        public void setmArrayListProfile(ArrayList<Profile> mArrayListProfile) {
            this.mArrayListProfile = mArrayListProfile;
        }


        class ProfileViewHolder {
            RelativeLayout ro_person_photo_48dip__lo_avatartitlesubtitle_delete;
            ImageView ro_person_photo_iv;
            TextView tvTitle__lo_avatartitlesubtitle_delete,
                    tvSubTitle__lo_avatartitlesubtitle_delete;
            ImageButton ibDelete;
        }

    }


}
