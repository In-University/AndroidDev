package com.example.recycleview_indicator_search;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView; // Correct import for SearchView
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcIcon;
    IconAdapter iconAdapter;
    // Keep the original unfiltered list separate
    ArrayList<IconModel> arrayList1;
    GridLayoutManager gridLayoutManager;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcIcon = findViewById(R.id.rcIcon);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus(); // Remove focus from SearchView initially

        // Initialize data (Slide 8)
        arrayList1 = new ArrayList<>();
        arrayList1.add(new IconModel(R.drawable.ic_baseline_person_24, "ABC 1"));
        arrayList1.add(new IconModel(R.drawable.ic_launcher_background, "DEF 2"));
        arrayList1.add(new IconModel(R.drawable.ic_launcher_foreground, "GHI 3"));
        arrayList1.add(new IconModel(R.drawable.tvicon, "dfgfhyh sxdff"));
//        arrayList1.add(new IconModel(R.drawable.icon5, "jfdjfdjf djfdh")); // Duplicated desc
//        arrayList1.add(new IconModel(R.drawable.icon6, "sdfdf sfdsf"));   // Duplicated desc
//        arrayList1.add(new IconModel(R.drawable.icon7, "sdfdf sfds"));    // Duplicated desc
//        arrayList1.add(new IconModel(R.drawable.icon8, "dfgfhyh sxdff")); // Duplicated desc
//        arrayList1.add(new IconModel(R.drawable.icon9, "dfgfhyh sxdff")); // Duplicated desc
        // Add more unique descriptions if needed for testing search better

        // Setup RecyclerView (Slide 8)
        // Context is 'this' (the Activity), spanCount=2, Horizontal orientation
        gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(gridLayoutManager);

        // Initialize adapter with the original list
        iconAdapter = new IconAdapter(getApplicationContext(), arrayList1);
        rcIcon.setAdapter(iconAdapter);

        // Add Item Decoration (Indicator) - Pass context 'this'
        rcIcon.addItemDecoration(new LinePagerIndicatorDecoration(this));

        // Setup SearchView listener (Slide 10)
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Optional: Handle submission if needed
                return false; // Typically false if you handle changes in onQueryTextChange
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the list based on new text
                filterListener(newText);
                return true; // Indicates the action was handled
            }
        });
    }

    // Filter method (Slide 10)
    private void filterListener(String text) {
        List<IconModel> filteredList = new ArrayList<>();
        for (IconModel iconModel : arrayList1) { // Filter from the original list
            // Case-insensitive search in the description
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(iconModel);
            }
        }

        if (filteredList.isEmpty()) {
            // Show a message if no results found
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            // Optionally clear the RecyclerView or show a placeholder
            iconAdapter.setListenerList(new ArrayList<>()); // Pass empty list
        } else {
            // Update the adapter with the filtered list
            iconAdapter.setListenerList(filteredList);
        }
    }
}
