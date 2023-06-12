package com.example.lab6v4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE = "Attractions";
    public static final String TABLE_COLUMN_id = "_id";
    public static final String TABLE_COLUMN_NAME = "name";
    public static final String TABLE_COLUMN_TYPE = "type";
    public static final String TABLE_COLUMN_IS_OPENED = "isOpened";

    public static final String TABLE_TICKET = "Tickets";
    public static final String TABLE_COLUMN_DATE = "date";
    public static final String TABLE_COLUMN_ATTRACTION = "attraction";
    public static final String TABLE_COLUMN_COST = "cost";

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "( "
                + TABLE_COLUMN_id + " integer primary key autoincrement, "
                + TABLE_COLUMN_NAME + " TEXT,"
                + TABLE_COLUMN_TYPE + " TEXT,"
                + TABLE_COLUMN_IS_OPENED + " TEXT"
                + " )"
        );

        db.execSQL("CREATE TABLE " + TABLE_TICKET + "( "
                + TABLE_COLUMN_id + " integer primary key autoincrement, "
                + TABLE_COLUMN_DATE + " TEXT,"
                + TABLE_COLUMN_ATTRACTION + " TEXT,"
                + TABLE_COLUMN_COST + " TEXT, "
                + "FOREIGN KEY (" + TABLE_COLUMN_ATTRACTION + ") REFERENCES " + TABLE + "(" + TABLE_COLUMN_id + ")"
                + " )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}