package com.kanishk.code.bloop.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.LayoutFragmentAttachmentBinding;

/**
 * Created by kanishk on 28/7/17.
 */

public class AttachmentFragment extends DialogFragment {

    private LayoutFragmentAttachmentBinding binding;
    private AttachmentFragmentListener mListener;

    public static AttachmentFragment newInstance() {
        return new AttachmentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_attachment, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.attachFromCamera.setOnClickListener(v -> {
            dismiss();
            mListener.onAttachFromCamera();
        });
        binding.attachFromGallery.setOnClickListener(v -> {
            dismiss();
            mListener.onAttachFromGallery();
        });
        binding.attachAudio.setOnClickListener(v -> {
            dismiss();
            mListener.onAttachAudio();
        });
        binding.attachChecklist.setOnClickListener(v -> {
            dismiss();
            mListener.onAttachChecklist();
        });
    }

    public void setAttachmentFragmentListener(AttachmentFragmentListener listener) {
        mListener = listener;
    }

    public interface AttachmentFragmentListener {
        void onAttachFromCamera();
        void onAttachFromGallery();
        void onAttachAudio();
        void onAttachChecklist();
    }
}
