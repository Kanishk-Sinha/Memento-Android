package com.kanishk.code.bloop.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ItemChecklistBinding;
import com.kanishk.code.bloop.databinding.ItemChecklistReadonlyBinding;
import com.kanishk.code.bloop.model.interfxs.ItemTouchHelperAdapter;
import com.kanishk.code.bloop.model.notes.ChecklistNoteItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kanishk on 2/8/17.
 */

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ChecklistAdapterBH> implements ItemTouchHelperAdapter {

    ArrayList<ChecklistNoteItem> list = new ArrayList<>();
    private ChecklistAdapterListener mListener;
    private boolean isReadOnlyFields;

    public ChecklistAdapter(ArrayList<ChecklistNoteItem> list, boolean isReadOnlyFields) {
        this.list = list;
        this.isReadOnlyFields = isReadOnlyFields;
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public ChecklistAdapterBH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isReadOnlyFields) {
            ItemChecklistReadonlyBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_checklist_readonly,
                    parent,
                    false);
            return new ChecklistAdapterBH(binding);
        } else {
            ItemChecklistBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_checklist,
                    parent,
                    false);
            return new ChecklistAdapterBH(binding);
        }
    }

    @Override
    public void onBindViewHolder(ChecklistAdapterBH holder, int position) {
        ItemChecklistBinding binding = holder.binding;
        ItemChecklistReadonlyBinding readonlyBinding = holder.readonlyBinding;

        if (readonlyBinding != null) {
            readonlyBinding.text.setText(list.get(position).getText());
            if (list.get(position).getIsChecked()) {
                readonlyBinding.checkbox.setChecked(true);
            }
        } else {
            binding.text.setText(list.get(position).getText());

            if (list.get(position).getIsChecked()) {
                binding.text.setEnabled(false);
                binding.checkbox.setChecked(true);
            }

            binding.checkbox.setOnClickListener(v -> {
                if (!list.get(position).getIsChecked()) {
                    mListener.onItemChecked(position, true);
                    binding.text.setEnabled(false);
                } else {
                    mListener.onItemChecked(position, false);
                    binding.text.setEnabled(true);
                }
            });
            setupEditTextField(binding, list.get(position));
        }

    }

    private void setupEditTextField(ItemChecklistBinding binding, ChecklistNoteItem checklistNoteItem) {
        binding.text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = binding.text.getText().toString();
                    if (text.equals("") || text.contains("   ")) {
                        mListener.onFinishEditing();
                    } else {
                        boolean isChecked = binding.checkbox.isChecked();
                        ChecklistNoteItem checklistNoteItem = new ChecklistNoteItem();
                        checklistNoteItem.setText(text);
                        checklistNoteItem.setIsChecked(isChecked);
                        mListener.onEnterPressed(checklistNoteItem);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(ChecklistAdapterBH holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.binding != null && holder.binding.text != null && holder.binding.text.getText().length() == 0) {
            holder.binding.text.requestFocus();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setChecklistAdapterListener(ChecklistAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public interface ChecklistAdapterListener {
        void onEnterPressed(ChecklistNoteItem checklistNoteItem);
        void onFinishEditing();
        void onItemChecked(int position, boolean status);
    }

    class ChecklistAdapterBH extends RecyclerView.ViewHolder {

        ItemChecklistBinding binding;
        ItemChecklistReadonlyBinding readonlyBinding;

        ChecklistAdapterBH(ItemChecklistBinding binding) {
            super(binding.content);
            this.binding = binding;
        }

        ChecklistAdapterBH(ItemChecklistReadonlyBinding binding) {
            super(binding.content);
            this.readonlyBinding = binding;
        }
    }

}
