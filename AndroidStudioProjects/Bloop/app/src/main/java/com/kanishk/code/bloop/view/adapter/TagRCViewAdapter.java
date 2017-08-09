package com.kanishk.code.bloop.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ItemTagBinding;
import com.kanishk.code.bloop.model.Tag;
import com.kanishk.code.bloop.model.interfxs.ItemTouchHelperAdapter;
import com.kanishk.code.bloop.presenter.adapter.TagRCViewAdapterPresenter;

import java.util.Collections;
import java.util.List;

/**
 * Created by kanishk on 19/7/17.
 */

public class TagRCViewAdapter extends RecyclerView.Adapter<TagRCViewAdapter.TagRCViewAdapterBH> implements ItemTouchHelperAdapter {

    private List<Tag> list;
    private AdapterItemTouchListener mListener;

    public TagRCViewAdapter(List<Tag> list) {
        this.list = list;
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
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
        mListener.onItemDeleted(list.get(position));
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public TagRCViewAdapterBH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTagBinding bloopSessionBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_tag,
                parent,
                false);
        return new TagRCViewAdapterBH(bloopSessionBinding);
    }

    @Override
    public void onBindViewHolder(TagRCViewAdapterBH holder, int position) {
        ItemTagBinding binding = holder.binding;
        binding.setPresenter(new TagRCViewAdapterPresenter(list.get(position)));
        binding.cardRipple.setOnClickListener(v -> mListener.onItemClicked(list.get(position)));
        binding.heading.setOnClickListener(v -> mListener.onItemClicked(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TagRCViewAdapterBH extends RecyclerView.ViewHolder {

        private ItemTagBinding binding;

        TagRCViewAdapterBH(ItemTagBinding binding) {
            super(binding.content);
            this.binding = binding;
        }
    }

    public void setAdapterItemClickListener(AdapterItemTouchListener listener) {
        this.mListener = listener;
    }

    public interface AdapterItemTouchListener {
        void onItemClicked(Tag tag);
        void onItemDeleted(Tag tag);
    }

}
