package com.jackleeentertainment.oq.object;

import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class Comment {


 public    Profile profile;
    public   String txt;
    public long ts;
    public List<Comment> comments;


    public Comment() {
        super();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }


}
