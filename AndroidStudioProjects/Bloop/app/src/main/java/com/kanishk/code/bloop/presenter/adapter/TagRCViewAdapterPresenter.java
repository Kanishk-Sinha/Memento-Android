package com.kanishk.code.bloop.presenter.adapter;

import android.databinding.BaseObservable;

import com.kanishk.code.bloop.model.Tag;

/**
 * Created by kanishk on 19/7/17.
 */

public class TagRCViewAdapterPresenter extends BaseObservable {

    private final Tag tag;

    public TagRCViewAdapterPresenter(Tag tag) {
        this.tag = tag;
    }

    public String getHeading() {
        if (tag.getTitle() != null)
            return tag.getTitle();
        else
            return "No title given";
    }

}
