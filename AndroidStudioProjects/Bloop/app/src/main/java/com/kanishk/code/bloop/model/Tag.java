package com.kanishk.code.bloop.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kanishk on 25/7/17.
 */

@DatabaseTable(tableName = "tag_index")
public class Tag implements Serializable {
    private static final long serialVersionUID = -222864131214757024L;

    @DatabaseField(generatedId = true, columnName = "id")
    private int index;

    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "item_count")
    private int item_count;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }
}
