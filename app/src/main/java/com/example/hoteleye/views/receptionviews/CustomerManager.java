package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoteleye.adapters.CustomerManagerAdapter;
import com.example.hoteleye.views.managerviews.ManagerHome;
import com.example.hoteleye.databases.entities.Customer;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.CustomerManagerItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;

public class CustomerManager extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton add_fab;

    ArrayList<CustomerManagerItem> customerManagerItems  = new ArrayList<>();;
    CustomerManagerAdapter adapter;

    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_manager);
        init();
    }

    private void init() {
        listView = findViewById(R.id.listview_customer_manager);
        add_fab = findViewById(R.id.floatingActionButton_customer_manager);
        database = FirebaseFirestore.getInstance();


//        customerManagerItems.add(new CustomerManagerItem(1,"thanhphan","25/10/1998","0396065919",(float)0.01,"VIP"));

        adapter = new CustomerManagerAdapter(getApplicationContext(), customerManagerItems);
        listView.setAdapter(adapter);
        fillList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerManagerItem item = customerManagerItems.get(position);
                Customer customer = new Customer(item.getCustomerID(), item.getIdCard(), item.getName(),
                        item.getDate(), item.getPhone_number(),item.getDiscount(), item.getNote());
                Intent i = new Intent(CustomerManager.this, CustomerDetail.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("customer", customer);
                startActivity(i);
            }
        });
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerManager.this, CustomerAdd.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("ReservationCustomer","null");
                startActivity(intent);
            }
        });


    }
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            switchView();
//        }
//        return false;
//    }

    private void fillList() {
        CollectionReference collectionReference = database.collection("customer");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        try {
                            customerManagerItems.add
                                    (new CustomerManagerItem(doc.getId(),
                                            Integer.parseInt(String.valueOf(doc.get("idcard"))),
                                            String.valueOf(doc.get("name")),
                                            MyUtils.covertTimestamptoString((Timestamp) doc.get("dateofbirth")),
                                            String.valueOf(doc.get("phonenumber")),
                                            Float.parseFloat(String.valueOf(doc.get("discount"))),
                                            String.valueOf(doc.get("note"))));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
//                    UIUtils.setListViewHeightBasedOnItems(listView);
                }
            }
        });
    }

    private void switchView() {
        Intent itent1 = getIntent();
        String tagg = itent1.getStringExtra("tagg");
        if(tagg.equals("reception")){
            Intent intent = new Intent(CustomerManager.this, ReceptionHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }else if(tagg.equals("manager")){
            Intent intent = new Intent(CustomerManager.this, ManagerHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}
