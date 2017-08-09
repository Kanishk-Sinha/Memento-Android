package com.kanishk.code.bloop.view.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.LayoutFragmentSearchBinding;

/**
 * Created by kanishk on 6/8/17.
 */

public class SearchFragment extends DialogFragment {

    private LayoutFragmentSearchBinding binding;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_search, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.dismiss.setOnClickListener(v -> dismiss());
        binding.actionSearch.setOnClickListener(v -> search(binding.searchField.getText().toString()));
    }

    private void search(String searchString) {
        
    }
}
