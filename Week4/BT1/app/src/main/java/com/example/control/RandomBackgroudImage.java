package com.example.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class RandomBackgroudImage extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private Switch switchBackground;


    private int[] backgrounds = {
            R.drawable.bg1,
            R.drawable.bg2,
            R.drawable.bg4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt1_random_backgroud_image);

        switchBackground = findViewById(R.id.switchBackground);
        mainLayout = findViewById(R.id.relativeLayout);

        switchBackground.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mainLayout.setBackgroundResource(backgrounds[1]);
            } else {
                mainLayout.setBackgroundResource(backgrounds[0]);
            }
        });

        setRandomBackground();
    }

    private void setRandomBackground() {
        Random random = new Random();
        int index = random.nextInt(backgrounds.length);
        mainLayout.setBackgroundResource(backgrounds[index]);
    }
}