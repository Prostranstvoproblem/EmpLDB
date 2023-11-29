package com.example.empldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String MY_TABLE = "MY_TABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_SURNAME = "COLUMN_SURNAME";
    public static final String COLUMN_YEAR = "COLUMN_YEAR";

    public DBHelper(Context context) {
        super(context, "example.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MY_TABLE + " (" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_NAME + " TEXT, " + COLUMN_SURNAME + " TEXT, " + COLUMN_YEAR + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MY_TABLE);
        onCreate(db);
    }


}
