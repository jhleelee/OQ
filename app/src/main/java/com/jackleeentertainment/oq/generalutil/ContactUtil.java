package com.jackleeentertainment.oq.generalutil;

import com.jackleeentertainment.oq.object.Profile;

/**
 * Created by jaehaklee on 2016. 10. 2..
 */

public class ContactUtil {

    /**
     * For Showing
     */

    public static String strPhoneTwoSpaceEmail(Profile profile) {

        String phone = profile.getPhone();
        String email = profile.getEmail();

        if (phone!= null && email != null) {
            return phone + "__" + email;
        } else if (phone!= null && email == null){
            return phone;
        } else if (phone== null && email != null){
            return email;
        } else {
            return null;
        }

    }

}
