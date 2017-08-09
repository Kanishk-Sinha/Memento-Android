package com.kanishk.code.bloop.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.FragmentNotesBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.model.interfxs.SimpleItemTouchHelperCallback;
import com.kanishk.code.bloop.presenter.fragment.RecentFragmentPresenter;
import com.kanishk.code.bloop.view.adapter.NotesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.kobakei.materialfabspeeddial.FabSpeedDialMenu;

/**
 * Created by kanishk on 18/7/17.
 */

public class NotesFragment extends Fragment {

    private RecentFragmentPresenter presenter;
    private RecyclerView recyclerView;
    private NotesRecyclerViewAdapter adapter;
    private FragmentNotesBinding binding;
    private ArrayList<NotesTable> notesTableList = new ArrayList<>();
    private int fragType;

    public NotesFragment() {
    }

    public static NotesFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        NotesFragment notesFragment = new NotesFragment();
        notesFragment.setArguments(args);
        return notesFragment;
    }

    private void sortList(List<NotesTable> list, int type) {
        switch (type) {
            case 1 :
                notesTableList.addAll(list);
                break;
            case 2 :
                for (NotesTable notesTable : list) {
                    if (notesTable.getPhotoNote() != null)
                        notesTableList.add(notesTable);
                }
                break;
            case 3 :
                for (NotesTable notesTable : list) {
                    if (notesTable.getAudioNote() != null)
                        notesTableList.add(notesTable);
                }
                break;
            case 4 :
                for (NotesTable notesTable : list) {
                    if (notesTable.getCheckListnote() != null)
                        notesTableList.add(notesTable);
                }
                break;
            case 5 :
                for (NotesTable notesTable : list) {
                    if (notesTable.getQuoteNote() != null)
                        notesTableList.add(notesTable);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragType = getArguments().getInt("type");
        /*Gson gson = new Gson();
        String list = getArguments().getString("list");
        Type type = new TypeToken<ArrayList<NotesTable>>(){}.getType();
        ArrayList<NotesTable> arrayList = gson.fromJson(list, type);
        sortList(arrayList, getArguments().getInt("type"));*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container,false);
        initViews(binding);
        initPresenter();
        return binding.getRoot();
    }

    private void initPresenter() {
        presenter = new RecentFragmentPresenter(binding, getContext());
        presenter.getNotesFromDb();
        presenter.setPresenterListener(new RecentFragmentPresenter.PresenterListener() {
            @Override
            public void onGetList(List<NotesTable> list) {
                if (list == null || list.size() <= 0) {
                    binding.emptyState.setVisibility(View.VISIBLE);
                } else
                    sortList(list, fragType);
            }
        });
    }

    private void initViews(FragmentNotesBinding binding) {

        recyclerView = binding.recyclerView;
        StaggeredGridLayoutManager grid = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(grid);
        int spanCount = 2;
        int spacing = 1;
        //recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && binding.fab.isShown()) {
                    binding.fab.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.fab.setVisibility(View.VISIBLE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        adapter = new NotesRecyclerViewAdapter(notesTableList, getContext().getApplicationContext());
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        adapter.setAdapterItemTouchListener(new NotesRecyclerViewAdapter.AdapterItemTouchListener() {
            @Override
            public void onItemClicked(NotesTable notesTable, int position) {
                notesTableList.remove(position);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                EditorFragment newFragment = EditorFragment.newInstance();
                newFragment.setNote(notesTable);
                newFragment.show(ft, "");
                newFragment.setEditorFragmentListener(new EditorFragment.EditorFragmentListener() {
                    @Override
                    public void onNoteAdded(NotesTable note) {
                        binding.emptyState.setVisibility(View.GONE);
                        notesTableList.add(0, note);
                        getActivity().runOnUiThread(() -> {
                            recyclerView.smoothScrollToPosition(0);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onNoteModified(NotesTable note) {

                    }
                });
            }

            @Override
            public void onItemDeleted(NotesTable notesTable) {
                presenter.deleteSession(notesTable.getIndex());
            }

            @Override
            public void onPlayAudio(String path) {

            }
        });

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        FabSpeedDialMenu menu = new FabSpeedDialMenu(getContext().getApplicationContext());
        menu.add("Add a Simple Note").setIcon(R.drawable.ic_bloop_count_black);
        menu.add("Take an Audio Note").setIcon(R.drawable.ic_microphone);
        menu.add("Add a picture").setIcon(R.drawable.ic_add_a_photo_black_24dp);

        switch (fragType) {
            case 1 :
                binding.fabImage.setImageResource(R.drawable.tab_icon_mix_note_selected);
                break;
            case 2 :
                binding.fabImage.setImageResource(R.drawable.tab_icon_photo_note_selected);
                break;
            case 3 :
                binding.fabImage.setImageResource(R.drawable.tab_icon_audio_note_selected);
                break;
            case 4 :
                binding.fabImage.setImageResource(R.drawable.tab_icon_checklist_note_selected);
                break;
            case 5 :
                binding.fabImage.setImageResource(R.drawable.tab_icon_quotes_note_selected);
                break;
        }

        //binding.fab.setMenu(menu);
        binding.fab.setOnClickListener(v -> {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            EditorFragment newFragment = EditorFragment.newInstance();
            newFragment.show(ft, "");
            newFragment.setEditorFragmentListener(new EditorFragment.EditorFragmentListener() {
                @Override
                public void onNoteAdded(NotesTable note) {
                    binding.emptyState.setVisibility(View.GONE);
                    notesTableList.add(0, note);
                    getActivity().runOnUiThread(() -> {
                        recyclerView.smoothScrollToPosition(0);
                        adapter.notifyDataSetChanged();
                    });
                }

                @Override
                public void onNoteModified(NotesTable note) {

                }
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
