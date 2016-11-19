package com.jackleeentertainment.oq;



  import com.jackleeentertainment.oq.object.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;



/**
 * Created by Jacklee on 2016. 9. 20..
 */

public class Ram {

    static public Profile myProfile;

//    static public ArrayList<OQPost> arlPosts;
    static public HashMap<String, Long> hmapKeyPostOIdValueLastTs;
//    static public HashMap<String, OQPost> hmapPosts;
    static public HashMap<String, Profile> hmapProfiles;

//    public static void initArlPosts() {
//        arlPosts = new ArrayList<>();
//    }

    public static void initHmapKeyPostOIdValueLastTs() {
        hmapKeyPostOIdValueLastTs = new HashMap<>();
    }

//    public static void initHmapPosts() {
//        hmapPosts = new HashMap<>();
//    }


    public static void initHmapProfiles() {
            hmapProfiles = new HashMap<>();
        }

//    public static void addPost(OQPost post) {
//        if (Ram.arlPosts == null){
//            Ram.initArlPosts();
//        }
//        Ram.arlPosts.add(post);
//    }


//
//    public static void addKeyPostOIdValueLastTs(String postId, long ts) {
//        if (Ram.hmapKeyPostOIdValueLastTs == null){
//            Ram.initHmapKeyPostOIdValueLastTs();
//        }
//        Ram.hmapKeyPostOIdValueLastTs.put(postId, ts);
//    }
//
//
////    public static void addPost(String postId, OQPost post) {
////        if (Ram.hmapPosts == null){
////            Ram.initHmapPosts();
////        }
////        Ram.hmapPosts.put(postId, post);
////    }
//
    public static void addProfile(String uid, Profile profile) {
        if (Ram.hmapProfiles == null){
            Ram.initHmapProfiles();
        }
        Ram.hmapProfiles.put(uid, profile);
    }



//     public static void sortArlPostsByTs(){
//         //sort by Ts
//         Collections.sort(Ram.arlPosts, new Comparator<OQPost>() {
//             @Override
//             public int compare(OQPost o1, OQPost o2) {
//                 return ((Long)o1.getTs()).compareTo((Long)o2.getTs());
//             }
//         });
//     }



}
