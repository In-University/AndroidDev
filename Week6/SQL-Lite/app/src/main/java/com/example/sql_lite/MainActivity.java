package com.example.sql_lite;

import static android.app.ProgressDialog.show;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;
    private ListView listView;
    private ArrayList<NotesModel> arrayList;
    private NotesAdapter adapter;
    private Button btnAddNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        InitDatabaseSQLite();
//        createDatabaseSQLite();

        listView = findViewById(R.id.listView1);
        btnAddNotes = findViewById(R.id.buttonAddNotes);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_notes, arrayList);
        listView.setAdapter(adapter);
        Toast.makeText(this, "Size::" + arrayList.size(), Toast.LENGTH_SHORT).show();
        databaseSQLite();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Log.d("sdg", "called:::::::::::::::::::::::::");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAddNotes){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDatabaseSQLite() {
        databaseHandler.QueryData("INSERT INTO Notes VALUES (1, 'Vi du SQLite 1')");
        databaseHandler.QueryData("INSERT INTO Notes VALUES (2, 'Ví dụ SQLite 2')" );
    }
    private void InitDatabaseSQLite() {
        //khởi tạo database
        databaseHandler = new DatabaseHandler( this,  "notes.sqlite",  null,  1);
        // tạo bảng Notes
        databaseHandler.QueryData( "CREATE TABLE IF NOT EXISTS Notes (Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }
//    private void databaseSQLite() {
//        Cursor cursor = databaseHandler.GetData( "SELECT * FROM Notes");
//        while (cursor.moveToNext()) {
//            String name = cursor.getString( 1);
////            Toast.makeText( this, name, Toast.LENGTH_SHORT).show();
//        }
//    }

    private void databaseSQLite(){
        //Lấy dữ liệu
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        while (cursor.moveToNext()) {
            //them dữ liệu vào arraylist
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            // Toast.makeText(this, "name::" + name + "id:::" + id, Toast.LENGTH_SHORT).show();

            arrayList.add(new NotesModel (id, name));
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            Log.e("Error", "Adapter is null");
        }
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_notes);
        // ánh xạ trong dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
        Button buttonHuy = dialog.findViewById(R.id.buttonCancel);
        // bắt sự kiện cho nút thêm và huy
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                if (name.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên Notes",
                            Toast.LENGTH_SHORT).show();
                } else {
                    databaseHandler.QueryData("INSERT INTO Notes VALUES (null, '" + name + "')");                    Toast.makeText(MainActivity.this,"Đã thêm Notes", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    arrayList.removeAll(arrayList);
                    databaseSQLite();//gọi
                }
            }
        });
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //ham dialog cập nhật Notes
    public void DialogCapNhatNotes(String name, int id) {
        // Tạo đối tượng Dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_notes);

        // Ánh xạ các view trong Dialog
        EditText editText = dialog.findViewById(R.id.editTextName);
        Button buttonEdit = dialog.findViewById(R.id.buttonUpdate);
        Button buttonHuy = dialog.findViewById(R.id.buttonCancel);

        // Hiển thị tên hiện tại vào EditText
        editText.setText(name);

        // Sự kiện cho nút cập nhật
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editText.getText().toString().trim();
                if(newName.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên mới", Toast.LENGTH_SHORT).show();
                } else {
                    // Cập nhật dữ liệu vào database (sửa lại câu truy vấn SQL cho đúng)
                    databaseHandler.QueryData("UPDATE Notes SET NameNotes = '" + newName + "' WHERE Id = " + id);
                    Toast.makeText(MainActivity.this, "Đã cập nhật Notes thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    // Load lại dữ liệu sau khi cập nhật
                    databaseSQLite();
                }
            }
        });

        // Sự kiện cho nút hủy
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Hiển thị Dialog ngay sau khi đã thiết lập xong sự kiện
        dialog.show();
    }
}