package com.jackleeentertainment.oq.ui.layout.fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.generalutil.JM;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class ListFrag extends Fragment {

    String TAG = this.getClass().getSimpleName();

    View view;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    SearchView searchView;
     RelativeLayout ro_tv_done;
    RelativeLayout roProgress, ro_empty_list;


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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    LinearLayout  loEmpty;
    ImageView ivEmpty;
    TextView tvEmptyTitle, tvEmptyDetail, tvEmptyLearnMore;

    public void initUI(){
        searchView = (SearchView)view.findViewById(R.id.searchView);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        ro_tv_done = (RelativeLayout)view.findViewById(R.id.ro_tv_done);
        recyclerView.setHasFixedSize(true);
         ro_tv_done.setVisibility(View.GONE);
        roProgress = (RelativeLayout) view.findViewById(R.id.vProgress);
        ro_empty_list = (RelativeLayout) view.findViewById(R.id.ro_empty_list);
        loEmpty = (LinearLayout) ro_empty_list.findViewById(R.id.loEmpty);
        ivEmpty = (ImageView) ro_empty_list.findViewById(R.id.ivEmpty);
        tvEmptyTitle = (TextView) ro_empty_list.findViewById(R.id.tvEmptyTitle);
        tvEmptyDetail= (TextView) ro_empty_list.findViewById(R.id.tvEmptyDetail);
        tvEmptyLearnMore= (TextView) ro_empty_list.findViewById(R.id.tvEmptyLearnMore);


    }

    @Override
    public void onResume() {
        super.onResume();
        uiProgressOrEmptyOnResume();
        initAdapterOnResume();
    }

    void initAdapterOnResume(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    void uiProgressOrEmptyOnResume(){


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.cleanup();
        }
    }
}
