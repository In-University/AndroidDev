package com.example.listview;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CustomAdapterActivity extends AppCompatActivity {
    ArrayList<MonHoc> arrayList;
    ListView listView;
    MonHocAdapter adapter;
    private void AnhXa() {
        listView = (ListView) findViewById(R.id.customListView);
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java));
//        arrayList.add(new MonHoc("C#","C# 1",R.drawable.c));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
//        arrayList.add(new MonHoc("Kotlin","Kotlin",R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart","Dart 1", R.drawable.dart));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_adapter);
        AnhXa();
        if (listView != null) {
            Toast.makeText(this, "ListView có data!" + String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();
        }
        adapter = new MonHocAdapter(CustomAdapterActivity.this,
                R.layout.row_monhoc,
                arrayList);
        listView.setAdapter(adapter);
    }
}
