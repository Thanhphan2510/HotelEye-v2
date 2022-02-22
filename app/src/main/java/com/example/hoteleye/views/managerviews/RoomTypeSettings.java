package com.example.hoteleye.views.managerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hoteleye.adapters.RoomTypeSettingsAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.RoomTypeItem;

import java.util.ArrayList;

public class RoomTypeSettings extends AppCompatActivity {
    private ListView roomType_listview;
    private Button add_btn, next_btn, back_btn;
    ArrayList<RoomTypeItem> roomTypeItems;
    RoomTypeSettingsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_type_settings);
        init();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchViewAdd();
            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchViewNext();
            }
        });
    }

    private void switchViewNext() {
        Intent intent = new Intent(RoomTypeSettings.this, DetailRoomTypeSettings.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void switchViewAdd() {
        Intent intent = new Intent(RoomTypeSettings.this, AddRoomTypeSettings.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void init() {
        roomType_listview = findViewById(R.id.room_type_listview);
        add_btn = findViewById(R.id.add_room_type_btn);
        next_btn = findViewById(R.id.next_step_room_type);

        roomTypeItems = new ArrayList<>();
        roomTypeItems.add(new RoomTypeItem("Phòng đơn"));
        roomTypeItems.add(new RoomTypeItem("Phòng đôi"));
        roomTypeItems.add(new RoomTypeItem("Phòng vip"));

        adapter = new RoomTypeSettingsAdapter(getApplicationContext(), roomTypeItems);
        roomType_listview.setAdapter(adapter);
    }
}

