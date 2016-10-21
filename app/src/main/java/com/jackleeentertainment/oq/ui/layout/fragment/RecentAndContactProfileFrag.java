package com.jackleeentertainment.oq.ui.layout.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.jackleeentertainment.oq.object.Profile;
import com.jackleeentertainment.oq.ui.adapter.ProfileArlRecentAndContactRVAdapter;

import java.util.ArrayList;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class RecentAndContactProfileFrag extends Fragment {

    ArrayList<Profile> arlProfileIHavePhoneOrEmail;
    ArrayList<Profile> arlProfileMyRecent;

    ProfileArlRecentAndContactRVAdapter profileArlRecentAndContactRVAdapter;

    @NonNull
    public static RecentAndContactProfileFrag newInstance() {
        return new RecentAndContactProfileFrag();
    }


    /**
     * Getter & Setter
     */

    public ArrayList<Profile> getArlProfileMyRecent() {
        return arlProfileMyRecent;
    }

    public void setArlProfileMyRecent(ArrayList<Profile> arlProfileMyRecent) {
        this.arlProfileMyRecent = arlProfileMyRecent;
    }

    public ArrayList<Profile> getArlProfileIHavePhoneOrEmail() {
        return arlProfileIHavePhoneOrEmail;
    }

    public void setArlProfileIHavePhoneOrEmail(ArrayList<Profile> arlProfileIHavePhoneOrEmail) {
        this.arlProfileIHavePhoneOrEmail = arlProfileIHavePhoneOrEmail;
    }





}
