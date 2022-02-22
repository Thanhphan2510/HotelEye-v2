package com.example.hoteleye.views.managerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hoteleye.adapters.FurnitureSettingsAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.FurnitureItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FurnitureSettings extends AppCompatActivity {
    private ListView listView;
    private Button next_button;
    private FloatingActionButton add_fab;
    ArrayList<FurnitureItem> furnitureItems;
    FurnitureSettingsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_settings);
        init();
    }

    private void init() {
        listView = findViewById(R.id.furniture_listview);
        add_fab = findViewById(R.id.add_floor_fab);
        next_button = findViewById(R.id.next_btn_furniture_setting);

        furnitureItems = new ArrayList<>();
        furnitureItems.add(new FurnitureItem("Giường","Cái",500000,"Không"));
        furnitureItems.add(new FurnitureItem("Tủ lạnh","Cái",2000000,"Không"));
        furnitureItems.add(new FurnitureItem("Bàn ghế","Bộ",4000000,"Không"));
        furnitureItems.add(new FurnitureItem("Tủ quần áo","Cái",2000000,"Không"));

        adapter = new FurnitureSettingsAdapter(getApplicationContext(), furnitureItems);
        listView.setAdapter(adapter);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }

    private void switchView() {
        Intent intent = new Intent(FurnitureSettings.this, AddRoomTypeSettings.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
//        finish();
    }
}
