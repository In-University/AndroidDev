package com.example.shortvideo_firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideoShortFireBaseActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private VideosFireBaseAdapter videosAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        Log.d("FirebaseInit", "Firebase initialized");
        Toast.makeText(this, "Firebase initialized", Toast.LENGTH_SHORT).show();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        Log.d("Layout", "Layout set");

        viewPager2 = findViewById(R.id.vpager);
        if (viewPager2 == null) {
            Log.e("ViewPager", "viewPager2 is null!");
            Toast.makeText(this, "viewPager2 is null", Toast.LENGTH_LONG).show();
        }

        getVideos();
    }

    private void getVideos() {
        Log.d("Firebase", "Fetching videos...");
        Toast.makeText(this, "Fetching videos...", Toast.LENGTH_SHORT).show();

        try {
            DatabaseReference mDataBase = FirebaseDatabase
                    .getInstance("https://videoshort-36306-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .getReference("videos");

            FirebaseRecyclerOptions<Video1Model> options = new FirebaseRecyclerOptions.Builder<Video1Model>()
                    .setQuery(mDataBase, Video1Model.class)
                    .build();

            videosAdapter = new VideosFireBaseAdapter(options);
            viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            viewPager2.setAdapter(videosAdapter);

            Log.d("Adapter", "Adapter set");
            Toast.makeText(this, "Adapter set", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Firebase", "Error setting up Firebase", e);
            Toast.makeText(this, "Firebase setup error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (videosAdapter != null) {
            videosAdapter.startListening();
            Log.d("Adapter", "startListening()");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videosAdapter != null) {
            videosAdapter.stopListening();
            Log.d("Adapter", "stopListening()");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videosAdapter != null) {
            videosAdapter.notifyDataSetChanged();
            Log.d("Adapter", "notifyDataSetChanged()");
        }
    }
}
