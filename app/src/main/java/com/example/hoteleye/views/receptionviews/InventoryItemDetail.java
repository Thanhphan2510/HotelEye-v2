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

import com.example.hoteleye.databases.entities.InventoryItem;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InventoryItemDetail extends AppCompatActivity {
    private EditText name_edt, quantity_edt,price_edt, date_edt, description_edt;
    private Button delete_btn, update_btn;

    FirebaseFirestore database;
    InventoryItem inventoryItem;
    String inventoryItemID, serviceItemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_item_detail);
        init();
    }
    private void init() {
        name_edt = findViewById(R.id.name_edt_invetory_item_detail);
        quantity_edt = findViewById(R.id.quantity_edt_invetory_item_detail);
        price_edt = findViewById(R.id.price_edt_invetory_item_detail);
        date_edt = findViewById(R.id.date_edt_invetory_item_detail);
        description_edt = findViewById(R.id.description_edt_invetory_item_detail);

        delete_btn = findViewById(R.id.delete_btn_invetory_item_detail);
        update_btn = findViewById(R.id.update_btn_invetory_item_detail);
        database = FirebaseFirestore.getInstance();

        fillData();

        date_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(InventoryItemDetail.this, new DatePickerDialog.OnDateSetListener() {
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

                        date_edt.setText(  myDayOfMonth + "/"+myMonthOfYear + "/" + +year);


                    }
                }, mYear, mMonth, mDay);
                dd.show();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = database.collection("inventoryitem").document(inventoryItemID);

                Map<String,Object> updates = new HashMap<>();
                updates.put("name", name_edt.getText().toString().trim());
                updates.put("importtotal", quantity_edt.getText().toString().trim());
//                updates.put("serviceitem", name_edt.getText().toString().trim());
                updates.put("description", description_edt.getText().toString().trim());
                try {
                    updates.put("importdate", MyUtils.covertStringtoTimestamp(date_edt.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InventoryItemDetail.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                });
                updateServiceItem();
                switchView();
            }

        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.collection("inventoryitem").document(inventoryItemID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(InventoryItemDetail.this, "delete sucess!", Toast.LENGTH_SHORT).show();
                            }
                        });
                switchView();
            }
        });


    }

    private void updateServiceItem() {
        DocumentReference docRef = database.collection("serviceitem").document(serviceItemID);

        Map<String,Object> updates = new HashMap<>();
        updates.put("name", name_edt.getText().toString().trim());
        updates.put("price", price_edt.getText().toString().trim());

        docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InventoryItemDetail.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show();
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
        inventoryItem = (InventoryItem) i.getSerializableExtra("inventoryitem");
        name_edt.setText(inventoryItem.getInventory_name());
        quantity_edt.setText(inventoryItem.getImport_total()+"");
        price_edt.setText(inventoryItem.getServiceItem().getPrice()+"");
        date_edt.setText(inventoryItem.getImport_date());
        description_edt.setText(inventoryItem.getDescription());

        inventoryItemID = inventoryItem.getInventory_id();
        serviceItemID = inventoryItem.getServiceItem().getId();
    }
    private void switchView() {
        Intent intent = new Intent(InventoryItemDetail.this, InventoryItemManager.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }
}
