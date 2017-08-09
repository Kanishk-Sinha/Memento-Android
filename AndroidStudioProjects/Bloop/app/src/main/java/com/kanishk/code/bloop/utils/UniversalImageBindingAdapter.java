package com.kanishk.code.bloop.utils;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Created by Kanishk on 7/3/17.
 */

public class UniversalImageBindingAdapter {

    @BindingAdapter({"url"})
    public static void loadImage(ImageView imageView, String url) {
        if (url != null) {
            File file = new File(url);
            Uri imageUri = Uri.fromFile(file);
            Glide.with(imageView.getContext())
                    .load(imageUri)
                    .centerCrop()
                    .crossFade()
                    .dontTransform()
                    .dontAnimate()
                    .thumbnail(0.2f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    public static ImageView loadImageView(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        return imageView;
    }

    @BindingAdapter({"local_path"})
    public static void loadLocalImage(ImageView imageView, String url) {
        imageView.setBackground(null);
        if (url == null) {
            Glide.with(imageView.getContext())
                    .load("bnb")
                    .centerCrop()
                    .crossFade()
                    .dontTransform()
                    .dontAnimate()
                    .thumbnail(0.2f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } else {
            File file = new File(url);
            Uri imageUri = Uri.fromFile(file);
            Glide.with(imageView.getContext())
                    .load(imageUri)
                    .centerCrop()
                    .crossFade()
                    .dontTransform()
                    .dontAnimate()
                    .thumbnail(0.2f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }


    @BindingAdapter({"load_drawable"})
    public static void loadDrawable(ImageView imageView, int path) {
        Glide.with(imageView.getContext())
                .load(path)
                .centerCrop()
                .crossFade()
                .dontTransform()
                .dontAnimate()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
