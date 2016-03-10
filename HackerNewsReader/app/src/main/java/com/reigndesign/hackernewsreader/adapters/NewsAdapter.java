package com.reigndesign.hackernewsreader.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reigndesign.hackernewsreader.R;
import com.reigndesign.hackernewsreader.sql.News;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class NewsAdapter extends CursorRecyclerViewAdapter<NewsAdapter.ViewHolder> {

    private INewsAdapterHolderClick iNewsAdapterHolderClick;

    public NewsAdapter(Context context, Cursor cursor, INewsAdapterHolderClick iNewsAdapterHolderClick) {
        super(context, cursor);
        this.iNewsAdapterHolderClick = iNewsAdapterHolderClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        System.out.println("Id: " + cursor.getString(cursor.getColumnIndex(News._ID)));

        viewHolder.title.setText(cursor.getString(cursor.getColumnIndex(News.TITLE)));
        viewHolder.authorAndDate.setText(cursor.getString(cursor.getColumnIndex(News.AUTHOR)) +
                " - " + cursor.getString(cursor.getColumnIndex(News.CREATED_AT)));
    }

    public void remove(int position) {
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        context.getContentResolver().delete(News.CONTENT_URI, News._ID + " = ?", new String[] { cursor.getString(cursor.getColumnIndex(News._ID)) + ""} );
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.authorAndDate)
        TextView authorAndDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iNewsAdapterHolderClick.onItemClick(getCursor().getString(getCursor().getColumnIndex(News.URL)));
        }
    }

    public interface INewsAdapterHolderClick {
        void onItemClick(String storyUrl);
    }
}
