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
import com.jackleeentertainment.oq.R;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class ListFrag extends Fragment {

    String TAG = this.getClass().getSimpleName();

    View view;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    public ListFrag() {
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
        initAdapter();
        return view;
    }


    void initUI(){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
    }

    void initAdapter(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.cleanup();
        }
    }
}
