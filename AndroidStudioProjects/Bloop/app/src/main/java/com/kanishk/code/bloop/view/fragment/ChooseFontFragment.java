package com.kanishk.code.bloop.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.model.FontChoose;
import com.kanishk.code.bloop.view.adapter.ChooseFontAdapter;
import com.kanishk.code.bloop.widget.GridItemDecoration;

import java.util.ArrayList;

/**
 * Created by kanishk on 17/7/17.
 */

public class ChooseFontFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private ArrayList<FontChoose> itemList = new ArrayList<>();

    public static ChooseFontFragment newInstance(Context context) {
        return new ChooseFontFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_choose_font, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(grid);
        int spanCount = 1;
        int spacing = 1;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        ChooseFontAdapter adapter = new ChooseFontAdapter(getFontList());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private ArrayList<FontChoose> getFontList() {
        FontChoose fontChoose = new FontChoose();
        fontChoose.setName("calm");
        fontChoose.setFontLocation("fonts/karla/Karla-Regular.ttf");
        fontChoose.setType(1);
        itemList.add(fontChoose);

        FontChoose fontChoose_2 = new FontChoose();
        fontChoose_2.setName("natural");
        fontChoose_2.setFontLocation("fonts/Catamaran/Catamaran-Regular.ttf");
        fontChoose_2.setType(2);
        itemList.add(fontChoose_2);

        FontChoose fontChoose_3 = new FontChoose();
        fontChoose_3.setName("calm");
        fontChoose_3.setFontLocation("fonts/karla/Karla-Regular.ttf");
        fontChoose_3.setType(1);
        itemList.add(fontChoose_3);

        return itemList;
    }
}
