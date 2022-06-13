package com.temp.wisatabinus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CampusDetailActivity extends AppCompatActivity {

    FavouritesHelper favouritesHelper = new FavouritesHelper(this);
    private Campus campus;
    private Integer userID;
    private boolean favourite;

    TextView tvCampusName, tvCampusLocation, tvCampusAddress;
    ImageView ivCampusImage;
    Button btnFavourite,btnViewLocation;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "myPreference";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_detail);

        Intent intent = getIntent();
        campus = (Campus) intent.getSerializableExtra("campus");

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        userID = sharedPreferences.getInt(KEY_ID, -1);

        tvCampusName = findViewById(R.id.tv_campus_name);
        tvCampusLocation = findViewById(R.id.tv_campus_location);
        tvCampusAddress = findViewById(R.id.tv_campus_address);
        ivCampusImage = findViewById(R.id.iv_campus_image);
        btnFavourite = findViewById(R.id.btn_favourite);
        btnViewLocation = findViewById(R.id.btn_view_location);

        tvCampusName.setText(campus.getCampusName());
        tvCampusLocation.setText(campus.getCampusLocation());
        tvCampusAddress.setText(campus.getCampusAddress());
        String imageURL = "https://wisata-binus.herokuapp.com/assets/" + campus.getCampusImage();
        Picasso.with(this).load(imageURL).into(ivCampusImage);

        // cari tau apakah campus ini favourite
        favouritesHelper.open();
        favourite = favouritesHelper.favouriteCheck(userID, campus.getCampusID());
        System.out.println("favourite check");
        favouritesHelper.close();

        if(favourite){
            btnFavourite.setText("remove from favourite");
            btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favouritesHelper.open();
                    favouritesHelper.removeFavourite(userID, campus.getCampusID());
                    favouritesHelper.close();
                    recreate();
                }
            });
        }
        else{
            btnFavourite.setText("add to favourite");
            btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favouritesHelper.open();
                    favouritesHelper.addFavourite(userID, campus.getCampusID());
                    boolean check = favouritesHelper.favouriteCheck(userID, campus.getCampusID());
                    favouritesHelper.close();
                    System.out.println("add to favourite clicked, check = " + check);
                    recreate();
                }
            });
        }

        btnViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampusDetailActivity.this, CampusLocationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("campus", campus);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}