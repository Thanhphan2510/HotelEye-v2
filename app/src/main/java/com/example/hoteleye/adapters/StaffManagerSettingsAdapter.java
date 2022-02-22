package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.StaffManagerItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StaffManagerSettingsAdapter extends ArrayAdapter<StaffManagerItem> {
    private Context context;
    private List<StaffManagerItem> staffManagerItems;


    public StaffManagerSettingsAdapter(Context context, List<StaffManagerItem> staffManagerItems) {
        super(context, 0, staffManagerItems);
        this.context = context;
        this.staffManagerItems = staffManagerItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_staff_manager_settings, parent, false);
        StaffManagerItem staffManagerItem = staffManagerItems.get(position);

        TextView name = view.findViewById(R.id.name_item_listview_staffmanager);
        TextView account = view.findViewById(R.id.account_item_listview_staffmanager);
        TextView s_position = view.findViewById(R.id.position_item_listview_staffmanager);
        TextView detail = view.findViewById(R.id.detail_item_listview_staffmanager);
        TextView delete = view.findViewById(R.id.del_item_listview_staffmanager);


        name.setText(staffManagerItem.getName());
        account.setText(staffManagerItem.getUsername());
        s_position.setText(staffManagerItem.getPosition());

        return view;
    }
}