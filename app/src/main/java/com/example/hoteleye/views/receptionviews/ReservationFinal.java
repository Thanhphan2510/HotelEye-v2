package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.RoomNameItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReservationFinal extends AppCompatActivity {
    private Button next_btn;
    private EditText idcard_edt, name_edt, phonenumber_edt, checkin_edt, checkout_edt, deposit_edt, note_edt, room_edt;
    private TextView back_tv;


    String TAG = "thanhphan";
    String tag="";
    String checkin_str,checkout_str,idcard, customer_name, phone_number, deposit, note, room="";
    String reservationID;
    ArrayList<String> roomName = new ArrayList<>();
    ArrayList<RoomNameItem> roomNameItems;

    FirebaseFirestore database;
    String customerID, roomID;
    Map<String, Object> docData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_final);
        initView();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putDatabase();
//                switchView();

            }
        });
        checkin_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkin_edt);
            }
        });
        checkout_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkout_edt);
            }
        });



    }
    private void showDatePickerDialog(final EditText etDates){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationFinal.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String myMonthOfYear=String.valueOf(monthOfYear+1);
                        String myDayOfMonth=String.valueOf(dayOfMonth);
                        if(monthOfYear<10){
                            myMonthOfYear = "0"+(monthOfYear+1);
                        }
                        if(dayOfMonth<10){
                            myDayOfMonth = "0"+ dayOfMonth;
                        }

                        etDates.setText(  myDayOfMonth + "/"+myMonthOfYear + "/" + +year);
                        showTimePickerDialog(etDates);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();

    }

    private void showTimePickerDialog(final EditText etTime) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ReservationFinal.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String selectedHour_str=String.valueOf(selectedHour);
                String selectedMinute_str=String.valueOf(selectedMinute);
                if(selectedHour<10){
                    selectedHour_str = "0"+selectedHour;
                }
                if(selectedMinute<10){
                    selectedMinute_str = "0"+selectedMinute;
                }

                etTime.setText(selectedHour_str + ":" + selectedMinute_str +" "+etTime.getText().toString());
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    private void initView() {
        next_btn = findViewById(R.id.next_btn_reservation_final);
        idcard_edt = findViewById(R.id.idcard_edt_reservation_final);
        name_edt = findViewById(R.id.customer_edt_reservation_final);
        phonenumber_edt = findViewById(R.id.phone_number_edt_reservation_final);
        checkin_edt = findViewById(R.id.checkin_edt_reservation_final);
        checkout_edt = findViewById(R.id.checkout_edt_reservation_final);
        deposit_edt = findViewById(R.id.deposit_edt_reservation_final);
        note_edt = findViewById(R.id.note_edt_reservation_final);
        room_edt = findViewById(R.id.room_edt_reservation_final);
        checkin_edt.setKeyListener(null);
        checkout_edt.setKeyListener(null);

        back_tv = findViewById(R.id.btnBack_reservation_final);
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        database = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

        //pass data from reservation room
        if(tag.equals("1")){
            checkin_str = intent.getStringExtra("checkin");
            checkout_str = intent.getStringExtra("checkout");
            idcard = intent.getStringExtra("idcard");
            customer_name = intent.getStringExtra("customer_name");
            phone_number = intent.getStringExtra("phone_number");
            deposit = intent.getStringExtra("deposit");
            note = intent.getStringExtra("note");
            roomNameItems = (ArrayList<RoomNameItem>) intent.getSerializableExtra("room");
            for (RoomNameItem i : roomNameItems){
                room += i.getName()+", ";
                roomName.add(i.getName());
            }
            idcard_edt.setText(idcard);
            name_edt.setText(customer_name);
            phonenumber_edt.setText(phone_number);
            checkin_edt.setText(checkin_str);
            checkout_edt.setText(checkout_str);
            deposit_edt.setText(deposit);
            note_edt.setText(note);
            room_edt.setText(room);

        }
        //check in from reservation
        else if(tag.equals("2")){
            next_btn.setText("Đặt phòng");
            reservationID = intent.getStringExtra("reservation_id");
            room = intent.getStringExtra("roomnumber");
            roomName.add(room);
            Log.e("lolll", "initView: "+reservationID );
            DocumentReference reference = database.collection("reservation").document(reservationID);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                         roomID = String.valueOf(documentSnapshot.get("room"));
                         customerID = String.valueOf(documentSnapshot.get("customer"));
                         try {
                             checkin_str = (MyUtils.covertTimestamptoString((Timestamp) documentSnapshot.get("arrivaldate")));
                             checkout_str = (MyUtils.covertTimestamptoString((Timestamp) documentSnapshot.get("departuredate")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        deposit =String.valueOf(documentSnapshot.get("deposit"));
                        note = ((String.valueOf(documentSnapshot.get("note"))));
                        DocumentReference reference = database.collection("customer").document(customerID);
                        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();

                                    customer_name = String.valueOf(documentSnapshot.get("name"));
                                    idcard = String.valueOf(documentSnapshot.get("idcard"));
                                    phone_number = String.valueOf(documentSnapshot.get("phonenumber"));
                                    idcard_edt.setText(idcard);
                                    name_edt.setText(customer_name);
                                    phonenumber_edt.setText(phone_number);
                                    checkin_edt.setText(checkin_str);
                                    checkout_edt.setText(checkout_str);
                                    deposit_edt.setText(deposit);
                                    note_edt.setText(note);
                                    room_edt.setText(room);

                                }
                            }
                        });

                    }
                }
            });


        }

        idcard_edt.setText(idcard);
        name_edt.setText(customer_name);
        phonenumber_edt.setText(phone_number);
        checkin_edt.setText(checkin_str);
        checkout_edt.setText(checkout_str);
        deposit_edt.setText(deposit);
        note_edt.setText(note);
        room_edt.setText(room);



    }
    private void putDatabase(){
        if(tag.equals("1")){
            final CollectionReference collectionReference = database.collection("customer");
            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (String.valueOf(doc.get("idcard")).equals(idcard)) {
                                customerID = doc.getId();
                                for(final String i : roomName){
                                    CollectionReference collectionReference = database.collection("room");
                                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    if (String.valueOf(doc.get("roomnumber")).equals(i)){
                                                        roomID = doc.getId();
                                                        docData = new HashMap<>();

                                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
                                                        String currentDateandTime = sdf.format(new Date());

                                                        docData.put("totalPrice",0);
                                                        docData.put("customer",customerID);

                                                        try {
                                                            docData.put("reservationdate",MyUtils.covertStringtoTimestamp2(currentDateandTime));
                                                            docData.put("arrivaldate",MyUtils.covertStringtoTimestamp2(checkin_str));
                                                            docData.put("departuredate",MyUtils.covertStringtoTimestamp2(checkout_str));

                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                        docData.put("ischecked",0);
                                                        docData.put("deposit",Integer.parseInt(deposit));
                                                        docData.put("note",note);
                                                        docData.put("room", roomID);
                                                        database.collection("reservation").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Log.e("thanhphan", "onSuccess: "+"reservation" );
                                                                switchView1();
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
                        }
                    }
                }
            });
        }
        else if(tag.equals("2")){

            DocumentReference washingtonRef1 = database.collection("reservation").document(reservationID);
            washingtonRef1
                    .update("ischecked", 1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("thanhphan", "DocumentSnapshot successfully updated!");
                            docData = new HashMap<>();
                            docData.put("reservation", reservationID);
                            database.collection("invoice").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.e("thanhphan", "onSuccess: "+"invoice" );

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Add Accont Fail",Toast.LENGTH_LONG).show();
                                    Log.e("thanhphan", "onFailure: "+e );
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("thanhphan", "Error updating document", e);
                        }
                    });

            DocumentReference washingtonRef = database.collection("room").document(roomID);
            washingtonRef
                    .update("status", 2)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("thanhphan", "DocumentSnapshot successfully updated!");
                            switchView();
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
    private void switchView() {

        Intent intent = new Intent(ReservationFinal.this, ReceptionHome.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
    private void switchView1() {

        Intent intent = new Intent(ReservationFinal.this, ReservationOverview.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}

