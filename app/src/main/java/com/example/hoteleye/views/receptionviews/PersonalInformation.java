package com.example.hoteleye.views.receptionviews;

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

import com.example.hoteleye.views.managerviews.ManagerHome;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PersonalInformation extends AppCompatActivity {

    private EditText idcard_tv, name_edt, dateOfBirth_edt, gender_edt, address_edt, phoneNumber_edt, mail_edt, note_edt, account_edt;
    private Button edit_btn;
    int check = 0;

    FirebaseFirestore database;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String personID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        init();

    }

    private void init() {
        idcard_tv = findViewById(R.id.id_card_edt_personal_info);
        name_edt = findViewById(R.id.name_edt_personal_info);
        dateOfBirth_edt = findViewById(R.id.date_of_birh_edt_personal_info);
        gender_edt = findViewById(R.id.gender_edt_personal_info);
        address_edt = findViewById(R.id.address_edt_personal_info);
        phoneNumber_edt = findViewById(R.id.phone_number_edt_personal_info);
        mail_edt = findViewById(R.id.mail_edt_personal_info);
        note_edt = findViewById(R.id.note_edt_personal_info);
        account_edt = findViewById(R.id.account_edt_personal_info);
        edit_btn = findViewById(R.id.save_btn_personal_information);
        database = FirebaseFirestore.getInstance();

        fillData();
        lockEditableEditText();
        dateOfBirth_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(PersonalInformation.this, new DatePickerDialog.OnDateSetListener() {
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
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == 0){
                    check = 1;
                    inLockEditableEditText();
                    edit_btn.setText("Cập nhật");
                }else if(check == 1){
                    check = 0;
                    updateDB();
                    lockEditableEditText();
                    edit_btn.setText("Sửa");
                }
            }
        });
    }

    private void updateDB() {
        DocumentReference docRef = database.collection("person").document(personID);

// Update the timestamp field with the value from the server
        Map<String,Object> updates = new HashMap<>();
        updates.put("idcard", Integer.parseInt(idcard_tv.getText().toString().trim()));
        updates.put("fullname", name_edt.getText().toString().trim());
        try {
            updates.put("dateofbirth", MyUtils.covertStringtoTimestamp(dateOfBirth_edt.getText().toString().trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        updates.put("mail", mail_edt.getText().toString().trim());
        updates.put("phonenumber", phoneNumber_edt.getText().toString().trim());
        updates.put("gender", gender_edt.getText().toString().trim());
        updates.put("note", note_edt.getText().toString().trim());

        docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PersonalInformation.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                switchView();
            }
        });
    }

    private void inLockEditableEditText() {
        idcard_tv.setEnabled(true);
        name_edt.setEnabled(true);
        dateOfBirth_edt.setEnabled(true);
        gender_edt.setEnabled(true);
        mail_edt.setEnabled(true);
        phoneNumber_edt.setEnabled(true);
        note_edt.setEnabled(true);
        address_edt.setEnabled(true);
    }
    private void lockEditableEditText() {
        idcard_tv.setEnabled(false);
        name_edt.setEnabled(false);
        dateOfBirth_edt.setEnabled(false);
        gender_edt.setEnabled(false);
        mail_edt.setEnabled(false);
        phoneNumber_edt.setEnabled(false);
        note_edt.setEnabled(false);
        address_edt.setEnabled(false);
        account_edt.setEnabled(false);
    }

    private void fillData() {
        final String email = user.getEmail();
        Log.e("user firebase", "saveInvoice: "+" || "+email );

        CollectionReference collectionReference = database.collection("account");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if(email.equals(String.valueOf(doc.get("username")))){
                            final String accountID = doc.getId();

                            account_edt.setText(String.valueOf(doc.get("username")));
                            Log.e("hihiii", "onComplete: accountID   "+accountID );

                            CollectionReference collectionReference1 = database.collection("employee");
                            collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (final QueryDocumentSnapshot doc : task.getResult()) {
                                            if(accountID.equals(String.valueOf(doc.get("account")))){
                                                 personID = String.valueOf(doc.get("person"));
                                                Log.e("Thanhphan", "onComplete: personID   "+personID );
                                                DocumentReference docRef = database.collection("person").document(personID);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                               idcard_tv.setText(String.valueOf(document.get("idcard")));
                                                               name_edt.setText(String.valueOf(document.get("fullname")));
                                                               gender_edt.setText(String.valueOf(document.get("gender")));
                                                               mail_edt.setText(String.valueOf(document.get("mail")));
                                                               phoneNumber_edt.setText(String.valueOf(document.get("phonenumber")));
                                                               note_edt.setText(String.valueOf(document.get("note")));
                                                                address_edt.setText(String.valueOf(document.get("address")));

                                                                try {
                                                                    dateOfBirth_edt.setText(
                                                                            MyUtils.covertTimestamptoString(
                                                                                    (Timestamp) document.get("dateofbirth")));
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }}});
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
    private void switchView() {
        Intent itent1 = getIntent();
        String tagg = itent1.getStringExtra("tagg");
        if(tagg.equals("reception")){
            Intent intent = new Intent(PersonalInformation.this, ReceptionHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }else if(tagg.equals("manager")){
            Intent intent = new Intent(PersonalInformation.this, ManagerHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}
