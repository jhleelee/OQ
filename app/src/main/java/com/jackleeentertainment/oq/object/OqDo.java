package com.jackleeentertainment.oq.object;

import java.io.Serializable;

/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class OqDo implements Serializable {



  public   String oid;
    public String referoid;
    public  String uidab;
    public Profile profilea;
    public Profile profileb;
    public long ammount;
    public  long ts;
    public  String currency;
    public  String oqwhen;//point - future=obligation, now=paying, past=paid
    public  String oqwhat;//get ; pa


    public OqDo() {
        super();
    }


    public long getAmmount() {
        return ammount;
    }

    public void setAmmount(long ammount) {
        this.ammount = ammount;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getOqwhen() {
        return oqwhen;
    }

    public void setOqwhen(String oqwhen) {
        this.oqwhen = oqwhen;
    }

    public String getOqwhat() {
        return oqwhat;
    }

    public void setOqwhat(String oqwhat) {
        this.oqwhat = oqwhat;
    }



    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }



    public String getReferoid() {
        return referoid;
    }

    public void setReferoid(String referoid) {
        this.referoid = referoid;
    }
    public Profile getProfileb() {
        return profileb;
    }

    public void setProfileb(Profile profileb) {
        this.profileb = profileb;
    }

    public String getUidab() {
        return uidab;
    }

    public void setUidab(String uidab) {
        this.uidab = uidab;
    }

    public Profile getProfilea() {
        return profilea;
    }

    public void setProfilea(Profile profilea) {
        this.profilea = profilea;
    }

}
