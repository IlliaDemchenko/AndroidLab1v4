package com.example.lab1v4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Одержуємо повідомлення і зберігаємо в змінній message
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Через вказівник на компонент екрану textView2 передаємо текст
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText(message);
    }
}