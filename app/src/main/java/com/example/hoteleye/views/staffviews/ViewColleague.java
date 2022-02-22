package com.example.hoteleye.views.staffviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.hoteleye.adapters.ViewColleagueAdapter;
import com.example.hoteleye.R;
import com.example.hoteleye.viewmodels.ViewColleagueItem;

import java.util.ArrayList;

public class ViewColleague extends AppCompatActivity {
    private ListView listView;
    ViewColleagueAdapter viewColleagueAdapter;
    ArrayList<ViewColleagueItem> viewColleagueItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_colleague);
        init();
    }

    private void init() {
        listView = findViewById(R.id.listview_view_colleague);

        viewColleagueItems.add(new ViewColleagueItem(1,"Phan Tiến Thành", "25/10/1998","0396065919"));
        viewColleagueItems.add(new ViewColleagueItem(1,"Phan Tiến Thành", "25/10/1998","0396065919"));
        viewColleagueItems.add(new ViewColleagueItem(1,"Phan Tiến Thành", "25/10/1998","0396065919"));
        viewColleagueItems.add(new ViewColleagueItem(1,"Phan Tiến Thành", "25/10/1998","0396065919"));
        viewColleagueItems.add(new ViewColleagueItem(1,"Phan Tiến Thành", "25/10/1998","0396065919"));

        viewColleagueAdapter = new ViewColleagueAdapter(getApplicationContext(), viewColleagueItems);

        listView.setAdapter(viewColleagueAdapter);
    }
}
