//package com.jackleeentertainment.oq.object;
//
//import java.io.Serializable;
//
///**
// * Created by Jacklee on 2016. 9. 29..
// */
//
//public class OqItem implements Serializable{
//
//    //id
//    String oid; //this object's id
//    String gid; //human group's id
//    String qid; //oqitem group's id (in the case of case oqitem)
//
//
//    //people - claim
//    String uidclaimer;
//    String uidclaimee;
//
//
//    String nameclaimer;
//    String nameclaimee;
//
//    //people - content
//    String uidpayer;
//    String uidgettor;
//    String namepayer;
//    String namegettor;
//    //ammount
//    long ammount;
//
//    //point - future=obligation, now=paying, past=paid
//    String oqtype;
//
//    //ts
//    long ts;
//
//    String currency;
//    String duedate;
//
//
//
//    public OqItem() {
//        super();
//    }
//
//    public String getOid() {
//        return oid;
//    }
//
//    public void setOid(String oid) {
//        this.oid = oid;
//    }
//
//    public String getGid() {
//        return gid;
//    }
//
//    public void setGid(String gid) {
//        this.gid = gid;
//    }
//
//    public String getQid() {
//        return qid;
//    }
//
//    public void setQid(String qid) {
//        this.qid = qid;
//    }
//
//    public String getUidpayer() {
//        return uidpayer;
//    }
//
//    public void setUidpayer(String uidpayer) {
//        this.uidpayer = uidpayer;
//    }
//
//    public String getUidgettor() {
//        return uidgettor;
//    }
//
//    public void setUidgettor(String uidgettor) {
//        this.uidgettor = uidgettor;
//    }
//
//    public long getAmmount() {
//        return ammount;
//    }
//
//    public void setAmmount(long ammount) {
//        this.ammount = ammount;
//    }
//
//    public String getOqtype() {
//        return oqtype;
//    }
//
//    public void setOqtype(String oqtype) {
//        this.oqtype = oqtype;
//    }
//
//    public long getTs() {
//        return ts;
//    }
//
//    public void setTs(long ts) {
//        this.ts = ts;
//    }
//
//    public String getCurrency() {
//        return currency;
//    }
//
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//
//    public String getDuedate() {
//        return duedate;
//    }
//
//    public void setDuedate(String duedate) {
//        this.duedate = duedate;
//    }
//
//    public String getUidclaimee() {
//        return uidclaimee;
//    }
//
//    public void setUidclaimee(String uidclaimee) {
//        this.uidclaimee = uidclaimee;
//    }
//
//    public String getUidclaimer() {
//        return uidclaimer;
//    }
//
//    public void setUidclaimer(String uidclaimer) {
//        this.uidclaimer = uidclaimer;
//    }
//    public String getNameclaimer() {
//        return nameclaimer;
//    }
//
//    public void setNameclaimer(String nameclaimer) {
//        this.nameclaimer = nameclaimer;
//    }
//
//    public String getNameclaimee() {
//        return nameclaimee;
//    }
//
//    public void setNameclaimee(String nameclaimee) {
//        this.nameclaimee = nameclaimee;
//    }
//
//    public String getNamepayer() {
//        return namepayer;
//    }
//
//    public void setNamepayer(String namepayer) {
//        this.namepayer = namepayer;
//    }
//
//    public String getNamegettor() {
//        return namegettor;
//    }
//
//    public void setNamegettor(String namegettor) {
//        this.namegettor = namegettor;
//    }
//
//}
