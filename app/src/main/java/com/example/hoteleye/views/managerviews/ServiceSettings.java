package com.example.hoteleye.views.managerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hoteleye.adapters.ServiceSettingsAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ServiceItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ServiceSettings extends AppCompatActivity {
    private ListView listView;
    private Button next_button;
    private FloatingActionButton add_fab;
    ArrayList<ServiceItem> serviceItems;
    ServiceSettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_settings);
        init();
    }

    private void init() {
        listView = findViewById(R.id.service_listview);
        add_fab = findViewById(R.id.add_floor_fab);
        next_button = findViewById(R.id.next_btn_service_setting);

        serviceItems = new ArrayList<>();
        serviceItems.add(new ServiceItem("Giặt là", (float) 12000, "1 bộ", "giặt là", "không"));
        serviceItems.add(new ServiceItem("Giặt làaaaa", (float) 12000, "1 bộaaiijkjkjkjka", "giặt là", "không"));
        serviceItems.add(new ServiceItem("Giặt là", (float) 1209999900, "1 bộ", "giặt là", "không"));
        serviceItems.add(new ServiceItem("Giặtđá là", (float) 12000, "1 bộ", "giặtádasdas là", "không"));

        adapter = new ServiceSettingsAdapter(getApplicationContext(), serviceItems);
        listView.setAdapter(adapter);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }

    private void switchView() {
        Intent intent = new Intent(ServiceSettings.this, StaffManagerSettings.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
//        finish();
    }
}
