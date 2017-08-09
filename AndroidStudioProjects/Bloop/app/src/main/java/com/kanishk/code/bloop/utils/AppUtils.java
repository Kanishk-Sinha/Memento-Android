package com.kanishk.code.bloop.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by kanishk on 16/7/17.
 */

public class AppUtils {

    public static void doCircularReveal(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int x = view.getWidth();
            int y = 0;
            int startRadius = 0;
            int hypotenuse = (int) Math.hypot(view.getWidth(), view.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, hypotenuse);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(400);
            view.setVisibility(View.VISIBLE);
            anim.start();
        }
        else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void doCircularExit(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int x = view.getWidth();
            int y = 0;
            int startRadius = Math.max(view.getWidth(), view.getHeight());
            int endRadius = 0;
            Animator anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(400);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setVisibility(View.INVISIBLE);

                }
                @Override
                public void onAnimationEnd(Animator animation) {
                }
            });
            anim.start();
        }
        else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
