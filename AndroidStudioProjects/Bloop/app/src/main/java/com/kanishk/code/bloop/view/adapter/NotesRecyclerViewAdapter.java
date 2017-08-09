package com.kanishk.code.bloop.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.data.AppConstants;
import com.kanishk.code.bloop.databinding.ItemNotesBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.model.interfxs.ItemTouchHelperAdapter;
import com.kanishk.code.bloop.model.notes.ChecklistNoteItem;
import com.kanishk.code.bloop.presenter.adapter.NotesRCViewPresenter;
import com.kanishk.code.bloop.utils.ColorFilter;
import com.kanishk.code.bloop.widget.GridItemDecoration;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kanishk on 16/7/17.
 */

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.BloopRecyclerViewAdapterBH> implements ItemTouchHelperAdapter {

    private Context appContext;
    private ArrayList<NotesTable> itemList;
    private AdapterItemTouchListener mListener;

    public NotesRecyclerViewAdapter(ArrayList<NotesTable> itemList, Context applicationContext) {
        this.appContext = applicationContext;
        this.itemList = itemList;
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).hashCode();
    }

    @Override
    public BloopRecyclerViewAdapterBH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNotesBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_notes,
                parent,
                false);
        return new BloopRecyclerViewAdapterBH(binding);
    }

    @Override
    public void onBindViewHolder(BloopRecyclerViewAdapterBH holder, int position) {
        ItemNotesBinding binding = holder.binding;
        NotesRCViewPresenter presenter = new NotesRCViewPresenter(itemList.get(position), binding);
        binding.setPresenter(presenter);
        int color = itemList.get(position).getColor();
        if (color != 0) {
            binding.content.setCardBackgroundColor(appContext.getResources().getColor(ColorFilter.getColorForType(itemList.get(position).getColor())));
            if (color == AppConstants.ColorConstants.COLOR_BLUE_1
                    || color == AppConstants.ColorConstants.COLOR_BLUE_2
                    || color == AppConstants.ColorConstants.COLOR_RED_1
                    || color == AppConstants.ColorConstants.COLOR_RED_2
                    || color == AppConstants.ColorConstants.COLOR_GREEN_2) {
                binding.noteHeading.setTextColor(appContext.getResources().getColor(R.color.colorWhite));
                binding.noteTextContent.setTextColor(appContext.getResources().getColor(R.color.colorWhite));
            }
        }
        if (itemList.get(position).getCheckListnote() != null) {
            setupChecklistMeta(binding, itemList.get(position).getCheckListnote());
        }
        binding.content.setOnClickListener(v -> mListener.onItemClicked(itemList.get(position), position));
        //Type type = new TypeToken<ArrayList<BaseNote>>(){}.getType();
    }

    private void setupChecklistMeta(ItemNotesBinding binding, String data) {
        Gson gson = new Gson();
        ArrayList<ChecklistNoteItem> list = gson.fromJson(data, new TypeToken<ArrayList<ChecklistNoteItem>>(){}.getType());

        binding.checklistRcView.setVisibility(View.VISIBLE);
        binding.noteTextContent.setVisibility(View.GONE);
        RecyclerView recyclerView = binding.checklistRcView;
        LinearLayoutManager grid = new LinearLayoutManager(appContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(grid);
        int spanCount = 1;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, 0, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        ChecklistAdapter adapter = new ChecklistAdapter(list, true);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        if (itemList != null)
            return itemList.size();
        else
            return 0;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mListener.onItemDeleted(itemList.get(position));
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    class BloopRecyclerViewAdapterBH extends RecyclerView.ViewHolder {

        ItemNotesBinding binding;

        public BloopRecyclerViewAdapterBH(ItemNotesBinding notesBinding) {
            super(notesBinding.content);
            this.binding = notesBinding;
        }
    }

    public void setAdapterItemTouchListener(AdapterItemTouchListener listener) {
        this.mListener = listener;
    }

    public interface AdapterItemTouchListener {
        void onItemClicked(NotesTable notesTable, int position);
        void onItemDeleted(NotesTable notesTable);
        void onPlayAudio(String path);
    }
}
