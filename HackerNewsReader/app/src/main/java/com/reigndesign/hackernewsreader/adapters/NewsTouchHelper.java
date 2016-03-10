package com.reigndesign.hackernewsreader.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class NewsTouchHelper extends ItemTouchHelper.SimpleCallback {

    private NewsAdapter newsAdapter;

    public NewsTouchHelper(NewsAdapter newsAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.newsAdapter = newsAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        newsAdapter.remove(viewHolder.getAdapterPosition());
    }
}
