package com.kanishk.code.bloop.model.notes;

import java.io.Serializable;

/**
 * Created by kanishk on 2/8/17.
 */

public class ChecklistNoteItem implements Serializable {
    private boolean isChecked;
    public boolean getIsChecked() {
        return isChecked;
    }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
