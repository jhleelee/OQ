package com.jackleeentertainment.oq.object.util;

import com.jackleeentertainment.oq.object.Group;

/**
 * Created by Jacklee on 2016. 10. 6..
 */

public class GroupUtil {

    public static Group getDefaultGroup(String gid){
        Group group = new Group();
        group.setBg("default");
        group.setIco("default");
        group.setGid(gid);
        group.setTs(System.currentTimeMillis());
        return group;
    }

}
