package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.jackleeentertainment.oq.object.MyOqPost;
import com.jackleeentertainment.oq.object.OQPostPhoto;
import com.jackleeentertainment.oq.object.OqDo;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.types.OQPostT;
import com.jackleeentertainment.oq.object.util.OqDoUtil;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.PostCommentActivity;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.PostViewHolder;
import com.jackleeentertainment.oq.ui.widget.EndlessRecyclerViewScrollListener;
import com.jackleeentertainment.oq.ui.widget.LoComment;
import com.jackleeentertainment.oq.ui.widget.LoMyOppo;
import com.jackleeentertainment.oq.ui.widget.LoOppoFeed;

import java.util.ArrayList;
import java.util.Iterator;
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

    LinearLayoutManager linearLayoutManager;

    @Override
    public void initUI() {
        super.initUI();
        linearLayoutManager = new LinearLayoutManager(App.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // Add the scroll listener
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                get100ObjIdOfFeedsFromFirebase(page);
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
            linearLayoutManager.scrollToPositionWithOffset(0, 0);


        } else {
            J.TOAST("App.fbaseDbRef!=null");
        }
    }


    void initRVAdapter() {

//        checkIfFirebaseListIsEmpty(getActivity());

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyOqPost, PostViewHolder>
                (MyOqPost.class,
                        R.layout.i_post,
                        PostViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyPosts)
                                .child(App.getUid(getActivity()))
                ) {


            @Override
            public int getItemCount() {
                int i = super.getItemCount();
                if (i==0){
                    ro_empty_list.setVisibility(View.VISIBLE);
                } else {
                    ro_empty_list.setVisibility(View.GONE);
                }
                return i;
            }



            public void populateViewHolder(
                    final PostViewHolder postViewHolder,
                    final MyOqPost myOqPost,
                    int position) {


                /*
                MyOqPost - Avatar, Name, Ts / by pid - OqDo, Comment
                 */

                if (myOqPost.getProfile().getUid() != null) {
                    JM.glideProfileThumb(
                            myOqPost.profile,
                            postViewHolder.ivAvatar,
                            postViewHolder.tvAvatar,
                            mFragment
                    );
                }


                postViewHolder.roAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (myOqPost != null && myOqPost.profile != null) {

                            Intent intent = new Intent(getActivity(), ProfileActivity.class);
                            intent.putExtra("Profile", myOqPost.profile);
                            if (myOqPost.profile.uid.equals(App.getUid(getActivity()))) {
                                intent.putExtra("isMe", true);
                            }
                            startActivity(intent);
                        }
                    }
                });

                postViewHolder.tvName.setText(myOqPost.profile.full_name);
                postViewHolder.tvDate.setText(JT.str(myOqPost.getTs()));


                //OqDo

                Query queryOqDo = App.fbaseDbRef
                        .child(FBaseNode0.OqDo)
                        .orderByChild("pid")
                        .equalTo(myOqPost.pid);
                queryOqDo
                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {

                                            List<OqDo> list = new ArrayList<OqDo>();
                                            long childrenCount = dataSnapshot.getChildrenCount();

                                            Iterable<DataSnapshot> i = dataSnapshot.getChildren();

                                            for (DataSnapshot d : i) {
                                                OqDo oqDo = d.getValue(OqDo.class);
                                                list.add(oqDo);
                                            }

                                            //(1) classify by Person
                                            ArrayList<ArrayList<OqDo>> arlArlOqDoPerPeople =
                                                    OqDoUtil
                                                    .getArlArlOqDoPerPeople(list, getActivity());

                                            for (ArrayList<OqDo> arlOqDoPerPeople :
                                                    arlArlOqDoPerPeople){ //For
                                                // each Person

                                                OqDoUtil.sortList(arlOqDoPerPeople);
                                                Profile profileOppo = OqDoUtil
                                                        .getOppoProfileFromOqDo(
                                                        arlOqDoPerPeople.get(0),
                                                                getActivity()
                                                );

                                                LoOppoFeed loMyOppo = new LoOppoFeed(getActivity());
                                                JM.glideProfileThumb(
                                                        profileOppo,
                                                        postViewHolder.ivAvatar,
                                                        postViewHolder.tvAvatar,
                                                        mFragment
                                                );
                                                loMyOppo.tvName.setText(profileOppo.full_name);


                                                {
                                                    loMyOppo.tvAmmount.setText(
                                                            J.st(OqDoUtil.getSumOqDoAmmounts
                                                                    (arlOqDoPerPeople))
                                                    );
                                                    loMyOppo.tvDeed.setText(OqDoUtil
                                                            .getOqDoListStr(arlOqDoPerPeople));
                                                }

                                                postViewHolder.loOqOppo.addView(loMyOppo);
                                            }



                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );

                //Comment

                Query query = App.fbaseDbRef
                        .child(FBaseNode0.OqPostComment)
                        .child(myOqPost.pid)
                        .orderByChild("ts")
                        .limitToLast(1);


                query
                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {
                                            Comment comment = dataSnapshot.getValue(Comment.class);
                                            if (comment != null) {

                                                postViewHolder.loCommentOne.removeAllViews();
                                                postViewHolder.loCommentOne.removeAllViewsInLayout();

                                                LoComment loComment = new LoComment(getActivity());

                                                JM.glideProfileThumb(
                                                        comment.profile,
                                                        loComment.ivAvatar,
                                                        loComment.tvAvatar,
                                                        mFragment
                                                );

                                                loComment.tvName.setText(comment.profile.full_name);

                                                loComment.tvTs.setText(JT.str(comment.ts));

                                                loComment.tvMultilineTxt.setText(comment.txt);

                                                postViewHolder.loCommentOne.addView(loComment);
                                            } else {
                                                postViewHolder.loCommentOne.setVisibility(View.GONE);
                                            }
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.d(TAG, databaseError.toString());
                                    }
                                }
                        );


                /*
                OqPost - Media, Comment
                 */


                //media ..


                App.fbaseDbRef
                        .child(FBaseNode0.OqPostPhoto)
                        .child(myOqPost.getPid())
                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {

                                            postViewHolder.roMedia.setVisibility(View.VISIBLE);
                                            postViewHolder.ivPhotoMain.setVisibility(View.VISIBLE);

                                            OQPostPhoto oqPostPhoto = dataSnapshot.getValue
                                                    (OQPostPhoto.class);

                                            List<String> oqPhotoIds = oqPostPhoto.getPhotoids();

                                            if (oqPhotoIds != null &&
                                                    oqPhotoIds.size() > 0) {

                                                Log.d(TAG, "oqPostPhoto.getPhotoids().get(0) " +
                                                        ": " + oqPhotoIds.get
                                                        (0));

                                                postViewHolder.roMedia.setVisibility(View.VISIBLE);
                                                postViewHolder.ivPhotoMain.setVisibility(View.VISIBLE);

                                                Glide.with(mFragment)
                                                        .using(new FirebaseImageLoader())
                                                        .load(App.fbaseStorageRef
                                                                .child(FStorageNode.FirstT.POST_PHOTO)
                                                                .child(oqPhotoIds.get(0)))
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
                                                                return false;
                                                            }
                                                        })
                                                        .into(postViewHolder.ivPhotoMain);


                                                if (oqPhotoIds.size() >= 2) {

                                                    postViewHolder.roPhotoSub
                                                            .setVisibility(View.VISIBLE);

                                                    //set Image
                                                    Glide.with(mFragment)
                                                            .using(new FirebaseImageLoader())
                                                            .load(App.fbaseStorageRef
                                                                    .child(FStorageNode.FirstT.POST_PHOTO)
                                                                    .child(oqPhotoIds
                                                                            .get(1)))
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .listener(new RequestListener<StorageReference, GlideDrawable>() {
                                                                @Override
                                                                public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                                    postViewHolder.roPhotoSub.setVisibility(View.GONE);

                                                                    return false;
                                                                }

                                                                @Override
                                                                public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                                                    return false;
                                                                }
                                                            })
                                                            .into(postViewHolder.ivPhotoSub);

                                                    if (oqPhotoIds.size() >= 3) {
                                                        //tv
                                                        postViewHolder.tvPhotoSubNum
                                                                .setVisibility(View.VISIBLE);

                                                        postViewHolder.tvPhotoSubNum
                                                                .setText("+" + J.st
                                                                        (oqPhotoIds.size()
                                                                                - 2));
                                                    }

                                                }


                                            }


                                        } else {
                                            postViewHolder.roMedia.setVisibility(View.GONE);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );


                App.fbaseDbRef
                        .child(FBaseNode0.OqPostText)
                        .child(myOqPost.getPid())
                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.exists()) {

                                            postViewHolder.tvSupportingText.setVisibility(View.VISIBLE);
                                            postViewHolder.tvSupportingText.setText(dataSnapshot
                                                    .getValue(String.class));

                                        } else {
                                            postViewHolder.tvSupportingText.setVisibility(View.GONE);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }
                        );


                postViewHolder.tvAddComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PostCommentActivity.class);
                        intent.putExtra("pid", myOqPost.getPid());
                        startActivity(intent);
                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


