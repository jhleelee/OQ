//package com.jackleeentertainment.oq.ui.layout.fragment;
//
//import android.app.SearchManager;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.jackleeentertainment.oq.App;
//import com.jackleeentertainment.oq.R;
//import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
//import com.jackleeentertainment.oq.generalutil.JM;
//import com.jackleeentertainment.oq.generalutil.LBR;
//import com.jackleeentertainment.oq.object.Profile;
//import com.jackleeentertainment.oq.ui.adapter.ProfileArlCheckableRVAdapter;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import static com.facebook.FacebookSdk.getApplicationContext;
//
///**
// * Created by Jacklee on 2016. 10. 23..
// */
//
//public class PhoneContactsFrag extends ListFrag
//
//
//{
//
//
//    String TAG = this.getClass().getSimpleName();
//    int numDone = 0;
//
//    View view;
//    SearchView searchView;
//    ArrayList<Profile> arlProfiles = new ArrayList<>();
//
//
//    public PhoneContactsFrag() {
//        super();
//    }
//
//    @NonNull
//    public static PhoneContactsFrag newInstance() {
//        return new PhoneContactsFrag();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.d(TAG, "onCreateView ...");
//        view = inflater.inflate(R.layout.frag_content_nodividerlist,
//                container, false);
//        initUI();
//        initAdapterOnResume(arlProfiles);
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        SearchManager searchManager =
//                (SearchManager)getActivity(). getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//        //
//        searchView.setOnQueryTextListener(
//                new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        initQueryByNamePhone(query);
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//
//                        return false;
//                    }
//                });
//
//
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Begin", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//
//    void initUI() {
//        searchView= (SearchView) view.findViewById(R.id.searchView);
//        searchView.setVisibility(View.VISIBLE);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//    }
//
//    void initAdapterOnResume(ArrayList<Profile> arlProfiles) {
//        if (arlProfiles!=null && arlProfiles.size()==0) {
//            JM.G(recyclerView);
//
//        } else {
//            JM.V(recyclerView);
//            profileArlRVAdapter = new ProfileArlCheckableRVAdapter(this, arlProfiles);
//        }
//    }
//
//
//
//
//
//
//}
