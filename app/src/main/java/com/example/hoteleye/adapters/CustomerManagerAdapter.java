package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.CustomerManagerItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomerManagerAdapter extends ArrayAdapter<CustomerManagerItem> {
    private Context context;
    private List<CustomerManagerItem> customerManagerItems;


    public CustomerManagerAdapter(Context context, List<CustomerManagerItem> customerManagerItems) {
        super(context, 0, customerManagerItems);
        this.context = context;
        this.customerManagerItems = customerManagerItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_customer_manager, parent, false);
        CustomerManagerItem customerManagerItem = customerManagerItems.get(position);

        TextView idcard = view.findViewById(R.id.idcard_item_listview_customer_manager);
        TextView name = view.findViewById(R.id.name_item_listview_customer_manager);
        TextView date = view.findViewById(R.id.date_item_listview_customer_manager);
        TextView phone_number = view.findViewById(R.id.phone_number_item_listview_customer_manager);
        TextView dicount = view.findViewById(R.id.discount_item_listview_customer_manager);
        TextView note = view.findViewById(R.id.note_item_listview_customer_manager);


        idcard.setText(customerManagerItem.getIdCard()+"");
        name.setText(customerManagerItem.getName());
        date.setText(customerManagerItem.getDate());
        phone_number.setText(customerManagerItem.getPhone_number());
        dicount.setText(customerManagerItem.getDiscount()+"");
        note.setText(customerManagerItem.getNote());

        return view;
    }
}

