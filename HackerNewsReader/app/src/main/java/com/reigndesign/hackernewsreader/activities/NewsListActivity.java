package com.reigndesign.hackernewsreader.activities;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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
import com.reigndesign.hackernewsreader.sql.News;
import com.reigndesign.hackernewsreader.sql.QueryHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.INewsAdapterHolderClick, LoaderManager.LoaderCallbacks<Cursor> {

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

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onRefresh() {
        //TODO check internet connection before
        getLatestNews();
    }

    @Override
    public void onItemClick(String storyUrl) {
        System.out.println("onItemClick: " + storyUrl);

        startActivity(new Intent(this, NewsDetailsActivity.class)
                .putExtra(getString(R.string.url), storyUrl));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, News.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        System.out.println("Cursor size: " + data.getCount());
//        if (productsAdapter != null && cursor != null) {
//            productsAdapter.swapCursor(cursor);
//        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void getLatestNews() {

        ClientApi.getLatestNews(new Callback<NewsListPOJO>() {
            @Override
            public void success(NewsListPOJO newsListPOJO, Response response) {

                newsAdapter.populate(newsListPOJO.getNewsPOJOList());

                swipeToRefreshLayout.setRefreshing(false);

                // insert news asynchronously to the database
                QueryHandler queryHandler = new QueryHandler(getContentResolver());

                for(NewsPOJO newsPOJO : newsListPOJO.getNewsPOJOList()) {

                    ContentValues contentValues = new ContentValues();

                    contentValues.put(News._ID, newsPOJO.getObjectId());

                    contentValues.put(News.TITLE, newsPOJO.getTitle() != null?
                            newsPOJO.getTitle() : newsPOJO.getStoryTitle());

                    contentValues.put(News.AUTHOR, newsPOJO.getAuthor());

                    contentValues.put(News.URL, newsPOJO.getUrl() != null?
                            newsPOJO.getUrl() : newsPOJO.getStoryUrl());

                    queryHandler.startInsert(newsPOJO.getObjectId(), null, News.CONTENT_URI, contentValues);
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
