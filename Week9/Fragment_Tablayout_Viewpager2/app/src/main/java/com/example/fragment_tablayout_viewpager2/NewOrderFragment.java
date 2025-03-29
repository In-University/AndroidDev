package com.example.fragment_tablayout_viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fragment_tablayout_viewpager2.databinding.FragmentNeworderBinding;

public class NewOrderFragment extends Fragment {
    private FragmentNeworderBinding binding;

    public NewOrderFragment() {
        // Bắt buộc phải có constructor mặc định
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout bằng ViewBinding
        binding = FragmentNeworderBinding.inflate(inflater, container, false);

        // Xử lý các thao tác ở đây (ví dụ: setup RecyclerView)

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng binding để tránh memory leak
    }
}

