package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.RoomTypeItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoomTypeSettingsAdapter extends ArrayAdapter<RoomTypeItem> {
    private Context context;
    private List<RoomTypeItem> roomTypeItems;


    public RoomTypeSettingsAdapter(Context context, List<RoomTypeItem> roomTypeItems) {
        super(context, 0, roomTypeItems);
        this.context = context;
        this.roomTypeItems = roomTypeItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_room_type, parent, false);
        final RoomTypeItem roomTypeItem = roomTypeItems.get(position);

        TextView name = view.findViewById(R.id.type_name_item_listview);
        Button edit_btn = view.findViewById(R.id.edit_btn_item_listview);
        Button del_btn = view.findViewById(R.id.delete_btn_item_listview);

        name.setText(roomTypeItem.getType_name());
        del_btn.setTag(position);
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomTypeItems.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}