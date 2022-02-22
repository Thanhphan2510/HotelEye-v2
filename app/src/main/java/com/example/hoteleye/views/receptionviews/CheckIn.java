package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CheckIn extends AppCompatActivity {
    private Button checkin_btn;
    private EditText idcard_edt, name_edt, phonenumber_edt, checkin_edt, deposit_edt, note_edt, room_edt;
    private TextView create_customer_tv;
    private TextView back_tv;
    String checkin_str,idcard, customer_name, phone_number, deposit, note, room="";

    FirebaseFirestore database;
    String customerID, roomID, reservationID;
    Map<String, Object> docData;
    private static final int REQUEST_CODE = 0x9345;
    int checkIDCard = 0;
    int ischecked_data = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        init();
        checkin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkin();
            }
        });
        create_customer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckIn.this, CustomerAdd.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("ReservationCustomer","Checkin");
                startActivityForResult(intent,REQUEST_CODE );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                idcard_edt.setText(String.valueOf(data.getStringExtra("idcard"))) ;
                name_edt.setText(String.valueOf( data.getStringExtra("customer_name")));
                phonenumber_edt.setText(String.valueOf( data.getStringExtra("phone_number")));
            }
        }
    }

    private void init() {
        checkin_btn = findViewById(R.id.next_btn_checkin);
        idcard_edt = findViewById(R.id.idcard_edt_checkin);
        name_edt = findViewById(R.id.name_edt_checkin);
        phonenumber_edt = findViewById(R.id.phone_number_ed_checkin);
        checkin_edt = findViewById(R.id.checkin_edt_checkin);
        deposit_edt = findViewById(R.id.deposit_edt_checkin);
        note_edt = findViewById(R.id.note_edt_checkin);
        room_edt = findViewById(R.id.room_edt_checkin);
        create_customer_tv = findViewById(R.id.create_customer_tv_checkin);
        back_tv = findViewById(R.id.btnBack_checkin);
        checkin_edt.setKeyListener(null);
        room_edt.setEnabled(false);
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent1 = getIntent();
        String id_card = String.valueOf(intent1.getStringExtra("idcard"));

        if(!id_card.equals("null")){
            idcard_edt.setText(String.valueOf(intent1.getStringExtra("idcard"))) ;
            name_edt.setText(String.valueOf( intent1.getStringExtra("customer_name")));
            phonenumber_edt.setText(String.valueOf( intent1.getStringExtra("phone_number")));
        }


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        checkin_edt.setText(currentDateandTime);
        database = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        room = intent.getStringExtra("roomnumber");
        room_edt.setText(room);

        idcard_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIDCard = 0;
                CollectionReference collectionReference2 = database.collection("customer");
                collectionReference2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                        if(String.valueOf(doc.get("description")))
                                if(idcard_edt.getText().toString().equals(String.valueOf(doc.get("idcard")))){
                                    checkIDCard = 1;
                                }
                            }

                        }
                    }
                });
            }
        });

    }
    private void switchView() {
        Intent intent = new Intent(CheckIn.this, ReceptionHome.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
        finish();
    }
    private void checkin(){
        idcard = idcard_edt.getText().toString().trim();
        customer_name = name_edt.getText().toString().trim();
        phone_number = phonenumber_edt.getText().toString().trim();
        deposit = deposit_edt.getText().toString().trim();
        note = note_edt.getText().toString().trim();
        checkin_str = checkin_edt.getText().toString().trim();




         if(idcard.equals("")){
            Toast.makeText(this, "Nhập số CMT", Toast.LENGTH_SHORT).show();

        }
        else if(checkIDCard==0){
            Toast.makeText(CheckIn.this, "Khách hàng không tồn tại!", Toast.LENGTH_SHORT).show();
        }
        else if(customer_name.equals("")){
            Toast.makeText(this, "Nhập Tên", Toast.LENGTH_SHORT).show();
        }
        else if(phone_number.equals("")){
            Toast.makeText(this, "Nhập Số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else if(deposit.equals("")){
            Toast.makeText(this, "Nhập Số tiền đặt cọc", Toast.LENGTH_SHORT).show();
        }
        else if(note.equals("")){
            Toast.makeText(this, "Nhập ghi chú", Toast.LENGTH_SHORT).show();
        }

        else {
            final CollectionReference collectionReference = database.collection("customer");
            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.e("idcard", "onComplete: " + idcard);
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (String.valueOf(doc.get("idcard")).equals(idcard)) {
                                customerID = doc.getId();

                                CollectionReference collectionReference = database.collection("room");
                                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Log.e("room", "onComplete: " + room);
                                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                                if (String.valueOf(doc.get("roomnumber")).equals(room)) {
                                                    roomID = doc.getId();
                                                    docData = new HashMap<>();

                                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
                                                    String currentDateandTime = sdf.format(new Date());

                                                    docData.put("totalPrice", 0);
                                                    docData.put("customer", customerID);


                                                    try {
                                                        docData.put("departuredate", MyUtils.covertStringtoTimestamp2(currentDateandTime));
                                                        docData.put("reservationdate", MyUtils.covertStringtoTimestamp2(currentDateandTime));
                                                        docData.put("arrivaldate", MyUtils.covertStringtoTimestamp2(checkin_str));

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    docData.put("ischecked", 1);
                                                    docData.put("deposit", Integer.parseInt(deposit));
                                                    docData.put("note", note);
                                                    docData.put("room", roomID);
                                                    database.collection("reservation").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Log.e("thanhphan", "onSuccess: " + "checkin");
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(), "Add Accont Fail", Toast.LENGTH_LONG).show();
                                                            Log.e("thanhphan", "onFailure: " + e);
                                                        }
                                                    });


                                                    DocumentReference washingtonRef = database.collection("room").document(roomID);

                                                    washingtonRef
                                                            .update("status", 2)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d("thanhphan", "DocumentSnapshot successfully updated!");

                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w("thanhphan", "Error updating document", e);
                                                                }
                                                            });


                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                        final String roomnumber = room;
                        CollectionReference collectionReference1 = database.collection("room");
                        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        int roomnumber_data = new Integer(String.valueOf(doc.get("roomnumber")));
                                        if (roomnumber.equals(String.valueOf(roomnumber_data))) {
                                            roomID = doc.getId();
                                            CollectionReference collectionReference = database.collection("reservation");
                                            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot docc : task.getResult()) {
                                                            ischecked_data = new Integer(String.valueOf(docc.get("ischecked")));
                                                            if (roomID.equals(String.valueOf(docc.get("room"))) && ischecked_data == 1) {
                                                                reservationID = docc.getId();
                                                                docData = new HashMap<>();
                                                                docData.put("reservation", reservationID);
                                                                database.collection("invoice").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        Log.e("thanhphan", "onSuccess: " + "invoice");
                                                                        switchView();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getApplicationContext(), "Add Accont Fail", Toast.LENGTH_LONG).show();
                                                                        Log.e("thanhphan", "onFailure: " + e);
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
                    }
                }
            });

        }
    }
}
