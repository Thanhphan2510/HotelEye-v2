package com.example.hoteleye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ReservationOverviewItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ReservationOverviewAdapter extends ArrayAdapter<ReservationOverviewItem> {
    private Context context;
    private List<ReservationOverviewItem> reservationOverviews;


    public ReservationOverviewAdapter(Context context, List<ReservationOverviewItem> reservationOverviewItems) {
        super(context, 0, reservationOverviewItems);
        this.context = context;
        this.reservationOverviews = reservationOverviewItems;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview_reservation_overview, parent, false);
        ReservationOverviewItem reservationOverviewItem = reservationOverviews.get(position);

        TextView reservation_date = view.findViewById(R.id.reservation_date_item_listview_overview_reservation);
        TextView arrival_date = view.findViewById(R.id.checkin_date_item_listview_overview_reservation);
        TextView departure_date = view.findViewById(R.id.checkout_date_item_listview_overview_reservation);

        TextView room_name = view.findViewById(R.id.room_name_item_listview_overview_reservation);
        TextView customer_name = view.findViewById(R.id.customer_name_item_listview_overview_reservation);
        TextView deposit = view.findViewById(R.id.deposit_item_listview_overview_reservation);
        TextView note = view.findViewById(R.id.note_item_listview_overview_reservation);

        reservation_date.setText(reservationOverviewItem.getReservation_date());
        arrival_date.setText(reservationOverviewItem.getArrival_date()  );
        departure_date.setText(reservationOverviewItem.getDeparture_date());
        room_name.setText(reservationOverviewItem.getRoom_name()+"");
        customer_name.setText(reservationOverviewItem.getCustomer_name());
        deposit.setText(reservationOverviewItem.getDeposit()+"");
        note.setText(reservationOverviewItem.getNote());

        return view;

    }


}
