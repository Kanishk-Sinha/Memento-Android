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
import android.widget.ImageView;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.data.DBHelper;
import com.kanishk.code.bloop.databinding.FragmentRecentBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.model.interfxs.SimpleItemTouchHelperCallback;
import com.kanishk.code.bloop.presenter.fragment.RecentFragmentPresenter;
import com.kanishk.code.bloop.view.adapter.NotesRecyclerViewAdapter;
import com.kanishk.code.bloop.widget.GridItemDecoration;

import java.util.ArrayList;

import io.github.kobakei.materialfabspeeddial.FabSpeedDialMenu;

/**
 * Created by kanishk on 18/7/17.
 */

public class MixNotesFragment extends Fragment {

    private RecentFragmentPresenter presenter;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private NotesRecyclerViewAdapter adapter;
    private long currentBloopIndexId;
    private FragmentRecentBinding binding;
    private ArrayList<NotesTable> notesTableList = new ArrayList<>();
    private static ImageView tabIcon;
    private static boolean m_iAmVisible;

    public MixNotesFragment() {

    }

    public static MixNotesFragment newInstance(View rl) {
        return new MixNotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent, container,false);
        presenter = new RecentFragmentPresenter(binding, getContext());
        initViews(binding);
        getNotesFromDb();
        return binding.getRoot();
    }

    private void getNotesFromDb() {
        presenter.getSessionsFromDb();
        presenter.setPresenterListener(list -> {
            if (list != null && list.size() > 0) {
                notesTableList.addAll(list);
                getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                adapter.setAdapterItemTouchListener(new NotesRecyclerViewAdapter.AdapterItemTouchListener() {
                    @Override
                    public void onItemClicked(NotesTable notesTable) {

                    }

                    @Override
                    public void onItemDeleted(NotesTable notesTable) {
                        presenter.deleteSession(notesTable.getIndex());
                    }

                    @Override
                    public void onPlayAudio(String path) {

                    }
                });
            } else {
                binding.emptyState.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initViews(FragmentRecentBinding binding) {
        FabSpeedDialMenu menu = new FabSpeedDialMenu(getContext().getApplicationContext());
        menu.add("Add a Simple Note").setIcon(R.drawable.ic_bloop_count_black);
        menu.add("Take an Audio Note").setIcon(R.drawable.ic_microphone);
        menu.add("Add a picture").setIcon(R.drawable.ic_add_a_photo_black_24dp);
        binding.fab.setMenu(menu);
        binding.fab.addOnMenuItemClickListener((fab, textView, itemId) -> {
            switch (itemId) {
                case 1 :
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
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
            }
        });

        recyclerView = binding.recyclerView;
        StaggeredGridLayoutManager grid = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(grid);
        int spanCount = 2;
        int spacing = 0;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 ||dy<0 && binding.fab.isShown()) {
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

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
