package com.reigndesign.hackernewsreader.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.reigndesign.hackernewsreader.network.responses.NewsListPOJO;
import com.squareup.okhttp.OkHttpClient;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class ClientApi {

    private static ClientApiDefinitions service;

    public static void initialize() {

        // logs
        RestAdapter.Log restLogs = new RestAdapter.Log() {
            @Override
            public void log(String s) {
                Log.d("CLIENT API", s);
            }
        };

        // connection timeout settings
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(3, TimeUnit.MINUTES);
        okHttpClient.setWriteTimeout(3, TimeUnit.MINUTES);
        okHttpClient.setConnectTimeout(3, TimeUnit.MINUTES);

        // create client
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Urls.ENDPOINT)
                .setLog(restLogs)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create()))
                .build();

        service = restAdapter.create(ClientApiDefinitions.class);

    }

    public static void getLatestNews(@NonNull Callback<NewsListPOJO> callback) {

        service.getLatestNews("android", callback);
    }
}
