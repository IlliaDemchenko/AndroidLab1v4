package com.example.lab6v4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

// Клас для підключення до бази даних, який описує основні методи взаємодії
class DatabaseConnector {
    private static final String DATABASE_NAME = "OnlineShop";
    private SQLiteDatabase database;
    private DBHelper databaseOpenHelper;

    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DBHelper(context, DATABASE_NAME, null, 1);
    }

    // Метод відкриття підключення до бази даних
    public void open() {
        database = databaseOpenHelper.getWritableDatabase();
    }

    // Метод закриття підключення до бази даних
    public void close() {
        if (database != null) database.close();
    }

    // Метод додавання рядка до бази даних
    public void insertRow(String text, String num) {
        ContentValues row = new ContentValues();
        row.put(DBHelper.TABLE_COLUMN_NAME, text);
        row.put(DBHelper.TABLE_COLUMN_COST, num);
        open();
        database.insert(DBHelper.TABLE, null, row);
        close();
    }

    // Метод повернення курсору для доступу до результатів запиту
    public Cursor getTableAllRows() {
        return database.query(DBHelper.TABLE,
                new String[] {DBHelper.TABLE_COLUMN_id, DBHelper.TABLE_COLUMN_NAME, DBHelper.TABLE_COLUMN_COST},
                null, null, null, null, DBHelper.TABLE_COLUMN_id
        );
    }

    // Метод видалення рядка з бази даних за унікальним ID
    public void deleteTableRow(long id) {
        open();
        database.delete(DBHelper.TABLE, "_id=" + id, null);
        close();
    }

    // Метод редагування рядка бази даних за унікальним id
    public void editTableRow(long id, String text, String num) {
        database = databaseOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_COLUMN_NAME, text);
        cv.put(DBHelper.TABLE_COLUMN_COST, num);

        String where = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        database.update(DBHelper.TABLE, cv, where, whereArgs);
    }
}