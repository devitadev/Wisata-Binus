package com.temp.wisatabinus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<Campus> campuses;
    private ArrayList<User> users;
    private CampusHelper campusHelper = new CampusHelper(this);
    private UserHelper userHelper = new UserHelper(this);

    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // kalo DB masih kosong maka fetch campus data and save ke DB
        campusHelper.open();
        campuses = campusHelper.getCampuses();
        campusHelper.close();
        if(campuses.size() == 0){
            fetchCampusData();
        }

        userHelper.open();
        users = userHelper.getUsers();
        userHelper.close();

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                boolean login = false;

                int n = users.size();
                for(int i = 0; i < n; i++){
                    if(email.equals(users.get(i).getUserEmailAddress())){
                        if(password.equals(users.get(i).getUserPassword())) {
                            login = true;
                            Intent intent = new Intent(new Intent(LoginActivity.this, CampusActivity.class));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", users.get(i));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        break;
                    }
                }
                // kalau login gagal tampilin pesan error
                if(!login) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Login Failed");
                    builder.setMessage("Your email address or password is incorrect");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchCampusData(){
        // fetch data dari JSON API
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://wisata-binus.herokuapp.com";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Integer campusID = jsonObject.getInt("campus_id");
                        String campusName = jsonObject.getString("campus_name");
                        String campusLocation = jsonObject.getString("campus_location");
                        String campusAddress = jsonObject.getString("campus_address");
                        Double campusLatitude = jsonObject.getDouble("latitude");
                        Double campusLongitude = jsonObject.getDouble("longitude");
                        String campusImage = jsonObject.getString("campus_image");
                        campusHelper.open();
                        campusHelper.insertCampus(new Campus(campusID, campusName, campusLocation, campusAddress, campusLatitude, campusLongitude, campusImage));
                        campusHelper.close();
                        // System.out.println(campusAddress + " " + campusName + " " + campusLocation + " " + campusAddress + " " + campusLatitude + " " + campusLongitude + " " + campusImage);
                    } catch (JSONException e) {
                        System.out.println("Invalid JSON Format");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("API ERROR");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}