package com.jackleeentertainment.oq.object;

/**
 * Created by Jacklee on 2016. 10. 26..
 */

public class ChatR { //multichat - saparate root

    String roomid; //receiverId or roomId?

    String sid;
    long ts;
    String txt;
    String atch;
    String atcht;



    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
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
