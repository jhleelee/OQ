package com.jackleeentertainment.oq.object;

/**
 * Created by Jacklee on 2016. 11. 5..
 */

public class MyOqPost {


   public long ts;
    public long mts;
    public  String pid;
    public Profile profile;


    public MyOqPost() {
        super();
    }


    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public long getMts() {
        return mts;
    }

    public void setMts(long mts) {
        this.mts = mts;
    }
}
