package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.Ram;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.object.Comment;
import com.jackleeentertainment.oq.object.OQPost;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.CommentViewHolder;
import com.jackleeentertainment.oq.ui.widget.EndlessRecyclerViewScrollListener;
import com.jackleeentertainment.oq.ui.widget.LoMyOppo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 8..
 */

public class PostCommentFrag extends Fragment {


    String TAG = this.getClass().getSimpleName();

    View view;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    SearchView searchView;
    RelativeLayout ro_tv_done;
    RelativeLayout roProgress, ro_empty_list;

    LinearLayout loEmpty;
    ImageView ivEmpty;
    TextView tvEmptyTitle, tvEmptyDetail, tvEmptyLearnMore;
    EditText etWrite;
    ImageView ivWrite;
    Fragment mFragment = this;
    int dxArlPostsToUpdate = 0;
    String pid = "";

    public PostCommentFrag() {
        super();
    }


    @NonNull
    public static PostCommentFrag newInstance(Bundle bundleFromFrag0) {
        PostCommentFrag frag = new PostCommentFrag();
        frag.setArguments(bundleFromFrag0);
        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_comment,
                container, false);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public void initUI() {
        searchView = (SearchView) view.findViewById(R.id.searchView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ro_tv_done = (RelativeLayout) view.findViewById(R.id.ro_tv_done);
        recyclerView.setHasFixedSize(true);
        ro_tv_done.setVisibility(View.GONE);
        roProgress = (RelativeLayout) view.findViewById(R.id.vProgress);
        ro_empty_list = (RelativeLayout) view.findViewById(R.id.ro_empty_list);
        loEmpty = (LinearLayout) ro_empty_list.findViewById(R.id.loEmpty);
        ivEmpty = (ImageView) ro_empty_list.findViewById(R.id.ivEmpty);
        tvEmptyTitle = (TextView) ro_empty_list.findViewById(R.id.tvEmptyTitle);
        tvEmptyDetail = (TextView) ro_empty_list.findViewById(R.id.tvEmptyDetail);
        tvEmptyLearnMore = (TextView) ro_empty_list.findViewById(R.id.tvEmptyLearnMore);
        etWrite = (EditText) view.findViewById(R.id.etWrite);
        ivWrite = (ImageView) view.findViewById(R.id.ivWrite);

    }

    @Override
    public void onResume() {
        super.onResume();
        initUIData();
        initAdapterOnResume();

        ivWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comment comment = new Comment();
                comment.setTs(System.currentTimeMillis());
                comment.setTxt(etWrite.getText().toString());
                comment.setUid(App.getUid(getActivity()));
                comment.setUname(App.getUname(getActivity()));

                if (App.fbaseDbRef != null && pid != null) {
                    App.fbaseDbRef
                            .child(FBaseNode0.OQPostComment)
                            .child(pid)
                            .push()
                            .setValue(comment)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    etWrite.setText("");
                                }
                            });
                }
            }
        });

    }


    void initAdapterOnResume() {
        pid = getArguments().getString("pid");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (App.fbaseDbRef != null) {
            initRVAdapter(pid);
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        } else {
            J.TOAST("App.fbaseDbRef!=null");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.cleanup();
        }
    }


    LinearLayoutManager linearLayoutManager;


    public void initUIData() {
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
        loEmpty.setBackgroundColor(JM.colorById(R.color.material_grey500));
        tvEmptyTitle.setText(JM.strById(R.string.no_comment));
        tvEmptyDetail.setText(JM.strById(R.string.add_firstcomment));
        tvEmptyLearnMore.setVisibility(View.GONE);
        ivEmpty.setImageDrawable(JM.tintedDrawable(
                R.drawable.ic_comment_white_48dp,
                R.color.text_black_54,
                getActivity()));

    }


    void initRVAdapter(String pid) {

        checkIfFirebaseListIsEmpty(pid, getActivity());

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>
                (Comment.class,
                        R.layout.lo_avatar_namemultilinetext_lohourlikereply,
                        CommentViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.MyPosts)
                                .child(pid)
                ) {

            public void populateViewHolder(
                    final CommentViewHolder commentViewHolder,
                    final Comment comment,
                    int position) {

                //avatar ..

                if (comment.getUid() != null) {


                    JM.glideProfileThumb(
                            comment.getUid(),
                            comment.getUname(),
                            commentViewHolder.ivAvatar,
                            commentViewHolder.tvAvatar,
                            mFragment
                    );


                }
                commentViewHolder.roAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (comment != null && comment.getUid() != null) {
                            App.fbaseDbRef
                                    .child(FBaseNode0.ProfileToPublic)
                                    .child(comment.getUid())
                                    .addListenerForSingleValueEvent(
                                            new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        Profile profile = dataSnapshot.getValue(Profile
                                                                .class);
                                                        Intent intent = new Intent(getActivity(), ProfileActivity.class);
                                                        intent.putExtra("Profile", profile);
                                                        if (profile.getUid().equals(App.getUid(getActivity()))) {
                                                            intent.putExtra("isMe", true);
                                                        }
                                                        startActivity(intent);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            }
                                    );
                        }


                    }
                });


                commentViewHolder.tvName.setText(comment.getUname());
                commentViewHolder.tvMultiline.setText(comment.getTxt());
                commentViewHolder.tvTs.setText(JT.str(comment.getTs()));


            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    void checkIfFirebaseListIsEmpty(String pid, Activity activity) {

        JM.V(roProgress);

        App.fbaseDbRef
                .child(FBaseNode0.OQPostComment)
                .child(pid)
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
