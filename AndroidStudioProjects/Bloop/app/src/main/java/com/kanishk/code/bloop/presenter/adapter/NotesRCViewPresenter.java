package com.kanishk.code.bloop.presenter.adapter;

import android.databinding.BaseObservable;
import android.graphics.Color;
import android.view.View;

import com.google.gson.Gson;
import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.data.AppConstants;
import com.kanishk.code.bloop.databinding.ItemNotesBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.model.notes.AudioNote;
import com.kanishk.code.bloop.model.notes.PhotoNote;

/**
 * Created by kanishk on 30/7/17.
 */

public class NotesRCViewPresenter extends BaseObservable {

    private NotesTable notesTable;
    private ItemNotesBinding binding;

    public NotesRCViewPresenter(NotesTable notesTable, ItemNotesBinding binding) {
        this.notesTable = notesTable;
        this.binding = binding;
    }

    public String getTitle() {
        if (notesTable.getTitle() == null || notesTable.getTitle().equals("")) {
            binding.noteHeading.setVisibility(View.GONE);
            return "";
        } else {
            return notesTable.getTitle();
        }
    }

    public String getTextContent() {
        return notesTable.getTextContent();
    }

    public String getPhotoAttachment() {
        if (notesTable.getPhotoNote() != null) {
            binding.noteImageAttachment.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            PhotoNote photoNote = gson.fromJson(notesTable.getPhotoNote(), PhotoNote.class);
            return photoNote.getPath();
        }
        else return null;
    }

    public String getAudioAttachmentDuration() {
        if (notesTable.getAudioNote() != null) {
            binding.noteAudioAttachment.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            AudioNote audioNote = gson.fromJson(notesTable.getAudioNote(), AudioNote.class);
            if (notesTable.getColor() == AppConstants.ColorConstants.COLOR_BLUE_1
                    || notesTable.getColor() == AppConstants.ColorConstants.COLOR_BLUE_2
                    || notesTable.getColor() == AppConstants.ColorConstants.COLOR_RED_1
                    || notesTable.getColor() == AppConstants.ColorConstants.COLOR_RED_2) {
                binding.playAudio.setImageResource(R.drawable.ic_microphone_white);
                binding.duration.setTextColor(binding.getRoot().getContext().getResources().getColor(R.color.colorWhite));
            }
            return audioNote.getDuration();
        }
        else return null;
    }

    public int getColor() {
        return Color.YELLOW;
    }
}
