package com.example.hoteleye.views.managerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hoteleye.adapters.StaffManagerSettingsAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.StaffManagerItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StaffManagerSettings extends AppCompatActivity {
    private ListView listView;
    private Button next_button;
    private FloatingActionButton add_fab;
    ArrayList<StaffManagerItem> staffManagerItems;
    StaffManagerSettingsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_manager_settings);
        init();
    }
    private void init() {
        listView = findViewById(R.id.staffmanager_listview);
        add_fab = findViewById(R.id.add_staff_fab);
        next_button = findViewById(R.id.next_btn_staffmanager_setting);

        staffManagerItems = new ArrayList<>();
        staffManagerItems.add(new StaffManagerItem("Thành PHan","thanhphan","CEO"));
        staffManagerItems.add(new StaffManagerItem("Thành đẹp trai","thanhphan","Lễ tân"));
        staffManagerItems.add(new StaffManagerItem("Thành siêu đẹp","thanhphan","Buồng"));
        adapter = new StaffManagerSettingsAdapter(getApplicationContext(), staffManagerItems);
        listView.setAdapter(adapter);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }
    private void switchView() {
        Intent intent = new Intent(StaffManagerSettings.this, ManagerHome.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
//        finish();
    }
}
