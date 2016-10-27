package com.jackleeentertainment.oq.object;

import java.io.Serializable;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class OqItem implements Serializable{

    String oid; //this object's id
    String gid; //human group's id
    String qid; //oqitem group's id (in the case of case oqitem)
    String uidpayer;
    String uidgettor;
    String oqgnltype;



    String oqwnttype;
    long ts;
    long ammount;
    String currency;
    String duedate;




    public OqItem() {
        super();
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUidpayer() {
        return uidpayer;
    }

    public void setUidpayer(String uidpayer) {
        this.uidpayer = uidpayer;
    }


    public String getUidgettor() {
        return uidgettor;
    }

    public void setUidgettor(String uidgettor) {
        this.uidgettor = uidgettor;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getAmmount() {
        return ammount;
    }

    public void setAmmount(long ammount) {
        this.ammount = ammount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }



    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getOqwnttype() {
        return oqwnttype;
    }

    public void setOqwnttype(String oqwnttype) {
        this.oqwnttype = oqwnttype;
    }

    public String getOqgnltype() {
        return oqgnltype;
    }

    public void setOqgnltype(String oqgnltype) {
        this.oqgnltype = oqgnltype;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }
}
