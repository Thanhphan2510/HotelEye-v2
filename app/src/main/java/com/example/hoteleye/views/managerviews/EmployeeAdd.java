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

import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmployeeAdd extends AppCompatActivity {
    private EditText
            idcard_edt, fullname_edt,  gender_edt, position_edt,
            address_edt, mail_edt, phonenumber_edt, description_edt, account_edt, role_edt;
    private EditText dateofbirth_edt;
    private Button  add_btn;

    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;
    String personID = UUID.randomUUID().toString();
    String accountID = UUID.randomUUID().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);
        init();
    }
    private void init() {
        idcard_edt = findViewById(R.id.idcard_edt_employee_add);
        fullname_edt = findViewById(R.id.fullname_edt_employee_add);
        dateofbirth_edt = findViewById(R.id.dateofbirth_edt_employee_add);
        gender_edt = findViewById(R.id.gender_edt_employee_add);
        mail_edt = findViewById(R.id.mail_edt_employee_add);
        phonenumber_edt = findViewById(R.id.phonenumber_edt_employee_add);
        description_edt = findViewById(R.id.description_edt_employee_add);
        address_edt = findViewById(R.id.address_edt_employee_add);
        position_edt = findViewById(R.id.position_edt_employee_add);
        account_edt = findViewById(R.id.account_edt_employee_add);
        role_edt = findViewById(R.id.role_edt_employee_add);



        add_btn = findViewById(R.id.add_btn_employee_add);
        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    

        dateofbirth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(EmployeeAdd.this, new DatePickerDialog.OnDateSetListener() {
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
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
                addPerson();
                addEmployee();


            }
        });
    }

    private void addEmployee() {
        Map<String, Object> docData = new HashMap<>();
        docData.put("person", personID);
        docData.put("account", accountID);
        docData.put("position", position_edt.getText().toString().trim());
        database.collection("employee")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(EmployeeAdd.this, "add success!", Toast.LENGTH_SHORT).show();
                        switchView();
                    }
                });
    }

    private void addPerson() {
        Map<String, Object> docData = new HashMap<>();
        docData.put("fullname", fullname_edt.getText().toString().trim());
        docData.put("address", address_edt.getText().toString().trim());
        docData.put("gender", gender_edt.getText().toString().trim());
        docData.put("idcard", Integer.parseInt(idcard_edt.getText().toString().trim()));
        docData.put("mail", mail_edt.getText().toString().trim());
        docData.put("phonenumber", phonenumber_edt.getText().toString().trim());
        docData.put("note", description_edt.getText().toString().trim());
        try {
            docData.put("dateofbirth", MyUtils.covertStringtoTimestamp(dateofbirth_edt.getText().toString().trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        database.collection("person").document(personID)
                .set(docData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EmployeeAdd.this, "add success!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addAccount() {
        Map<String, Object> docData = new HashMap<>();
        docData.put("username", account_edt.getText().toString().trim());
        docData.put("role", Integer.parseInt(role_edt.getText().toString().trim()));
        docData.put("password", "thanh123");


        database.collection("account").document(accountID)
                .set(docData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EmployeeAdd.this, "add success!", Toast.LENGTH_SHORT).show();
            }
        });
        firebaseAuth.createUserWithEmailAndPassword(account_edt.getText().toString().trim(),"thanh123").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Fail Registered", Toast.LENGTH_LONG).show();

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
        Intent intent = new Intent(EmployeeAdd.this, EmployeeManager.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }
}
