package com.kanishk.code.bloop.view.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ItemChooseFontBinding;
import com.kanishk.code.bloop.model.FontChoose;
import com.kanishk.code.bloop.widget.FontCache;

import java.util.ArrayList;

/**
 * Created by kanishk on 17/7/17.
 */

public class ChooseFontAdapter extends RecyclerView.Adapter<ChooseFontAdapter.ChooseFontAdapterBH> {

    private ArrayList<FontChoose> itemList = new ArrayList<>();
    private String fontText = "A major bug in Play Services could be the reason GCM messages that you send aren't reaching your users. Pacemaker helps patch this bug by doing for Play Services what it should be doing on its own.";


    public ChooseFontAdapter(ArrayList<FontChoose> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ChooseFontAdapterBH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemChooseFontBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_choose_font,
                parent,
                false);
        return new ChooseFontAdapterBH(binding);
    }

    @Override
    public void onBindViewHolder(ChooseFontAdapterBH holder, int position) {
        ItemChooseFontBinding binding = holder.binding;
        TextView fontNameView = holder.binding.fontName;
        fontNameView.setText(itemList.get(position).getName());

        TextView fontTextView = holder.binding.fontText;
        fontTextView.setText(fontText);

        Typeface font = FontCache.getTypeface(itemList.get(position).getFontLocation(), binding.getRoot().getContext());
        fontTextView.setTypeface(font);
        fontNameView.setTypeface(font);

        binding.content.setOnClickListener(v -> {});
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ChooseFontAdapterBH extends RecyclerView.ViewHolder {

        ItemChooseFontBinding binding;

        public ChooseFontAdapterBH(ItemChooseFontBinding binding) {
            super(binding.content);
            this.binding = binding;
        }
    }
}
