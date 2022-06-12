package com.temp.wisatabinus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CampusDetailActivity extends AppCompatActivity {

    FavouritesHelper favouritesHelper = new FavouritesHelper(this);
    private Campus campus;
    private User user;
    private boolean favourite;

    TextView tvCampusName, tvCampusLocation, tvCampusAddress;
    ImageView ivCampusImage;
    Button btnFavourite,btnViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_detail);

        Intent intent = getIntent();
        campus = (Campus) intent.getSerializableExtra("campus");
        user = (User) intent.getSerializableExtra("user");

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
        favourite = favouritesHelper.favouriteCheck(user.getUserID(), campus.getCampusID());
        System.out.println("favourite check");
        favouritesHelper.close();

        if(favourite){
            btnFavourite.setText("remove from favourite");
            btnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favouritesHelper.open();
                    favouritesHelper.removeFavourite(user.getUserID(), campus.getCampusID());
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
                    favouritesHelper.addFavourite(user.getUserID(), campus.getCampusID());
                    boolean check = favouritesHelper.favouriteCheck(user.getUserID(), campus.getCampusID());
                    favouritesHelper.close();
                    System.out.println("add to favourite clicked, check = " + check);
                    recreate();
                }
            });
        }

    }
}