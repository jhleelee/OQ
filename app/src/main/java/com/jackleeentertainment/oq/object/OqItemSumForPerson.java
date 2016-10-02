package com.jackleeentertainment.oq.object;

/**
 * Created by Jacklee on 2016. 9. 29..
 */

public class OqItemSumForPerson {

    Profile profile;
    OqResult oqresult;

    public OqItemSumForPerson() {
        super();
    }

    public OqResult getOqresult() {
        return oqresult;
    }

    public void setOqresult(OqResult oqresult) {
        this.oqresult = oqresult;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
