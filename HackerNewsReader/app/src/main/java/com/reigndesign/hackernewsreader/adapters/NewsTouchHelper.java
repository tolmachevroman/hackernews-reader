package com.reigndesign.hackernewsreader.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.reigndesign.hackernewsreader.R;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class NewsTouchHelper extends ItemTouchHelper.SimpleCallback {

    private NewsAdapter newsAdapter;

    private String deleteText;

    public NewsTouchHelper(NewsAdapter newsAdapter, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.newsAdapter = newsAdapter;
        this.deleteText = context.getString(R.string.delete).toUpperCase();
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        newsAdapter.remove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Get RecyclerView item from the ViewHolder
            View itemView = viewHolder.itemView;

            Paint p = new Paint();
            if (dX > 0) {

                p.setColor(Color.RED);

                // Draw Rect with varying right side, equal to displacement dX
                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                        (float) itemView.getBottom(), p);

                p.setColor(Color.WHITE);

                p.setTextSize(32);
                p.setFakeBoldText(true);
                c.drawText(deleteText, (float) itemView.getLeft() + 20,
                        (float) itemView.getTop() + 16 + ((float) itemView.getBottom() - (float) itemView.getTop())/2,
                        p);

            } else {

                p.setColor(Color.RED);

                // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                        (float) itemView.getRight(), (float) itemView.getBottom(), p);

                p.setColor(Color.WHITE);

                p.setTextSize(32);
                p.setFakeBoldText(true);
                c.drawText(deleteText, (float) itemView.getRight() - 20 - p.measureText(deleteText),
                        (float) itemView.getTop() + 16 + ((float) itemView.getBottom() - (float) itemView.getTop()) / 2,
                        p);
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
}
