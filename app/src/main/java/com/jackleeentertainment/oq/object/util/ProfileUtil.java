package com.jackleeentertainment.oq.object.util;

import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class ProfileUtil {


    public static ArrayList<String> getArlUid(List<Profile> arlProfiles) {

        ArrayList<String> arlProfileUids = new ArrayList<>();

        for (int i = 0; i < arlProfiles.size(); i++) {
            arlProfileUids.add(arlProfiles.get(i).getUid());
        }
        return arlProfileUids;
    }

    public static ArrayList<String> getArlName(List<Profile> arlProfiles) {

        ArrayList<String> arlProfileNames = new ArrayList<>();

        for (int i = 0; i < arlProfiles.size(); i++) {
            arlProfileNames.add(arlProfiles.get(i).getFull_name());
        }
        return arlProfileNames;
    }

}
