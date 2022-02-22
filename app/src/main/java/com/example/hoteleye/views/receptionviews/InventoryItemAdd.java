package com.example.hoteleye.views.receptionviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoteleye.databases.entities.InventoryItem;
import com.example.hoteleye.commons.MyUtils;
import com.example.hoteleye.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryItemAdd extends AppCompatActivity {
    private EditText name_edt, quantity_edt,price_edt, date_edt, description_edt;
    private Button add_btn;

    FirebaseFirestore database;
    InventoryItem inventoryItem;
    String inventoryItemID, serviceItemID = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_item_add);
        init();
    }
    private void init() {
        name_edt = findViewById(R.id.name_edt_invetory_item_add);
        quantity_edt = findViewById(R.id.quantity_edt_invetory_item_add);
        price_edt = findViewById(R.id.price_edt_invetory_item_add);
        date_edt = findViewById(R.id.date_edt_invetory_item_add);
        description_edt = findViewById(R.id.description_edt_invetory_item_add);

        add_btn = findViewById(R.id.add_btn_invetory_item_add);

        database = FirebaseFirestore.getInstance();
        date_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dd = new DatePickerDialog(InventoryItemAdd.this, new DatePickerDialog.OnDateSetListener() {
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
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hiii", "onClick: "+serviceItemID );
                Map<String, Object> docData = new HashMap<>();
                docData.put("name", name_edt.getText().toString().trim());
                docData.put("importtotal", Integer.parseInt(quantity_edt.getText().toString().trim()));
                docData.put("description", description_edt.getText().toString().trim());
                docData.put("serviceitem", serviceItemID);
                try {
                    docData.put("importdate", MyUtils.covertStringtoTimestamp(date_edt.getText().toString().trim()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                database.collection("inventoryitem")
                        .add(docData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                            }
                        });
                addServiceItem();
                addService();
            }
        });
    }

    private void addServiceItem() {
        Map<String, Object> docData = new HashMap<>();

        docData.put("name", name_edt.getText().toString().trim());
        docData.put("price", Float.parseFloat(price_edt.getText().toString().trim()));
       database.collection("serviceitem").document(serviceItemID)
                .set(docData).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               Toast.makeText(InventoryItemAdd.this, "add success!", Toast.LENGTH_SHORT).show();
               switchView();
           }
       });

    }
    private void addService() {
        Map<String, Object> docData = new HashMap<>();

        docData.put("serviceitem", serviceItemID);
        docData.put("name", name_edt.getText().toString().trim());
        docData.put("description", "codinh");
        docData.put("unitprice", Float.parseFloat(price_edt.getText().toString().trim()));
        database.collection("service")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

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
        Intent intent = new Intent(InventoryItemAdd.this, InventoryItemManager.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }
}
