package com.jackleeentertainment.oq.ui.layout.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.object.Post;
import com.jackleeentertainment.oq.ui.widget.EndlessRecyclerViewScrollListener;
import com.jackleeentertainment.oq.ui.widget.FeedRVAdapter;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag1_Feeds extends ListFrag {
    String TAG = this.getClass().getSimpleName();
    View view;

    int dxArlPostsToUpdate = 0;

    public MainFrag1_Feeds() {
        super();
    }

    @NonNull
    public static MainFrag1_Feeds newInstance() {
        return new MainFrag1_Feeds();
    }


    @Override
    void initUI() {
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
    }


    @Override
    void initAdapter() {

        FeedRVAdapter feedRVAdapter = new FeedRVAdapter();
        recyclerView.setAdapter(feedRVAdapter);

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
                    Ram.addKeyPostOIdValueLastTs(keyPostOId,valuePostLastUpdatedTs);
                }

                Ram.sortArlPostsByTs();
                getContentsOfFirstEmpty20OfRAMArlPosts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    void getContentsOfFirstEmpty20OfRAMArlPosts(){

        //specify first empty up to 20 items
        ArrayList<Integer> arlDx = new ArrayList<>();
        for (int i = 0 ; i < Ram.arlPosts.size() ; i++){
            if (Ram.arlPosts.get(i).getPosttype()==null){
                arlDx.add(i);
            }
        }

        for (dxArlPostsToUpdate = 0 ; dxArlPostsToUpdate < arlDx.size() ; dxArlPostsToUpdate++){

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




}
