package com.reigndesign.hackernewsreader.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.reigndesign.hackernewsreader.R;
import com.reigndesign.hackernewsreader.network.ClientApi;
import com.reigndesign.hackernewsreader.network.responses.NewsListPOJO;
import com.reigndesign.hackernewsreader.network.responses.NewsPOJO;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_to_refresh_layout)
    SwipeRefreshLayout swipeToRefreshLayout;

    @Bind(R.id.news_list)
    ListView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        swipeToRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        //TODO check internet connection before
        getLatestNews();
    }

    private void getLatestNews() {

        ClientApi.getLatestNews(new Callback<NewsListPOJO>() {
            @Override
            public void success(NewsListPOJO newsListPOJO, Response response) {
                System.out.println("Size: " + newsListPOJO.getNewsPOJOList().size());

                for (NewsPOJO newsPOJO : newsListPOJO.getNewsPOJOList()) {
                    System.out.println("Date: " + newsPOJO.getCreatedAt());
                }

                swipeToRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Error: " + error.getMessage());

                swipeToRefreshLayout.setRefreshing(false);
            }
        });

    }

}
