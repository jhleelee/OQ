package com.jackleeentertainment.oq.object;

import java.util.ArrayList;

/**
 * Created by Jacklee on 2016. 10. 26..
 */

public class ChatM {

    ArrayList<String> arlrids; //receiverId or roomId?
    String sid;
    long ts;
    String txt;
    String atch;
    String atcht;

    public ArrayList<String> getArlrids() {
        return arlrids;
    }

    public void setArlrids(ArrayList<String> arlrids) {
        this.arlrids = arlrids;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
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
