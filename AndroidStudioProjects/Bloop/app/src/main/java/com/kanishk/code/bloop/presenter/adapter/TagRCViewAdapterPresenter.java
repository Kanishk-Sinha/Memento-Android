package com.kanishk.code.bloop.presenter.adapter;

import android.databinding.BaseObservable;

import com.kanishk.code.bloop.model.BloopSessionIndex;

/**
 * Created by kanishk on 19/7/17.
 */

public class BloopSessionsAdapterPresenter extends BaseObservable {

    private final BloopSessionIndex bloopSessionIndex;

    public BloopSessionsAdapterPresenter(BloopSessionIndex bloopSessionIndex) {
        this.bloopSessionIndex = bloopSessionIndex;
    }

    public String getTimestamp() {
        if (bloopSessionIndex.getLastOpened() != null)
            return bloopSessionIndex.getLastOpened();
        else
            return "Just now";
    }

    public String getHeading() {
        if (bloopSessionIndex.getTitle() != null)
            return bloopSessionIndex.getTitle();
        else
            return "No title given";
    }

    public String getMetaContent() {
        return bloopSessionIndex.getMostRecentBloop();
    }

}
