package com.example.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class EditTexExample extends AppCompatActivity {
    private TextView txtSoN;
    private Button btnRnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext_example);

        txtSoN = findViewById(R.id.textViewSo);
        btnRnd = findViewById(R.id.buttonRnd);

        btnRnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int number = random.nextInt(10);
                Toast.makeText(EditTexExample.this, "Số ngẫu nhiên đã tạo: " + number, Toast.LENGTH_LONG).show();

                txtSoN.setText(String.valueOf(number));
            }
        });
    }
}
