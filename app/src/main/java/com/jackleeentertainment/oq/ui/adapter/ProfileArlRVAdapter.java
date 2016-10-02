package com.jackleeentertainment.oq.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameEmailChkViewHolder;

import java.util.ArrayList;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class ProfileArlRVAdapter extends RecyclerView.Adapter<AvatarNameEmailChkViewHolder> {


    ArrayList<Profile> arlProfilesAll;
    ArrayList<Profile> arlProfileSelected;
    Fragment mFragment;

    public ProfileArlRVAdapter() {
        super();
    }

    public ProfileArlRVAdapter(Fragment _mFragment) {
        super();
        mFragment = _mFragment;
    }

    public ProfileArlRVAdapter(Fragment _mFragment, ArrayList<Profile> _arlProfiles) {
        super();
        mFragment = _mFragment;
        arlProfilesAll = _arlProfiles;
    }

    @Override
    public AvatarNameEmailChkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lo_avatar_titlesubtitle_chk, parent, false);
        return new AvatarNameEmailChkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvatarNameEmailChkViewHolder viewHolder, int position) {
        Profile profile = getItem(position);

        //set Name
        viewHolder.tvTitle__lo_avatartitlesubtitle_chk.setText(profile.getFull_name());

        //set Email
        viewHolder.tvSubTitle__lo_avatartitlesubtitle_chk.setText(profile.getEmail());

        //set Image
        Glide.with(mFragment)
                .using(new FirebaseImageLoader())
                .load(App.fbaseStorageRef
                        .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                        .child(profile.getUid())
                        .child(FStorageNode.createFileName(
                                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                profile.getUid(),
                                JM.getSuffixOfImgWithDeviceDpi(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                        )))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.ro_person_photo_iv);

        //set if checked
        if (arlProfileSelected.contains(profile)) {
            ((AvatarNameEmailChkViewHolder) viewHolder).checkboxJack__lo_avatartitlesubtitle_chk.setChecked(true);
        } else {
            ((AvatarNameEmailChkViewHolder) viewHolder).checkboxJack__lo_avatartitlesubtitle_chk.setChecked(false);
        }
    }

    Profile getItem(int position) {
        return arlProfilesAll.get(position);
    }


    @Override
    public int getItemCount() {
        return arlProfilesAll.size();
    }

    /**
     * Getter / Setter
     */

    public ArrayList<Profile> getArlProfilesAll() {
        return arlProfilesAll;
    }

    public void setArlProfilesAll(ArrayList<Profile> arlProfilesAll) {
        this.arlProfilesAll = arlProfilesAll;
    }

}
