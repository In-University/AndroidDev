package com.example.gridview;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Grid;

import java.util.ArrayList;

public class CustomGridAdapterActivity extends AppCompatActivity {
    ArrayList<MonHoc> arrayList;
    GridView gridView;
    MonhocAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gridview);

        AnhXa();
        //Tạo Adapter
        adapter = new MonhocAdapter(CustomGridAdapterActivity.this,
                R.layout.row_monhoc,
                arrayList
        );
        gridView.setAdapter(adapter);
    }

    private void AnhXa() {
        gridView = (GridView) findViewById(R.id.customGridView);
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java));
//        arrayList.add(new MonHoc("C#","C# 1",R.drawable.c));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
//        arrayList.add(new MonHoc("Kotlin","Kotlin",R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart","Dart 1", R.drawable.dart));
    }
}
