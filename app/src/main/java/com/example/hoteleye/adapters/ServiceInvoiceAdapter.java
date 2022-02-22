package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ServiceInvoiceItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServiceInvoiceAdapter extends ArrayAdapter<ServiceInvoiceItem> {
    private Context context;
    private List<ServiceInvoiceItem> serviceInvoiceItems;


    public ServiceInvoiceAdapter(Context context, List<ServiceInvoiceItem> serviceInvoiceItems) {
        super(context, 0, serviceInvoiceItems);
        this.context = context;
        this.serviceInvoiceItems = serviceInvoiceItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_invoice_service, parent, false);
        ServiceInvoiceItem serviceInvoiceItem = serviceInvoiceItems.get(position);

        TextView id = view.findViewById(R.id.id_item_listview_invoice_service);
        TextView name = view.findViewById(R.id.name_item_listview_invoice_service);
        TextView quantity = view.findViewById(R.id.quantity_item_listview_invoice_service);
        TextView price = view.findViewById(R.id.price_item_listview_invoice_service);
        TextView amount = view.findViewById(R.id.amount_item_listview_invoice_service);

        id.setText(serviceInvoiceItem.getId()+"");
        name.setText(serviceInvoiceItem.getName());
        quantity.setText(serviceInvoiceItem.getQuantity()+"");
        price.setText(MyUtils.convertToCurrencyFormat(serviceInvoiceItem.getPrice()));
        amount.setText(MyUtils.convertToCurrencyFormat(serviceInvoiceItem.getAmout()));

        return view;
    }


}
