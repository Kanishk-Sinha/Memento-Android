package com.kanishk.code.bloop.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ItemColorSelectorBinding;
import com.kanishk.code.bloop.model.NoteColor;

import java.util.ArrayList;

/**
 * Created by kanishk on 28/7/17.
 */

public class ColorSelectorAdapter extends RecyclerView.Adapter<ColorSelectorAdapter.ColorSelectorAdapterBH> {

    private ArrayList<NoteColor> colorArrayList = new ArrayList<>();
    private ColorSelectorAdapterListener mListener;

    public ColorSelectorAdapter(ArrayList<NoteColor> colorArrayList) {
        this.colorArrayList = colorArrayList;
    }

    @Override
    public ColorSelectorAdapterBH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemColorSelectorBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_color_selector,
                parent,
                false);
        return new ColorSelectorAdapterBH(binding);
    }

    @Override
    public void onBindViewHolder(ColorSelectorAdapterBH holder, int position) {
        ItemColorSelectorBinding binding = holder.binding;
        int colorId = colorArrayList.get(position).getColorId();
        binding.color.setImageResource(colorId);
        binding.content.setOnClickListener(v -> mListener.onColorSelected(position, colorId));
    }

    @Override
    public int getItemCount() {
        return colorArrayList.size();
    }

    public void setColorSelectorAdapterListener(ColorSelectorAdapterListener listener) {
        mListener = listener;
    }

    public interface ColorSelectorAdapterListener {
        void onColorSelected(int position, int colorId);
    }

    class ColorSelectorAdapterBH extends RecyclerView.ViewHolder {

        ItemColorSelectorBinding binding;

        ColorSelectorAdapterBH(ItemColorSelectorBinding binding) {
            super(binding.content);
            this.binding = binding;
        }
    }
}
