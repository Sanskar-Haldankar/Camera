package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int request = 1;
    private Button btnCaptureImg;
    private ImageView imageView;
    Button btnrecapture;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCaptureImg = findViewById(R.id.btn_captureImage);
        imageView = findViewById(R.id.imgCapture);
        btnrecapture = findViewById(R.id.recapture);

        btnCaptureImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, request);
            }
        });


        btnrecapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageURI(null);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, request);
            }
        });

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == request && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            
            saveImageToGallery(imageBitmap);
        }
    }
    
    private void saveImageToGallery(Bitmap imageBitmap) {
        String ImagePath = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "Captured_Image", "Image captured from camera");
        
        if (ImagePath != null) {
            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save image to gallery", Toast.LENGTH_SHORT).show();
        }
    }
}
