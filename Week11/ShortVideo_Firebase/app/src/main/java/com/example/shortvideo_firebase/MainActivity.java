package com.example.shortvideo_firebase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private VideosAdapter videosAdapter;
    private List<VideoModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bỏ tiêu đề và set full màn hình
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Ánh xạ ViewPager2 từ XML
        viewPager2 = findViewById(R.id.vpager);
        list = new ArrayList<>();

        // Gọi API
        getVideos();
    }

    private void getVideos() {
        APIService.Factory.getInstance().getVideos().enqueue(new Callback<MessageVideoModel>() {
            @Override
            public void onResponse(Call<MessageVideoModel> call, Response<MessageVideoModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list = response.body().getResult();
                    videosAdapter = new VideosAdapter(MainActivity.this, list);
                    viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                    viewPager2.setAdapter(videosAdapter);
                } else {
                    Log.e("TAG", "Response không hợp lệ hoặc rỗng.");
                }
            }

            @Override
            public void onFailure(Call<MessageVideoModel> call, Throwable t) {
                Log.e("TAG", "Gọi API thất bại: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Gọi API thất bại: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
