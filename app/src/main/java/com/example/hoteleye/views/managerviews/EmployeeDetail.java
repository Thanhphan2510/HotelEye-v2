package com.example.hoteleye.views.managerviews;

import androidx.annotation.NonNull;
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

import com.example.hoteleye.databases.entities.Account;
import com.example.hoteleye.databases.entities.Employee;
import com.example.hoteleye.databases.entities.Person;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDetail extends AppCompatActivity {
    private EditText
            idcard_edt, fullname_edt,  gender_edt, position_edt,
            address_edt, mail_edt, phonenumber_edt, description_edt, account_edt;
    private EditText dateofbirth_edt;
    private Button delete_btn, update_btn;

    FirebaseFirestore database;
    Person person;
    Account account;
    Employee employee;
    String personID, accountID, employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        init();
    }
    private void init() {
        idcard_edt = findViewById(R.id.idcard_edt_employee_detail);
        fullname_edt = findViewById(R.id.fullname_edt_employee_detail);
        dateofbirth_edt = findViewById(R.id.dateofbirth_edt_employee_detail);
        gender_edt = findViewById(R.id.gender_edt_employee_detail);
        mail_edt = findViewById(R.id.mail_edt_employee_detail);
        phonenumber_edt = findViewById(R.id.phonenumber_edt_employee_detail);
        description_edt = findViewById(R.id.description_edt_employee_detail);
        address_edt = findViewById(R.id.address_edt_employee_detail);
        position_edt = findViewById(R.id.position_edt_employee_detail);
        account_edt = findViewById(R.id.account_edt_employee_detail);

        delete_btn = findViewById(R.id.delete_btn_employee_detail);
        update_btn = findViewById(R.id.update_btn_employee_detail);
        database = FirebaseFirestore.getInstance();
        account_edt.setEnabled(false);
        fillData();

        dateofbirth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(EmployeeDetail.this, new DatePickerDialog.OnDateSetListener() {
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

                        dateofbirth_edt.setText(  myDayOfMonth + "/"+myMonthOfYear + "/" + +year);


                    }
                }, mYear, mMonth, mDay);
                dd.show();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update person infor
                DocumentReference docRef = database.collection("person").document(personID);
                Map<String,Object> updates = new HashMap<>();
                updates.put("fullname", fullname_edt.getText().toString().trim());
                updates.put("address", address_edt.getText().toString().trim());
                updates.put("gender", gender_edt.getText().toString().trim());
                updates.put("idcard", Integer.parseInt(idcard_edt.getText().toString().trim()));
                updates.put("mail", mail_edt.getText().toString().trim());
                updates.put("phonenumber", phonenumber_edt.getText().toString().trim());
                updates.put("note", description_edt.getText().toString().trim());
                try {
                    updates.put("dateofbirth", MyUtils.covertStringtoTimestamp(dateofbirth_edt.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EmployeeDetail.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                });

                //update employee infor
                DocumentReference docRef1 = database.collection("employee").document(employeeID);
                Map<String,Object> updates1 = new HashMap<>();
                updates1.put("position", position_edt.getText().toString().trim());

                docRef1.update(updates1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EmployeeDetail.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                });
                switchView();
            }

        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.collection("person").document(personID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EmployeeDetail.this, "delete sucess!", Toast.LENGTH_SHORT).show();
                            }
                        });
                database.collection("account").document(accountID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EmployeeDetail.this, "delete sucess!", Toast.LENGTH_SHORT).show();
                            }
                        });
                database.collection("employee").document(employeeID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EmployeeDetail.this, "delete sucess!", Toast.LENGTH_SHORT).show();
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
        Employee employee = (Employee) i.getSerializableExtra("employee");
        personID = employee.getPersonID();
        Account account = employee.getAccount();
        employeeID = employee.getEmployee_id();
        accountID  =account.getAccountID();

        idcard_edt.setText(employee.getId_card()+"");
        fullname_edt.setText(employee.getFullName());
        dateofbirth_edt.setText(employee.getDateOfBirth());
        phonenumber_edt.setText(employee.getPhone_number());
        account_edt.setText(account.getUsername());
        DocumentReference docRef = database.collection("person").document(personID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        gender_edt.setText(String.valueOf(document.get("gender")));
                        address_edt.setText(String.valueOf(document.get("address")));
                        mail_edt.setText(String.valueOf(document.get("mail")));
                        description_edt.setText(String.valueOf(document.get("note")));
                    }
                }}});
        DocumentReference docRef1 = database.collection("employee").document(employeeID);
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        position_edt.setText(String.valueOf(document.get("position")));
                    }
                }}});



    }


    private void switchView() {
        Intent intent = new Intent(EmployeeDetail.this, EmployeeManager.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }
}
