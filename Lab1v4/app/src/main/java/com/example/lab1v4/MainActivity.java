package com.example.lab1v4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final String EXTRA_MESSAGE = "MainActivity.MESSAGE";
    /* Цей метод викликається натисканням на кнопку "Кошик покупок" */
    public void sendMessage(View view) {
        // Створення екземпляру класу Intent
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        // Одержання значення вказівника textView з id textView
        TextView textView = (TextView)findViewById(R.id.textView);
        String message = textView.getText().toString(); // одержання тексту з textView

        // Додаємо значення змінної message у створений екземпляр класу Intent
        intent.putExtra(EXTRA_MESSAGE, message);
        // Викликаємо стандартний метод startActivity для обробки системою
        startActivity(intent);
    }
}