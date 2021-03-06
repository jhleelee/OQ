package com.jackleeentertainment.oq.ui.layout.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.activity.PeopleActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameEmailChkViewHolder;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class ContactProfileFrag extends Fragment {
    View view;

    boolean isContactItemExists = false;
    boolean isEmptyViewShown = false;
    boolean isProgressViewShown = true;

      String TAG = "RtCtctProfileFrag";

    RecyclerView rvAllMyContacts;
    FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder>
            frvAdapterAllMyContact;

    RelativeLayout vProgress, ro_empty_list;


    @NonNull
    public static ContactProfileFrag newInstance() {
        return new ContactProfileFrag();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_contactprofile, container, false);
        initUI();
        return view;
    }


    LinearLayout loEmpty;
    ImageView ivEmpty;
    TextView tvEmptyTitle, tvEmptyDetail, tvEmptyLearnMore;

    void initUI() {
        rvAllMyContacts = (RecyclerView) view.findViewById(R.id.rvAllMyContacts);
         vProgress = (RelativeLayout) view.findViewById(R.id.vProgress);
        ro_empty_list = (RelativeLayout) view.findViewById(R.id.ro_empty_list);
        loEmpty = (LinearLayout) ro_empty_list.findViewById(R.id.loEmpty);
        ivEmpty = (ImageView) ro_empty_list.findViewById(R.id.ivEmpty);
        tvEmptyTitle = (TextView) ro_empty_list.findViewById(R.id.tvEmptyTitle);
        tvEmptyDetail= (TextView) ro_empty_list.findViewById(R.id.tvEmptyDetail);
        tvEmptyLearnMore= (TextView) ro_empty_list.findViewById(R.id.tvEmptyLearnMore);
        tvEmptyTitle.setText(JM.strById(R.string.update_contact));
        tvEmptyDetail.setText(JM.strById(R.string.begin_sync_contact_long));
        tvEmptyLearnMore.setText(JM.strById(R.string.learn_more));

        vProgress .setVisibility(View.VISIBLE);
        boolean isProgressViewShown = true;

    }




    @Override
    public void onResume() {
        super.onResume();

        rvAllMyContacts.setHasFixedSize(true);
        rvAllMyContacts.setLayoutManager(new LinearLayoutManager(getActivity()));


//        frvAdapterAllMyContact = new FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder>
//                (Profile.class,
//                        R.layout.lo_avatar_titlesubtitle_chk,
//                        AvatarNameEmailChkViewHolder.class,
//                        App.fbaseDbRef
//                                .child(FBaseNode0.MyContacts)
//                                .child(App.getUid(getActivity()))
//                ) {
//            public void populateViewHolder(AvatarNameEmailChkViewHolder avatarNameEmailChkViewHolder,
//                                           Profile profile,
//                                           int position) {
//
//                avatarNameEmailChkViewHolder.tvTitle__lo_avatartitlesubtitle_chk
//                        .setText(profile.getFull_name()
//                        );
//
//                avatarNameEmailChkViewHolder.tvSubTitle__lo_avatartitlesubtitle_chk
//                        .setText(profile.getEmail()
//                        );
//
//                //set Image
//                JM.glideProfileThumb(
//                        profile.getUid(),
//                        profile.getFull_name(),
//                        avatarNameEmailChkViewHolder.ivAva,
//                        avatarNameEmailChkViewHolder.tvAva,
//                        getActivity()
//                );
//
//                ;
//
//                avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
//                        .setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //
//
//                            }
//                        });
//
//                isContactItemExists = true;
//
//                if (isProgressViewShown){
//                    vProgress.setVisibility(View.GONE);
//                    isProgressViewShown = false;
//                }
//
//                if (isEmptyViewShown){
//                    ro_empty_list.setVisibility(View.GONE);
//                    isEmptyViewShown=false;
//                }
//
//
//            }
//        };
        initRvAdapter();

        rvAllMyContacts.setAdapter(frvAdapterAllMyContact);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isContactItemExists=false) {

                    if (frvAdapterAllMyContact.getItemCount() == 0) {

                        vProgress.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list.setVisibility(View.VISIBLE);
                        isEmptyViewShown = true;

                        isContactItemExists = false;


                    } else {
                        vProgress.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list.setVisibility(View.GONE);
                        isEmptyViewShown = false;

                        isContactItemExists = true;

                    }
                } else {
                    vProgress.setVisibility(View.GONE);
                    isProgressViewShown = false;

                    ro_empty_list.setVisibility(View.VISIBLE);
                    isEmptyViewShown = true;

                }
            }
        }, 3000);

    }







    void initRvAdapter(){
        frvAdapterAllMyContact = new FirebaseRecyclerAdapter<Profile, AvatarNameEmailChkViewHolder>
                (Profile.class,
                        R.layout.lo_avatar_titlesubtitle_chk,
                        AvatarNameEmailChkViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyContacts)
                                .child(App.getUid(getActivity()))
                ) {
            public void populateViewHolder(
                    final AvatarNameEmailChkViewHolder avatarNameEmailChkViewHolder,
                    final Profile profile,
                    int position) {

                avatarNameEmailChkViewHolder.tvTitle__lo_avatartitlesubtitle_chk.setTextColor(
                        JM.colorById(R.color.text_black_87)
                );


                avatarNameEmailChkViewHolder.tvSubTitle__lo_avatartitlesubtitle_chk.setTextColor(
                        JM.colorById(R.color.text_black_54)
                );


                avatarNameEmailChkViewHolder.tvTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getFull_name()
                        );

                avatarNameEmailChkViewHolder.tvSubTitle__lo_avatartitlesubtitle_chk
                        .setText(profile.getEmail()
                        );

                //set Image

                //set Image
                JM.glideProfileThumb(
                        profile.getUid(),
                        profile.getFull_name(),
                        avatarNameEmailChkViewHolder.ro_person_photo_iv,
                        avatarNameEmailChkViewHolder.ro_person_photo_tv,
                        getActivity()
                );





                // if size is larger than 1, it is a problem.


                if (((PeopleActivity)getActivity()).arlSelectedProfile.contains(profile)) {
                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                            .setChecked(true);
                } else {
                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                            .setChecked(false);
                }

                avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (((PeopleActivity)getActivity()).arlSelectedProfile.contains
                                        (profile )) {
                                    ((PeopleActivity)getActivity()).arlSelectedProfile.remove(profile);
                                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk.setChecked(false);
                                } else {
                                    ((PeopleActivity)getActivity()).arlSelectedProfile.add(profile);
                                    avatarNameEmailChkViewHolder.checkboxJack__lo_avatartitlesubtitle_chk.setChecked(true);
                                }
                                ((PeopleActivity)getActivity()).bottomSheetControl((
                                        (PeopleActivity)getActivity()).arlSelectedProfile.size());
//                                notifyDataSetChanged();
                            }
                        });


                isContactItemExists = true;

                if (isProgressViewShown) {
                    vProgress.setVisibility(View.GONE);
                    isProgressViewShown = false;
                }

                if (isEmptyViewShown) {
                    ro_empty_list.setVisibility(View.GONE);
                    isEmptyViewShown = false;
                }
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(frvAdapterAllMyContact!=null){
            frvAdapterAllMyContact.cleanup();
        }
    }

}
