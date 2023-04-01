package com.example.lab4v4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Вказівник на компонент GridView
        GridView gridView = findViewById(R.id.gridView);

        // Масив рядків
        String[] result = new String[250];

        // Заповнюємо масив квадратами чисел від 0 до 250, округлюючи до 6 знаків після коми
        // також кому необхідно замінити на крапку
        for (int i = 0; i < 250; i++) {
            result[i] = ("Число\n" + String.format("%.6f", Math.sqrt(i))).replace(",", ".");
        }

        // Створюємо адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result);

        // Встановлюємо створений адаптер у gridView
        gridView.setAdapter(adapter);
    }
}