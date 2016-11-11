package com.jackleeentertainment.oq.object;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class OqWrap implements Serializable {


    String wid; //this's id;
    String gid; //groups'id;
    String qid; //appointment's id;
    String pid; //post's id;
    long ts; //listopdo's;
    List<OqDo> listoqdo;


    public OqWrap() {
        super();
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }


    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public List<OqDo> getListoqdo() {
        return listoqdo;
    }

    public void setListoqdo(List<OqDo> listoqdo) {
        this.listoqdo = listoqdo;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
