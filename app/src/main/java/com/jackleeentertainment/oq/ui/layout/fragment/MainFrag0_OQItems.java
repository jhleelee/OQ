package com.jackleeentertainment.oq.ui.layout.fragment;

import android.support.annotation.NonNull;
import android.view.View;

import com.google.firebase.database.Query;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.R;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;
import com.jackleeentertainment.oq.object.OqItemSumForPerson;
import com.jackleeentertainment.oq.ui.adapter.MyOqItemSumPerPersonRVAdapter;
import com.jackleeentertainment.oq.ui.layout.viewholder.AvatarNameDetailViewHolder;

import hugo.weaving.DebugLog;

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag0_OQItems extends ListFrag {
    String TAG = this.getClass().getSimpleName();
    View view;


    public MainFrag0_OQItems() {
        super();
    }

    @NonNull
    public static MainFrag0_OQItems newInstance() {
        return new MainFrag0_OQItems();
    }

    @DebugLog
    @Override
    void initAdapter() {
        super.initAdapter();
        Query query = App.fbaseDbRef
                .child(FBaseNode0.MyOqItemSums)
                .child(App.getUID())
                .orderByChild("ts");
        firebaseRecyclerAdapter = new MyOqItemSumPerPersonRVAdapter(
                OqItemSumForPerson.class,
                R.layout.lo_avatar_titlesubtitle,
                AvatarNameDetailViewHolder.class,
                query
        );
    }
}
