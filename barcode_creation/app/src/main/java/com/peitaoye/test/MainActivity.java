package com.peitaoye.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.peitaoye.qrencoder.Contents;
import com.peitaoye.qrencoder.QRCodeEncoder;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button store;
    Button show;
    Bitmap bitmap;
    ImageView imageView;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        store = findViewById(R.id.button2);
        show = findViewById(R.id.button3);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    Log.d("info", file.getAbsolutePath());
                    ((ImageView) findViewById(R.id.imageView2)).setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("info", "show fail");
                }
            }
        });
//        Log.d("info","getFilsFir"+getFilesDir().getAbsolutePath());
//        Log.d("info","getFilsCacheFir"+getCacheDir().getAbsolutePath());
//        Log.d("info","getExternalFilesDir"+getExternalFilesDir(null).getAbsolutePath());
//        Log.d("info","getExternalCacheDir"+getExternalCacheDir().getAbsolutePath());
//        Log.d("info","Environment.getExternalStorageDirectory()"+ Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.d("info","Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
//        Log.d("info","Environment.getDataDirectory()"+Environment.getDataDirectory().getAbsolutePath());
//        Log.d("info","getDownloadCacheDirectory"+Environment.getDownloadCacheDirectory().getAbsolutePath());
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/qrcodemmm.png");
            Log.d("info", "filepath" + file.getAbsolutePath());
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(this, "http://www.baidu.com", 500, Contents.Type.EMAIL);
            bitmap = qrCodeEncoder.encodeAsBitmap(file.getAbsolutePath());
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
