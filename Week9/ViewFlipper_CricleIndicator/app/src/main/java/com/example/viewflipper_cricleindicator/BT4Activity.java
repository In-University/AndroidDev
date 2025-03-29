package com.example.viewflipper_cricleindicator;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class BT4Activity extends AppCompatActivity {
    // Khai báo biến toàn cục
    private SliderView sliderView;
    private ArrayList<Integer> arrayList;
    private SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt4); // Thay your_layout bằng layout của bạn

        // Ánh xạ SliderView từ XML
        sliderView = findViewById(R.id.imageSlider);

        // Khởi tạo mảng dữ liệu và thêm ảnh
        arrayList = new ArrayList<>();
        arrayList.add(R.drawable.cake);
        arrayList.add(R.drawable.coffee);
        arrayList.add(R.drawable.componypizza);
        arrayList.add(R.drawable.quangcao);

        // Gọi Adapter, truyền dữ liệu và gán cho SliderView
        sliderAdapter = new SliderAdapter(getApplicationContext(), arrayList);
        sliderView.setSliderAdapter(sliderAdapter);

        // Cấu hình thuộc tính cho SliderView
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.black));
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5);
        sliderView.startAutoCycle();
    }

}
