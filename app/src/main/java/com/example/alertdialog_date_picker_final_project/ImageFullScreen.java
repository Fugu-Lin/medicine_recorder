package com.example.alertdialog_date_picker_final_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ImageFullScreen extends Activity {

    ImageView imageView;
    String ImagePath;
    boolean isImageFitToScreen;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen);
        imageView = findViewById(R.id.imageView2);

        Intent intent = getIntent();
        ImagePath = String.valueOf(intent.getExtras().getString("ImagePath"));
        Bitmap myBitmap = BitmapFactory.decodeFile(ImagePath);
        imageView.setImageBitmap(myBitmap);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setAdjustViewBounds(true);
                }else{
                    finish();
                }
            }
        });

    }
}
