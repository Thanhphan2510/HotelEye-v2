package com.example.hoteleye.views.managerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hoteleye.adapters.DetailRoomTypeSettingsAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.DetailRoomTypeItem;

import java.util.ArrayList;
import java.util.List;

public class DetailRoomTypeSettings extends AppCompatActivity {
    ListView listView;
    Button next_btn;
    ArrayList<DetailRoomTypeItem> detailRoomTypeItems;
    List<String> spinnerList;
    DetailRoomTypeSettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room_type_settings);
        init();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }

    private void init() {
        listView = findViewById(R.id.detail_room_type_listview);

        next_btn = findViewById(R.id.next_btn_detail_room_type);
        detailRoomTypeItems = new ArrayList<>();
        spinnerList = new ArrayList<>();

        spinnerList.add("Phòng đơn");
        spinnerList.add("Phòng đôi");
        spinnerList.add("Phòng vip");

        detailRoomTypeItems.add(new DetailRoomTypeItem("301", spinnerList));
        detailRoomTypeItems.add(new DetailRoomTypeItem("302", spinnerList));
        detailRoomTypeItems.add(new DetailRoomTypeItem("303", spinnerList));
        detailRoomTypeItems.add(new DetailRoomTypeItem("304", spinnerList));
        detailRoomTypeItems.add(new DetailRoomTypeItem("305", spinnerList));


        adapter = new DetailRoomTypeSettingsAdapter(getApplicationContext(), detailRoomTypeItems);
        listView.setAdapter(adapter);
    }
    private void switchView() {
        Intent intent = new Intent(DetailRoomTypeSettings.this, ServiceSettings.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
//        finish();
    }
}
