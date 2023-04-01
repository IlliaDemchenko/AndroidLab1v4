package com.example.lab3v4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Отримання компонентів
        Switch switchButton = (Switch)findViewById(R.id.switchButton);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        EditText editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);

        // Обробник подій для SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Встановлюємо значення у компонент EditText
                editTextNumber.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Обробник подій для Switch
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Якщо switch активований, необхідно показати кнопку
                // інашке необіхдно її сховати
                if (isChecked) {
                    button2.setVisibility(View.VISIBLE);
                    switchButton.setText("На екрані");
                }
                else {
                    button2.setVisibility(View.GONE);
                    switchButton.setText("Не на екрані");
                }
            }
        });

        // Обробний подій при натисканні на кнопку "Кнопка 1"
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Отримання значення з текстового поля EditText
                String value = editTextNumber.getText().toString();
                // Якщо поле не порожнє, виводимо число, інакше - інформаційне повідомлення
                String message = value.isEmpty() ? "Поле порожнє!" : "Число: " + value;
                Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}