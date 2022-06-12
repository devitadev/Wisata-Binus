package com.temp.wisatabinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CampusActivity extends AppCompatActivity {

    private CampusHelper campusHelper = new CampusHelper(this);
    ArrayList<Campus> campuses;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        campusHelper.open();
        campuses = campusHelper.getCampuses();
        campusHelper.close();

        System.out.println("Size : " + campuses.size());

        RecyclerView campusRecycler = findViewById(R.id.rv_campuses);
        CampusAdapter campusAdapter = new CampusAdapter(this);
        campusAdapter.setCampuses(campuses);
        campusAdapter.setUser(user);
        campusRecycler.setAdapter(campusAdapter);
        campusRecycler.setLayoutManager(new GridLayoutManager(this, 2));

    }
}