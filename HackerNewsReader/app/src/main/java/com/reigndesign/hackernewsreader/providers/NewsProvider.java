package com.reigndesign.hackernewsreader.providers;

/**
 * Created by romantolmachev on 10/3/2016.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.reigndesign.hackernewsreader.sql.DatabaseHelper;
import com.reigndesign.hackernewsreader.sql.News;


public class NewsProvider extends ContentProvider implements BaseColumns {
    private static final String DEBUG = NewsProvider.class.getSimpleName();

    public static final String AUTHORITY = "com.reigndesign.hackernewsreader.news";
    public static final String DEFAULT_SORT_ORDER = BaseColumns._ID + " DESC"; // sort by auto-id

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int SOME = 1;
    private static final int ONE = 2;

    static {
        URI_MATCHER.addURI(AUTHORITY, News.TABLE_NAME, SOME);
        URI_MATCHER.addURI(AUTHORITY, News.TABLE_NAME + "/#", ONE);
    }

    private SQLiteOpenHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String tableName = getTableName(uri);
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case SOME:
                qBuilder.setTables(tableName);
                break;
            case ONE:
                qBuilder.setTables(tableName);
                qBuilder.appendWhere("_id=");
                qBuilder.appendWhere(uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = qBuilder.query(db, projection, selection, selectionArgs,
                null, null, orderBy);
        if (cursor == null) {
            Log.d(DEBUG, "query: failed");
        } else {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    private String getTableName(Uri uri) throws IllegalArgumentException {
        String tableName = uri.getPath();
        if (tableName.contains(News.TABLE_NAME)) {
            tableName = News.TABLE_NAME;
        } else {
            throw new IllegalArgumentException("Unknown Uri Path");
        }
        return tableName;
    }

    @Override
    public String getType(Uri uri) {
        String tableName = uri.getPath();
        if (tableName.contains(News.TABLE_NAME)) {
            int match = URI_MATCHER.match(uri);
            switch (match) {
                case SOME:
                    return News.CONTENT_TYPE;
                case ONE:
                    return News.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalArgumentException("Unknown Uri");
            }
        }
        throw new IllegalArgumentException("Unknown Uri Path");
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        String tableName = getTableName(uri);
        if (URI_MATCHER.match(uri) != SOME) {
            throw new IllegalArgumentException("Cannot insert into Uri: " + uri);
        }
        ContentValues values = (initialValues != null) ? new ContentValues(
                initialValues) : new ContentValues();
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId < 0) {
            throw new SQLException("Failed to insert row into " + uri);
        }

        Uri noteUri = ContentUris.withAppendedId(News.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(noteUri, null);
        return noteUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case SOME:
                count = db.delete(tableName, selection, selectionArgs);
                break;
            case ONE:
                String segment = uri.getPathSegments().get(1);

                if (TextUtils.isEmpty(selection)) {
                    selection = BaseColumns._ID + "=" + segment;
                } else {
                    selection = BaseColumns._ID + "=" + segment + " AND (" + selection + ")";
                }

                count = db.delete(tableName, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Cannot update Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        long rowId = 0;
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case SOME:
                count = db.update(tableName, values, selection, selectionArgs);
                break;
            case ONE:
                String segment = uri.getPathSegments().get(1);
                rowId = Long.parseLong(segment);
                count = db.update(tableName, values, BaseColumns._ID + "=" + rowId, null);
                break;
            default:
                throw new IllegalArgumentException("Cannot update Uri: " + uri);
        }
        Log.d(DEBUG, "update: notifyChange() rowId: " + rowId + " uri " + uri);

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}