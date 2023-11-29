package com.example.empldb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATA_BASE_NAME = "example.db";
    public static final int DATA_BASE_SCHEME = 1;
    public static final String MY_TABLE = "MY_TABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_SURNAME = "COLUMN_SURNAME";
    public static final String COLUMN_YEAR = "COLUMN_YEAR";

    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_SCHEME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MY_TABLE
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_SURNAME + " TEXT, "
                + COLUMN_YEAR + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MY_TABLE);
        onCreate(db);
    }


}
