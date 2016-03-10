package com.reigndesign.hackernewsreader.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.reigndesign.hackernewsreader.R;
import com.reigndesign.hackernewsreader.adapters.NewsAdapter;
import com.reigndesign.hackernewsreader.adapters.NewsTouchHelper;
import com.reigndesign.hackernewsreader.adapters.VerticalSpaceItemDecoration;
import com.reigndesign.hackernewsreader.network.ClientApi;
import com.reigndesign.hackernewsreader.network.responses.NewsListPOJO;
import com.reigndesign.hackernewsreader.network.responses.NewsPOJO;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.INewsAdapterHolderClick {

    @Bind(R.id.swipe_to_refresh_layout)
    SwipeRefreshLayout swipeToRefreshLayout;

    @Bind(R.id.news_list)
    RecyclerView newsRecyclerView;

    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        // adapter
        newsAdapter = new NewsAdapter(this, this);
        newsRecyclerView.setAdapter(newsAdapter);

        // recycler view layout manager and vertical space decoration
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(linearLayoutManager);
        newsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(2));

        // item touch helper to enable swipe to dismiss
        ItemTouchHelper.Callback callback = new NewsTouchHelper(newsAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(newsRecyclerView);

        // swipe to refresh listener
        swipeToRefreshLayout.setOnRefreshListener(this);

        // api request
        getLatestNews();
    }

    @Override
    public void onRefresh() {
        //TODO check internet connection before
        getLatestNews();
    }

    @Override
    public void onItemClick(String newsUrl) {
        System.out.println("onItemClick: " + newsUrl);
    }

    private void getLatestNews() {

        ClientApi.getLatestNews(new Callback<NewsListPOJO>() {
            @Override
            public void success(NewsListPOJO newsListPOJO, Response response) {

                newsAdapter.populate(newsListPOJO.getNewsPOJOList());

                swipeToRefreshLayout.setRefreshing(false);

                for(NewsPOJO newsPOJO : newsListPOJO.getNewsPOJOList()) {
                    System.out.println("Story title: " + newsPOJO.getStoryTitle());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Error: " + error.getMessage());

                swipeToRefreshLayout.setRefreshing(false);
            }
        });

    }

}
