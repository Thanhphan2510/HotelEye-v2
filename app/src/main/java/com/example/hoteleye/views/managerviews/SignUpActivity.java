package com.example.hoteleye.views.managerviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class SignUpActivity extends AppCompatActivity {
    private EditText username_edt, pass_edt, repass_edt,
            idcard_edt, fullname_edt,  gender_edt, position_edt,
            address_edt, mail_edt, phonenumber_edt, description_edt;
    private EditText dateofbirth_edt;
    private Button signup_btn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    Map<String, Object> docData;
    String personID = UUID.randomUUID().toString();
    String accountID = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        dateofbirth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(username_edt.getText().toString().trim(), pass_edt.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                            createAccountDB();
                            createPersonDB();
                            createEmployeeDB();
                            switchView();

                        } else {
                            Toast.makeText(getApplicationContext(), "Fail Registered", Toast.LENGTH_LONG).show();

                        }

                    }
                });


            }
        });

    }

    private void init() {
        username_edt = findViewById(R.id.username_edt_signup);
        pass_edt = findViewById(R.id.password_edt_signup);
        repass_edt = findViewById(R.id.re_password_edt_signup);
        idcard_edt = findViewById(R.id.idcard_edt_signup);
        fullname_edt = findViewById(R.id.fullname_edt_signup);
        dateofbirth_edt = findViewById(R.id.dateofbirth_edt_signup);
        gender_edt = findViewById(R.id.gender_edt_signup);
        mail_edt = findViewById(R.id.mail_edt_signup);
        phonenumber_edt = findViewById(R.id.phonenumber_edt_signup);
        description_edt = findViewById(R.id.description_edt_signup);
        address_edt = findViewById(R.id.address_edt_signup);
        position_edt = findViewById(R.id.position_edt_signup);
        signup_btn = findViewById(R.id.signup_btn_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dateofbirth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateofbirth_edt.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
                dd.show();
            }
        });

    }
    private void switchView() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }
    private void createAccountDB(){
        docData = new HashMap<>();
        docData.put("username", username_edt.getText().toString().trim());
        docData.put("password", pass_edt.getText().toString().trim());
        docData.put("role", 1);
        database.collection("account").document(accountID)
                .set(docData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpActivity.this, "add success!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createPersonDB(){
        docData = new HashMap<>();
        docData.put("idcard", idcard_edt.getText().toString().trim());
        docData.put("fullname", fullname_edt.getText().toString().trim());
        try {
            docData.put("dateofbirth", MyUtils.covertStringtoTimestamp(dateofbirth_edt.getText().toString().trim())) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        docData.put("address",address_edt.getText().toString().trim());
        docData.put("gender", gender_edt.getText().toString().trim());
        docData.put("mail", mail_edt.getText().toString().trim());
        docData.put("phonenumber", phonenumber_edt.getText().toString().trim());
        docData.put("note", description_edt.getText().toString().trim());

        database.collection("person").document(personID)
                .set(docData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpActivity.this, "add success!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void createEmployeeDB(){
        docData = new HashMap<>();
        docData.put("account", accountID);
        docData.put("position", position_edt.getText().toString().trim());
        docData.put("salary", "");
        docData.put("person", personID);

        database.collection("employee").add(docData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.e("thanhphan", "onSuccess: "+"employee" );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("thanphan", "onFailure: "+"employee" );
            }
        });
    }
}
