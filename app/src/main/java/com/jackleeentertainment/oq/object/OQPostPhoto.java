package com.jackleeentertainment.oq.object;

import java.util.List;

/**
 * Created by Jacklee on 2016. 11. 3..
 */

public class OQPostPhoto {

    String pid;
    List<String> photoids;

    public OQPostPhoto() {
        super();
    }

    public List<String> getPhotoids() {
        return photoids;
    }

    public void setPhotoids(List<String> photoids) {
        this.photoids = photoids;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
