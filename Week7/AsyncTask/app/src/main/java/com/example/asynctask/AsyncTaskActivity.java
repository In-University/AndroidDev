package com.example.asynctask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncTaskActivity extends AppCompatActivity {
    Button btnStart;
    MyAsyncTask myAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khởi tạo tiến trình của bạn
                //Truyền Activity chính là MainActivity sang bên tiến trình của mình
                myAsyncTask = new MyAsyncTask(AsyncTaskActivity.this);
                //Gọi hàm execute để kích hoạt tiến trinh
                myAsyncTask.execute();
            }
        });
    }
}
