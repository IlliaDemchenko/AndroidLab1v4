package com.example.lab6v4;

import android.database.Cursor;
import android.os.AsyncTask;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

class GetRowsTask extends AsyncTask<Object, Object, Cursor> {
    private WeakReference<MainAttraction> act;
    private DatabaseConnector databaseConnector;

    public GetRowsTask(MainAttraction activity){
        act = new WeakReference<>(activity);
        databaseConnector = new DatabaseConnector(activity);
    }

    @Override
    protected Cursor doInBackground(Object... objects) {
        databaseConnector.open();
        return databaseConnector.getTableAllRows();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String isOpened = Integer.parseInt(cursor.getString(3)) == 1 ? "Відкрито" : "Закрито";
            arrayList.add(
                    cursor.getString(0) + ", "
                    + cursor.getString(1) +
                            ", " + cursor.getString(2) +
                    ", " + isOpened
            );
        }

        databaseConnector.close();
        act.get().update_list(arrayList);
    }
}