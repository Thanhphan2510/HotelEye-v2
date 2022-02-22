package com.example.hoteleye.views.managerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hoteleye.R;

public class AddRoomTypeSettings extends AppCompatActivity {
    Button next_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_type_settings);
        init();
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchView();
            }
        });
    }

    private void init() {
        next_btn = findViewById(R.id.next_btn_add_room_type);
    }
    private void switchView() {
        Intent intent = new Intent(AddRoomTypeSettings.this, RoomTypeSettings.class);
//                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
