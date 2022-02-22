package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoteleye.adapters.InventoryItemManagerAdapter;
import com.example.hoteleye.views.managerviews.ManagerHome;
import com.example.hoteleye.databases.entities.InventoryItem;
import com.example.hoteleye.databases.entities.ServiceItem;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.InventoryItemItem;
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

public class InventoryItemManager extends AppCompatActivity {
    private ListView listView;
    private FloatingActionButton add_fab;

    ArrayList<InventoryItemItem> inventoryItemItems  = new ArrayList<>();;
    InventoryItemManagerAdapter adapter;

    FirebaseFirestore database;
    InventoryItemItem i;
    String inventoryItemID, serviceitemID, name, importdate, description;
    int quantity;
    InventoryItemItem itemItem;
    int iii,ii;
    float price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_item_manager);
        init();
    }
    private void init() {
        listView = findViewById(R.id.listview_inventory_item_manager);
        add_fab = findViewById(R.id.floatingActionButton_inventory_item_manager);
        database = FirebaseFirestore.getInstance();


//        customerManagerItems.add(new CustomerManagerItem(1,"thanhphan","25/10/1998","0396065919",(float)0.01,"VIP"));

        adapter = new InventoryItemManagerAdapter(getApplicationContext(), inventoryItemItems);
        listView.setAdapter(adapter);
        fillList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InventoryItemItem item = inventoryItemItems.get(position);
                InventoryItem inventoryItem = new InventoryItem(item.getId(),
                        item.getName(), item.getDate(), item.getQuantity(),item.getServiceItem(),item.getDescription());
                Intent intent = new Intent(InventoryItemManager.this, InventoryItemDetail.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("inventoryitem", inventoryItem);
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
        final int check = 0;
        CollectionReference collectionReference = database.collection("inventoryitem");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for ( QueryDocumentSnapshot doc : task.getResult()) {
                        inventoryItemID =   String.valueOf(doc.getId());
                        serviceitemID =   String.valueOf(doc.get("serviceitem"));
                        name =   String.valueOf(doc.get("name"));
                        quantity =  Integer.parseInt(String.valueOf(doc.get("importtotal")));
                        try {
                            importdate =  MyUtils.covertTimestamptoString((Timestamp) doc.get("importdate"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        description =  String.valueOf(doc.get("description"));
                        Log.e("haizz", "onComplete: "+inventoryItemID );
                        i = new InventoryItemItem(inventoryItemID, name, quantity, importdate, 0,
                                new ServiceItem(serviceitemID), description);
                        inventoryItemItems.add(i);
                        adapter.notifyDataSetChanged();

                    }
                    CollectionReference collectionReference = database.collection("serviceitem");
                    collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                              serviceitemID = doc.getId();
                                        price = Float.parseFloat(String.valueOf(doc.get("price")));
                                        Log.e("lolly", "onComplete: " + price);
                                        for (ii = 0; ii < inventoryItemItems.size(); ii++
                                             ) {
                                            if (inventoryItemItems.get(ii).getServiceItem().getId().equals(serviceitemID)){
                                                inventoryItemItems.get(ii).setPrice(price);
                                                inventoryItemItems.get(ii).getServiceItem().setPrice(price);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }

                                }
                            }
                        }});
                }
            }
        });

       add_fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(InventoryItemManager.this, InventoryItemAdd.class)
                       .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               startActivity(intent);
//               finish();
           }
       });
    }

    private void switchView() {
           Intent itent1 = getIntent();
        String tagg = itent1.getStringExtra("tagg");
        if(tagg.equals("reception")){
            Intent intent = new Intent(InventoryItemManager.this, ReceptionHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }else if(tagg.equals("manager")){
            Intent intent = new Intent(InventoryItemManager.this, ManagerHome.class)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

}
