package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoteleye.adapters.ReservationRoomAdapter;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.RoomNameItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservationRoom extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button next_btn;
    private TextView back_tv;
    List<RoomNameItem> roomNameItems;
    ReservationRoomAdapter adapter;
    FirebaseFirestore database;
    String checkin_str,checkout_str,idcard, customer_name, phone_number, deposit, note;
    String roomID;
     RoomNameItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_room);
        init();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }
    private void init() {
        recyclerView = findViewById(R.id.rerservation_room_receyclerview);
        next_btn = findViewById(R.id.next_btn_reservation_room);
        back_tv = findViewById(R.id.btnBack_reservation_room);
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        roomNameItems = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        checkin_str = intent.getStringExtra("checkin");
        checkout_str = intent.getStringExtra("checkout");
        idcard = intent.getStringExtra("idcard");
        customer_name = intent.getStringExtra("customer_name");
        phone_number = intent.getStringExtra("phone_number");
        deposit = intent.getStringExtra("deposit");
        note = intent.getStringExtra("note");

        //thêm danh sách phòng
//        for(int i =101; i <107;i++){
//            roomNameItems.add(new RoomNameItem(""+i));
//
//        }
    //get all room -> set booked room & unbooked room

        //get data DB
        CollectionReference collectionReference = database.collection("room");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for ( QueryDocumentSnapshot doc : task.getResult()) {
                        final RoomNameItem item =  new
                                RoomNameItem(String.valueOf(doc.get("roomnumber")),
                                0);

                        item.setRoomID(doc.getId());
                        roomID = doc.getId();
                        Log.e("roomID", "onComplete: "+roomID );

                        CollectionReference collectionReference = database.collection("reservation");
                        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    boolean check = true;
                                    for ( QueryDocumentSnapshot doc : task.getResult()) {
                                        try {
                                            Log.e("thanhphan4", "onComplete: "+(Integer.parseInt(String.valueOf(doc.get("ischecked"))))+" || "+item.getRoomID()+" || "+ String.valueOf(doc.get("room")));

                                            if(((Integer.parseInt(String.valueOf(doc.get("ischecked")))==0)||
                                            (Integer.parseInt(String.valueOf(doc.get("ischecked")))==0))
                                                    &&item.getRoomID().equals(String.valueOf(doc.get("room")))){

                                                if((MyUtils.covertStringtoDate2(checkin_str)
                                                        .before(MyUtils.covertStringtoDate2(
                                                                MyUtils.covertTimestamptoString2((Timestamp) doc.get("arrivaldate"))))&&
                                                        MyUtils.covertStringtoDate2(checkout_str)
                                                                .after(MyUtils.covertStringtoDate2(
                                                                        MyUtils.covertTimestamptoString2((Timestamp) doc.get("arrivaldate")))))
                                                        ||
                                                        (MyUtils.covertStringtoDate2(checkin_str)
                                                                .before(MyUtils.covertStringtoDate2(
                                                                        MyUtils.covertTimestamptoString2((Timestamp) doc.get("departuredate"))))&&
                                                                MyUtils.covertStringtoDate2(checkout_str)
                                                                        .after(MyUtils.covertStringtoDate2(
                                                                                MyUtils.covertTimestamptoString2((Timestamp) doc.get("departuredate")))))
                                                        ||
                                                        (MyUtils.covertStringtoDate2(checkin_str)
                                                                .after(MyUtils.covertStringtoDate2(
                                                                        MyUtils.covertTimestamptoString2((Timestamp) doc.get("arrivaldate"))))&&
                                                                MyUtils.covertStringtoDate2(checkout_str)
                                                                        .before(MyUtils.covertStringtoDate2(
                                                                                MyUtils.covertTimestamptoString2((Timestamp) doc.get("departuredate")))))
                                                ){
//                                                    check = false;
                                                    item.setRoom_status(-1 );

                                                    Log.e("item false", "onComplete: "+item.toString());
                                                }

//
//                                                if(check==true){
//                                                    item.setRoom_status(0);
//                                                    Log.e("item ok", "onComplete: "+item.toString());
//                                                }else{
//
//                                                    Log.e("item false", "onComplete: "+item.toString());
////                                                        check = true;
//                                                }

                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }//end for
                                    roomNameItems.add(item);
                                    Collections.sort(roomNameItems);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });

                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });



//        roomNameItems.add(new RoomNameItem("101",0));
//        roomNameItems.add(new RoomNameItem("102",0));
//        roomNameItems.add(new RoomNameItem("103",0));
//        roomNameItems.add(new RoomNameItem("104",0));
//        roomNameItems.add(new RoomNameItem("105",0));
//        roomNameItems.add(new RoomNameItem("106",-1));
//        roomNameItems.add(new RoomNameItem("107",0));
//        roomNameItems.add(new RoomNameItem("201",0));
//        roomNameItems.add(new RoomNameItem("202",0));
//        roomNameItems.add(new RoomNameItem("203",0));
//        roomNameItems.add(new RoomNameItem("204",0));
//        roomNameItems.add(new RoomNameItem("205",0));
//        roomNameItems.add(new RoomNameItem("206",0));
//        roomNameItems.add(new RoomNameItem("207",0));
//        roomNameItems.add(new RoomNameItem("301",0));
//        roomNameItems.add(new RoomNameItem("302",0));
//        roomNameItems.add(new RoomNameItem("303",0));
//        roomNameItems.add(new RoomNameItem("304",0));
//        roomNameItems.add(new RoomNameItem("305",0));
//        roomNameItems.add(new RoomNameItem("306",0));
//        roomNameItems.add(new RoomNameItem("307",0));


        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ReservationRoomAdapter(getApplicationContext(), roomNameItems);
        recyclerView.setAdapter(adapter);

    }



    private void switchView() {
        Intent intent = new Intent(ReservationRoom.this, ReservationFinal.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra("idcard",idcard);
        intent.putExtra("tag","1");
        intent.putExtra("customer_name",customer_name);
        intent.putExtra("phone_number",phone_number);
        intent.putExtra("checkin",checkin_str);
        intent.putExtra("checkout",checkout_str);
        intent.putExtra("deposit",deposit);
        intent.putExtra("note",note);
        intent.putExtra("room", (Serializable) adapter.getSelect_roomNameItems());
        startActivity(intent);

    }
}
