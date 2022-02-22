package com.example.hoteleye.views.managerviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hoteleye.adapters.ManagerHomeAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.views.receptionviews.CustomerManager;
import com.example.hoteleye.views.receptionviews.InventoryItemManager;
import com.example.hoteleye.views.receptionviews.PersonalInformation;
import com.example.hoteleye.views.receptionviews.ReceptionHome;
import com.example.hoteleye.views.receptionviews.ReservationOverview;
import com.example.hoteleye.viewmodels.RoomNameItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManagerHome extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView viewreport_tv;
    private TextView switchrole_tv, employee_tv, logOut_tv,activity_main_tv_user_name,customer_tv,inventoryItem_tv,personalInformation_tv;
    List<RoomNameItem> roomNameItems;
    ManagerHomeAdapter adapter;
    FirebaseAuth firebaseAuth;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseFirestore database;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String employeeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home);
        toolbar = findViewById(R.id.toolbar_manager);
        firebaseAuth = FirebaseAuth.getInstance();

//        toolbar.setSubtitle("Test Subtitle");


        init();
        viewreport_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReport();
            }
        });
        switchrole_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
        employee_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerHome.this, EmployeeManager.class);
                intent.putExtra("tagg","manager");
                startActivity(intent);

            }
        });
        customer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerHome.this, CustomerManager.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("tagg","manager");
                startActivity(intent);
            }
        });
        inventoryItem_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerHome.this, InventoryItemManager.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("tagg","manager");
                startActivity(intent);
            }
        });
        logOut_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        personalInformation_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerHome.this, PersonalInformation.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("tagg","manager");
                startActivity(intent);
            }
        });

    }

    private void logOut() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ManagerHome.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void switchView() {
        Intent intent = new Intent(ManagerHome.this, ReceptionHome.class);
        intent.putExtra("EXTRA_SESSION_ID", "1");
        startActivity(intent);

    }

    private void viewReport() {
        Intent intent = new Intent(ManagerHome.this, ViewReportOverview.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void init() {
        recyclerView = findViewById(R.id.managerhome_receyclerview);
        viewreport_tv = findViewById(R.id.viewrepot_tv_home);
        switchrole_tv = findViewById(R.id.switchrole_tv_managerhome);
        employee_tv = findViewById(R.id.employee_tv_manager_home);
        logOut_tv = findViewById(R.id.logout_manager_home);
        activity_main_tv_user_name = findViewById(R.id.activity_main_tv_user_name);
        inventoryItem_tv = findViewById(R.id.inventory_item_tv_manager_home);
        customer_tv = findViewById(R.id.customer_tv_manager_home);
        personalInformation_tv = findViewById(R.id.personalinformation_tv_manager_home);
        roomNameItems = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

        //thêm danh sách phòng

        CollectionReference collectionReference = database.collection("room");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for ( QueryDocumentSnapshot doc : task.getResult()) {
                        final RoomNameItem item =  new
                                RoomNameItem(String.valueOf(doc.get("roomnumber")),
                                new Integer(String.valueOf(doc.get("status"))));
                        Log.e("item", "onComplete: "+item.toString());

                        roomNameItems.add(item);
                        Collections.sort(roomNameItems);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ManagerHomeAdapter(getApplicationContext(), roomNameItems);
        recyclerView.setAdapter(adapter);

        String uid = user.getUid();
        final String email = user.getEmail();

        CollectionReference collectionReference1 = database.collection("account");
        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if(email.equals(String.valueOf(doc.get("username")))){
                            final String accountID = doc.getId();
                            Log.e("hihiii", "onComplete: accountID   "+accountID );
                            CollectionReference collectionReference1 = database.collection("employee");
                            collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            if(accountID.equals(String.valueOf(doc.get("account")))){
                                                employeeID = doc.getId();
                                                Log.e("hihiii", "onComplete: employeeID   "+employeeID );
                                                DocumentReference docRef = database.collection("person").document(String.valueOf(doc.get("person")));
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                activity_main_tv_user_name.setText(String.valueOf(document.get("fullname")));
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



    private void reservation() {
        Intent intent = new Intent(ManagerHome.this, ReservationOverview.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
