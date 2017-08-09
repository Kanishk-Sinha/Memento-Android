package com.kanishk.code.bloop.model.notes;

import java.io.Serializable;

/**
 * Created by kanishk on 26/7/17.
 */

public class BaseNote implements Serializable {

    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    private int colorId;
    public int getColorId() {
        return colorId;
    }
    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    private AudioNote audioNote;
    public AudioNote getAudioNote() {
        return audioNote;
    }
    public void setAudioNote(AudioNote audioNote) {
        this.audioNote = audioNote;
    }

    private PhotoNote photoNote;
    public PhotoNote getPhotoNote() {
        return photoNote;
    }
    public void setPhotoNote(PhotoNote photoNote) {
        this.photoNote = photoNote;
    }
}
