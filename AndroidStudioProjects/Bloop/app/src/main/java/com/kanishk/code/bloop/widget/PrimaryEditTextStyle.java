package com.kanishk.code.bloop.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by kanishk on 30/03/17.
 *
 * DESCRIPTION -
 * Custom EditText for the input fields.
 */

public class PrimaryEditTextStyle extends android.support.v7.widget.AppCompatEditText {

    public PrimaryEditTextStyle(Context context) {
        super(context);
        init();
    }

    public PrimaryEditTextStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PrimaryEditTextStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // SET CUSTOM TYPEFACE FROM CACHE
    private void init() {
        Typeface font = FontCache.getTypeface("fonts/karla/Karla-Regular.ttf", getContext());
        setTypeface(font);
    }
}
