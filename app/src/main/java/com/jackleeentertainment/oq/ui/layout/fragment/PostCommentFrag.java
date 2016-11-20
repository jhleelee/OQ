package com.jackleeentertainment.oq.ui.layout.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.JT;
import com.jackleeentertainment.oq.object.Comment;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.object.util.ProfileUtil;
import com.jackleeentertainment.oq.ui.layout.activity.ProfileActivity;
import com.jackleeentertainment.oq.ui.layout.viewholder.CommentViewHolder;
import com.jackleeentertainment.oq.ui.widget.EndlessRecyclerViewScrollListener;

/**
 * Created by Jacklee on 2016. 11. 8..
 */

public class PostCommentFrag extends Fragment {

    boolean isContactItemExists = false;
    boolean isEmptyViewShown = false;
    boolean isProgressViewShown = true;


    String TAG = this.getClass().getSimpleName();

    View view;
    RecyclerView recyclerView;
    LinearLayout lo_chat_writesend;
    EditText etWrite;
    ImageView ivWrite;


    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    RelativeLayout roProgress, ro_empty_list;


    TextView tvEmptyTitle, tvEmptyDetail ;
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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        lo_chat_writesend = (LinearLayout) view.findViewById(R.id.lo_chat_writesend);
        etWrite = (EditText) view.findViewById(R.id.etWrite);
        ivWrite = (ImageView) view.findViewById(R.id.ivWrite);
        ro_empty_list = (RelativeLayout) view.findViewById(R.id.ro_empty_list);
        roProgress = (RelativeLayout) view.findViewById(R.id.vProgress);

        tvEmptyTitle = (TextView) ro_empty_list.findViewById(R.id.tvEmptyTitle);
        tvEmptyDetail = (TextView) ro_empty_list.findViewById(R.id.tvEmptyDetail);

        recyclerView.setHasFixedSize(true);

        ivWrite.setImageDrawable(
                JM.tintedDrawable(
                        R.drawable.ic_send_white_48dp,
                        R.color.colorAccent,
                        getActivity()
                )
        );
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
                comment.profile = ProfileUtil.getMyProfileWithUidNameEmail(getActivity());

                if (App.fbaseDbRef != null && pid != null) {
                    App.fbaseDbRef
                            .child(FBaseNode0.OqPostComment)
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
         tvEmptyTitle.setText(JM.strById(R.string.no_comment));
        tvEmptyDetail.setText(JM.strById(R.string.add_firstcomment));


    }


    void initRVAdapter(String pid) {

        ro_empty_list.setVisibility(View.GONE);
        roProgress.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>
                (Comment.class,
                        R.layout.lo_commentlayout,
                        CommentViewHolder.class,
                        App.fbaseDbRef
                                .child(FBaseNode0.OqPostComment)
                                .child(pid)
                ) {

            public void populateViewHolder(
                    final CommentViewHolder commentViewHolder,
                    final Comment comment,
                    int position) {

                //avatar ..

                if (comment.profile.uid != null) {


                    JM.glideProfileThumb(
                            comment.profile,
                            commentViewHolder.ivAvatar,
                            commentViewHolder.tvAvatar,
                            mFragment
                    );


                }
                commentViewHolder.roAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (comment != null && comment.profile.uid != null) {
                            App.fbaseDbRef
                                    .child(FBaseNode0.ProfileToPublic)
                                    .child(comment.profile.uid)
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


                commentViewHolder.tvName.setText(comment.profile.full_name);
                commentViewHolder.tvMultiline.setText(comment.getTxt());
                commentViewHolder.tvTs.setText(JT.str(comment.getTs()));

                isContactItemExists = true;

                if (isProgressViewShown) {
                    roProgress.setVisibility(View.GONE);
                    isProgressViewShown = false;
                }

                if (isEmptyViewShown) {
                    ro_empty_list.setVisibility(View.GONE);
                    isEmptyViewShown = false;
                }
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isContactItemExists=false) {

                    if (firebaseRecyclerAdapter.getItemCount() == 0) {

                        roProgress.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list.setVisibility(View.VISIBLE);
                        isEmptyViewShown = true;

                        isContactItemExists = false;


                    } else {
                        roProgress.setVisibility(View.GONE);
                        isProgressViewShown = false;

                        ro_empty_list.setVisibility(View.GONE);
                        isEmptyViewShown = false;

                        isContactItemExists = true;

                    }
                } else {
                    roProgress.setVisibility(View.GONE);
                    isProgressViewShown = false;

                    ro_empty_list.setVisibility(View.GONE);
                    isEmptyViewShown = true;

                }
            }
        }, 3000);



    }



}
