package com.example.lab6v4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class DatabaseConnector {
    private static final String DATABASE_NAME = "WaterPark4.db";
    private SQLiteDatabase database;
    private DBHelper databaseOpenHelper;

    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DBHelper(context, DATABASE_NAME, null, 1);
    }

    public void open() {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) database.close();
    }

    public void insertRow(String name, String type, boolean isOpened) {
        ContentValues row = new ContentValues();
        row.put(DBHelper.TABLE_COLUMN_NAME, name);
        row.put(DBHelper.TABLE_COLUMN_TYPE, type);
        row.put(DBHelper.TABLE_COLUMN_IS_OPENED, isOpened);
        open();
        database.insert(DBHelper.TABLE, null, row);
        close();
    }

    public void insertRowTicket(String date, String attraction, String cost) {
        ContentValues row = new ContentValues();
        row.put(DBHelper.TABLE_COLUMN_DATE, date);
        row.put(DBHelper.TABLE_COLUMN_ATTRACTION, attraction);
        row.put(DBHelper.TABLE_COLUMN_COST, cost);
        open();
        database.insert(DBHelper.TABLE_TICKET, null, row);
        close();
    }

    public Cursor getTableAllRows() {
        return database.query(DBHelper.TABLE,
                new String[] {DBHelper.TABLE_COLUMN_id, DBHelper.TABLE_COLUMN_NAME, DBHelper.TABLE_COLUMN_TYPE, DBHelper.TABLE_COLUMN_IS_OPENED},
                null, null, null, null, DBHelper.TABLE_COLUMN_id
        );
    }

    public Cursor getTableAllRowsTickets() {
        return database.query(DBHelper.TABLE_TICKET,
                new String[] {DBHelper.TABLE_COLUMN_id, DBHelper.TABLE_COLUMN_DATE, DBHelper.TABLE_COLUMN_ATTRACTION, DBHelper.TABLE_COLUMN_COST},
                null, null, null, null, DBHelper.TABLE_COLUMN_id
        );
    }

    public void deleteTableRowTicket(long id) {
        open();
        database.delete(DBHelper.TABLE_TICKET, "_id=" + id, null);
        close();
    }

    public void deleteTableRow(long id) {
        open();
        database.delete(DBHelper.TABLE, "_id=" + id, null);
        close();
    }

    public void editTableRowTicket(long id, String date, String attraction, String cost) {
        database = databaseOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_COLUMN_DATE, date);
        cv.put(DBHelper.TABLE_COLUMN_ATTRACTION, attraction);
        cv.put(DBHelper.TABLE_COLUMN_COST, cost);

        String where = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        database.update(DBHelper.TABLE_TICKET, cv, where, whereArgs);
    }

    public void editTableRow(long id, String name, String type, boolean isOpened) {
        database = databaseOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_COLUMN_NAME, name);
        cv.put(DBHelper.TABLE_COLUMN_TYPE, type);
        cv.put(DBHelper.TABLE_COLUMN_IS_OPENED, isOpened);

        String where = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        database.update(DBHelper.TABLE, cv, where, whereArgs);
    }
}