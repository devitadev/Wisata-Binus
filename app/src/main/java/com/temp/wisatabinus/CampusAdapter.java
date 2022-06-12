package com.temp.wisatabinus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CampusAdapter extends RecyclerView.Adapter<CampusAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Campus> campuses;
    private User user;

    public CampusAdapter(Context context) { this.context = context; }
    public void setCampuses(ArrayList<Campus> campuses) { this.campuses = campuses; }
    public void setUser(User user) { this.user = user; }

    @NonNull
    @Override
    public CampusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.campus_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampusAdapter.ViewHolder holder, int position) {
        holder.tvCampusName.setText(campuses.get(position).getCampusName());
        holder.tvCampusLocation.setText(campuses.get(position).getCampusLocation());
        String imageURL = "https://wisata-binus.herokuapp.com/assets/" + campuses.get(position).getCampusImage();
        Picasso.with(context).load(imageURL).into(holder.ivCampusImage);

        holder.cvCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CampusDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("campus", campuses.get(position));
                bundle.putSerializable("user", user);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return campuses.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCampusName, tvCampusLocation;
        ImageView ivCampusImage;
        CardView cvCampus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCampusName = itemView.findViewById(R.id.tv_campus_name);
            tvCampusLocation = itemView.findViewById(R.id.tv_campus_location);
            ivCampusImage = itemView.findViewById(R.id.iv_campus_image);
            cvCampus = itemView.findViewById(R.id.cv_campus);
        }
    }

}
