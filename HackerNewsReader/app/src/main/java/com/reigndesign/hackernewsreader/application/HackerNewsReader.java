package com.reigndesign.hackernewsreader.application;

import android.app.Application;

import com.reigndesign.hackernewsreader.network.ClientApi;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class HackerNewsReader extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ClientApi.initialize();
    }
}
