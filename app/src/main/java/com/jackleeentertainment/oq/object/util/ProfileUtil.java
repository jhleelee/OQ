package com.jackleeentertainment.oq.object.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class ProfileUtil {


    public static Profile getProfileToPublic(Profile profileToMe) {
        Profile profileToPublic = new Profile();
        profileToPublic.setLast_name(profileToMe.getLast_name());
        profileToPublic.setMiddle_name(profileToMe.getMiddle_name());
        profileToPublic.setFirst_name(profileToMe.getFirst_name());
        profileToPublic.setFull_name(profileToMe.getFull_name());
        profileToPublic.setEmail(profileToMe.getEmail());
        profileToPublic.setUid(profileToMe.getUid());
        return profileToPublic;
    }

    public static ArrayList<Profile> getArlProfileFromJson(String json) {
        ArrayList<Profile> arlReturn = new ArrayList();
        if (json != null) {
            arlReturn = new Gson().fromJson(json, new TypeToken<ArrayList<Profile>>(){}
                    .getType());
            return arlReturn;
        } else {
            return null;
        }
    }


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
