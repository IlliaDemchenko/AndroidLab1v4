package com.example.lab7v4;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;


public class PaintActivity extends AppCompatActivity {
    static final int MENU_COLOR_BLACK = Menu.FIRST + 1;
    static final int MENU_CLEAR = Menu.FIRST + 2;
    static final int MENU_SAVE = Menu.FIRST + 3;
    static final int MENU_SELECT = Menu.FIRST + 4;

    private static final int PICK_IMAGE_REQUEST = 1;

    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paintView = new PaintView(this);
        setContentView(paintView);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MENU_COLOR_BLACK, Menu.NONE, R.string.erase);
        menu.add(Menu.NONE, MENU_CLEAR, Menu.NONE, R.string.clear);
        menu.add(Menu.NONE, MENU_SAVE, Menu.NONE, R.string.save);
        menu.add(Menu.NONE, MENU_SELECT, Menu.NONE, R.string.select);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_COLOR_BLACK:
                paintView.set_line_color(Color.BLACK);
                return true;
            case MENU_CLEAR:
                paintView.clear();
                return true;
            case MENU_SAVE:
                paintView.save_image();
                return true;
            case MENU_SELECT:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Метод, який викликається після того, як користувач обрав зображення
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Отримати адресу зображення
            Uri imageUri = data.getData();
            // Встановити його на екран
            paintView.setImage(imageUri);
        }
        catch (Exception e) {
            Toast.makeText(this, "Помилка вставки зображення.", Toast.LENGTH_SHORT).show();
        }
    }
}