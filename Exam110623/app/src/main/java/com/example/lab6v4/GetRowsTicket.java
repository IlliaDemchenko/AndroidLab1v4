package com.example.lab6v4;

import android.database.Cursor;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

class GetRowsTicket extends AsyncTask<Object, Object, Cursor> {
    private WeakReference<MainTicket> act;
    private DatabaseConnector databaseConnector;

    public GetRowsTicket(MainTicket activity){
        act = new WeakReference<>(activity);
        databaseConnector = new DatabaseConnector(activity);
    }

    @Override
    protected Cursor doInBackground(Object... objects) {
        databaseConnector.open();
        return databaseConnector.getTableAllRowsTickets();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            arrayList.add(
                    cursor.getString(0) + ", "
                    + cursor.getString(1) +
                            ", " + cursor.getString(2) +
                            ", " + cursor.getString(3) + "грн"
            );
        }

        databaseConnector.close();
        act.get().update_list(arrayList);
    }
}