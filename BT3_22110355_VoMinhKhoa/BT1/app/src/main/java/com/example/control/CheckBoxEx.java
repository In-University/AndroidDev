package com.example.control;

import android.os.Bundle;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout; // Đảm bảo bạn đang import đúng

public class CheckBoxEx extends AppCompatActivity {
    private ConstraintLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_example);

        bg = findViewById(R.id.rootLayout);

        RadioGroup radioGroup = findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton) {
                    bg.setBackgroundResource(R.drawable.bg1);
                } else if (checkedId == R.id.radioButton2) {
                    bg.setBackgroundResource(R.drawable.bg4);
                }
            }
        });
    }
}
