package com.kanishk.code.bloop.model.notes;

import java.io.Serializable;

/**
 * Created by kanishk on 26/7/17.
 */

public class AudioNote implements Serializable {
    private String path;
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    private String duration;
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
