package com.jackleeentertainment.oq.object;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class Group {

    String gid;
    String bg;
    String ico;
   long ts;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }
    public Group() {
        super();
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

}


