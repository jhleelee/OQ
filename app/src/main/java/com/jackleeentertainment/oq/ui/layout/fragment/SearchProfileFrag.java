package com.jackleeentertainment.oq.ui.layout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.generalutil.JM;
import com.jackleeentertainment.oq.generalutil.LBR;
import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.adapter.ProfileArlRVAdapter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class SearchProfileFrag extends Fragment {

    String TAG = this.getClass().getSimpleName();
    int numDone = 0;

    View view;
    RecyclerView recyclerView;
    ProfileArlRVAdapter profileArlRVAdapter;

    ArrayList<Profile> arlProfiles = new ArrayList<>();

    public SearchProfileFrag() {
        super();
    }

    @NonNull
    public static ListFrag newInstance() {
        return new ListFrag();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView ...");
        view = inflater.inflate(R.layout.frag_content_nodividerlist,
                container, false);
        initUI();
        initAdapter(arlProfiles);
        return view;
    }


    void initUI() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
    }

    void initAdapter(ArrayList<Profile> arlProfiles) {
        if (arlProfiles!=null && arlProfiles.size()==0) {
            JM.G(recyclerView);

        } else {
            JM.V(recyclerView);
            profileArlRVAdapter = new ProfileArlRVAdapter(this, arlProfiles);
        }
    }


    void initQueryByFullFirstLastNameOrEmail(String fullfirstlastnameOrEmail) {

        numDone = 0;
        final ArrayList<DataSnapshot> arlDataSnapshotOfProfiles = new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshotAll) {
                if (dataSnapshotAll.exists()) {
                    if (dataSnapshotAll.hasChildren()) {
                        Iterator<DataSnapshot> it = dataSnapshotAll.getChildren().iterator();
                        while (it.hasNext()) {
                            DataSnapshot dItem = (DataSnapshot) it.next();
                            if (arlDataSnapshotOfProfiles.contains(dItem)) {
                                arlDataSnapshotOfProfiles.add(dItem);
                            }
                        }
                    }
                }
                numDone++;
                if (numDone == 3) {
                    LBR.send("initQueryByFullFirstLastNameOrEmail()", arlDataSnapshotOfProfiles);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //(1 - Full) Query by Full Name and Update
        Query queryFullName =
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .orderByChild("full_name")
                        .limitToFirst(16);
        queryFullName.addListenerForSingleValueEvent(valueEventListener);

        //(1 - Last) Query by Last Name and Update
        Query queryLastName =
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .orderByChild("last_name")
                        .limitToFirst(16);
        queryLastName.addListenerForSingleValueEvent(valueEventListener);

        //(1) Query by Last Name and Update
        Query queryEmail =
                App.fbaseDbRef
                        .child(FBaseNode0.ProfileToPublic)
                        .orderByChild("email")
                        .limitToFirst(32);
        queryEmail.addListenerForSingleValueEvent(valueEventListener);


    }

    ArrayList<Profile> arlProfileFromarlDataSnapshot(ArrayList<DataSnapshot> arlDS) {
        ArrayList<Profile> arlProfiles = new ArrayList<>();
        for (int i = 0; i < arlDS.size(); i++) {
            Profile profile = arlDS.get(i).getValue(Profile.class);
            profile.setUid(arlDS.get(i).getKey());
            arlProfiles.add(profile);
        }
        return arlProfiles;
    }

}
