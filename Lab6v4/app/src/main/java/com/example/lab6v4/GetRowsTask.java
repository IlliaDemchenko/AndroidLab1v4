package com.example.lab6v4;

import android.database.Cursor;
import android.os.AsyncTask;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

// Клас, який використовується для завантаження даних з бази даних
class GetRowsTask extends AsyncTask<Object, Object, Cursor> {
    private WeakReference<MainActivity> act;
    private DatabaseConnector databaseConnector;

    public GetRowsTask(MainActivity activity){
        act = new WeakReference<>(activity);
        databaseConnector = new DatabaseConnector(activity);
    }

    // Виконання у фоновому потоці
    @Override
    protected Cursor doInBackground(Object... objects) {
        databaseConnector.open();
        return databaseConnector.getTableAllRows();
    }

    // Метод, що виконується після завершення фонової операції
    @Override
    protected void onPostExecute(Cursor cursor) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            arrayList.add(
                    cursor.getString(0) + ", "
                    + cursor.getString(1) +
                    ", " + cursor.getString(2) + "грн"
            );
        }

        databaseConnector.close();
        act.get().update_list(arrayList);
    }
}