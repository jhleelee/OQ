package com.jackleeentertainment.oq.object.util;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.generalutil.J;
import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 30..
 */

public class ProfileUtil {

    static
    String TAG = "ProfileUtil";
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


    public static Profile getMyProfileWithUidNameEmail(Activity activity){
        Profile myProfile = new Profile();
        myProfile.setUid(App.getUid(activity));
        myProfile.setFull_name(App.getUname(activity));
        myProfile.setEmail(App.getUemail(activity));
        return myProfile;
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



    public static ArrayList<String> getArlJsonProfiles(ArrayList<Profile> arrayListProfile) {

        Log.d(TAG, "getArlJsonProfiles()");
        ArrayList<String> arlReturn = new ArrayList();
        if (arrayListProfile != null) {
            Gson gson = new Gson();
            for (int i = 0 ; i < arrayListProfile.size() ; i++){
                arlReturn.add(gson.toJson(arrayListProfile.get(i)));
            }
            Log.d(TAG, "arlReturn.size() : " + J.st(arlReturn.size()));
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
