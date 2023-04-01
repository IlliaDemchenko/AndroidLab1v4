package com.example.lab7v4;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class PaintView extends View {
    private static final float paint_tolerance = 10;
    private Bitmap bitmap;

    private Canvas bitmap_canvas;
    private final Paint paint_for_screen;
    private final Paint paint_for_line;
    private final HashMap<Integer, Path> map_pid_path;
    private final HashMap<Integer, Point> map_pid_point_prev;

    public PaintView(Context context){
        super(context);
        paint_for_screen = new Paint();
        paint_for_line = new Paint();
        paint_for_line.setColor(Color.BLUE);

        paint_for_line.setAntiAlias(true);
        paint_for_line.setStrokeWidth(6);
        paint_for_line.setStyle(Paint.Style.STROKE);
        paint_for_line.setStrokeCap(Paint.Cap.ROUND);
        map_pid_path = new HashMap<Integer, Path>();
        map_pid_point_prev = new HashMap<Integer, Point>();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmap_canvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }

    // Метод очищення
    void clear() {
        map_pid_path.clear();
        map_pid_point_prev.clear();
        bitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    // Встановити новий колір
    public void set_line_color(int color) {
        paint_for_line.setColor(color);
    }

    // Метод отримання кольору
    public int get_line_color() {
        return paint_for_line.getColor();
    }

    // Встановити товщину
    public void set_line_width(int width) {
        paint_for_line.setStrokeWidth(width);
    }

    // Метод отримання товщини
    public int get_line_width() {
        return (int)paint_for_line.getStrokeWidth();
    }

    // Метод малювання ліній
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0,0, paint_for_screen);
        for (Integer key: map_pid_path.keySet())
            canvas.drawPath(map_pid_path.get(key), paint_for_line);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int actionIndex = event.getActionIndex();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex),
                    event.getPointerId(actionIndex));
        }
        else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            touchEnded(event.getPointerId(actionIndex));
        }
        else {
            touchMoved(event);
        }
        invalidate();
        return true;
    }

    private void touchStarted(float x, float y, int lineID) {
        Path path;
        Point point;

        if (map_pid_path.containsKey(lineID)) {
            path = map_pid_path.get(lineID);
            path.reset();
            point = map_pid_point_prev.get(lineID);
        }
        else {
            path = new Path();
            map_pid_path.put(lineID, path);
            point = new Point();
            map_pid_point_prev.put(lineID, point);
        }

        path.moveTo(x, y);  point.x = (int) x;  point.y = (int) y;
    }

    private void touchMoved(MotionEvent event) {
        for (int i = 0; i < event.getPointerCount(); i++) {
            int pid = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pid);

            if (map_pid_path.containsKey(pid)) {
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                Path path = map_pid_path.get(pid);
                Point point = map_pid_point_prev.get(pid);

                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                if (deltaX >= paint_tolerance || deltaY >= paint_tolerance) {
                    path.quadTo(point.x,point.y, (newX + point.x) / 2, (newY + point.y) / 2);
                    point.x = (int)newX;
                    point.y = (int)newY;
                }
            }
        }
    }

    private void touchEnded (int lineID) {
        Path path = map_pid_path.get(lineID);
        bitmap_canvas.drawPath(path, paint_for_line);
        path.reset();
    }

    // Метод збереження зображення
    public void save_image() {
        String fileName = getResources().getString(R.string.painting_saved) + (System.currentTimeMillis() / 1000);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/PNG");

        Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {
            OutputStream outStream = getContext().getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            Toast message = Toast.makeText(getContext(), "Збережно! " + fileName, Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2, message.getYOffset() / 2);
            message.show();
        }
        catch (IOException ex) {
            Toast message = Toast.makeText(getContext(), "Помилка! " + ex, Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2, message.getYOffset() / 2);
            message.show();
        }
    }

    // Метод встановлення зображення
    public void setImage(Uri uri) {
        try {
            // Отримання з вхідного потоку зображення за його адресом
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);

            // Створення bitmap зображення (decodeStream повертає bitmap, який не можна змінювати)
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            // Створюємо bitmap, який можна змінювати (тобто малювати на ньому)
            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            // Створюємо bitmap, який за розміром екрану встановить зображення
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mutableBitmap, getWidth(), getHeight(), true);
            inputStream.close();

            // Встановлюємо створений bitmap та новий canvas
            this.bitmap = scaledBitmap;
            this.bitmap_canvas = new Canvas(scaledBitmap);
            invalidate(); // оновлення екрану
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "error! " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}