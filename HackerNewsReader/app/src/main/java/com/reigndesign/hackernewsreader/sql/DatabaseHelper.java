package com.reigndesign.hackernewsreader.sql;

/**
 * Created by romantolmachev on 10/3/2016.
 */

import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "database.db";

    static final int DATABASE_VERSION = 4;

    @Nullable
    private static DatabaseHelper instance = null;

    @Nullable
    public static DatabaseHelper getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        /**
         * News table
         */
        db.execSQL("CREATE TABLE " + News.TABLE_NAME + " (" + News._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + News.TITLE + " TEXT,"
                + News.AUTHOR + " TEXT,"
                + News.CREATED_AT + " TEXT,"
                + News.URL + " TEXT);");

    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + News.TABLE_NAME);
        onCreate(db);
    }
}
