package com.kanishk.code.bloop.model.notes;

import java.io.Serializable;

/**
 * Created by kanishk on 26/7/17.
 */

public class PhotoNote implements Serializable {
    private String path;
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    private String timeStamp;
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
