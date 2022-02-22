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

import com.example.hoteleye.databases.entities.Customer;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomerDetail extends AppCompatActivity {
    private EditText idcard_tv, name_edt, dateOfBirth_edt, phoneNumber_edt, note_edt, discount_edt;
    private Button delete_btn, update_btn;
    FirebaseFirestore database;
    Customer customer;
    String customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        init();
    }
    private void init() {
        idcard_tv = findViewById(R.id.id_card_edt_customer_detail);
        name_edt = findViewById(R.id.name_edt_customer_detail);
        dateOfBirth_edt = findViewById(R.id.date_of_birh_edt_customer_detail);
        phoneNumber_edt = findViewById(R.id.phone_number_edt_customer_detail);
        discount_edt = findViewById(R.id.discount_edt_customer_detail);
        note_edt = findViewById(R.id.note_edt_customer_detail);
        delete_btn = findViewById(R.id.delete_btn_customer_detail);
        update_btn = findViewById(R.id.update_btn_customer_detail);
        database = FirebaseFirestore.getInstance();

        fillData();

        dateOfBirth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(CustomerDetail.this, new DatePickerDialog.OnDateSetListener() {
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
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = database.collection("customer").document(customerID);

                Map<String,Object> updates = new HashMap<>();
                updates.put("idcard", Integer.parseInt(idcard_tv.getText().toString().trim()));
                updates.put("name", name_edt.getText().toString().trim());
                try {
                    updates.put("dateofbirth", MyUtils.covertStringtoTimestamp(dateOfBirth_edt.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                updates.put("phonenumber", phoneNumber_edt.getText().toString().trim());
                updates.put("discount", Float.parseFloat(discount_edt.getText().toString().trim()));
                updates.put("note", note_edt.getText().toString().trim());

                docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CustomerDetail.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                });
                switchView();
            }

        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.collection("customer").document(customerID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CustomerDetail.this, "delete sucess!", Toast.LENGTH_SHORT).show();
                            }
                        });
                switchView();
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

    private void fillData() {
        Intent i = getIntent();
        customer = (Customer) i.getSerializableExtra("customer");
        idcard_tv.setText(customer.getId_card()+"");
        name_edt.setText(customer.getCustomer_name());
        phoneNumber_edt.setText(customer.getPhone_number());
        note_edt.setText(customer.getNote());
        discount_edt.setText(customer.getDiscount()+"");
        dateOfBirth_edt.setText(customer.getDateOfBirth());
        customerID = customer.getId();
    }
    private void switchView() {
        Intent intent = new Intent(CustomerDetail.this, CustomerManager.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }
}
