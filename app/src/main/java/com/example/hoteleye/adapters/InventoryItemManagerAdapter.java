package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.InventoryItemItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InventoryItemManagerAdapter extends ArrayAdapter<InventoryItemItem> {
    private Context context;
    private List<InventoryItemItem> inventoryItemItems;


    public InventoryItemManagerAdapter(Context context, List<InventoryItemItem> inventoryItemItems) {
        super(context, 0, inventoryItemItems);
        this.context = context;
        this.inventoryItemItems = inventoryItemItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_inventoryitem_manager, parent, false);
        InventoryItemItem inventoryItemItem = inventoryItemItems.get(position);

        TextView name = view.findViewById(R.id.name_item_listview_inventoryitem_manager);
        TextView quantity = view.findViewById(R.id.quantity_item_listview_inventoryitem_manager);
        TextView price = view.findViewById(R.id.price_item_listview_inventoryitem_manager);
        TextView date = view.findViewById(R.id.date_item_listview_inventoryitem_manager);
        TextView description = view.findViewById(R.id.description_item_listview_inventoryitem_manager);


        name.setText(inventoryItemItem.getName());
        quantity.setText(inventoryItemItem.getQuantity()+"");
        price.setText(inventoryItemItem.getPrice()+"");
        date.setText(inventoryItemItem.getDate());
        description.setText(inventoryItemItem.getDescription());
        return view;
    }
}
