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

/**
 * Created by Jacklee on 2016. 9. 14..
 */
public class MainFrag0 extends ListFrag {
    String TAG = this.getClass().getSimpleName();
    View view;


    public MainFrag0() {
        super();
    }

    @NonNull
    public static MainFrag0 newInstance() {
        return new MainFrag0();
    }

    @Override
    void initAdapter() {
        super.initAdapter();
        Query query = App.fbaseDbRef.child(FBaseNode0.MyOqItemSums)
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
