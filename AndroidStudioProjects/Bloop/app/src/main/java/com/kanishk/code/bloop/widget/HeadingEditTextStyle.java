package com.kanishk.code.bloop.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by kanishk on 31/7/17.
 */

public class HeadingEditTextStyle extends android.support.v7.widget.AppCompatEditText {

    public HeadingEditTextStyle(Context context) {
        super(context);
        init();
    }

    public HeadingEditTextStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeadingEditTextStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // SET CUSTOM TYPEFACE FROM CACHE
    private void init() {
        Typeface font = FontCache.getTypeface("fonts/Quicksand/Quicksand-Medium.ttf", getContext());
        setTypeface(font);
    }
}
