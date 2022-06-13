package com.temp.wisatabinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class CampusActivity extends AppCompatActivity {

    private CampusHelper campusHelper = new CampusHelper(this);
    ArrayList<Campus> campuses;
    User user;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "myPreference";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);

        campusHelper.open();
        campuses = campusHelper.getCampuses();
        campusHelper.close();

        System.out.println("Size : " + campuses.size());

        RecyclerView campusRecycler = findViewById(R.id.rv_campuses);
        CampusAdapter campusAdapter = new CampusAdapter(this);
        campusAdapter.setCampuses(campuses);
        campusRecycler.setAdapter(campusAdapter);
        campusRecycler.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.favourite_campus_page:
                intent = new Intent (this, FavouriteCampusActivity.class);
                startActivity(intent);
                break;
            case R.id.profile_page:
                intent = new Intent (this, ProfileActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

}