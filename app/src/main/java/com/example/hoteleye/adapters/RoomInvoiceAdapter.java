package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.RoomInvoiceItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoomInvoiceAdapter extends ArrayAdapter<RoomInvoiceItem> {
    private Context context;
    private List<RoomInvoiceItem> roomInvoiceItems;


    public RoomInvoiceAdapter(Context context, List<RoomInvoiceItem> roomInvoiceItems) {
        super(context, 0, roomInvoiceItems);
        this.context = context;
        this.roomInvoiceItems = roomInvoiceItems;
    }

    public void setRoomInvoiceItems(List<RoomInvoiceItem> roomInvoiceItems) {
        this.roomInvoiceItems = roomInvoiceItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_invoice_room, parent, false);
        RoomInvoiceItem roomInvoiceItem = roomInvoiceItems.get(position);

        TextView id = view.findViewById(R.id.id_item_listview_invoice_room);
        TextView room = view.findViewById(R.id.room_item_listview_invoice_room);
        TextView intime = view.findViewById(R.id.intime_item_listview_invoice_room);
        TextView outtime = view.findViewById(R.id.outtime_item_listview_invoice_room);
        TextView amount = view.findViewById(R.id.amount_item_listview_invoice_room);

        id.setText(roomInvoiceItem.getId()+"");
        room.setText(roomInvoiceItem.getRoom_name());
        intime.setText(roomInvoiceItem.getCheckin_date());
        outtime.setText(roomInvoiceItem.getCheckout_date());
        amount.setText(MyUtils.convertToCurrencyFormat(roomInvoiceItem.getAmout()));


        return view;
    }
}
