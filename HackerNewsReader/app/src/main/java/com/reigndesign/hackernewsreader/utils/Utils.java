package com.reigndesign.hackernewsreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class Utils {

    /**
     * Checks if there is an active internet connection
     *
     * @return If there is an active internet connection
     */
    public static boolean hasActiveInternetConnection(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


}
