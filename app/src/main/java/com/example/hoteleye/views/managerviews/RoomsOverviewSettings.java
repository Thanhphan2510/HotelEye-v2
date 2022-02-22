package com.example.hoteleye.views.managerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hoteleye.adapters.RoomsOverviewSettingsAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.FloorItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RoomsOverviewSettings extends AppCompatActivity {
    private ListView floor_listview;
    private Button next_button;
    private FloatingActionButton add_fab;
    ArrayList<FloorItem> floorItems;
    RoomsOverviewSettingsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_overview_settings);
        init();
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }

        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }

    private void init() {
        floor_listview = findViewById(R.id.floor_listview);
        add_fab = findViewById(R.id.add_floor_fab);
        next_button = findViewById(R.id.next_btn_room_overview);
        floorItems = new ArrayList<>();
        floorItems.add(new FloorItem("Tầng 1","7"));
        floorItems.add(new FloorItem("Tầng 2","7"));
        floorItems.add(new FloorItem("Tầng 3","7"));
        floorItems.add(new FloorItem("Tầng 4","7"));
        floorItems.add(new FloorItem("Tầng 5","7"));
        adapter = new RoomsOverviewSettingsAdapter(getApplicationContext(), floorItems);
        floor_listview.setAdapter(adapter);
    }

    private void addDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_floor_dialog);
        dialog.show();
    }
    private void switchView() {
        Intent intent = new Intent(RoomsOverviewSettings.this, FurnitureSettings.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

}

//https://androidcoban.com/tim-hieu-cac-loai-dialog-trong-android.html
//https://thangcoder.com/lap-trinh-android/hoc-lap-trinh-android-can-ban/dialog-va-alertdialog-trong-lap-trinh-android