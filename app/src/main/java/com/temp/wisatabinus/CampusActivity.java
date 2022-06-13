package com.temp.wisatabinus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        Bundle bundle = new Bundle();

        switch(item.getItemId()){
            case R.id.favourite_campus_page:
                intent = new Intent (this, FavouriteCampusActivity.class);
                break;
            case R.id.profile_page:
                intent = new Intent (this, ProfileActivity.class);
                break;
        }

        if(intent != null) {
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return true;
    }

}