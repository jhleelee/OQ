package com.jackleeentertainment.oq.object;

import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class Post {





    String oid;
    String uid;
    String name;
    String src;
    String txt;
    long ts;
    long revealat; // for video
    String posttype ; // 0null, 1video, 2photo
    List<Comment> comments;
    List<String> like_uids;
    List<OqItem> oqItems;

    public Post() {
        super();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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

    public long getRevealat() {
        return revealat;
    }

    public void setRevealat(long revealat) {
        this.revealat = revealat;
    }

    public String getPosttype() {
        return posttype;
    }

    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getLike_uids() {
        return like_uids;
    }

    public void setLike_uids(List<String> like_uids) {
        this.like_uids = like_uids;
    }
}
