package com.reigndesign.hackernewsreader.sql;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;

/**
 * Created by romantolmachev on 10/3/2016.
 */
public class QueryHandler extends AsyncQueryHandler {

    public QueryHandler(ContentResolver cr) {
        super(cr);
    }
}
