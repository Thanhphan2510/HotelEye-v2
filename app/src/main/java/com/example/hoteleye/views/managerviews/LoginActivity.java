package com.example.hoteleye.views.managerviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hoteleye.R;
import com.example.hoteleye.views.receptionviews.ReceptionHome;
import com.example.hoteleye.views.staffviews.StaffHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private EditText username_edt, pass_edt;
    private TextView signup_tv, forgot_tv;
    private Button login_btn;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        forgot_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPass();
            }
        });


    }

    private void forgotPass() {
        String account = username_edt.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.sendPasswordResetEmail(account).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signup() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void login() {
        String usernameString = "a";
        usernameString = username_edt.getText().toString();
        String passString = "a";
        passString = pass_edt.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(usernameString, passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();

                    CollectionReference collectionReference = database.collection("account");
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    if(String.valueOf(doc.get("username")).equals(username_edt.getText().toString().trim())){
                                        checkrole(new Integer(String.valueOf(doc.get("role"))));
                                    }
                                }
                            }
                        }
                    });
                } else {

                    Toast.makeText(getApplicationContext(), "Fail Logged In", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkrole(int role){
        if(role == 0){
            CollectionReference collectionReference = database.collection("employee");
            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                           if(String.valueOf(doc.get("account")).equals(username_edt.getText().toString().trim()))
                            {
                               if(String.valueOf(doc.get("hotelid")).equals(null)){
                                   switchView_ManagerStart();
                               }else{
                                   switchView_ManagerHome();
                               }
                            }
                        }
                    }
                }
            });


        }
        else if(role == 1){
            switchView_ManagerHome();
        }
        else if(role == 2){
            switchView_ReceptionHome();
        }
        else if(role == 3){
            switchView_StaffHome();
        }
    }
    private void switchView_ManagerStart() {
        Intent intent = new Intent(LoginActivity.this, HotelBasicInforSettings.class);
        startActivity(intent);
        finish();
    }
    private void switchView_ManagerHome() {

        Intent intent = new Intent(LoginActivity.this, ManagerHome.class);
        startActivity(intent);
        finish();
    }
    private void switchView_ReceptionHome() {
        Intent intent = new Intent(LoginActivity.this, ReceptionHome.class);
        startActivity(intent);
        finish();
    }
    private void switchView_StaffHome() {
        Intent intent = new Intent(LoginActivity.this, StaffHome.class);
        startActivity(intent);
        finish();
    }



    private void init() {
        username_edt = findViewById(R.id.username_login);
        pass_edt = findViewById(R.id.password_login);
        login_btn = findViewById(R.id.login_btn);
        signup_tv = findViewById(R.id.signup_tv_login);
        forgot_tv = findViewById(R.id.forgotpass_textview_login);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

    }
    
}
