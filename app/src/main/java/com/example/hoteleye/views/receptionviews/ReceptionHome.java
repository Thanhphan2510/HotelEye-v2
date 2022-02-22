package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hoteleye.adapters.ReceptionHomeAdapter;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.commons.RequestAction;
import com.example.hoteleye.converters.RoomConverter;
import com.example.hoteleye.databases.entities.Room;
import com.example.hoteleye.views.managerviews.EmployeeManager;
import com.example.hoteleye.views.managerviews.LoginActivity;
import com.example.hoteleye.views.managerviews.ManagerHome;
import com.example.hoteleye.R;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceptionHome extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView switchrole_tv, activity_main_tv_user_name;
    private TextView personalInformation_tv, customer_tv, inventoryItem_tv, viewColleague_tv, logOut_tv;
    List<RoomNameItem> roomNameItems;
    ReceptionHomeAdapter adapter;
    int role = 1;
    FirebaseFirestore database;
    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_home);
        toolbar = findViewById(R.id.toolbar_manager);
//        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.menu_manager);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reservation_item_menu:
                        reservation();
                        return true;
                }
                return false;
            }
        });

        init();


    }


    private void init() {
        recyclerView = findViewById(R.id.receptionhome_receyclerview);
        switchrole_tv = findViewById(R.id.switchrole_tv_receptionhome);
        personalInformation_tv = findViewById(R.id.personalinformation_tv_reception_home);
        customer_tv = findViewById(R.id.customer_tv_reception_home);
        inventoryItem_tv = findViewById(R.id.inventory_item_tv_reception_home);
        viewColleague_tv = findViewById(R.id.view_colleague_rv_reception_home);
        activity_main_tv_user_name = findViewById(R.id.activity_main_tv_user_name);
        logOut_tv = findViewById(R.id.logout_reception_home);

        roomNameItems = new ArrayList<>();
        database = FirebaseFirestore.getInstance();

        try {
            String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
            if (sessionId.equalsIgnoreCase("1")) {
                Log.e("thanhphan", "init: " + sessionId);
                switchrole_tv.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            Log.e("thanhphan", "error:  " + e);
        }

        //set name
        String uid = user.getUid();
        final String email = user.getEmail();

        CollectionReference collectionReference1 = database.collection("account");
        collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if (email.equals(String.valueOf(doc.get("username")))) {
                            final String accountID = doc.getId();
                            Log.e("hihiii", "onComplete: accountID   " + accountID);
                            CollectionReference collectionReference1 = database.collection("employee");
                            collectionReference1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            if (accountID.equals(String.valueOf(doc.get("account")))) {
                                                employeeID = doc.getId();
                                                Log.e("hihiii", "onComplete: employeeID   " + employeeID);
                                                DocumentReference docRef = database.collection("person").document(String.valueOf(doc.get("person")));
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                activity_main_tv_user_name.setText(String.valueOf(document.get("fullname")));
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
                    }

                }
            }
        });


        //add room list
//        CollectionReference collectionReference = database.collection("room");
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        final RoomNameItem item = new
//                                RoomNameItem(String.valueOf(doc.get("roomnumber")),
//                                new Integer(String.valueOf(doc.get("status"))));
//                        Log.e("item", "onComplete: " + item.toString());
//
//                        roomNameItems.add(item);
//                        Collections.sort(roomNameItems);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                }
//            }
//        });

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new Request.Builder()
                .url(RequestAction.ROOM_LIST)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String myResponse = response.body().string();


                ReceptionHome.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("thanhpt", "run: "+myResponse );
                        List<Room> roomResponse = (List<Room>)(Object) MyUtils.fromJSon(myResponse, Room[].class);
                        roomNameItems = RoomConverter.covertRoomsToRoomNameItems(roomResponse);
                        Collections.sort(roomNameItems);
                        adapter = new ReceptionHomeAdapter(getApplicationContext(), roomNameItems);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }
                });

            }
        });


        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



//        adapter.notifyDataSetChanged();

        switchrole_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceptionHome.this, ManagerHome.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        personalInformation_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceptionHome.this, PersonalInformation.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("tagg", "reception");
                startActivity(intent);
            }
        });
        customer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceptionHome.this, CustomerManager.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("tagg", "reception");
                startActivity(intent);
            }
        });
        inventoryItem_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceptionHome.this, InventoryItemManager.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("tagg", "reception");
                startActivity(intent);
            }
        });
        viewColleague_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceptionHome.this, EmployeeManager.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("tagg", "reception");
                startActivity(intent);
            }
        });
        logOut_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();

            }
        });


    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ReceptionHome.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (role == 1) {
            MenuInflater findMenuItems = getMenuInflater();
            findMenuItems.inflate(R.menu.menu_manager, menu);
        }


        return super.onCreateOptionsMenu(menu);
    }


    private void reservation() {
        Intent intent = new Intent(ReceptionHome.this, ReservationOverview.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(intent);
    }
}
