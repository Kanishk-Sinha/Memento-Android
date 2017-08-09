package com.kanishk.code.bloop.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ItemBloopBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.model.interfxs.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kanishk on 16/7/17.
 */

public class BloopRecyclerViewAdapter extends RecyclerView.Adapter<BloopRecyclerViewAdapter.BloopRecyclerViewAdapterBH> implements ItemTouchHelperAdapter {

    private ArrayList<NotesTable> itemList = new ArrayList<>(0);
    private AdapterItemTouchListener mListener;

    public BloopRecyclerViewAdapter(ArrayList<NotesTable> itemList) {
        this.itemList = itemList;
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).hashCode();
    }

    @Override
    public BloopRecyclerViewAdapterBH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBloopBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_bloop,
                parent,
                false);
        return new BloopRecyclerViewAdapterBH(binding);
    }

    @Override
    public void onBindViewHolder(BloopRecyclerViewAdapterBH holder, int position) {
        /*ItemBloopBinding binding = holder.binding;
        BaseBloop baseBloop;
        Gson gson = new Gson();
        baseBloop = gson.fromJson(itemList.get(position).getBaseBloop(), BaseBloop.class);
        BloopAdapterPresenter presenter = new BloopAdapterPresenter(itemList.get(position));
        switch (itemList.get(position).getBloopType()) {
            case 1 :
                binding.layoutNormalBloop.content.setVisibility(View.VISIBLE);
                binding.layoutNormalBloop.text.setText(baseBloop.getNormalBloop().getTextContent());
                if (baseBloop.getBaseBloopLocation() != null && !baseBloop.getBaseBloopLocation().equals(""))
                    binding.layoutNormalBloop.location.setText(baseBloop.getBaseBloopLocation());
                if (baseBloop.getNormalBloop().getTitle() != null && !baseBloop.getNormalBloop().getTitle().equals(""))
                    binding.layoutNormalBloop.title.setText(baseBloop.getNormalBloop().getTitle());
                else {
                    binding.layoutNormalBloop.title.setText("No Title Given");
                }
                break;
            case 2 :
                binding.layoutAudioBloop.content.setVisibility(View.VISIBLE);
                binding.layoutAudioBloop.text.setText(baseBloop.getAudioBloop().getTitle());
                binding.layoutAudioBloop.duration.setText(baseBloop.getAudioBloop().getDuration());
                if (baseBloop.getBaseBloopLocation() != null && !baseBloop.getBaseBloopLocation().equals(""))
                    binding.layoutAudioBloop.location.setText(baseBloop.getBaseBloopLocation());
                binding.layoutAudioBloop.playAudio.setOnClickListener(v -> {
                    mListener.onPlayAudio(baseBloop.getAudioBloop().getFilePath());
                });
                break;
            case 3 :
                binding.layoutPhotoBloop.content.setVisibility(View.VISIBLE);
                if (baseBloop.getPhotoBloop().getText() == null || baseBloop.getPhotoBloop().getText().equals(""))
                    binding.layoutPhotoBloop.text.setVisibility(View.GONE);
                else
                    binding.layoutPhotoBloop.text.setText(baseBloop.getPhotoBloop().getText());
                if (baseBloop.getPhotoBloop().getTitle() != null && !baseBloop.getPhotoBloop().getTitle().equals(""))
                    binding.layoutPhotoBloop.title.setText(baseBloop.getPhotoBloop().getTitle());
                else {
                    binding.layoutPhotoBloop.title.setVisibility(View.GONE);
                }
                UniversalImageBindingAdapter.loadImage(binding.layoutPhotoBloop.image, baseBloop.getPhotoBloop().getPath());
                break;
        }
        binding.setPresenter(presenter);*/
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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

        ItemBloopBinding binding;

        public BloopRecyclerViewAdapterBH(ItemBloopBinding itemBloopBinding) {
            super(itemBloopBinding.content);
            this.binding = itemBloopBinding;
        }
    }

    public void setAdapterItemTouchListener(AdapterItemTouchListener listener) {
        this.mListener = listener;
    }

    public interface AdapterItemTouchListener {
        void onItemClicked(NotesTable notesTable);
        void onItemDeleted(NotesTable notesTable);
        void onPlayAudio(String path);
    }
}
