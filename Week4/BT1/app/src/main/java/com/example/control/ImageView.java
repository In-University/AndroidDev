package com.example.control;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ImageView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        android.widget.ImageView img1= findViewById(R.id.imageView);
        img1.setImageResource(R.drawable.bg1);

        ImageButton img2 = (ImageButton)
                findViewById(R.id.imageButton1);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setImageResource(R.drawable.on);
                img1.getLayoutParams().width=550;
                img1.getLayoutParams().height=550;
            }
        });
    }
}
