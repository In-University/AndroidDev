package com.example.retrofit2.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2.APIService;
import com.example.retrofit2.Category;
import com.example.retrofit2.R;
import com.example.retrofit2.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    RecyclerView rcCate;
    //Khai báo Adapter
    CategoryAdapter categoryAdapter;
    APIService apiService;
    List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        AnhXa();
        GetCategory();//load dữ liệu cho category
    }

    private void AnhXa() {
        rcCate = (RecyclerView) findViewById(R.id.rc_category);
    }

    private void GetCategory() {
        //Goi Interface trong APIService
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoriesAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body();
                    //khởi tạo Adopter
                    categoryAdapter = new CategoryAdapter(RetrofitActivity.this, categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcCate.setLayoutManager(layoutManager);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                    Toast.makeText(RetrofitActivity.this,
                            "Loading dataa:::" + categoryList.size(), Toast.LENGTH_SHORT).show();
                } else {
                    int statusCode = response.code();
                    //handle request errors depending on status cade
                    Toast.makeText(RetrofitActivity.this,
                            "Error:::" + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("Logg", t.getMessage());
            }
        });
    }
}