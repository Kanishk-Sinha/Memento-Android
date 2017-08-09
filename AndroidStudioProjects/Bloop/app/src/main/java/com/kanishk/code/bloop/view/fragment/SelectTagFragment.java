package com.kanishk.code.bloop.view.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.LayoutFragmentSelectTagBinding;
import com.kanishk.code.bloop.model.Tag;
import com.kanishk.code.bloop.utils.SharedPrefManager;
import com.kanishk.code.bloop.view.adapter.TagRCViewAdapter;
import com.kanishk.code.bloop.widget.GridItemDecoration;

import java.util.List;

/**
 * Created by kanishk on 25/7/17.
 */

public class SelectTagFragment extends DialogFragment {
    private LayoutFragmentSelectTagBinding binding;
    private RecyclerView recyclerView;
    private TagRCViewAdapter adapter;
    private List<Tag> list;
    public static SelectTagFragment newInstance() {
        return new SelectTagFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.ThemeOverlay_Material_Light);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_select_tag, container, false);
        checkData();
        return binding.getRoot();
    }

    private void checkData() {
        initViews();
        list = SharedPrefManager.getTagList(getContext().getApplicationContext());
        if (list != null && list.size() > 0) {
            showTags(list);
        } else {
            showEmptyState();
        }
    }

    private void showEmptyState() {
        binding.emptyState.setVisibility(View.VISIBLE);
        binding.heading.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.GONE);
    }

    private void hideEmptyState() {
        binding.emptyState.setVisibility(View.GONE);
        binding.heading.setVisibility(View.VISIBLE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        binding.fab.setOnClickListener(v -> {
            binding.fab.setVisibility(View.GONE);
            binding.addTagLayout.setVisibility(View.VISIBLE);
        });

        binding.close.setOnClickListener(v -> {
            binding.addTagInput.destroyDrawingCache();
            binding.fab.setVisibility(View.VISIBLE);
            binding.addTagLayout.setVisibility(View.GONE);
        });

        binding.addTagDone.setOnClickListener(v -> {
            Tag tag = new Tag();
            tag.setTitle(binding.addTagInput.getText().toString());
            addNewTag(new Tag());
            binding.addTagInput.destroyDrawingCache();
            if (binding.emptyState.isShown()) {
                binding.emptyState.setVisibility(View.GONE);
                binding.content.setVisibility(View.VISIBLE);
            } else {
                binding.fab.setVisibility(View.VISIBLE);
                binding.addTagLayout.setVisibility(View.GONE);
            }
        });

        recyclerView = binding.recyclerView;
        StaggeredGridLayoutManager grid = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(grid);
        int spanCount = 2;
        int spacing = 1;
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
    }

    private void showTags(List<Tag> tagList) {
        hideEmptyState();
        adapter = new TagRCViewAdapter(tagList);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }

    private void addNewTag(Tag tag) {
        hideEmptyState();
        try {
            list.add(0, tag);
            getActivity().runOnUiThread(() -> {
                recyclerView.smoothScrollToPosition(0);
                adapter.notifyDataSetChanged();
            });
            SharedPrefManager.saveTagList(getContext().getApplicationContext(), list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        list = null;
        super.onDismiss(dialog);
    }
}
