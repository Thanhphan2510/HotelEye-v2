package com.example.hoteleye.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hoteleye.interfaces.ItemClickListener;
import com.example.hoteleye.R;
import com.example.hoteleye.views.receptionviews.CheckOut;
import com.example.hoteleye.viewmodels.RoomNameItem;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ManagerHomeAdapter extends RecyclerView.Adapter<ManagerHomeAdapter.ViewHolder> {
    Context context;
    List<RoomNameItem> roomNameItems;
    int index = -1;

    public ManagerHomeAdapter(Context context, List<RoomNameItem> roomNameItems) {
        this.context = context;
        this.roomNameItems = roomNameItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_recyclerview_home, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }
    @Override
    public  void onBindViewHolder(final ManagerHomeAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.iconName.setText(roomNameItems.get(position).getName());
        if(roomNameItems.get(position).getRoom_status() == 1){ //xanh
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#233BBD"));
        }
        if(roomNameItems.get(position).getRoom_status() == 2){ //do
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFF32929"));
        }
        if(roomNameItems.get(position).getRoom_status() == 3){ //vang
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFF1F153"));
        }
        if(roomNameItems.get(position).getRoom_status() == 4){ //da cam
            viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFAB40"));
        }

/*
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#FFF1F153"));
//                else
//                    Toast.makeText(context, " "+listData.get(position), Toast.LENGTH_SHORT).show();
                else if(viewHolder.cardView.getCardBackgroundColor().getDefaultColor()==Color.parseColor("#233BBD")){
                    Intent intent = new Intent(view.getContext(), CheckIn.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
                else if(viewHolder.cardView.getCardBackgroundColor().getDefaultColor()==Color.parseColor("#FFF32929")){
                    Intent intent = new Intent(view.getContext(), CheckOut.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }else if(viewHolder.cardView.getCardBackgroundColor().getDefaultColor()==Color.parseColor("#FFF1F153")) {
                    viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#233BBD"));
                }

            }
        });
*/
       }

    @Override
    public int getItemCount() {
        return roomNameItems.size();
    }
    private void switchViewAdd() {
        Intent intent = new Intent(context, CheckOut.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);
//        finish();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView iconName;
        CardView cardView;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
//            icon = (ImageView) itemView.findViewById(R.id.icon);
            iconName = (TextView) itemView.findViewById(R.id.name_item_recyclerview);
            cardView = itemView.findViewById(R.id.bg_item);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              cardView.setCardBackgroundColor(Color.parseColor("#FFF32929"));
//              iconName.setText("lol");
//              notifyDataSetChanged();
//
//            }
//});

//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);

        }
/*
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }

 */
    }
}
