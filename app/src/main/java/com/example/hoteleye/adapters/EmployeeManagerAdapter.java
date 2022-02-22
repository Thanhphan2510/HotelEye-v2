package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.EmployeeManagerItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EmployeeManagerAdapter extends ArrayAdapter<EmployeeManagerItem> {
    private Context context;
    private List<EmployeeManagerItem> employeeManagerItems;


    public EmployeeManagerAdapter(Context context, List<EmployeeManagerItem> employeeManagerItems) {
        super(context, 0, employeeManagerItems);
        this.context = context;
        this.employeeManagerItems = employeeManagerItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_employee_manager, parent, false);
        EmployeeManagerItem employeeManagerItem = employeeManagerItems.get(position);

        TextView idcard = view.findViewById(R.id.idcard_item_listview_employee_manager);
        TextView name = view.findViewById(R.id.name_item_listview_employee_manager);
        TextView date = view.findViewById(R.id.date_item_listview_employee_manager);
        TextView phone_number = view.findViewById(R.id.phone_number_item_listview_employee_manager);
        TextView account = view.findViewById(R.id.account_item_listview_employee_manager);



        idcard.setText(employeeManagerItem.getIdCard()+"");
        name.setText(employeeManagerItem.getName());
        date.setText(employeeManagerItem.getDate());
        phone_number.setText(employeeManagerItem.getPhoneNumber());
        account.setText(employeeManagerItem.getAccount());

        return view;
    }
}
