package com.jackleeentertainment.oq.object;

import com.google.firebase.database.Query;
import com.jackleeentertainment.oq.App;
import com.jackleeentertainment.oq.firebase.database.FBaseNode0;

import java.io.Serializable;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class Profile implements Serializable{

    /**
     * search people is done by email or full_name.
     * (1) query by email
     * (2) query by name
     * (3) merge & show
     *
     */



 public    String uid;
    public   String email;
    public   String full_name;
    public  String first_name;
    public  String middle_name;
    public  String last_name;


    public  String locale;
    public  String gender;
    public   String age;
    public  String phone;
    public   String updated_time;

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }



    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}
