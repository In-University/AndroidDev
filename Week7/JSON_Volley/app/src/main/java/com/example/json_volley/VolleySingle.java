package com.example.json_volley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingle {
    private static VolleySingle mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    // Constructor
    private VolleySingle(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    // Lấy instance (Singleton Pattern)
    public static synchronized VolleySingle getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingle(context);
        }
        return mInstance;
    }

    // Hàm tạo RequestQueue
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() giúp tránh rò rỉ bộ nhớ
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    // Hàm thêm request vào hàng đợi
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

