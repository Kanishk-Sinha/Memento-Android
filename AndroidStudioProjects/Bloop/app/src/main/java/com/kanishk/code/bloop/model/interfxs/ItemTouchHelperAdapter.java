package com.kanishk.code.bloop.model.interfxs;

/**
 * Created by kanishk on 20/7/17.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
