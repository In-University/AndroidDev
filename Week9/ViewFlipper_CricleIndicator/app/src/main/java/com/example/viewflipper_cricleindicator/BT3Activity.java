package com.example.viewflipper_cricleindicator;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;

public class BT3Activity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private CircleIndicator3 circleIndicator;
    private List<Images> imagesList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == imagesList.size() - 1){
                viewPager.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }

            if(viewPager.getCurrentItem() == imagesList.size() - 1){
                viewPager.setCurrentItem(0);
            }else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt3);

        viewPager = findViewById(R.id.viewpage2);
        circleIndicator = findViewById(R.id.circle_indicator3);

        imagesList = getListImages();
        ImagesViewPager2Adapter adapter = new ImagesViewPager2Adapter(imagesList);
        viewPager.setAdapter(adapter);

        // Liên kết ViewPager với CircleIndicator
        circleIndicator.setViewPager(viewPager);

        handler.postDelayed(runnable, 3000);

        // Đăng ký callback cho ViewPager2
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Xóa các callback trước đó và đặt callback mới với delay 3000ms (3 giây)
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        // Thiết lập page transformer cho ViewPager2 (ví dụ sử dụng DepthPageTransformer)
        viewPager.setPageTransformer(new DepthPageTransformer());

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    private List<Images> getListImages() {
        List<Images> list = new ArrayList<>();
        list.add(new Images(R.drawable.quangcao));
        list.add(new Images(R.drawable.coffee));
        list.add(new Images(R.drawable.componypizza));
        list.add(new Images(R.drawable.cake));
        return list;
    }
}
