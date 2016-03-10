package com.reigndesign.hackernewsreader.activities;

import android.os.Bundle;
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

public class NewsListActivity extends AppCompatActivity {

    @Bind(R.id.news_list)
    ListView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


    }

    private void getLatestNews() {

        ClientApi.getLatestNews(new Callback<NewsListPOJO>() {
            @Override
            public void success(NewsListPOJO newsListPOJO, Response response) {
                System.out.println("Size: " + newsListPOJO.getNewsPOJOList().size());

                for (NewsPOJO newsPOJO : newsListPOJO.getNewsPOJOList()) {
                    System.out.println("Date: " + newsPOJO.getCreatedAt());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Error: " + error.getMessage());
            }
        });

    }

}
