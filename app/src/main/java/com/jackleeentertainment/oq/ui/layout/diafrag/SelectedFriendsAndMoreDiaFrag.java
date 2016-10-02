package com.jackleeentertainment.oq.ui.layout.diafrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class SelectedFriendsAndMoreDiaFrag extends BaseDiaFrag {

    static ArrayList<Profile> arlSelectedProfiles;

    public static SelectedFriendsAndMoreDiaFrag  newInstance(Bundle bundle, Context context) {
        SelectedFriendsAndMoreDiaFrag  frag = new SelectedFriendsAndMoreDiaFrag ();
        frag.setArguments(bundle);
        mContext = context;
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //bindData

        ////recyclerView
        Gson gson = new Gson();
        String jsonSelectedProfilesArrayList = savedInstanceState.getString("jsonSelectedProfilesArrayList");
        arlSelectedProfiles =
                gson.fromJson(jsonSelectedProfilesArrayList, new TypeToken<ArrayList<Profile>>(){}.getType());

        ////selectedPeopleNum & ocl

        //oclOk
    }
}
