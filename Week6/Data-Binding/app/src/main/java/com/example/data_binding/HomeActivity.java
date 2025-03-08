package com.example.data_binding;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.data_binding.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ListUserAdapter.OnItemClickListener{
//    public MutableLiveData<String> title = new MutableLiveData<>();
    public ObservableField<String> title = new ObservableField<>("Ví dụ về DataBinding cho Recycler View");

    private ListUserAdapter listUserAdapter;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setLifecycleOwner(this);
        binding.setHome2(this);
        title.set("ViDi");
        binding.executePendingBindings();
//        binding.textView2.setText("ssgshsh123");
        title.set("Ví dụ về DataBinding cho Recycler View");
        Log.d("HomeActivity", "Title set to: " + title.get());
        setData();

        binding.rcView.setLayoutManager(new LinearLayoutManager(this));
        binding.rcView.setAdapter(listUserAdapter);
        listUserAdapter.setOnItemClickListener(this);
    }

    @Override
    public void itemClick(UserModel user) {
        if (user != null) {
            Toast.makeText(this, "Bạn vừa click: " + user.getFirstName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        List<UserModel> userList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            UserModel user = new UserModel("Khoa " + i, "Vo " + i);
            userList.add(user);
        }
        listUserAdapter = new ListUserAdapter(userList);
    }
}