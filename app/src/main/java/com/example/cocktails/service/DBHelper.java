package com.example.cocktails.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cocktailsDb";
    public static final String TABLE_HISTORY = "history";

    public static final String KEY_ID = "_id";
    public static final String KEY_COCKTAIL_ID = "cocktail_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE_URL = "image_url";
    public static final String KEY_VIEW_DATE = "view_date";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_HISTORY + " (" +
                KEY_ID + " long primary key, " +
                KEY_COCKTAIL_ID + " long, " +
                KEY_NAME + " text, " +
                KEY_IMAGE_URL + " text, " +
                KEY_VIEW_DATE + " timestamp"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_HISTORY);
        onCreate(db);
    }
}
