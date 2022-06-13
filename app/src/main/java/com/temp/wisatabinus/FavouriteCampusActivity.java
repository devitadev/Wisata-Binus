package com.temp.wisatabinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouriteCampusActivity extends AppCompatActivity {

    private FavouritesHelper favouritesHelper = new FavouritesHelper(this);

    private ArrayList<Campus> favourites;
    private Integer userID;

    TextView tvEmptyFavourite;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "myPreference";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_campus);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        userID = sharedPreferences.getInt(KEY_ID, -1);

        tvEmptyFavourite = findViewById(R.id.tv_empty_favourite);

        favouritesHelper.open();
        favourites = favouritesHelper.getFavourites(userID);
        favouritesHelper.close();

        if(favourites.size() > 0){
            RecyclerView campusRecycler = findViewById(R.id.rv_campus);
            CampusAdapter campusAdapter = new CampusAdapter(this);
            campusAdapter.setCampuses(favourites);
            campusRecycler.setAdapter(campusAdapter);
            campusRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else {
            tvEmptyFavourite.setText("There are no favourite campuses");
            tvEmptyFavourite.setPadding(0, 100, 0, 0);
        }
    }
}