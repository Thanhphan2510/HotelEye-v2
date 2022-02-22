package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoteleye.adapters.CheckoutAdapter;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.commons.UIUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ChooseServiceItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CheckOut extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton add_service_fab;
    private Button checkout_btn,save_btn;
    private TextView idcard_tv, name_tv, phonenumber_tv, checkin_tv, checkout_tv, roomnumber_tv;
    private EditText note_edt;
    private TextView back_tv;
    private EditText name_service_edt, price_service_edt, note_service_edt;
    private Button  cancel_btn_dialog,add_btn_dialog;
    ArrayList<ChooseServiceItem> chooseServiceItems;
    CheckoutAdapter adapter;
    FirebaseFirestore database;
    Dialog dialog;
    String roomID, customerID, reservationID, invoiceID;

    Map<String, Object> docData;
    String serviceID;
    String surchargeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        init();
    }
    private void init() {
        listView = findViewById(R.id.checkout_listview);
        add_service_fab = findViewById(R.id.add_service_checkout);
        checkout_btn = findViewById(R.id.checkout_btn_checkout);
        idcard_tv = findViewById(R.id.idcard_edt_checkout);
        name_tv = findViewById(R.id.customer_name_edt_checkout);
        phonenumber_tv = findViewById(R.id.phone_number_edt_checkout);
        checkin_tv = findViewById(R.id.checkin_date_edt_checkout);
        checkout_tv = findViewById(R.id.checkout_edt_checkout);
        note_edt = findViewById(R.id.note_edt_checkout);
        save_btn = findViewById(R.id.save_btn_checkout);
        roomnumber_tv = findViewById(R.id.room_edt_checkout);
        back_tv = findViewById(R.id.btnBack_checkout);
        database = FirebaseFirestore.getInstance();

        chooseServiceItems= new ArrayList<>();
        adapter = new CheckoutAdapter(getApplicationContext(), chooseServiceItems);
        listView.setAdapter(adapter);
        UIUtils.setListViewHeightBasedOnItems(listView);
        adapter.notifyDataSetChanged();

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //get data -> show UI

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        checkout_tv.setText(currentDateandTime);

        Intent intent = getIntent();
        final String roomnumber = intent.getStringExtra("roomnumber");
        roomnumber_tv.setText(roomnumber);
        CollectionReference collectionReference = database.collection("room");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot doc : task.getResult()) {
                        int roomnumber_data = new Integer(String.valueOf(doc.get("roomnumber")));
                        if(roomnumber.equals(String.valueOf(roomnumber_data))){
                            roomID = doc.getId();
                            CollectionReference collectionReference = database.collection("reservation");
                            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (final QueryDocumentSnapshot doc : task.getResult()) {
                                            int ischecked_data = new Integer(String.valueOf(doc.get("ischecked")));
                                            if(roomID.equals(String.valueOf(doc.get("room")))&&ischecked_data==1){
                                                reservationID = doc.getId();
                                                getDataListView();
                                                customerID = String.valueOf(doc.get("customer"));
                                                try {
                                                    checkin_tv.setText(MyUtils.covertTimestamptoString2((Timestamp) doc.get("arrivaldate")));
//                                                    checkout_tv.setText(MyUtils.covertTimestamptoString((Timestamp) doc.get("departuredate")));
                                                    note_edt.setText(String.valueOf(doc.get("note")));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                DocumentReference reference = database.collection("customer").document(customerID);
                                                reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot documentSnapshot = task.getResult();

                                                            name_tv.setText(String.valueOf(documentSnapshot.get("name")));
                                                            idcard_tv.setText(String.valueOf(documentSnapshot.get("idcard")));
                                                            phonenumber_tv.setText(String.valueOf(documentSnapshot.get("phonenumber")));

                                                        }
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


        fillNameofListView();

        add_service_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }

    private void saveData() {
        //get invoiceID
        CollectionReference collectionReference = database.collection("invoice");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot doc : task.getResult()) {

                         String reservationID_data = String.valueOf(doc.get("reservation"));
                        if(reservationID.equals(reservationID_data)){
                            invoiceID = doc.getId();
                        }
                    }
                    final ArrayList<ChooseServiceItem> serviceItems = adapter.getchooseServiceItem();

                    //get list surchargeID for invoice
                    CollectionReference collectionReference1 = database.collection("surcharge");
                    collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<ChooseServiceItem> items = serviceItems;
                                Log.e("thanhphan", "onComplete11: items "+items.toString() );
                                for ( QueryDocumentSnapshot doc : task.getResult()) {
                                    if(invoiceID.equals(String.valueOf(doc.get("invoice")))){
                                        surchargeID = doc.getId();
                                        serviceID = String.valueOf(doc.get("service"));
                                        Log.e("thanhphan", "onComplete1: serrviceID:   "+serviceID );
                                        Log.e("thanhphan", "onComplete2: surchargeID:   "+surchargeID );

                                        //update quantity for surcharge
                                        for(int i =0 ;i< serviceItems.size(); i++){
                                            if(serviceID.equals(serviceItems.get(i).getId())&&serviceItems.get(i).getQuantity()>0){
                                                DocumentReference washingtonRef = database.collection("surcharge").document(surchargeID);
                                                washingtonRef
                                                        .update("quantity", serviceItems.get(i).getQuantity())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                            }
                                                        });
                                                items.remove(i);
                                            }
                                            //delete surcharge has quantity = 0
                                           else if(serviceID.equals(serviceItems.get(i).getId())&&(serviceItems.get(i).getQuantity()==0)){
                                                DocumentReference washingtonRef = database.collection("surcharge").document(surchargeID);
                                                washingtonRef
                                                        .delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(CheckOut.this, "Delete!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                items.remove(i);
                                            }
                                        }
                                    }
                                }

                                //add new surcharge
                                Log.e("thanhphan", "onComplete: items"+items.toString() );
                                for (ChooseServiceItem i : items){
                                    if(i.getQuantity()>0){
                                        docData = new HashMap<>();
                                        docData.put("service", i.getId());
                                        docData.put("invoice", invoiceID );
                                        docData.put("quantity", i.getQuantity());
                                        database.collection("surcharge").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.e("thanhphan", "onSuccess: "+"reservation" );
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),"Add Accont Fail",Toast.LENGTH_LONG).show();
                                                Log.e("thanhphan", "onFailure: "+e );
                                            }
                                        });
                                    }

                                }

                            }
                        }
                    });
                }
            }
        });
        Toast.makeText(this, "Đã lưu!", Toast.LENGTH_SHORT).show();
    }

    private void fillNameofListView(){
        CollectionReference collectionReference = database.collection("service");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        if(String.valueOf(doc.get("description")))
                        chooseServiceItems.add(new ChooseServiceItem(doc.getId(),String.valueOf(doc.get("name")),0));
                        adapter.notifyDataSetChanged();
                    }
                    UIUtils.setListViewHeightBasedOnItems(listView);
                }
            }
        });
    }

    private void getDataListView() {


        CollectionReference collectionReference1 = database.collection("invoice");
        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final QueryDocumentSnapshot doc : task.getResult()) {

                        String reservationID_data = String.valueOf(doc.get("reservation"));
                        if(reservationID.equals(reservationID_data)){
                            invoiceID = doc.getId();
                        }
                    }

                    //get surchargeID for invoice
                    if(!invoiceID.equals(null)){
                        CollectionReference collectionReference1 = database.collection("surcharge");
                        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for ( QueryDocumentSnapshot doc : task.getResult()) {
                                        if(invoiceID.equals(String.valueOf(doc.get("invoice")))){
                                            surchargeID = doc.getId();
                                            int quantity = Integer.parseInt(String.valueOf(doc.get("quantity")));
                                            serviceID = String.valueOf(doc.get("service"));
                                            for (ChooseServiceItem i : chooseServiceItems
                                            ) {
                                                if(serviceID.equals(i.getId())){
                                                    i.setQuantity(quantity);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }

                                        }

                                    }
                                }
                            }
                        });
                    }

                }
            }
        });
    }

    private void addDialog() {
        dialog = new Dialog(CheckOut.this);
        dialog.setContentView(R.layout.add_service_dialog);
        name_service_edt = dialog.findViewById(R.id.name_service_dialog);
        price_service_edt = dialog.findViewById(R.id.price_service_dialog);
        note_service_edt = dialog.findViewById(R.id.note_service_dialog);
        cancel_btn_dialog = dialog.findViewById(R.id.cancel_btn_service_dialog);
        add_btn_dialog = dialog.findViewById(R.id.add_btn_service_dialog);
        dialog.show();

        add_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_service_edt.getText().toString().equals("")){
                    Toast.makeText(CheckOut.this, "Nhập tên dịch vụ muốn thêm", Toast.LENGTH_SHORT).show();
                    return;
                }else if (price_service_edt.getText().toString().equals("")){
                    Toast.makeText(CheckOut.this, "Nhập giá tiền dịch vụ muốn thêm", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    chooseServiceItems.add(new ChooseServiceItem(name_service_edt.getText().toString(),1));
                    adapter.notifyDataSetChanged();
                    addSurcharge(name_service_edt.getText().toString(),Integer.parseInt(price_service_edt.getText().toString()));
                    dialog.hide();
                    UIUtils.setListViewHeightBasedOnItems(listView);
                }


            }
        });
        cancel_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
    }

    private void addSurcharge(final String name, int price) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("unitprice",price);
        data.put("description","soft");


        database.collection("service")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        CollectionReference collectionReference = database.collection("service");
                        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        if(name.equals(String.valueOf(doc.get("name")))){
                                            String id = doc.getId();
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("invoice", invoiceID);
                                            data.put("service",id);
                                            data.put("quantity",1);

                                            database.collection("surcharge")
                                                    .add(data)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(CheckOut.this, "Thêm dịch vụ thành công", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                        }


                                    }
                                }
                            }
                        });

                    }
                });

    }

    private void switchView() {
//        saveData();
        Intent intent = new Intent(CheckOut.this, Invoice.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("reservationID",reservationID);
        intent.putExtra("invoiceID",invoiceID);
        intent.putExtra("customerID",customerID);
//        Log.e("thanhphan", "switchView: "+reservationID+"  ||| "+invoiceID+"  ||| "+customerID );
        startActivity(intent);
//        finish();
    }
}
