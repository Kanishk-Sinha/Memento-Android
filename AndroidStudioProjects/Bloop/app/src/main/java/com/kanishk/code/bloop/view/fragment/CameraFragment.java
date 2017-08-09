package com.kanishk.code.bloop.view.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.LayoutFragmentCameraBinding;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.result.PhotoResult;

import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

/**
 * Created by kanishk on 22/7/17.
 */

public class CameraFragment extends DialogFragment {

    private LayoutFragmentCameraBinding binding;
    private Fotoapparat fotoapparat;
    private CameraFragmentListener mListener;
    private PhotoResult photoResult;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.ThemeOverlay_Material_Light);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_camera, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        fotoapparat = Fotoapparat
                .with(getContext())
                .into(binding.cameraView)           // view which will draw the camera preview
                .previewScaleType(ScaleType.CENTER_CROP)  // we want the preview to fill the view
                .photoSize(biggestSize())   // we want to have the biggest photo possible
                .lensPosition(back())       // we want back camera
                .build();

        fotoapparat.start();

        binding.capture.setOnClickListener(v -> {
            captureImage();
        });
    }

    private void captureImage() {
        photoResult = fotoapparat.takePicture();
        photoResult.toBitmap().whenAvailable(bitmapPhoto -> {
            setPictureAskConfirmation(bitmapPhoto.bitmap);
        });
    }

    private void setPictureAskConfirmation(Bitmap result) {
        showAfterImageCaptureLayout();
        binding.imageCaptured.setImageBitmap(result);
        fotoapparat.stop();
        binding.cameraView.setVisibility(View.GONE);
    }

    private void showAfterImageCaptureLayout() {
        binding.capture.setVisibility(View.GONE);
        binding.afterCaptureLayout.setVisibility(View.VISIBLE);
        binding.recapture.setOnClickListener(v -> {
            initViews();
            hideAfterImageCaptureLayout();
        });
        binding.done.setOnClickListener(v -> {
            if (photoResult != null) {
                mListener.onPhotoResult(photoResult);
                dismiss();
            }
        });
    }

    private void hideAfterImageCaptureLayout() {
        binding.capture.setVisibility(View.VISIBLE);
        binding.cameraView.setVisibility(View.VISIBLE);
        binding.afterCaptureLayout.setVisibility(View.INVISIBLE);
        binding.imageCaptured.destroyDrawingCache();
        binding.imageCaptured.setVisibility(View.GONE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        fotoapparat = null;
        photoResult = null;
        super.onDismiss(dialog);
    }

    public void setCameraFragmentListener(CameraFragmentListener listener) {
        mListener = listener;
    }

    public interface CameraFragmentListener {
        void onPhotoResult(PhotoResult photoResult);
    }
}
