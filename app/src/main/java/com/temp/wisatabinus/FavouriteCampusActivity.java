package com.temp.wisatabinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouriteCampusActivity extends AppCompatActivity {

    private FavouritesHelper favouritesHelper = new FavouritesHelper(this);

    private ArrayList<Campus> favourites;
    private User user;

    TextView tvEmptyFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_campus);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        tvEmptyFavourite = findViewById(R.id.tv_empty_favourite);

        favouritesHelper.open();
        favourites = favouritesHelper.getFavourites(user.getUserID());
        favouritesHelper.close();

        if(favourites.size() > 0){
            RecyclerView campusRecycler = findViewById(R.id.rv_campus);
            CampusAdapter campusAdapter = new CampusAdapter(this);
            campusAdapter.setCampuses(favourites);
            campusAdapter.setUser(user);
            campusRecycler.setAdapter(campusAdapter);
            campusRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else {
            tvEmptyFavourite.setText("There are no favourite campuses");
            tvEmptyFavourite.setPadding(0, 100, 0, 0);
        }
    }
}