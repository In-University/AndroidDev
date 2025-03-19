package com.example.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextParent;

    // Constructor
    public MyAsyncTask(Activity contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Hiển thị thông báo trước khi bắt đầu tiến trình
        Toast.makeText(contextParent, "Bắt đầu tiến trình...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Tiến hành vòng lặp để mô phỏng tiến trình chạy
        for (int i = 0; i <= 100; i++) {
            SystemClock.sleep(100); // Giả lập tiến trình chạy
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        // Lấy control từ MainActivity
        ProgressBar progressBar = contextParent.findViewById(R.id.prbDemo);
        TextView textView = contextParent.findViewById(R.id.txtStatus);

        int number = values[0]; // Giá trị progress

        // Cập nhật giao diện
        progressBar.setProgress(number);
        textView.setText(number + "%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Thông báo hoàn thành tiến trình
        Toast.makeText(contextParent, "Đã hoàn thành, Finished", Toast.LENGTH_SHORT).show();
    }
}
