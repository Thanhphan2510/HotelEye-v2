package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class ReservationCustomer extends AppCompatActivity {
    private EditText idcard_edt, name_edt, phonenumber_edt, checkin_edt, checkout_edt, deposit_edt, note_edt;
    private TextView create_customer;
    private Button next_btn;
    private TextView back_tv;
    FirebaseFirestore database;
    int checkIDCard = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_customer);
        initView();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
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

        create_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationCustomer.this, CustomerAdd.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("ReservationCustomer","ReservationCustomer");
                startActivity(intent);
            }
        });

    }
    private void showDatePickerDialog(final EditText etDates){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationCustomer.this,
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
        mTimePicker = new TimePickerDialog(ReservationCustomer.this, new TimePickerDialog.OnTimeSetListener() {
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
        next_btn = findViewById(R.id.next_btn_reservation_customer);
        idcard_edt = findViewById(R.id.idcard_edt_reservation_customer);
        name_edt = findViewById(R.id.name_edt_reservation_customer);
        phonenumber_edt = findViewById(R.id.phone_number_edt_reservation_customer);
        checkin_edt = findViewById(R.id.checkin_edt_reservation_customer);
        checkout_edt = findViewById(R.id.checkout_edt_reservation_customer);
        deposit_edt = findViewById(R.id.deposit_edt_reservation_customer);
        note_edt = findViewById(R.id.note_edt_reservation_customer);
        create_customer = findViewById(R.id.create_customer_reservation_customer);
        checkin_edt.setKeyListener(null);
        checkout_edt.setKeyListener(null);
        back_tv = findViewById(R.id.btnBack_reservation_customer);
        back_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        database = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String id_card = String.valueOf(intent.getStringExtra("idcard"));

        if(!id_card.equals("null")){
            idcard_edt.setText(String.valueOf(intent.getStringExtra("idcard"))) ;
            name_edt.setText(String.valueOf( intent.getStringExtra("customer_name")));
            phonenumber_edt.setText(String.valueOf( intent.getStringExtra("phone_number")));
        }

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
        if(idcard_edt.getText().toString().equals("")){
            Toast.makeText(this, "Nhập số CMT", Toast.LENGTH_SHORT).show();

        }
        else if(checkIDCard==0){
            Toast.makeText(ReservationCustomer.this, "Khách hàng không tồn tại!", Toast.LENGTH_SHORT).show();
        }else if(name_edt.getText().toString().equals("")){
            Toast.makeText(this, "Nhập Tên", Toast.LENGTH_SHORT).show();
        }
        else if(phonenumber_edt.getText().toString().equals("")){
            Toast.makeText(this, "Nhập Số điện thoại", Toast.LENGTH_SHORT).show();
        }
        else if(checkin_edt.getText().toString().equals("")){
            Toast.makeText(this, "Nhập thời gian check in", Toast.LENGTH_SHORT).show();
        }
        else if(checkout_edt.getText().toString().equals("")){
            Toast.makeText(this, "Nhập thời gian check out", Toast.LENGTH_SHORT).show();
        }
        else if(deposit_edt.getText().toString().equals("")){
            Toast.makeText(this, "Nhập số tiền đặt cọc", Toast.LENGTH_SHORT).show();
        }
        else if(note_edt.getText().toString().equals("")){
            Toast.makeText(this, "Nhập ghi chú", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(ReservationCustomer.this, ReservationRoom.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("idcard",idcard_edt.getText().toString());
            intent.putExtra("customer_name",name_edt.getText().toString());
            intent.putExtra("phone_number",phonenumber_edt.getText().toString());
            intent.putExtra("checkin",checkin_edt.getText().toString());
            intent.putExtra("checkout",checkout_edt.getText().toString());
            intent.putExtra("deposit",deposit_edt.getText().toString());
            intent.putExtra("note",note_edt.getText().toString());
            startActivity(intent);
        }

    }
}