//    private void get100ObjIdOfFeedsFromFirebase(int lastLoadedObjIdDx) {
//
//        Query query = App.fbaseDbRef
//                .child("m_fd")
//                .child(App.getUid(getActivity()))
//                .startAt(lastLoadedObjIdDx + 1)
//                .endAt(lastLoadedObjIdDx + 1 + 100);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> i = dataSnapshot.getChildren();
//                int fidcount = 0;
//                int intTotalMemberNum = (int) dataSnapshot.getChildrenCount();
//
//                for (DataSnapshot d : i) {
//                    String keyPostOId = d.getKey();
//                    long valuePostLastUpdatedTs = d.getValue(Long.class);
//                    Ram.addKeyPostOIdValueLastTs(keyPostOId, valuePostLastUpdatedTs);
//                }
//
//                Ram.sortArlPostsByTs();
//                getContentsOfFirstEmpty20OfRAMArlPosts();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }


//    void getContentsOfFirstEmpty20OfRAMArlPosts() {
//
//        //specify first empty up to 20 items
//        ArrayList<Integer> arlDx = new ArrayList<>();
//        for (int i = 0; i < Ram.arlPosts.size(); i++) {
//            if (Ram.arlPosts.get(i).getPosttype() == null) {
//                arlDx.add(i);
//            }
//        }
//
//        for (dxArlPostsToUpdate = 0; dxArlPostsToUpdate < arlDx.size(); dxArlPostsToUpdate++) {
//
//            App.fbaseDbRef
//                    .child(FBaseNode0.OQPost)
//                    .child(Ram.arlPosts.get(arlDx.get(dxArlPostsToUpdate)).profile.uid)
//                    .child(Ram.arlPosts.get(arlDx.get(dxArlPostsToUpdate)).getPid())
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String key = dataSnapshot.getKey();
//                            OQPost post = dataSnapshot.getValue(OQPost.class);
//                            post.setPid(key);
//                            Ram.arlPosts.set(dxArlPostsToUpdate, post);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//        }
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    void checkIfFirebaseListIsEmpty(Activity activity) {

        JM.V(roProgress);

        App.fbaseDbRef
                .child(FBaseNode0.MyPosts)
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
