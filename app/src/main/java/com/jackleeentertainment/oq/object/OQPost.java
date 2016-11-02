package com.jackleeentertainment.oq.object;

import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 1..
 */

public class OQPost {

    String pid;
    String uid;
    String uname;
    String udeed;
    String posttype ; // 0null, 1video, 2photo
    String txt; //supportingTxt
    long ts;
    List<MyOppo> myOppos;
    Comment lastcmt;


    public OQPost() {
        super();
    }



    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUdeed() {
        return udeed;
    }

    public void setUdeed(String udeed) {
        this.udeed = udeed;
    }

    public String getPosttype() {
        return posttype;
    }

    public void setPosttype(String posttype) {
        this.posttype = posttype;
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

    public List<MyOppo> getMyOppos() {
        return myOppos;
    }

    public void setMyOppos(List<MyOppo> myOppos) {
        this.myOppos = myOppos;
    }

    public Comment getLastcmt() {
        return lastcmt;
    }

    public void setLastcmt(Comment lastcmt) {
        this.lastcmt = lastcmt;
    }

}
