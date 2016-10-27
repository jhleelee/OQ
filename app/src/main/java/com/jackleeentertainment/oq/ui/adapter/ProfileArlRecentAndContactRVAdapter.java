package com.jackleeentertainment.oq.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.ContactsUtil;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameEmailChkViewHolder;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class ProfileArlRecentAndContactRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    boolean isAllRecentAll = false;
    boolean hasAllRecentRetrievedLeastOnce = false;
    Fragment mFragment;
    ArrayList<Profile> arlProfileSelected;
    ArrayList<Profile> arlMerged;
    ArrayList<Profile> arlProfileIHavePhoneOrEmail;
    ArrayList<Profile> arlProfileMyRecent;
    int numRecent = 0;
    int numContact = 0;
    String tempJsonArlProfileRecent;
    Gson gson = new Gson();

    static class SectionViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView tvTitle__lo_sectionviewholder, tvAsRightButton__lo_sectionviewholder;

        public SectionViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvTitle__lo_sectionviewholder =
                    (TextView) mView
                            .findViewById(R.id.tvTitle__lo_sectionviewholder);
            tvAsRightButton__lo_sectionviewholder =
                    (TextView) mView
                            .findViewById(R.id.tvAsRightButton__lo_sectionviewholder);
        }
    }

    public ProfileArlRecentAndContactRVAdapter(Fragment _mFragment) {
        super();
        mFragment = _mFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:

                return new AvatarNameEmailChkViewHolder(parent);
            case 1:
                return new SectionViewHolder(parent);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 1;
        } else if (1 <= position && position <= numRecent) {
            return 0;
        } else if (position == numRecent + 1) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        Profile profile = getItem(position);

        if (position == 0) {
            ((SectionViewHolder) viewHolder).tvTitle__lo_sectionviewholder.setText(JM.strById(R.string.recent_profiles));
            ((SectionViewHolder) viewHolder).tvAsRightButton__lo_sectionviewholder.setText(JM.strById(R.string.show_all));
            ((SectionViewHolder) viewHolder).tvAsRightButton__lo_sectionviewholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isAllRecentAll && !hasAllRecentRetrievedLeastOnce) {


                    } else if (!isAllRecentAll && hasAllRecentRetrievedLeastOnce) {

                        if (tempJsonArlProfileRecent != null) {
                            gson.fromJson(tempJsonArlProfileRecent, new TypeToken<ArrayList<Profile>>() {
                            }.getType());
                        }
                        mergeArlProfile(arlProfileMyRecent, arlProfileIHavePhoneOrEmail);
                        notifyDataSetChanged();

                    } else if (isAllRecentAll && !hasAllRecentRetrievedLeastOnce) {

                        // cannot be possible but....

                        tempJsonArlProfileRecent = gson.toJson(arlProfileMyRecent);
                        for (int i = 0; i < arlProfileMyRecent.size(); i++) {
                            if (i >= 5) {
                                arlProfileMyRecent.remove(i);
                            }
                        }
                        mergeArlProfile(arlProfileMyRecent, arlProfileIHavePhoneOrEmail);
                        notifyDataSetChanged();

                    } else if (isAllRecentAll && hasAllRecentRetrievedLeastOnce) {

                        tempJsonArlProfileRecent = gson.toJson(arlProfileMyRecent);
                        for (int i = 0; i < arlProfileMyRecent.size(); i++) {
                            if (i >= 5) {
                                arlProfileMyRecent.remove(i);
                            }
                        }
                        mergeArlProfile(arlProfileMyRecent, arlProfileIHavePhoneOrEmail);
                        notifyDataSetChanged();

                    }
                    //show all recents

                }
            });

        } else if (position == numRecent + 1) {

            ((SectionViewHolder) viewHolder).tvTitle__lo_sectionviewholder.setText(JM.strById(R.string.contacts));
            ((SectionViewHolder) viewHolder).tvAsRightButton__lo_sectionviewholder.setText(JM.strById(R.string.invite));
            ((SectionViewHolder) viewHolder).tvAsRightButton__lo_sectionviewholder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //invite
                }
            });

        } else if (1 <= position && position <= numRecent
                || numRecent + 2 <= position) {

            //set Name
            ((AvatarNameEmailChkViewHolder) viewHolder)
                    .tvTitle__lo_avatartitlesubtitle_chk.setText(profile.getFull_name());

            //set Phone Or Email
            ((AvatarNameEmailChkViewHolder) viewHolder)
                    .tvSubTitle__lo_avatartitlesubtitle_chk.setText(
                    ContactsUtil.strPhoneTwoSpaceEmail(profile)
            );

            //set Image
            Glide.with(mFragment)
                    .using(new FirebaseImageLoader())
                    .load(App.fbaseStorageRef
                            .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                            .child(profile.getUid())
                            .child(FStorageNode.createMediaFileNameToDownload(
                                    FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                    profile.getUid()

                            )))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((AvatarNameEmailChkViewHolder) viewHolder).ro_person_photo_iv);

            //set if checked
            if (arlProfileSelected.contains(profile)) {
                ((AvatarNameEmailChkViewHolder) viewHolder).checkboxJack__lo_avatartitlesubtitle_chk.setChecked(true);
            } else {
                ((AvatarNameEmailChkViewHolder) viewHolder).checkboxJack__lo_avatartitlesubtitle_chk.setChecked(false);
            }
        }


    }

    Profile getItem(int position) {
        return arlMerged.get(position);
    }


    @Override
    public int getItemCount() {
        return arlMerged.size();
    }


    /**
     * Firebase Database
     */
    void getAllRecentProfilesFirebase() {
        App.fbaseDbRef
                .child(FBaseNode0.MyRecent)
                .child(App.getUid(mFragment.getActivity()))
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {

                                    hasAllRecentRetrievedLeastOnce = true;

                                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                                    Iterator<DataSnapshot> iterator = iterable.iterator();
                                    arlProfileMyRecent = new ArrayList<Profile>();

                                    while (iterator.hasNext()) {
                                        DataSnapshot d = iterator.next();
                                        Profile profile = d.getValue(Profile.class);
                                        profile.setUid(d.getKey());
                                        arlProfileMyRecent.add(profile);
                                    }

                                    mergeArlProfile(arlProfileMyRecent, arlProfileIHavePhoneOrEmail);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );
    }


    void get5RecentProfilesFirebase() {

        if (!hasAllRecentRetrievedLeastOnce) {
            Query query = App.fbaseDbRef
                    .child(FBaseNode0.MyRecent)
                    .child(App.getUid(mFragment.getActivity()))
                    .limitToLast(5);

            query.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                                Iterator<DataSnapshot> iterator = iterable.iterator();
                                arlProfileMyRecent = new ArrayList<Profile>();

                                while (iterator.hasNext()) {
                                    DataSnapshot d = iterator.next();
                                    Profile profile = d.getValue(Profile.class);
                                    profile.setUid(d.getKey());
                                    arlProfileMyRecent.add(profile);
                                }

                                mergeArlProfile(arlProfileMyRecent, arlProfileIHavePhoneOrEmail);
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );
        }


    }


    void getMyContactsProfilesFirebase() {
        Query query = App.fbaseDbRef
                .child(FBaseNode0.MyContacts)
                .child(App.getUid(mFragment.getActivity()))
                .orderByChild("full_name");

        query.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {

                            Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                            Iterator<DataSnapshot> iterator = iterable.iterator();
                            arlProfileIHavePhoneOrEmail = new ArrayList<Profile>();

                            while (iterator.hasNext()) {
                                DataSnapshot d = iterator.next();
                                Profile profile = d.getValue(Profile.class);
                                profile.setUid(d.getKey());
                                arlProfileIHavePhoneOrEmail.add(profile);
                            }

                            mergeArlProfile(arlProfileMyRecent, arlProfileIHavePhoneOrEmail);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }


    /**
     * Merge
     */

    ArrayList<Profile> mergeArlProfile(ArrayList<Profile> arlRecent, ArrayList<Profile> arlContact) {

        if (arlRecent == null) {
            arlRecent = new ArrayList<>();
        }

        if (arlContact == null) {
            arlContact = new ArrayList<>();
        }

        numRecent = arlRecent.size();
        numContact = arlContact.size();

        ArrayList<Profile> mergedArl = new ArrayList<>();
        mergedArl.add(new Profile()); //Section for Recent
        mergedArl.addAll(arlRecent);
        mergedArl.add(new Profile()); //Section for Contact
        mergedArl.addAll(arlContact);
        return mergedArl;
    }

    /**
     * Getter Setter
     */

    public ArrayList<Profile> getArlMerged() {
        return arlMerged;
    }

    public void setArlMerged(ArrayList<Profile> arlMerged) {
        this.arlMerged = arlMerged;
    }

    public ArrayList<Profile> getArlProfileIHavePhoneOrEmail() {
        return arlProfileIHavePhoneOrEmail;
    }

    public void setArlProfileIHavePhoneOrEmail(ArrayList<Profile> arlProfileIHavePhoneOrEmail) {
        this.arlProfileIHavePhoneOrEmail = arlProfileIHavePhoneOrEmail;
        arlMerged = mergeArlProfile(this.arlProfileMyRecent, this.arlProfileIHavePhoneOrEmail);
    }

    public ArrayList<Profile> getArlProfileMyRecent() {
        return arlProfileMyRecent;
    }

    public void setArlProfileMyRecent(ArrayList<Profile> arlProfileMyRecent) {
        this.arlProfileMyRecent = arlProfileMyRecent;
        arlMerged = mergeArlProfile(this.arlProfileMyRecent, this.arlProfileIHavePhoneOrEmail);
    }
}
