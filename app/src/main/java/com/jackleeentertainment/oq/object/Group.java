package com.jackleeentertainment.oq.object;

import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class Group {

    String gid;
    String title;
    String ico;
    String bg;
    String reqtype;
    String reqammount;
    String reqfrom;
    long ts;

    List<Profile> listprofile;


    public Group() {
        super();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

    public String getReqammount() {
        return reqammount;
    }

    public void setReqammount(String reqammount) {
        this.reqammount = reqammount;
    }

    public String getReqfrom() {
        return reqfrom;
    }

    public void setReqfrom(String reqfrom) {
        this.reqfrom = reqfrom;
    }



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

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
    public List<Profile> getListprofile() {
        return listprofile;
    }

    public void setListprofile(List<Profile> listprofile) {
        this.listprofile = listprofile;
    }
}


