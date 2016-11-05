package com.jackleeentertainment.oq.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.gson.Gson;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.object.OqItem;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQT;
import com.jackleeentertainment.oq.ui.layout.activity.BaseActivity;
import com.jackleeentertainment.oq.ui.layout.diafrag.DiaFragT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 10. 1..
 */

public class SetCardViewRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    FirstObject_IWant firstObjectIWant;
    SecondObject_Yous secondObject_yous;
    ThirdObject_Cause thirdObject_cause;
    FourthObject_Effect fourthObject_effect;
    FifthObject_Conclusion fifthObject_conclusion;
    Fragment mFragment;

    String wantT;
    Profile profileSubject;
    ArrayList<Profile> profileObjects;
    ArrayList<OqItem> oqItemCauses;

    class FirstObject_IWant {
        public FirstObject_IWant() {
            super();
        }
    }

    class SecondObject_Yous {
        public SecondObject_Yous() {
            super();
        }
    }


    class ThirdObject_Cause {
        public ThirdObject_Cause() {
            super();
        }
    }

    class FourthObject_Effect {
        OqItem oqItemEffect; // can auto-generated by goItemCauses

        public FourthObject_Effect(OqItem oqItemEffect) {
            this.oqItemEffect = oqItemEffect;
        }

    }


    class FifthObject_Conclusion {
        OqItem oqItemResult; // the same with oqItemEffect

        public FifthObject_Conclusion(OqItem oqItemResult) {
            super();
            this.oqItemResult = oqItemResult;
        }
    }

    /**
     * ViewHolderReceipt
     */

    class ViewHolderFirst extends RecyclerView.ViewHolder {
        CardView cardview__cardview_titlesubtitlemediatwoactionbtn;
        LinearLayout
                lo_titlesubtitlerectangle48_incardview__cardview_titlesubtitlemediatwoactionbtn, lo_twoactionbuttons__cardview_titlesubtitlemediatwoactionbtn;
        TextView tvContent__cardview_titlesubtitlemediatwoactionbtn,
                tvTitle__lo_titlesubtitlerectangle48_incardview,
                tvSubTitle__lo_titlesubtitlerectangle48_incardview;
        RelativeLayout
                ro_person_photo_48dip__lessmargin__lo_titlesubtitlerectangle48_incardview;
        Button btActionOne__lo_twoactionbuttons, btActionTwo__lo_twoactionbuttons;
        ImageView ro_person_photo_iv;

        public ViewHolderFirst(View itemView) { // root : cardview_titlesubtitlemediatwoactionbtn
            super(itemView);

            //mother

            cardview__cardview_titlesubtitlemediatwoactionbtn = (CardView) itemView.findViewById(R.id
                    .cardview__cardview_titlesubtitlemediatwoactionbtn);
            lo_titlesubtitlerectangle48_incardview__cardview_titlesubtitlemediatwoactionbtn =
                    (LinearLayout) itemView.findViewById(R.id
                            .lo_titlesubtitlerectangle48_incardview__cardview_titlesubtitlemediatwoactionbtn);
            tvContent__cardview_titlesubtitlemediatwoactionbtn =
                    (TextView) itemView.findViewById(R.id
                            .tvContent__cardview_titlesubtitlemediatwoactionbtn);
            lo_twoactionbuttons__cardview_titlesubtitlemediatwoactionbtn =
                    (LinearLayout) itemView.findViewById(R.id
                            .lo_twoactionbuttons__cardview_titlesubtitlemediatwoactionbtn);

            //lo_titlesubtitlerectangle48_incardview__cardview_titlesubtitlemediatwoactionbtn

            tvTitle__lo_titlesubtitlerectangle48_incardview =
                    (TextView)
                            lo_titlesubtitlerectangle48_incardview__cardview_titlesubtitlemediatwoactionbtn
                                    .findViewById(R.id.tvTitle__lo_titlesubtitlerectangle48_incardview);
            tvSubTitle__lo_titlesubtitlerectangle48_incardview =
                    (TextView)
                            lo_titlesubtitlerectangle48_incardview__cardview_titlesubtitlemediatwoactionbtn
                                    .findViewById(R.id.tvSubTitle__lo_titlesubtitlerectangle48_incardview);
            ro_person_photo_48dip__lessmargin__lo_titlesubtitlerectangle48_incardview =
                    (RelativeLayout)
                            lo_titlesubtitlerectangle48_incardview__cardview_titlesubtitlemediatwoactionbtn
                                    .findViewById(R.id.ro_person_photo_48dip__lessmargin__lo_titlesubtitlerectangle48_incardview);

            ////ro_person_photo_48dip__lessmargin__lo_titlesubtitlerectangle48_incardview

            ro_person_photo_iv = (ImageView)
                    ro_person_photo_48dip__lessmargin__lo_titlesubtitlerectangle48_incardview
                            .findViewById(R.id
                                    .ro_person_photo_48dip__lessmargin__lo_titlesubtitlerectangle48_incardview);

            //lo_twoactionbuttons__cardview_titlesubtitlemediatwoactionbtn

            btActionOne__lo_twoactionbuttons = (Button)
                    lo_twoactionbuttons__cardview_titlesubtitlemediatwoactionbtn.findViewById(R.id
                            .btActionOne__lo_twoactionbuttons);

            btActionTwo__lo_twoactionbuttons = (Button)
                    lo_twoactionbuttons__cardview_titlesubtitlemediatwoactionbtn.findViewById(R.id
                            .btActionTwo__lo_twoactionbuttons);


        }
    }

    class ViewHolderSecond extends RecyclerView.ViewHolder { // Share ViewHolderFirst?


        public ViewHolderSecond(View itemView) {
            super(itemView);

        }
    }

    class ViewHolderThird extends RecyclerView.ViewHolder {

        public ViewHolderThird(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderFourth extends RecyclerView.ViewHolder {
        public ViewHolderFourth(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderFifth extends RecyclerView.ViewHolder {

        public ViewHolderFifth(View itemView) {
            super(itemView);
        }
    }


    /**
     * Getter & Setter
     */

    public FirstObject_IWant getFirstObjectIWant() {
        return firstObjectIWant;
    }

    public void setFirstObjectIWant(FirstObject_IWant firstObjectIWant) {
        this.firstObjectIWant = firstObjectIWant;
        notifyDataSetChanged();
    }

    public SecondObject_Yous getSecondObject_yous() {
        return secondObject_yous;
    }

    public void setSecondObject_yous(SecondObject_Yous secondObject_yous) {
        this.secondObject_yous = secondObject_yous;
    }

    public ThirdObject_Cause getThirdObject_cause() {
        return thirdObject_cause;
    }

    public void setThirdObject_cause(ThirdObject_Cause thirdObject_cause) {
        this.thirdObject_cause = thirdObject_cause;
    }

    public FourthObject_Effect getFourthObject_effect() {
        return fourthObject_effect;
    }

    public void setFourthObject_effect(FourthObject_Effect fourthObject_effect) {
        this.fourthObject_effect = fourthObject_effect;
    }

    public FifthObject_Conclusion getFifthObject_conclusion() {
        return fifthObject_conclusion;
    }

    public void setFifthObject_conclusion(FifthObject_Conclusion fifthObject_conclusion) {
        this.fifthObject_conclusion = fifthObject_conclusion;
    }

    public Fragment getmFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    /**
     * Usual
     */


    public SetCardViewRVAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
            case 1:
                return new ViewHolderFirst(parent);
            case 2:
                return new ViewHolderThird(parent);
            case 3:
                return new ViewHolderFourth(parent);
            case 4:
                return new ViewHolderFifth(parent);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (position) {

            case 0:

                // title
                if (wantT.equals(OQT.DoWhat.GET))
                    ((ViewHolderFirst) holder).tvTitle__lo_titlesubtitlerectangle48_incardview
                            .setText(JM.strById(R.string.getter));
                else {
                    ((ViewHolderFirst) holder).tvTitle__lo_titlesubtitlerectangle48_incardview
                            .setText(JM.strById(R.string.payer));
                }

                // photo
                //set Image
                Glide.with(mFragment)
                        .using(new FirebaseImageLoader())
                        .load(App.fbaseStorageRef
                                .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                .child(profileSubject.getUid())
                                .child(FStorageNode.createMediaFileNameToDownload(
                                        FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                        profileSubject.getUid()
                                )))
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((ViewHolderFirst) holder).ro_person_photo_iv);


                // subtitle
                String name = profileSubject.getFull_name();

                String subTitle = StringGenerator.xNeedToXAboutMoney(name, wantT);
                ((ViewHolderFirst) holder).tvSubTitle__lo_titlesubtitlerectangle48_incardview
                        .setText(subTitle);

                // btns - listeners
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btActionOne__lo_twoactionbuttons:
                                wantT = OQT.DoWhat.GET;
                                JM.BGD(((Button) v), R.drawable.rec_radmd2_nostr_getprimary);
                                JM.TC(((Button) v), R.color.white);
                                JM.BGD(((ViewHolderFirst) holder).btActionTwo__lo_twoactionbuttons, R.color.transparent);
                                JM.TC(((Button) v), R.color.payPrimary);
                                notifyDataSetChanged();
                                break;

                            case R.id.btActionTwo__lo_twoactionbuttons:
                                wantT = OQT.DoWhat.PAY;
                                JM.BGD(((Button) v), R.drawable.rec_radmd2_nostr_payprimary);
                                JM.TC(((Button) v), R.color.white);
                                JM.BGD(((ViewHolderFirst) holder).btActionOne__lo_twoactionbuttons,
                                        R.color.transparent);
                                JM.TC(((Button) v), R.color.getPrimary);
                                notifyDataSetChanged();
                                break;
                        }
                    }
                };
                ((ViewHolderFirst) holder).btActionOne__lo_twoactionbuttons.setOnClickListener(onClickListener);
                ((ViewHolderFirst) holder).btActionTwo__lo_twoactionbuttons.setOnClickListener
                        (onClickListener);
                break;

            case 1:

                // title
                if (wantT.equals(OQT.DoWhat.GET))
                    ((ViewHolderFirst) holder).tvTitle__lo_titlesubtitlerectangle48_incardview
                            .setText(JM.strById(R.string.payer));
                else {
                    ((ViewHolderFirst) holder).tvTitle__lo_titlesubtitlerectangle48_incardview
                            .setText(JM.strById(R.string.getter));
                }

                // photo
                //set Image
                Glide.with(mFragment)
                        .using(new FirebaseImageLoader())
                        .load(App.fbaseStorageRef
                                .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                .child(profileSubject.getUid())
                                .child(FStorageNode.createMediaFileNameToDownload(
                                        FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                        profileSubject.getUid()
                                )))
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((ViewHolderFirst) holder).ro_person_photo_iv);

                // subtitle
                String nameAndNumOfObjects = StringGenerator.xAndXPeople(profileObjects);
                String subTitleOfObjects = StringGenerator.xNeedToXAboutMoney(nameAndNumOfObjects, wantT);
                ((ViewHolderFirst) holder).tvSubTitle__lo_titlesubtitlerectangle48_incardview
                        .setText(subTitleOfObjects);

                //btn
                // btns - listeners
                View.OnClickListener onClickListener2 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.btActionOne__lo_twoactionbuttons:
                                Gson gson = new Gson();
                                String jsonSelectedProfilesArrayList
                                         = gson.toJson(profileObjects);
                                Bundle bundle = new Bundle();
                                bundle.putString("diaFragT", DiaFragT.SelectedFriendsAndMore);
                                bundle.putString("jsonSelectedProfilesArrayList", jsonSelectedProfilesArrayList);
                                ((BaseActivity)mFragment.getActivity()).showDialogFragment(bundle);
                                break;

                        }
                    }
                };
                ((ViewHolderFirst) holder).btActionOne__lo_twoactionbuttons.setOnClickListener
                        (onClickListener2);
                ((ViewHolderFirst) holder).btActionTwo__lo_twoactionbuttons.setVisibility(View.GONE);

                break;

            case 2:

                break;


            case 3:

                break;


            case 4:

                break;


            default:
                break;
        }
    }


}