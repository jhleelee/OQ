package com.jackleeentertainment.oq.firebase.fcm;


import com.jackleeentertainment.oq.object.Profile;

/**
 * Created by Jacklee on 2016. 9. 20..
 */

public class NotificationBody {

    String objId;
    Profile profile;
    Object obj;

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }


    public NotificationBody() {
        super();
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
