package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ServiceItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServiceSettingsAdapter extends ArrayAdapter<ServiceItem> {
    private Context context;
    private List<ServiceItem> serviceItems;


    public ServiceSettingsAdapter(Context context, List<ServiceItem> serviceItems) {
        super(context, 0, serviceItems);
        this.context = context;
        this.serviceItems = serviceItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_service_settings, parent, false);
        ServiceItem serviceItem = serviceItems.get(position);

        TextView name = view.findViewById(R.id.name_item_listview_service);
        TextView type = view.findViewById(R.id.type_item_listview_service);
        TextView unity = view.findViewById(R.id.unity_item_listview_service);
        TextView price = view.findViewById(R.id.price_item_listview_service);
        TextView description = view.findViewById(R.id.description_item_listview_service);


        name.setText(serviceItem.getName());
        type.setText(serviceItem.getType());
        unity.setText(serviceItem.getUnity());
        price.setText(""+serviceItem.getUnit_price());
        description.setText(serviceItem.getDescription());

        return view;
    }
}
