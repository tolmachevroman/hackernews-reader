package com.reigndesign.hackernewsreader.network;

import com.reigndesign.hackernewsreader.network.responses.NewsListPOJO;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public interface ClientApiDefinitions {

    @GET(Urls.LATEST_NEWS)
    void getLatestNews(
            @Query("query") String queryBy,
            Callback<NewsListPOJO> callback
    );

}
