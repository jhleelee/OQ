package com.jackleeentertainment.oq.object;

import java.io.Serializable;

/**
 * Created by Jacklee on 2016. 11. 4..
 */

public class OqDo implements Serializable {



    String oid;


    String referoid;
    String wid;
    String uida;
    String namea;
    String emaila;
    String uidb;
    String nameb;
    String emailb;
    long ammount;
    long ts;
    String currency;
    String oqwhen;//point - future=obligation, now=paying, past=paid
    String oqwhat;//get ; pa


    public OqDo() {
        super();
    }

    public String getUida() {
        return uida;
    }

    public void setUida(String uida) {
        this.uida = uida;
    }

    public String getNamea() {
        return namea;
    }

    public void setNamea(String namea) {
        this.namea = namea;
    }

    public String getEmaila() {
        return emaila;
    }

    public void setEmaila(String emaila) {
        this.emaila = emaila;
    }

    public String getUidb() {
        return uidb;
    }

    public void setUidb(String uidb) {
        this.uidb = uidb;
    }

    public String getNameb() {
        return nameb;
    }

    public void setNameb(String nameb) {
        this.nameb = nameb;
    }

    public String getEmailb() {
        return emailb;
    }

    public void setEmailb(String emailb) {
        this.emailb = emailb;
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

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getReferoid() {
        return referoid;
    }

    public void setReferoid(String referoid) {
        this.referoid = referoid;
    }

}
