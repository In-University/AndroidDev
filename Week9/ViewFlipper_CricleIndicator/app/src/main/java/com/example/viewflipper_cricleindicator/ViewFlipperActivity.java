package com.example.viewflipper_cricleindicator;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity {

    private ViewFlipper viewFlipperMain; // Sử dụng tên nhất quán cho biến

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper); // Đảm bảo tên layout chính xác

        // Gán biến viewFlipperMain từ layout
        viewFlipperMain = findViewById(R.id.viewFlipperMain);

        // Gọi hàm xử lý ViewFlipper
        ActionViewFlipperMain();
    }

    private void ActionViewFlipperMain() {
        // Tạo danh sách các URL hình ảnh
        List<String> arrayListFlipper = new ArrayList<>();
        arrayListFlipper.add("http://app.iotstar.vn:8081/appfoods/flipper/quangcao.png");
        arrayListFlipper.add("http://app.lotstar.vn:8081/appfoods/flipper/coffee.jpg");
        arrayListFlipper.add("http://app.lotstar.vn:8081/wppfoods/flipper/companypizza.jpeg");
        arrayListFlipper.add("http://app.lotstar.vn:8081/appfoods/flipper/theneingen.png");

        // Thêm các ImageView chứa hình ảnh vào ViewFlipper
        for (int i = 0; i < arrayListFlipper.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext())
                    .load(arrayListFlipper.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMain.addView(imageView);
        }

        // Thiết lập các thuộc tính cho ViewFlipper
        viewFlipperMain.setFlipInterval(3089);
        viewFlipperMain.setAutoStart(true);

        // Tải animation từ resource và áp dụng cho ViewFlipper
        Animation slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipperMain.setInAnimation(slideIn);
        viewFlipperMain.setOutAnimation(slideOut);
    }
}
