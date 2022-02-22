package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.FurnitureItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FurnitureSettingsAdapter extends ArrayAdapter<FurnitureItem> {
    private Context context;
    private List<FurnitureItem> furnitureItems;


    public FurnitureSettingsAdapter(Context context, List<FurnitureItem> furnitureItems) {
        super(context, 0, furnitureItems);
        this.context = context;
        this.furnitureItems = furnitureItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_furniture_settings, parent, false);
        FurnitureItem furnitureItem = furnitureItems.get(position);

        TextView name = view.findViewById(R.id.name_item_listview_furniture);
        TextView unity = view.findViewById(R.id.unity_item_listview_furniture);
        TextView price = view.findViewById(R.id.price_item_listview_furniture);
        TextView description = view.findViewById(R.id.description_item_listview_furniture);


        name.setText(furnitureItem.getName());
        unity.setText(furnitureItem.getUnity());
        price.setText(""+furnitureItem.getPrice());
        description.setText(furnitureItem.getDescription());

        return view;
    }
}
