package com.reigndesign.hackernewsreader.sql;

import android.net.Uri;
import android.provider.BaseColumns;

import com.reigndesign.hackernewsreader.providers.NewsProvider;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class News implements BaseColumns {

    public static final String TABLE_NAME = "news";
    public static final Uri CONTENT_URI = Uri.parse("content://" + NewsProvider.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.reigndesign.hackernewsreader.news";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.reigndesign.hackernewsreader.news";

    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String CREATED_AT = "created_at";
    public static final String URL = "url";

}
