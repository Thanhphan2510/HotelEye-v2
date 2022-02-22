package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.FloorItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class RoomsOverviewSettingsAdapter extends ArrayAdapter<FloorItem> {
    private Context context;
    private List<FloorItem> floorItems;


    public RoomsOverviewSettingsAdapter(Context context, List<FloorItem> floorItems) {
        super(context, 0, floorItems);
        this.context = context;
        this.floorItems = floorItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_floor_setting_rooms, parent, false);
        FloorItem floorItem = floorItems.get(position);

        EditText name = view.findViewById(R.id.floorname_item_listview);
        EditText quanitity = view.findViewById(R.id.roomquanitity_item_listview);

        name.setText(floorItem.getFloor_name());
        quanitity.setText(floorItem.getRooms_quanitity());

        return view;
    }
}
