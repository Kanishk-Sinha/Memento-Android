package com.kanishk.code.bloop.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.LayoutFragmentSelectCollectionBinding;

/**
 * Created by kanishk on 25/7/17.
 */

public class SelectCollectionFragment extends DialogFragment {
    public SelectCollectionFragment newInstance() {
        return new SelectCollectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutFragmentSelectCollectionBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_select_collection, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        
    }
}
