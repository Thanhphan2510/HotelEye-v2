package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ViewColleagueItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewColleagueAdapter extends ArrayAdapter<ViewColleagueItem> {
    private Context context;
    private List<ViewColleagueItem> viewColleagueItems;


    public ViewColleagueAdapter(Context context, List<ViewColleagueItem> viewColleagueItems) {
        super(context, 0, viewColleagueItems);
        this.context = context;
        this.viewColleagueItems = viewColleagueItems;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_view_colleague, parent, false);
        ViewColleagueItem viewColleagueItem = viewColleagueItems.get(position);

        TextView id = view.findViewById(R.id.id_item_listview_view_colleague);
        final TextView name = view.findViewById(R.id.name_item_listview_view_colleague);
        TextView dateOfBirth = view.findViewById(R.id.date_of_birth_item_listview_view_colleague);
        TextView phoneNumber = view.findViewById(R.id.phone_number_item_listview_view_colleague);
        Button detail = view.findViewById(R.id.detail_item_item_listview_view_colleague);

        id.setText(viewColleagueItem.getId()+"");
        name.setText(viewColleagueItem.getName());
        dateOfBirth.setText(viewColleagueItem.getDate());
        phoneNumber.setText(viewColleagueItem.getPhone_number());

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Detail "+name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

