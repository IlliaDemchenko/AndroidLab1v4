package com.example.lab5v4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Вказівник на кнопку для анімації
        Button buttonAnimate = (Button)findViewById(R.id.buttonAnimate);

        // Вказівники на компоненти RadioButton
        RadioButton radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        RadioButton radioButton3 = (RadioButton)findViewById(R.id.radioButton3);

        // Вказівник на компонент тексту для анімації
        TextView textViewForAnimation = (TextView)findViewById(R.id.textViewForAnimation);

        // Обробник подій при натисканні на кнопку анімації
        // х - положення за віссю х
        // y - положення за віссю у
        // rotationBy - обертання
        // scaleX - масштабування за віссю х
        // scaleY - масштабування за віссю у
        // alpha - прозорість
        // setDuration - встановити тривалість анімації
        // start - запустити анімацію
        buttonAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButton1.isChecked()) { // якщо обрана перша анімація
                    textViewForAnimation.animate()
                            .y(0)
                            .rotation(0.5f)
                            .alpha(.5f)
                            .translationX(200)
                            .rotationBy(45)
                            .scaleX(1.5f)
                            .scaleY(1.5f)
                            .setDuration(5000)
                            .start();
                }
                else if (radioButton2.isChecked()) { // якщо обрана друга анімація
                    textViewForAnimation.animate()
                            .x(0)
                            .y(600)
                            .alpha(.3f)
                            .setDuration(4000)
                            .start();
                }
                else if (radioButton3.isChecked()) { // якщо обрана третя анімація
                    textViewForAnimation.animate()
                            .x(600)
                            .y(300)
                            .alpha(.3f)
                            .scaleX(.7f)
                            .scaleY(.7f)
                            .setDuration(6000)
                            .start();
                }
            }
        });
    }
}