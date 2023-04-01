package com.example.lab6v4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Online Shop: ";
    private ArrayList<String> arrayList;

    private Integer currentItemID = 0;
    private ListView listView;

    private int count = 7; // кількість елементів на сторінці
    private int currentPage = 0; // номер поточної сторінки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Отримання покажчиків на компоненти
        this.listView = findViewById(R.id.listViewProducts);
        registerForContextMenu(this.listView);

        Button buttonBack = (Button)findViewById(R.id.buttonBack);
        Button buttonNext = (Button)findViewById(R.id.buttonNext);

        // Обробний подій при натисканні на кнопку Назад
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage > 0) {
                    currentPage--;
                    updateListAdapter();
                }
            }
        });

        // Обробник подій при натисканні на кнопку Вперед
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage < arrayList.size() / count) {
                    currentPage++;
                    updateListAdapter();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh_screen();
    }

    // Метод для оновлення екрану
    void refresh_screen() {
        new GetRowsTask(this).execute();
    }

    // Метод, який викликається при натисканні на кнопку додавання чи редагування
    public void add_btn_clicked(View view) {
        String text = ((EditText) findViewById(R.id.editTextNameProduct)).getText().toString();
        String num = ((EditText) findViewById(R.id.editTextCost)).getText().toString();

        DatabaseConnector databaseConnector = new DatabaseConnector(this);

        if (currentItemID > 0) {
            databaseConnector.editTableRow(currentItemID, text, num);
            currentItemID = 0;
        } else {
            databaseConnector.insertRow(text, num);
        }
        refresh_screen();

        // виведення на екран повідомлення про виконану дію
        Toast.makeText(this, "Список оновлено!", Toast.LENGTH_SHORT).show();
    }

    final int MENU_CONTEXT_DELETE_ID = 123;
    final int MENU_CONTEXT_EDIT_ID = 124;
    // Створення контекстного меню
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        if (view.getId() == R.id.listViewProducts) {
            menu.add(Menu.NONE, MENU_CONTEXT_DELETE_ID, Menu.NONE, "Remove item");
            menu.add(Menu.NONE, MENU_CONTEXT_EDIT_ID, Menu.NONE, "Edit item");
        }
    }

    // Обробний подій при натисканні на пункт меню
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position + count * currentPage;
        String str = arrayList.get(position);
        int chooseID = Integer.parseInt(str.split(", ")[0]);

        switch (item.getItemId()) {
            case MENU_CONTEXT_DELETE_ID: {
                Log.d(TAG, "Видалення: " + position);

                DatabaseConnector databaseConnector = new DatabaseConnector(this);
                databaseConnector.deleteTableRow(chooseID);

                refresh_screen();
                Toast.makeText(this, "Видалено елемент " + String.valueOf(chooseID), Toast.LENGTH_SHORT).show();
                return true;
            }
            case MENU_CONTEXT_EDIT_ID:{
                Log.d(TAG, "Редагування: " + position);
                currentItemID = chooseID;
                ((EditText)findViewById(R.id.editTextNameProduct)).setText(str.split(", ")[1]);
                ((EditText)findViewById(R.id.editTextCost)).setText(str.split(", ")[2].replace("грн", ""));
                return true;
            }
            default:
                return super.onContextItemSelected(item);
        }
    }

    // Метод оновлення списку
    public void update_list(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        this.updateListAdapter();
    }

    public void updateListAdapter() {
        int startIndex = currentPage * this.count;
        int endIndex = Math.min(startIndex + this.count, arrayList.size());

        List<String> currentPageItems = arrayList.subList(startIndex, endIndex);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currentPageItems);
        this.listView.setAdapter(listAdapter);
    }
}