package com.example.lab6v4;

import android.content.Context;
import android.database.Cursor;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private DatabaseConnector databaseConnector;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        databaseConnector = new DatabaseConnector(context);
        databaseConnector.open();
    }

    @After
    public void cleanup() {
        databaseConnector.close();
    }

    @Test
    public void testInsertRow() {
        String name = "Water Park";
        String type = "Outdoor";
        boolean isOpened = true;
        databaseConnector.insertRow(name, type, isOpened);

        Cursor cursor = databaseConnector.getTableAllRows();

        assertTrue(cursor.moveToFirst());
        assertEquals(name, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_NAME)));
        assertEquals(type, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_TYPE)));
        assertTrue(cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_IS_OPENED)) != 0);

        cursor.close();
    }

    @Test
    public void testDeleteTableRow() {
        String name = "Water Park";
        String type = "Outdoor";
        boolean isOpened = true;
        databaseConnector.insertRow(name, type, isOpened);

        Cursor cursor = databaseConnector.getTableAllRows();
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_id));
        cursor.close();

        databaseConnector.deleteTableRow(id);
        cursor = databaseConnector.getTableAllRows();
        assertFalse(cursor.moveToFirst());
        cursor.close();
    }

    @Test
    public void testInsertRowTicket() {
        String date = "2023-06-12";
        String attraction = "Water Slide";
        String cost = "10";
        databaseConnector.insertRowTicket(date, attraction, cost);

        Cursor cursor = databaseConnector.getTableAllRowsTickets();

        assertTrue(cursor.moveToFirst());
        assertEquals(date, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_DATE)));
        assertEquals(attraction, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_ATTRACTION)));
        assertEquals(cost, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_COST)));

        cursor.close();
    }

    @Test
    public void testDeleteTableRowTicket() {
        String date = "2023-06-12";
        String attraction = "Water Slide";
        String cost = "10";
        databaseConnector.insertRowTicket(date, attraction, cost);

        Cursor cursor = databaseConnector.getTableAllRowsTickets();
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_id));
        cursor.close();

        databaseConnector.deleteTableRowTicket(id);
        cursor = databaseConnector.getTableAllRowsTickets();
        assertFalse(cursor.moveToFirst());
        cursor.close();
    }

    @Test
    public void testEditTableRow() {
        String name = "Water Park";
        String type = "Outdoor";
        boolean isOpened = true;
        databaseConnector.insertRow(name, type, isOpened);

        Cursor cursor = databaseConnector.getTableAllRows();
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_id));
        cursor.close();

        String updatedName = "New Water Park";
        String updatedType = "Indoor";
        boolean updatedIsOpened = false;
        databaseConnector.editTableRow(id, updatedName, updatedType, updatedIsOpened);

        cursor = databaseConnector.getTableAllRows();
        cursor.moveToFirst();
        assertEquals(updatedName, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_NAME)));
        assertEquals(updatedType, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_TYPE)));
        assertFalse(cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_IS_OPENED)) != 0);

        cursor.close();
    }

    @Test
    public void testEditTableRowTicket() {
        String date = "2023-06-12";
        String attraction = "Water Slide";
        String cost = "10";
        databaseConnector.insertRowTicket(date, attraction, cost);

        Cursor cursor = databaseConnector.getTableAllRowsTickets();
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_id));
        cursor.close();

        String updatedDate = "2023-06-13";
        String updatedAttraction = "Lazy River";
        String updatedCost = "15";
        databaseConnector.editTableRowTicket(id, updatedDate, updatedAttraction, updatedCost);

        cursor = databaseConnector.getTableAllRowsTickets();
        cursor.moveToFirst();
        assertEquals(updatedDate, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_DATE)));
        assertEquals(updatedAttraction, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_ATTRACTION)));
        assertEquals(updatedCost, cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_COLUMN_COST)));

        cursor.close();
    }
}