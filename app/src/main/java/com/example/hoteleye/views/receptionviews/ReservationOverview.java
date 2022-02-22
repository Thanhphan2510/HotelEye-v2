package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hoteleye.adapters.ReservationOverviewAdapter;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ReservationOverviewItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;

public class ReservationOverview extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton add_fab;
    private Button back_tv;
    ArrayList<ReservationOverviewItem> reservationOverviewItems;
    ReservationOverviewAdapter adapter;
    FirebaseFirestore database;
    String TAG = "thanhphan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_overview);
        init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reservation_id = reservationOverviewItems.get(position).getReservation_id();
                int room_name =  reservationOverviewItems.get(position).getRoom_name();
                Intent intent = new Intent(ReservationOverview.this, ReservationFinal.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("reservation_id",reservation_id);
                intent.putExtra("tag","2");
                intent.putExtra("roomnumber",room_name+"");

                startActivity(intent);
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void init() {
        listView = findViewById(R.id.reservation_overview_listview);
        add_fab = findViewById(R.id.add_reservation_overview_fab);
        database = FirebaseFirestore.getInstance();
        back_tv = findViewById(R.id.btnBack_reservation_overview);
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reservationOverviewItems = new ArrayList<>();
//        reservationOverviewItems.add(new ReservationOverviewItem(101, "01/10/2020",
//                "02/10/2020","08/10/2020","Thành Phan",200000,"Không"));

        CollectionReference collectionReference = database.collection("reservation");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot doc : task.getResult()) {
                        Integer ischecked = new Integer(String.valueOf(doc.get("ischecked")));
                        if(ischecked == 0){
                            final String roomID = String.valueOf(doc.get("room"));
                            final String customerID = String.valueOf(doc.get("customer"));
                            final ReservationOverviewItem item = new ReservationOverviewItem();
                            item.setReservation_id(doc.getId());


                            try {
                                item.setReservation_date(MyUtils.covertTimestamptoString((Timestamp) doc.get("reservationdate")));
                                item.setArrival_date(MyUtils.covertTimestamptoString((Timestamp) doc.get("arrivaldate")));
                                item.setDeparture_date(MyUtils.covertTimestamptoString((Timestamp) doc.get("departuredate")));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            item.setDeposit(new Float(String.valueOf(doc.get("deposit"))));
                            item.setNote((String.valueOf(doc.get("note"))));

                            DocumentReference docRef = database.collection("room").document(roomID);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.e("thanhphan", "DocumentSnapshot data: " + document.getData());
                                            int room_name = new Integer(String.valueOf(document.get("roomnumber"))) ;
                                            item.setRoom_name(room_name);
                                            DocumentReference docRef = database.collection("customer").document(customerID);
                                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        String customer_name = String.valueOf(document.get("name"));
                                                        item.setCustomer_name(customer_name);
                                                        reservationOverviewItems.add(item);
                                                        adapter.notifyDataSetChanged();



                                                    }
                                                }
                                            });
                                        } else {
                                            Log.e("thanhphan", "No such document");
                                        }
                                    } else {
                                        Log.e("thanhphan", "get failed with ", task.getException());
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });



        adapter = new ReservationOverviewAdapter(getApplicationContext(), reservationOverviewItems);
        listView.setAdapter(adapter);

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }
    private void switchView() {
        Intent intent = new Intent(ReservationOverview.this, ReservationCustomer.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
//        finish();
    }
}
