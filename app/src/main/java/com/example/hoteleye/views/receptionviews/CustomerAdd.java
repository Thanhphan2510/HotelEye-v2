package com.example.hoteleye.views.receptionviews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomerAdd extends AppCompatActivity {
    private EditText idcard_tv, name_edt, dateOfBirth_edt, phoneNumber_edt, note_edt, discount_edt;
    private Button add_btn;
    FirebaseFirestore database;
    String reservationTag = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);
        init();
    }
    private void init() {
        idcard_tv = findViewById(R.id.id_card_edt_customer_add);
        name_edt = findViewById(R.id.name_edt_customer_add);
        dateOfBirth_edt = findViewById(R.id.date_of_birh_edt_customer_add);
        phoneNumber_edt = findViewById(R.id.phone_number_edt_customer_add);
        discount_edt = findViewById(R.id.discount_edt_customer_add);
        note_edt = findViewById(R.id.note_edt_customer_add);
        add_btn = findViewById(R.id.add_btn_customer_add);
        database = FirebaseFirestore.getInstance();

        dateOfBirth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(CustomerAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String myMonthOfYear=String.valueOf(month+1);
                        String myDayOfMonth=String.valueOf(dayOfMonth);
                        if(month<10){
                            myMonthOfYear = "0"+(month+1);
                        }
                        if(dayOfMonth<10){
                            myDayOfMonth = "0"+ dayOfMonth;
                        }

                        dateOfBirth_edt.setText(  myDayOfMonth + "/"+myMonthOfYear + "/" + +year);


                    }
                }, mYear, mMonth, mDay);
                dd.show();
            }
        });
       add_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(idcard_tv.getText().toString().equals("")){
                   Toast.makeText(CustomerAdd.this, "Nhập số CMT", Toast.LENGTH_SHORT).show();

               }else if(name_edt.getText().toString().equals("")){
                   Toast.makeText(CustomerAdd.this, "Nhập Tên", Toast.LENGTH_SHORT).show();
               }
               else if(phoneNumber_edt.getText().toString().equals("")){
                   Toast.makeText(CustomerAdd.this, "Nhập Số điện thoại", Toast.LENGTH_SHORT).show();
               }
               else if(dateOfBirth_edt.getText().toString().equals("")){
                   Toast.makeText(CustomerAdd.this, "Nhập ngày sinh", Toast.LENGTH_SHORT).show();
               }
               else if(discount_edt.getText().toString().equals("")){
                   Toast.makeText(CustomerAdd.this, "Nhập giảm giá", Toast.LENGTH_SHORT).show();
               }

               else if(note_edt.getText().toString().equals("")){
                   Toast.makeText(CustomerAdd.this, "Nhập ghi chú", Toast.LENGTH_SHORT).show();
               }
               else{
                   Map<String, Object> docData = new HashMap<>();
                   docData.put("idcard", Integer.parseInt(idcard_tv.getText().toString().trim()));
                   docData.put("name", name_edt.getText().toString().trim());
                   try {
                       docData.put("dateofbirth", MyUtils.covertStringtoTimestamp(dateOfBirth_edt.getText().toString().trim()));
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   docData.put("phonenumber", phoneNumber_edt.getText().toString().trim());
                   docData.put("discount", Float.parseFloat(discount_edt.getText().toString().trim()));
                   docData.put("note", note_edt.getText().toString().trim());


                   database.collection("customer")
                           .add(docData)
                           .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                               @Override
                               public void onSuccess(DocumentReference documentReference) {
                                   Toast.makeText(CustomerAdd.this, "add success!", Toast.LENGTH_SHORT).show();
                                   switchView();
                               }
                           });
               }

           }
       });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            switchView();
        }
        return false;
    }
    private void switchView() {
        Intent i = getIntent();
        reservationTag = i.getStringExtra("ReservationCustomer");
        if(reservationTag.equals("ReservationCustomer")){
            Intent intent = new Intent(CustomerAdd.this, ReservationCustomer.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("idcard",idcard_tv.getText().toString());
            intent.putExtra("customer_name",name_edt.getText().toString());
            intent.putExtra("phone_number",phoneNumber_edt.getText().toString());
            startActivity(intent);
            finish();
        }else if(reservationTag.equals("Checkin")){
           onBackPressed();
        }
        else{
            Intent intent = new Intent(CustomerAdd.this, CustomerManager.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(CustomerAdd.this, CheckIn.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("idcard",idcard_tv.getText().toString());
        intent.putExtra("customer_name",name_edt.getText().toString());
        intent.putExtra("phone_number",phoneNumber_edt.getText().toString());
//        startActivity(intent);
        setResult(RESULT_OK,intent);
        finish();
    }
}
