package com.jackleeentertainment.oq.object;

import java.util.List;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class OqItem {

    String oid;
    String uidgiver;
    List<String> listuidbenefit;
    String uidgettor;
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

    public String getUidgiver() {
        return uidgiver;
    }

    public void setUidgiver(String uidgiver) {
        this.uidgiver = uidgiver;
    }

    public List<String> getListuidbenefit() {
        return listuidbenefit;
    }

    public void setListuidbenefit(List<String> listuidbenefit) {
        this.listuidbenefit = listuidbenefit;
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


}
