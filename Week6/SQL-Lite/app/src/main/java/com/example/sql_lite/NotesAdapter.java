package com.example.sql_lite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NotesAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NotesModel> noteList;
    //tạo constructor
    public NotesAdapter (Context context, int layout, List<NotesModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }
    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //tạo viewHolder
    private class ViewHolder{
        TextView textViewNote;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.textViewNote = convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewEdit = convertView.findViewById(R.id.imageViewEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy đối tượng NotesModel tại vị trí position
        final NotesModel note = noteList.get(position);
        viewHolder.textViewNote.setText(note.getNameNote());

        // Bắt sự kiện cho nút chỉnh sửa
        viewHolder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cập nhật " + note.getNameNote() + "id::" + note.getIdNote(), Toast.LENGTH_SHORT).show();
                // Ép kiểu context sang MainActivity để gọi DialogCapNhatNotes
                ((MainActivity) context).DialogCapNhatNotes(note.getNameNote(), note.getIdNote());
            }
        });

        //bắt sự kiện xóa notes
        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).DialogDelete(note.getNameNote(), note.getIdNote());
            }
        });

        return convertView;
    }
}
