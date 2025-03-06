package com.example.recyclerview;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvSongs;
    private SongAdapter songAdapter;
    private List<SongModel> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ánh xạ RecyclerView từ layout
        rvSongs = findViewById(R.id.rv_songs);

        songs = new ArrayList<>();
        songs.add(new SongModel("60696", "NẾU EM CÒN TỒN TẠI",
                "Khi anh bắt đầu 1 tình yêu Là lúc anh tự thay", "Trịnh Đình Quang"));
        songs.add(new SongModel("68701", "NGỐC",
                "Có rất nhiều những câu chuyện Em dấu riêng mình em biết", "Khắc Việt"));
        songs.add(new SongModel("68656", "HÃY TIN ANH LẦN NỮA",
                "Dẫu cho ta đã sai khi ở bên nhau Cô yêu thương", "Thiên Dũng"));
        songs.add(new SongModel("60618", "CHUỖI NGÀY VẮNG EM",
                "Từ khi em bước ra đi cõi lòng anh ngập tràn bao", "Duy Cường"));
        songs.add(new SongModel("60656", "KHI NGƯỜI MÌNH YÊU KHÓC",
                "Nước mắt em đang rơi trên những ngón tay Nước mắt em", "Phạm Mạnh Quỳnh"));
        songs.add(new SongModel("60685", "NO",
                "Anh mơ gặp em anh mơ được ôm anh mơ được gần", "Trịnh Thăng Bình"));
        songs.add(new SongModel("60752", "TÌNH YÊU CHẤP VÁ",
                "Muốn đi xa nơi yêu thương mình từng có Để không nghe", "Mr. Siro"));
        songs.add(new SongModel("60688", "CHỜ NGÀY MƯA TAN",
                "1 ngày mưa và em khuất xa nơi anh bóng dáng cũ", "Trung Đức"));
        songs.add(new SongModel("66663", "CÂU HỎI EM CHƯA TRẢ LỜI",
                "Cần nơi em 1 lời giải thích thật lòng Đừng lặng im", "Yuki Huy Nam"));
        songs.add(new SongModel("68720", "QUA ĐI LẶNG LẼ",
                "Đôi khi đến với nhau yêu thương chẳng được lâu nhưng khi", "Phan Mạnh Quỳnh"));
        songs.add(new SongModel("68856", "QUÊN ANH LÀ ĐIỀU EM KHÔNG THỂ REMIX",
                "Cần thêm bao lâu để em quên đi niềm đau Cần thêm", "Thiện Ngôn"));

        // Khởi tạo Adapter
        songAdapter = new SongAdapter(this, songs);
        rvSongs.setAdapter(songAdapter);

        // Thiết lập LayoutManager cho RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSongs.setLayoutManager(linearLayoutManager);
    }
}