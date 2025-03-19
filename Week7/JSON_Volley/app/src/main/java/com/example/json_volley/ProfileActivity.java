package com.example.json_volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    TextView id, userName, userEmail, gender;
    Button btnLogout;
    ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            // Ánh xạ các view
            id = findViewById(R.id.textViewId);
            userName = findViewById(R.id.textViewUsername);
            userEmail = findViewById(R.id.textViewEmail);
            gender = findViewById(R.id.textViewGender);
            btnLogout = findViewById(R.id.buttonLogout);
            imageViewProfile = findViewById(R.id.imageViewProfile);

            // Lấy thông tin người dùng
            User user = SharedPrefManager.getInstance(this).getUser();

            id.setText(String.valueOf(user.getId()));
            userEmail.setText(user.getEmail());
            gender.setText(user.getGender());
            userName.setText(user.getName());

            // Load ảnh với Glide
            Glide.with(getApplicationContext())
                    .load(user.getImages())
                    .into(imageViewProfile);

            btnLogout.setOnClickListener(this);
        } else {
            // Chuyển hướng về màn hình đăng nhập nếu chưa đăng nhập
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonLogout) {
            SharedPrefManager.getInstance(this).logout();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
