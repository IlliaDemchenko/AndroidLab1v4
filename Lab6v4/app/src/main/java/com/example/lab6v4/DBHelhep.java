package com.example.lab6v4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Клас для створення та оновлення бази даних
class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE = "Products";
    public static final String TABLE_COLUMN_id = "_id";
    public static final String TABLE_COLUMN_NAME = "name"; // назва
    public static final String TABLE_COLUMN_COST = "cost"; // ціна

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version); // Батьківський конструктор
    }

    // Метод, який викликається один раз при створенні
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "( "
                + TABLE_COLUMN_id + " integer primary key autoincrement, "
                + TABLE_COLUMN_NAME + " TEXT,"
                + TABLE_COLUMN_COST + " TEXT"
                + " )"
        );
    }

    // Метод оновлення, який викликається відповідно до версій бази даних
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}