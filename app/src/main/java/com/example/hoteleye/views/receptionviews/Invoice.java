package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoteleye.adapters.RoomInvoiceAdapter;
import com.example.hoteleye.adapters.ServiceInvoiceAdapter;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.commons.UIUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.RoomInvoiceItem;
import com.example.hoteleye.viewmodels.ServiceInvoiceItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Invoice extends AppCompatActivity {
    private ListView listView_room, listView_service;
    private Button next_btn;
    private TextView customer_name_tv, idcard_tv, invoice_date_tv, room_price_tv, service_price_tv, total_price_tv, total_tv, discount_tv;
    ArrayList<RoomInvoiceItem> roomInvoiceItems = new ArrayList<>();;
    ArrayList<ServiceInvoiceItem> serviceInvoiceItems = new ArrayList<>();
    RoomInvoiceAdapter adapter_room;
    private TextView back_tv;
    ServiceInvoiceAdapter  adapter_service;
    FirebaseFirestore database;
    String reservationID, invoiceID,customerID, roomID, roomTypeID, serviceID, surchargeID, room_price_Str, employeeID;
    String name, price;
    String hour_price_Str, night_price_Str, open_price_Str;
    String quantity;
    String roomname;
    float service_amout;
    float amout = 0;
    int qua;
    String checkin_Str, checkout_Str;
    ArrayList<String> surchargeIDs = new ArrayList<>();
    int stt = 1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        init();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            saveInvoice();
            }

        });
    }
    private void init() {
        listView_room = findViewById(R.id.room_listview_invoice);
        listView_service = findViewById(R.id.service_listview_invoice);
        next_btn = findViewById(R.id.next_btn_invoice);
        customer_name_tv = findViewById(R.id.customer_name_tv_invoice);
        idcard_tv = findViewById(R.id.idcard_tv_invoice);
        invoice_date_tv = findViewById(R.id.create_invoice_date_tv_invoice);
        room_price_tv = findViewById(R.id.room_price_tv_invoice);
        service_price_tv = findViewById(R.id.serivce_price_tv_invoice);
        total_price_tv = findViewById(R.id.total_price_tv_invoice);
        discount_tv = findViewById(R.id.discount_tv_invoice);
        total_tv = findViewById(R.id.total_iv_invoice);
        database = FirebaseFirestore.getInstance();
        back_tv = findViewById(R.id.btnBack_invoice);
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        reservationID = intent.getStringExtra("reservationID");
        invoiceID = intent.getStringExtra("invoiceID");
        customerID = intent.getStringExtra("customerID");
        Log.e("thanhphan2", "switchView: "+reservationID+"  ||| "+invoiceID+"  ||| "+customerID );

        filldata();
        fillroomlist();
        fillservicelist();

        total_price_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getdiscount();

            }
        });

    }

    private void getdiscount() {
        DocumentReference docRef = database.collection("customer").document(customerID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                     float di = Float.parseFloat(String.valueOf(document.get("discount"))) ;
                     discount_tv.setText(String.valueOf(di));
                     float i = Float.parseFloat(MyUtils.convertStringToFloat(total_price_tv.getText().toString()));
                     total_tv.setText(MyUtils.convertToCurrencyFormat1(i-i*di));
                     total_price_tv.setText(MyUtils.convertToCurrencyFormat(
                             Float.parseFloat(total_price_tv.getText().toString().replace(",",""))));
                    }
                }}});
    }

    private void fillservicelist() {
        CollectionReference collectionReference = database.collection("surcharge");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {

                        if(invoiceID.equals(String.valueOf(doc.get("invoice")))){
                            surchargeID = doc.getId();
                            quantity = (String.valueOf(doc.get("quantity")));
                             final int qua =  Integer.parseInt(quantity);
                            Log.e("LOlll", "onComplete: "+quantity );
                            String serviceID = (String.valueOf(doc.get("service")));
                            DocumentReference docRef = database.collection("service").document(serviceID);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            name = (String.valueOf(document.get("name")));
                                            price = (String.valueOf(document.get("unitprice")));
                                            service_amout += qua* Float.parseFloat(price);
                                            serviceInvoiceItems.add(
                                                    new ServiceInvoiceItem(stt,name,
                                                            qua, Float.parseFloat(price),
                                                            qua* Float.parseFloat(price)));
                                            Log.e("thanhphan", "onComplete: "+serviceInvoiceItems.toString() );
                                            adapter_service = new ServiceInvoiceAdapter(getApplicationContext(), serviceInvoiceItems);
                                            listView_service.setAdapter(adapter_service);
                                            UIUtils.setListViewHeightBasedOnItems(listView_service);
                                            stt++;
                                            service_price_tv.setText(MyUtils.convertToCurrencyFormat(service_amout));
                                            amout += qua* Float.parseFloat(price);
                                            total_price_tv.setText(String.valueOf(amout));

                                        }
                                    }}});
                        }
                    }

                }
            }
        });
    }
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            Intent intent = new Intent(Invoice.this, CheckOut.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(intent);
//            finish();
//        }
//        return false;
//    }

    private void fillroomlist() {
        DocumentReference docRef = database.collection("reservation").document(reservationID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        roomID = (String.valueOf(document.get("room")));
                        Log.e("thanphan", "fillroomlist: "+roomID );
                        checkin_Str = (String.valueOf(document.get("idcard")));
                        try {
                            checkin_Str = (MyUtils.covertTimestamptoString2((Timestamp) document.get("arrivaldate")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DocumentReference docRef1 = database.collection("room").document(roomID);
                        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        roomTypeID = (String.valueOf(document.get("type")));
                                        roomname = (String.valueOf(document.get("roomnumber")));
                                        final RoomInvoiceItem item = new RoomInvoiceItem();
                                        DocumentReference docRef = database.collection("roomtype").document(roomTypeID);
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
//                                                        room_price_Str = (String.valueOf(document.get("room")));
                                                        open_price_Str = String.valueOf(document.get("openprice"));
                                                        hour_price_Str = String.valueOf(document.get("hourprice"));
                                                        night_price_Str = String.valueOf(document.get("nightprice"));
                                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
                                                        String currentDateandTime = sdf.format(new Date());
                                                        checkout_Str = currentDateandTime;

                                                        item.setId(1);
                                                        item.setRoom_name(roomname);
                                                        item.setCheckin_date(checkin_Str);
                                                        item.setCheckout_date(checkout_Str);
                                                        item.setAmout( Float.parseFloat(night_price_Str));

//                                                        roomInvoiceItems.add(item);
                                                        float gia = (float)MyUtils.calculateRoomPrice(open_price_Str, hour_price_Str, night_price_Str,
                                                                checkin_Str,checkout_Str);

//                                                        room_price_tv.setText(String.valueOf(Float.parseFloat(night_price_Str)));
                                                        room_price_tv.setText(MyUtils.convertToCurrencyFormat(gia));
                                                        roomInvoiceItems.add(
                                                                new RoomInvoiceItem(1,roomname,checkin_Str,checkout_Str,
                                                                        gia ));
                                                        Log.e("thanhphn", "onComplete:lolll "+roomInvoiceItems.toString() );
                                                        adapter_room = new RoomInvoiceAdapter(getApplicationContext(), roomInvoiceItems);
                                                        listView_room.setAdapter(adapter_room);
                                                        UIUtils.setListViewHeightBasedOnItems(listView_room);
                                                        amout += Float.parseFloat(MyUtils.convertStringToFloat(room_price_tv.getText().toString()));
                                                        total_price_tv.setText(String.valueOf(amout));
                                                    }
                                                }}});
                                    }
                                }}});
                    }
                }}});

    }

    private void filldata() {
        DocumentReference docRef = database.collection("customer").document(customerID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        customer_name_tv.setText(String.valueOf(document.get("name")));
                        idcard_tv.setText(String.valueOf(document.get("idcard")));
                    }

            }}});

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        invoice_date_tv.setText(currentDateandTime);

        }


    private void switchView() {
        Intent intent = new Intent(Invoice.this, ReceptionHome.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
    private void saveInvoice(){
        String uid = user.getUid();
        final String email = user.getEmail();
        Log.e("user firebase", "saveInvoice: "+uid+" || "+email );

        //save infor invoice
        CollectionReference collectionReference = database.collection("account");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if(email.equals(String.valueOf(doc.get("username")))){
                            final String accountID = doc.getId();
                            Log.e("hihiii", "onComplete: accountID   "+accountID );
                            CollectionReference collectionReference1 = database.collection("employee");
                            collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            if(accountID.equals(String.valueOf(doc.get("account")))){
                                                employeeID = doc.getId();
                                                Log.e("hihiii", "onComplete: employeeID   "+employeeID );
                                                DocumentReference docRef = database.collection("invoice")
                                                        .document(invoiceID);

                                                Map<String,Object> updates = new HashMap<>();
                                                updates.put("employee",employeeID);
                                                updates.put("paymentamount",Float.parseFloat(MyUtils.convertStringToFloat( total_tv.getText().toString())));
                                                updates.put("discount",Float.parseFloat( discount_tv.getText().toString()));


                                                docRef.update(updates)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(Invoice.this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }

                }
            }
        });
        //update status ischeck reservation
        DocumentReference washingtonRef1 = database.collection("reservation").document(reservationID);
        washingtonRef1
                .update("ischecked", 3)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });

        //update status room
        DocumentReference washingtonRef = database.collection("room").document(roomID);
        washingtonRef
                .update("status", 3)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        switchView();
                    }
                });
    }
}
