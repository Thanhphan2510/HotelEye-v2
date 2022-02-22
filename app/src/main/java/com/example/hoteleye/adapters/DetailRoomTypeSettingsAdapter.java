package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.DetailRoomTypeItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DetailRoomTypeSettingsAdapter extends ArrayAdapter<DetailRoomTypeItem> {
    private Context context;
    private List<DetailRoomTypeItem> detailRoomTypeItems;



    public DetailRoomTypeSettingsAdapter(Context context, List<DetailRoomTypeItem> detailRoomTypeItems) {
        super(context, 0, detailRoomTypeItems);
        this.context = context;
        this.detailRoomTypeItems = detailRoomTypeItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_detail_room_type, parent, false);
        DetailRoomTypeItem detailRoomTypeItem = detailRoomTypeItems.get(position);

        TextView name = view.findViewById(R.id.type_name_item_listview_detail);
        Spinner spinner = view.findViewById(R.id.type_spinner_item_listview);

        name.setText(detailRoomTypeItem.getRoom_name());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item,
                        detailRoomTypeItem.getRoom_type());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();

        return view;
    }
}