package com.reigndesign.hackernewsreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reigndesign.hackernewsreader.R;
import com.reigndesign.hackernewsreader.network.responses.NewsPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private INewsAdapterHolderClick iNewsAdapterHolderClick;
    private List<NewsPOJO> newsList;

    public NewsAdapter(Context context, INewsAdapterHolderClick iNewsAdapterHolderClick) {
        this.context = context;
        this.iNewsAdapterHolderClick = iNewsAdapterHolderClick;
        this.newsList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).getStoryTitle());
        holder.authorAndDate.setText(newsList.get(position).getAuthor() + " - " + newsList.get(position).getCreatedAt().toString());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void populate(List<NewsPOJO> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        newsList.remove(position);
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
            System.out.println("Item position: " + getAdapterPosition());
            iNewsAdapterHolderClick.onItemClick(newsList.get(getAdapterPosition()).getAuthor());
        }
    }

    public interface INewsAdapterHolderClick {
        void onItemClick(String newsUrl);
    }
}
