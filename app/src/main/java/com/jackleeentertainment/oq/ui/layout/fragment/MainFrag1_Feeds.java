package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.firebase.storage.FStorageNode;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.generalutil.StringGenerator;
import com.jackleeentertainment.oq.object.Comment;
import com.jackleeentertainment.oq.object.MyOppo;
import com.jackleeentertainment.oq.object.OQPost;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.object.types.OQPostT;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.PostViewHolder;
import com.jackleeentertainment.oq.ui.widget.EndlessRecyclerViewScrollListener;
import com.jackleeentertainment.oq.ui.widget.LoMyOppo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag1_Feeds extends ListFrag {
    String TAG = this.getClass().getSimpleName();
    View view;
    Fragment mFragment = this;

    int dxArlPostsToUpdate = 0;

    public MainFrag1_Feeds() {
        super();
    }

    @NonNull
    public static MainFrag1_Feeds newInstance() {
        return new MainFrag1_Feeds();
    }


    @Override
    public void initUI() {
        super.initUI();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // Add the scroll listener
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                get100ObjIdOfFeedsFromFirebase(page);
            }
        });
        searchView.setVisibility(View.GONE);

        tvEmptyTitle.setText(JM.strById(R.string.begin_feed));
        tvEmptyDetail.setText(JM.strById(R.string.begin_feed_long));
        tvEmptyLearnMore.setText(JM.strById(R.string.learn_more));
        ivEmpty.setImageDrawable(JM.drawableById(R.drawable.bg_feed0));

    }


    @Override
    void initAdapterOnResume() {

//        FeedRVAdapter feedRVAdapter = new FeedRVAdapter();
//        recyclerView.setAdapter(feedRVAdapter);

        if (App.fbaseDbRef != null) {

            initRVAdapter();


        } else {
            J.TOAST("App.fbaseDbRef!=null");
        }
    }


    void initRVAdapter() {

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<OQPost, PostViewHolder>
                (OQPost.class,
                        R.layout.i_post,
                        PostViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyPosts)
                                .child(App.getUid(getActivity()))
                ) {

            public void populateViewHolder(
                    final PostViewHolder postViewHolder,
                    final OQPost oqPost,
                    int position) {


                //avatar ..

                if (oqPost.getUid() != null) {

                    //set Image
                    Glide.with(mFragment)
                            .using(new FirebaseImageLoader())
                            .load(App.fbaseStorageRef
                                    .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                    .child(oqPost.getUid())
                                    .child(FStorageNode.createMediaFileNameToDownload(
                                            FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                            oqPost.getUid()
                                    )))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<StorageReference, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    postViewHolder.ivAvatar.setVisibility(View.GONE);
                                    postViewHolder.tvAvatar.setVisibility(View
                                            .VISIBLE);
                                    postViewHolder.tvAvatar.setText(oqPost.getUname()
                                            .substring(0, 1));
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    postViewHolder.ivAvatar.setVisibility(View.VISIBLE);
                                    postViewHolder.tvAvatar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(postViewHolder.ivAvatar);
                }

                postViewHolder.tvName.setText(oqPost.getUname());
                postViewHolder.tvDeed.setText(J.st(oqPost.getUdeed()));
                postViewHolder.tvDate.setText(JT.str(oqPost.getTs()));


                //media ..


                if (oqPost.getPosttype().equals(OQPostT.NONE)) {
                    postViewHolder.roMedia.setVisibility(View.GONE);
                }

                if (oqPost.getPosttype().equals(OQPostT.PHOTO)) {

                    //set Image
                    Glide.with(mFragment)
                            .using(new FirebaseImageLoader())
                            .load(App.fbaseStorageRef
                                    .child(FStorageNode.FirstT.POST_PHOTO)
                                    .child(oqPost.getPid())
                                    .child(FStorageNode.createMediaFileNameToDownload(
                                            FStorageNode.FirstT.POST_PHOTO,
                                            oqPost.getPid()
                                    )))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(new RequestListener<StorageReference, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    postViewHolder.roMedia.setVisibility(View.GONE);

                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    postViewHolder.roMedia.setVisibility(View.VISIBLE);
                                    postViewHolder.ivMedia.setVisibility(View.VISIBLE);

                                    return false;
                                }
                            })
                            .into(postViewHolder.ivMedia);
                }

                if (oqPost.getPosttype().equals(OQPostT.VIDEO)) {
                    postViewHolder.roMedia.setVisibility(View.VISIBLE);
                    postViewHolder.ivMedia.setVisibility(View.GONE);
                    postViewHolder.vvMedia.setVisibility(View.VISIBLE);

                }

                postViewHolder.tvSupportingText.setText(oqPost.getTxt());


                if (oqPost.getMyOppos()!=null){
                    List<MyOppo> list =oqPost.getMyOppos();

                    for (final MyOppo myOppo : list){

                        final LoMyOppo lo = new
                                LoMyOppo(getActivity());

                        //set Image
                        Glide.with(mFragment)
                                .using(new FirebaseImageLoader())
                                .load(App.fbaseStorageRef
                                        .child(FStorageNode.FirstT.PROFILE_PHOTO_THUMB)
                                        .child(App.getUid(getActivity()))
                                        .child(FStorageNode.createMediaFileNameToDownload(
                                                FStorageNode.FirstT.PROFILE_PHOTO_THUMB,
                                                myOppo.getUid()
                                        )))
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        lo.ivAvatar.setVisibility(View.GONE);
                                        lo.tvAvatar.setVisibility(View.VISIBLE);
                                        lo.tvAvatar.setText(myOppo.getUname()
                                                .substring(0, 1));
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        lo.ivAvatar.setVisibility(View.VISIBLE);
                                        lo.tvAvatar.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(lo.ivAvatar);

                        lo.ro_person_photo_48dip__i_oppo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                Bundle bundle = new Bundle();
                                intent.putExtra("uid", oqPost.getUid());
                                intent.putExtra("uname", oqPost.getUname());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });


                        lo.tvTitle__i_oppo.setText(myOppo.getUname());
                        JM.tvAmtTextBgAboutMuOppo(lo.tvAmtConfirmed, myOppo, 0);
                        JM.tvAmtTextBgAboutMuOppo(lo.tvAmtYet, myOppo, 1);
                        JM.tvAmtTextBgAboutMuOppo(lo.tvAmtDone, myOppo, 2);
                        lo.tvDeed.setText(StringGenerator.deed(myOppo));

                        postViewHolder.loOqOppo.addView(lo);
                    }



                }




                if (oqPost.getLastcmt()!=null){
                    Comment comment = oqPost.getLastcmt();

                    //comment layout

                }




            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    private void get100ObjIdOfFeedsFromFirebase(int lastLoadedObjIdDx) {

        Query query = App.fbaseDbRef
                .child("m_fd")
                .child(App.getUid(getActivity()))
                .startAt(lastLoadedObjIdDx + 1)
                .endAt(lastLoadedObjIdDx + 1 + 100);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> i = dataSnapshot.getChildren();
                int fidcount = 0;
                int intTotalMemberNum = (int) dataSnapshot.getChildrenCount();

                for (DataSnapshot d : i) {
                    String keyPostOId = d.getKey();
                    long valuePostLastUpdatedTs = d.getValue(Long.class);
                    Ram.addKeyPostOIdValueLastTs(keyPostOId, valuePostLastUpdatedTs);
                }

                Ram.sortArlPostsByTs();
                getContentsOfFirstEmpty20OfRAMArlPosts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    void getContentsOfFirstEmpty20OfRAMArlPosts() {

        //specify first empty up to 20 items
        ArrayList<Integer> arlDx = new ArrayList<>();
        for (int i = 0; i < Ram.arlPosts.size(); i++) {
            if (Ram.arlPosts.get(i).getPosttype() == null) {
                arlDx.add(i);
            }
        }

        for (dxArlPostsToUpdate = 0; dxArlPostsToUpdate < arlDx.size(); dxArlPostsToUpdate++) {

            App.fbaseDbRef
                    .child("fd")
                    .child(Ram.arlPosts.get(arlDx.get(dxArlPostsToUpdate)).getOid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String key = dataSnapshot.getKey();
                            Post post = dataSnapshot.getValue(Post.class);
                            post.setOid(key);
                            Ram.arlPosts.set(dxArlPostsToUpdate, post);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkIfFirebaseListIsEmpty(getActivity());

    }

    void checkIfFirebaseListIsEmpty(Activity activity) {

        JM.V(roProgress);

        App.fbaseDbRef
                .child(FBaseNode0.MyOqItems)
                .child(App.getUid(activity))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            JM.G(roProgress);
                            JM.V(ro_empty_list);
                        } else {
                            JM.G(roProgress);
                            JM.G(ro_empty_list);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        JM.G(roProgress);
                        JM.V(ro_empty_list);
                    }
                });

    }

}
