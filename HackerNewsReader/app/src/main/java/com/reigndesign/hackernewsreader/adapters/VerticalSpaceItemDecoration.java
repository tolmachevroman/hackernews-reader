package com.reigndesign.hackernewsreader.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public VerticalSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
    }
}
