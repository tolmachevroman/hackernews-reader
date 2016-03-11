package com.reigndesign.hackernewsreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.reigndesign.hackernewsreader.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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


    /**
     * Calculates and shows formatted time elapsed from the given Date
     *
     * @param createdAt
     * @return
     */
    public static String getTimePassed(Date createdAt, Context context) {

        long timeDifference = new Date().getTime() - createdAt.getTime();

        int days = (int) TimeUnit.MILLISECONDS.toDays(timeDifference);
        int hours = (int) (TimeUnit.MILLISECONDS.toHours(timeDifference) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDifference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDifference)));

        if(days == 1) {
            return context.getString(R.string.yesterday);
        } else if (days == 0) {
            return hours + "h";
        } else {
            return minutes + "m";
        }

    }
}
