package com.jackleeentertainment.oq.object;

/**
 * Created by Jacklee on 2016. 9. 20..
 */

public class Chat {

    String rid; //receiverId or roomId?
    String sid;
    String sname;
    long ts;
    String txt;
    String atch;
    String atcht;

    public Chat() {
        super();
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getAtch() {
        return atch;
    }

    public void setAtch(String atch) {
        this.atch = atch;
    }

    public String getAtcht() {
        return atcht;
    }

    public void setAtcht(String atcht) {
        this.atcht = atcht;
    }
}
