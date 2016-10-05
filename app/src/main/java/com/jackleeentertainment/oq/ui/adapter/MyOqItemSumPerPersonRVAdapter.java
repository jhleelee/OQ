package com.jackleeentertainment.oq.ui.adapter;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.object.OqItemSumForPerson;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameDetailViewHolder;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class MyOqItemSumPerPersonRVAdapter extends
        FirebaseRecyclerAdapter<OqItemSumForPerson, AvatarNameDetailViewHolder
                > {


    Fragment mFragment;

    public MyOqItemSumPerPersonRVAdapter(Class<OqItemSumForPerson> modelClass, int modelLayout,
                                         Class<AvatarNameDetailViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public MyOqItemSumPerPersonRVAdapter(Class<OqItemSumForPerson> modelClass, int modelLayout, Class<AvatarNameDetailViewHolder> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    public AvatarNameDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lo_avatar_titlesubtitle, parent, false);
        return new AvatarNameDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvatarNameDetailViewHolder viewHolder, int position) {
        OqItemSumForPerson oqItemSumForPerson = getItem(position);

        //set Name
        viewHolder.getTvTitle__lo_avatartitlesubtitle().setText(oqItemSumForPerson.getProfile().getFull_name());

        //set Image
        Glide.with(mFragment)
                .using(new FirebaseImageLoader())
                .load(App.fbaseStorageRef
                        .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                        .child(oqItemSumForPerson.getProfile().getUid())
                        .child(FStorageNode.createMediaFileNameToDownload(
                                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                oqItemSumForPerson.getProfile().getUid()
                        )))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.getRo_person_photo_iv());

        checkOqItemSum(oqItemSumForPerson.getProfile().getUid());
    }


    @Override
    protected void populateViewHolder(AvatarNameDetailViewHolder viewHolder, OqItemSumForPerson model, int position) {

    }

    void checkOqItemSum(String uid) {
        App.fbaseDbRef
                .child(FBaseNode0.MyOqItemSums)
                .child(App.getUID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> i = dataSnapshot.getChildren();
                        for (DataSnapshot d : i) {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public Fragment getmFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

}
