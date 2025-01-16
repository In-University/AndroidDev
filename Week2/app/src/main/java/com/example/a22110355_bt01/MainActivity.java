package com.example.a22110355_bt01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Will hide the title not the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // int flag, int mask
        setContentView(R.layout.activity_main);

        ArrayList<Integer> primeList = new ArrayList<>();
        ArrayList<Integer> perfectSquareList = new ArrayList<>();

        EditText numberInput = findViewById(R.id.numberInput);
        Button btnCheck = findViewById(R.id.btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = numberInput.getText().toString().trim();
                Toast.makeText(MainActivity.this, "Test click!", Toast.LENGTH_SHORT).show();

                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số tự nhiên!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String[] numbers = input.split(",");

                for (String numStr : numbers) {
                    numStr = numStr.trim();
                    if (!isNumber(numStr)) {
                        Toast.makeText(MainActivity.this, numStr + " không phải là số tự nhiên hợp lệ!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int number = Integer.parseInt(numStr);
                    if (isPrime(number)) {
                        Log.d("SoNT", "Số nguyên tố: " + number);
                    }

                    if (isPerfectSquare(number)) {
                        perfectSquareList.add(number);
                    }
                }

                // Find the TextView to display results
                TextView txtPerfectSquareList = findViewById(R.id.txtPerfectSquareList);
                StringBuilder sb = new StringBuilder();
                for (int number : perfectSquareList) {
                    sb.append(number).append(" ");
                }
                if (perfectSquareList.isEmpty()) {
                    txtPerfectSquareList.setText("Không có số chính phương.");
                } else {
                    txtPerfectSquareList.setText("Số chính phương: " + sb.toString());
                }
            }
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean isNumber(String numStr) {
        try {
            int num = Integer.parseInt(numStr);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isPerfectSquare(int number) {
        if (number < 0) {
            return false;
        }

        int sqrt = (int) Math.sqrt(number);
        return sqrt * sqrt == number;
    }


    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}