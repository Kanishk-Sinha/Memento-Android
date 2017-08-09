package com.kanishk.code.bloop.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kanishk on 19/7/17.
 */

@DatabaseTable(tableName = "bloop_session_table")
public class BloopSessionTable implements Serializable {

    private static final long serialVersionUID = -222864131214757024L;

    @DatabaseField(generatedId = true, columnName = "index")
    private int index;

    @DatabaseField(columnName = "session_index_id")
    private long sessionIndexId;

    @DatabaseField(columnName = "base_bloop")
    private String baseBloop;

    @DatabaseField(columnName = "is_starred")
    private boolean isStarred;

    @DatabaseField(columnName = "last_edited")
    private String lastEdited;

    @DatabaseField(columnName = "bloop_type")
    private int bloopType;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBaseBloop() {
        return baseBloop;
    }

    public void setBaseBloop(String baseBloop) {
        this.baseBloop = baseBloop;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public String getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }

    public int getBloopType() {
        return bloopType;
    }

    public void setBloopType(int bloopType) {
        this.bloopType = bloopType;
    }

    public long getSessionIndexId() {
        return sessionIndexId;
    }

    public void setSessionIndexId(long sessionIndexId) {
        this.sessionIndexId = sessionIndexId;
    }
}
