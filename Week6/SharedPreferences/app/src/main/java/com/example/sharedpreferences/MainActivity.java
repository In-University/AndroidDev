package com.example.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button buttonTxt;
    EditText usernameTxt, passwordTxt;
    CheckBox cbRememberMe;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AnhXa();
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        // Sau khi khởi tạo giao diện, lấy giá trị đã lưu từ SharedPreferences và set cho các EditText, CheckBox
        usernameTxt.setText(sharedPreferences.getString("taikhoan", ""));
        passwordTxt.setText(sharedPreferences.getString("matkhau", ""));
        cbRememberMe.setChecked(sharedPreferences.getBoolean("trangthai", false));

        buttonTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu người dùng nhập vào
                String username = usernameTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();

                // Tạo Editor để chỉnh sửa SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (username.equals("admin") && password.equals("admin")) {
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    // Nếu checkbox "Remember Me" được chọn thì lưu thông tin đăng nhập
                    if (cbRememberMe.isChecked()) {
                        editor.putString("taikhoan", username);
                        editor.putString("matkhau", password);
                        editor.putBoolean("trangthai", true);
                        editor.commit(); // Xác nhận việc lưu
                    } else {
                        // Nếu không chọn thì xóa thông tin đăng nhập đã lưu
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("trangthai");
                        editor.commit();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa(){
        buttonTxt = (Button) findViewById(R.id.buttonTxt);
        usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        cbRememberMe = (CheckBox) findViewById(R.id.cbmemberme);

    }
}