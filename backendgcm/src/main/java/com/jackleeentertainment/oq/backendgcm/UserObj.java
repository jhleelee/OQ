package com.jackleeentertainment.oq.backendgcm;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Jacklee on 2016. 10. 18..
 */
/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
@Cache
public class UserObj {
    @Id
    String uid;

    @Index
    String regId;
    // you can add more fields...

    public UserObj() {
        super();
    }
}
