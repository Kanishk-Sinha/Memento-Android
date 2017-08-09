package com.kanishk.code.bloop.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ItemBloopSessionBinding;
import com.kanishk.code.bloop.model.BloopSessionIndex;
import com.kanishk.code.bloop.model.interfxs.ItemTouchHelperAdapter;
import com.kanishk.code.bloop.presenter.adapter.BloopSessionsAdapterPresenter;

import java.util.Collections;
import java.util.List;

/**
 * Created by kanishk on 19/7/17.
 */

public class BloopSessionsAdapter extends RecyclerView.Adapter<BloopSessionsAdapter.BloopSessionsAdapterBH> implements ItemTouchHelperAdapter {

    private List<BloopSessionIndex> list;
    private AdapterItemTouchListener mListener;

    public BloopSessionsAdapter(List<BloopSessionIndex> list) {
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
    public BloopSessionsAdapterBH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBloopSessionBinding bloopSessionBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_bloop_session,
                parent,
                false);
        return new BloopSessionsAdapterBH(bloopSessionBinding);
    }

    @Override
    public void onBindViewHolder(BloopSessionsAdapterBH holder, int position) {
        ItemBloopSessionBinding binding = holder.binding;
        binding.setPresenter(new BloopSessionsAdapterPresenter(list.get(position)));
        binding.cardRipple.setOnClickListener(v -> mListener.onItemClicked(list.get(position)));
        binding.heading.setOnClickListener(v -> mListener.onItemClicked(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BloopSessionsAdapterBH extends RecyclerView.ViewHolder {

        private ItemBloopSessionBinding binding;

        BloopSessionsAdapterBH(ItemBloopSessionBinding binding) {
            super(binding.content);
            this.binding = binding;
        }
    }

    public void setAdapterItemClickListener(AdapterItemTouchListener listener) {
        this.mListener = listener;
    }

    public interface AdapterItemTouchListener {
        void onItemClicked(BloopSessionIndex bloopSessionIndex);
        void onItemDeleted(BloopSessionIndex bloopSessionIndex);
    }

}
