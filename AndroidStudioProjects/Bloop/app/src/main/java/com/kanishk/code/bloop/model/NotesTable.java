package com.kanishk.code.bloop.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kanishk on 19/7/17.
 */

@DatabaseTable(tableName = "notes")
public class NotesTable implements Serializable {

    public NotesTable() {

    }

    private static final long serialVersionUID = -222864131214757024L;

    @DatabaseField(generatedId = true, columnName = "index")
    private int index;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @DatabaseField(columnName = "title")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @DatabaseField(columnName = "text_content")
    private String textContent;
    public String getTextContent() {
        return textContent;
    }
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @DatabaseField(columnName = "photo_note")
    private String photoNote;
    public String getPhotoNote() {
        return photoNote;
    }
    public void setPhotoNote(String photoNote) {
        this.photoNote = photoNote;
    }

    @DatabaseField(columnName = "audio_note")
    private String audioNote;
    public String getAudioNote() {
        return audioNote;
    }
    public void setAudioNote(String audioNote) {
        this.audioNote = audioNote;
    }

    @DatabaseField(columnName = "checklist_note")
    private String checkListnote;
    public String getCheckListnote() {
        return checkListnote;
    }
    public void setCheckListnote(String checkListnote) {
        this.checkListnote = checkListnote;
    }

    @DatabaseField(columnName = "quote_note")
    private String quoteNote;
    public String getQuoteNote() {
        return quoteNote;
    }
    public void setQuoteNote(String quoteNote) {
        this.quoteNote = quoteNote;
    }

    @DatabaseField(columnName = "color")
    private int color;
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

}
