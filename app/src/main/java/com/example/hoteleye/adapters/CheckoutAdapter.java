package com.example.hoteleye.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ChooseServiceItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CheckoutAdapter extends ArrayAdapter<ChooseServiceItem> {
    private Context context;
    private List<ChooseServiceItem> chooseServiceItems;


    public CheckoutAdapter(Context context, List<ChooseServiceItem> chooseServiceItems) {
        super(context, 0, chooseServiceItems);
        this.context = context;
        this.chooseServiceItems = chooseServiceItems;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_checkout, parent, false);
        final ChooseServiceItem chooseServiceItem = chooseServiceItems.get(position);

        TextView name = view.findViewById(R.id.name_item_listview_checkout);
        final EditText quantity = view.findViewById(R.id.quantity_edt_item_listview_checkout);
        Button minus = view.findViewById(R.id.minus_btn_item_listview_checkout);
        Button plus = view.findViewById(R.id.plus_btn_item_listview_checkout);

        name.setText(chooseServiceItem.getName());
        quantity.setText(chooseServiceItem.getQuantity()+"");

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chooseServiceItem.getQuantity()>0){
                    chooseServiceItem.setQuantity(chooseServiceItem.getQuantity()-1);
                    notifyDataSetChanged();
                }
                else if (chooseServiceItem.getQuantity()==0){
                    chooseServiceItem.setQuantity(0);
                    notifyDataSetChanged();
                }

            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    chooseServiceItem.setQuantity(Integer.parseInt(quantity.getText().toString()));
                    notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("thanhphan", "onClick: plus" );
                chooseServiceItem.setQuantity(chooseServiceItem.getQuantity()+1);
                notifyDataSetChanged();
            }
        });

        quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseServiceItem.setQuantity(Integer.parseInt(quantity.getText().toString()));
                notifyDataSetChanged();
            }
        });


        return view;
    }
    public ArrayList<ChooseServiceItem> getchooseServiceItem(){
        ArrayList<ChooseServiceItem> serviceItems = new ArrayList<>();
        for (ChooseServiceItem i : chooseServiceItems){
//            if(i.getQuantity()>0){
                serviceItems.add(i);
//            }
        }
        return serviceItems;
    }

}
