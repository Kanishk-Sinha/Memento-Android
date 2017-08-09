package com.kanishk.code.bloop.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.data.AppConstants;
import com.kanishk.code.bloop.data.DBHelper;
import com.kanishk.code.bloop.databinding.LayoutEditorFragmentBinding;
import com.kanishk.code.bloop.model.NoteColor;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.model.interfxs.SimpleItemTouchHelperCallback;
import com.kanishk.code.bloop.model.notes.AudioNote;
import com.kanishk.code.bloop.model.notes.BaseNote;
import com.kanishk.code.bloop.model.notes.ChecklistNoteItem;
import com.kanishk.code.bloop.model.notes.PhotoNote;
import com.kanishk.code.bloop.utils.RealPathUtil;
import com.kanishk.code.bloop.utils.UniversalImageBindingAdapter;
import com.kanishk.code.bloop.view.adapter.ChecklistAdapter;
import com.kanishk.code.bloop.view.adapter.ColorSelectorAdapter;
import com.kanishk.code.bloop.widget.GridItemDecoration;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kanishk on 26/7/17.
 */

public class EditorFragment extends DialogFragment {
    private LayoutEditorFragmentBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<BaseNote> baseNoteArrayList = new ArrayList<>();
    private ArrayList<ChecklistNoteItem> checklistNoteItemArrayList = new ArrayList<>();
    private EditorFragmentListener mListener;
    private DBHelper dbHelper;
    private boolean colorSelectorShown = false;
    private Gson gson = new Gson();
    private ChecklistAdapter adapter;
    private NotesTable notesTable;
    private boolean isInEditMode;

    public static EditorFragment newInstance() {
        return new EditorFragment();
    }

    public void setNote(NotesTable notesTable) {
        this.notesTable = notesTable;
        isInEditMode = true;
    }

