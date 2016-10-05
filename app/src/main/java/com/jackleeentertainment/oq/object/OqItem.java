package com.jackleeentertainment.oq.object;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class OqItem {

    String oid;
    String uidpayer;
    String uidgettor;


    String uidgavebnf;
    String uidreceivedbnf;


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

    public String getUidgavebnf() {
        return uidgavebnf;
    }

    public void setUidgavebnf(String uidgavebnf) {
        this.uidgavebnf = uidgavebnf;
    }

    public String getUidreceivedbnf() {
        return uidreceivedbnf;
    }

    public void setUidreceivedbnf(String uidreceivedbnf) {
        this.uidreceivedbnf = uidreceivedbnf;
    }

}