    private NotesTable getNotesTable() {
        return this.notesTable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_editor_fragment, container, false);
        dbHelper = new DBHelper(getContext().getApplicationContext());
        initViews();
        if (!isInEditMode) {
            notesTable = new NotesTable();
            notesTable.setColor(AppConstants.ColorConstants.COLOR_WHITE_1);
        } else {
            notesTable = getNotesTable();
            showNote(notesTable);
        }
        Window window = getDialog().getWindow();
        assert window != null;
        window.setBackgroundDrawableResource(R.color.colorWhite);
        return binding.getRoot();
    }

    private void showNote(NotesTable notesTable) {
        Gson gson = new Gson();

        // GET HEADING
        binding.noteTitle.setText(notesTable.getTitle());

        // GET TEXT CONTENT
        binding.noteTextContent.setText(notesTable.getTextContent());

        // GET AUDIO ATTACHMENT
        if (notesTable.getAudioNote() != null) {
            binding.soundAttachment.setVisibility(View.VISIBLE);
            AudioNote audioNote = gson.fromJson(notesTable.getAudioNote(), AudioNote.class);
            binding.duration.setText(audioNote.getDuration());
            binding.soundAttachment.setOnClickListener(v -> {
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                AudioPlayerFragment newFragment1 = AudioPlayerFragment.newInstance(audioNote.getPath());
                newFragment1.show(ft1, "");
            });
        }

        // GET IMAGE ATTACHMENT
        if (notesTable.getPhotoNote() != null) {
            PhotoNote photoNote = gson.fromJson(notesTable.getPhotoNote(), PhotoNote.class);
            UniversalImageBindingAdapter.loadImage(binding.imageAttachment, photoNote.getPath());
            binding.imageContainerAtt.setVisibility(View.VISIBLE);
        }

        // GET CHECKLIST ATTACHMENT
        if (notesTable.getCheckListnote() != null) {
            this.checklistNoteItemArrayList = gson.fromJson(notesTable.getCheckListnote(), new TypeToken<ArrayList<ChecklistNoteItem>>(){}.getType());
            setupChecklistView(this.checklistNoteItemArrayList);
        }

        // GET COLOR
        /*Window window = getDialog().getWindow();
        assert window != null;
        window.setBackgroundDrawableResource(getContext()
                .getApplicationContext()
                .getResources()
                .getColor(ColorFilter.getColorForType(notesTable.getColor())));
        if (notesTable.getColor() == R.color.colorWhite) {
            setFieldsToBlack();
        } else {
            setFieldsToWhite();
        }*/
    }

    private void initViews() {
        binding.dismiss.setOnClickListener(v -> dismiss());
        binding.attach.setOnClickListener(v -> revealMenu());
        binding.starNote.setOnClickListener(v -> starThisNote());
        binding.changeColor.setOnClickListener(v -> {
            if (colorSelectorShown) {
                colorSelectorShown = false;
                showColorSelector(false);
            } else {
                colorSelectorShown = true;
                showColorSelector(true);
            }
        });

        RecyclerView colorSelectorRCView = binding.colorSelectorRcView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        colorSelectorRCView.setLayoutManager(linearLayoutManager);
        colorSelectorRCView.setNestedScrollingEnabled(false);
        colorSelectorRCView.setHasFixedSize(true);

        ColorSelectorAdapter colorSelectorAdapter = new ColorSelectorAdapter(getColorList());
        colorSelectorAdapter.setHasStableIds(true);
        colorSelectorRCView.setAdapter(colorSelectorAdapter);
        colorSelectorAdapter.setColorSelectorAdapterListener(new ColorSelectorAdapter.ColorSelectorAdapterListener() {
            @Override
            public void onColorSelected(int position, int colorId) {
                colorSelectorShown = false;
                showColorSelector(false);
                switch (position) {
                    case 0 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_RED_1);
                        break;
                    case 1 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_BLUE_1);
                        break;
                    case 2 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_GREEN_1);
                        break;
                    case 3 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_YELLOW_1);
                        break;
                    case 4 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_CREAM_1);
                        break;
                    case 5 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_RED_2);
                        break;
                    case 6 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_BLUE_2);
                        break;
                    case 7 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_GREEN_2);
                        break;
                    case 8 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_YELLOW_2);
                        break;
                    case 9 :
                        notesTable.setColor(AppConstants.ColorConstants.COLOR_CREAM_2);
                        break;
                }
                Window window = getDialog().getWindow();
                assert window != null;
                window.setBackgroundDrawableResource(colorId);
                if (colorId == R.color.colorWhite) {
                    setFieldsToBlack();
                } else {
                    setFieldsToWhite();
                }
            }
        });
    }

    private void setFieldsToWhite() {
        binding.noteTitle.setHintTextColor(getResources().getColor(R.color.colorWhite));
        binding.noteTitle.setTextColor(getResources().getColor(R.color.colorWhite));

        binding.noteTextContent.setHintTextColor(getResources().getColor(R.color.colorWhite));
        binding.noteTextContent.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void setFieldsToBlack() {
        binding.noteTitle.setHintTextColor(getResources().getColor(R.color.colorTextHeading));
        binding.noteTitle.setTextColor(getResources().getColor(R.color.colorTextHeading));

        binding.noteTextContent.setHintTextColor(getResources().getColor(R.color.colorTextHeading));
        binding.noteTextContent.setTextColor(getResources().getColor(R.color.colorTextHeading));
    }

    private void showColorSelector(boolean b) {
        if (b)
            binding.colorSelectorLayout.setVisibility(View.VISIBLE);
        else
            binding.colorSelectorLayout.setVisibility(View.GONE);
    }

    private void revealMenu() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        AttachmentFragment newFragment = AttachmentFragment.newInstance();
        newFragment.show(ft, "");
        newFragment.setAttachmentFragmentListener(new AttachmentFragment.AttachmentFragmentListener() {
            @Override
            public void onAttachFromCamera() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                CameraFragment newFragment = CameraFragment.newInstance();
                newFragment.show(ft, "");
                newFragment.setCameraFragmentListener(photoResult -> {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                    File imagesFolder = new File(Environment.getExternalStorageDirectory(), "bloop");
                    imagesFolder.mkdirs();
                    String path = imagesFolder + "/" + timeStamp + ".jpg";
                    File file = new File(path);
                    new Thread(() -> photoResult.saveToFile(file)).start();
                    UniversalImageBindingAdapter.loadImage(binding.imageAttachment, path);

                    PhotoNote p  = new PhotoNote();
                    p.setPath(path);
                    p.setTimeStamp(new SimpleDateFormat("HH:mm, EEE, d MMM yyyy", Locale.ENGLISH).format(new Date()));
                    notesTable.setPhotoNote(gson.toJson(p));

                    binding.imageAttachment.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onAttachFromGallery() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }

            @Override
            public void onAttachAudio() {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                AudioBloopFragment newFragment = AudioBloopFragment.newInstance(getContext().getApplicationContext());
                newFragment.show(ft, "");
                newFragment.setAudioBloopFragmentListener(audioNote -> {
                    notesTable.setAudioNote(gson.toJson(audioNote));
                    binding.soundAttachment.setVisibility(View.VISIBLE);
                    binding.duration.setText(audioNote.getDuration());
                    binding.soundAttachment.setOnClickListener(v -> {
                        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                        AudioPlayerFragment newFragment1 = AudioPlayerFragment.newInstance(audioNote.getPath());
                        newFragment1.show(ft1, "");
                    });
                });
            }

            @Override
            public void onAttachChecklist() {
                checklistNoteItemArrayList.add(new ChecklistNoteItem());
                setupChecklistView(checklistNoteItemArrayList);
            }
        });
    }

    private void setupChecklistView(ArrayList<ChecklistNoteItem> list) {
        binding.checklistView.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = binding.checklistRcView;
        LinearLayoutManager grid = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(grid);
        int spanCount = 1;
        int spacing = 1;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        adapter = new ChecklistAdapter(list, false);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        adapter.setChecklistAdapterListener(new ChecklistAdapter.ChecklistAdapterListener() {
            @Override
            public void onEnterPressed(ChecklistNoteItem checklistNoteItem) {
                checklistNoteItemArrayList.add(checklistNoteItemArrayList.size() - 1, checklistNoteItem);
                getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
            }

            @Override
            public void onFinishEditing() {
                checklistNoteItemArrayList.remove(checklistNoteItemArrayList.size() - 1);
                binding.noteTextContent.requestFocus();
            }

            @Override
            public void onItemChecked(int position, boolean status) {
                ChecklistNoteItem checklistNoteItem = checklistNoteItemArrayList.get(position);
                checklistNoteItem.setIsChecked(status);
                checklistNoteItemArrayList.remove(position);
                checklistNoteItemArrayList.add(position, checklistNoteItem);
            }
        });
    }

    private void starThisNote() {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        saveNote();
        super.onDismiss(dialog);
    }

    private void saveNote() {
        try {
            if (binding.noteTextContent.getText().toString().equals("")
                    && !binding.soundAttachment.isShown()
                    && !binding.imageAttachment.isShown()
                    && !binding.checklistRcView.isShown()) {
                // NOTHING TO SAVE
            } else {
                notesTable.setTitle(binding.noteTitle.getText().toString());
                notesTable.setTextContent(binding.noteTextContent.getText().toString());
                if (this.checklistNoteItemArrayList.size() > 0)
                    notesTable.setCheckListnote(gson.toJson(this.checklistNoteItemArrayList));
                dbHelper.createOrUpdate(notesTable);
                mListener.onNoteAdded(notesTable);
                dbHelper.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEditorFragmentListener(EditorFragmentListener listener) {
        mListener = listener;
    }

    public ArrayList<NoteColor> getColorList() {
        ArrayList<NoteColor> list = new ArrayList<>();
        list.add(new NoteColor(R.color.colorRed1));
        list.add(new NoteColor(R.color.colorBlue1));
        list.add(new NoteColor(R.color.colorGreen1));
        list.add(new NoteColor(R.color.colorYellow1));
        list.add(new NoteColor(R.color.colorCream1));
        list.add(new NoteColor(R.color.colorRed2));
        list.add(new NoteColor(R.color.colorBlue2));
        list.add(new NoteColor(R.color.colorGreen2));
        list.add(new NoteColor(R.color.colorYellow2));
        list.add(new NoteColor(R.color.colorCream2));
        return list;
    }

    public interface EditorFragmentListener {
        void onNoteAdded(NotesTable note);
        void onNoteModified(NotesTable note);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String realPath = RealPathUtil.getRealPathFromURI_API19(getContext().getApplicationContext(), data.getData());
            UniversalImageBindingAdapter.loadImage(binding.imageAttachment, realPath);

            PhotoNote p  = new PhotoNote();
            p.setPath(realPath);
            p.setTimeStamp(new SimpleDateFormat("HH:mm, EEE, d MMM yyyy", Locale.ENGLISH).format(new Date()));
            notesTable.setPhotoNote(gson.toJson(p));

            binding.imageAttachment.setVisibility(View.VISIBLE);
        }
    }
}
