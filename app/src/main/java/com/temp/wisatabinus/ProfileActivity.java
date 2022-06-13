package com.temp.wisatabinus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView tvEmailAddress, tvPhoneNumber;
    EditText etOldPassword, etNewPassword;
    Button btnChangePassword, btnLogout;

    private User user;
    UserHelper userHelper = new UserHelper(this);
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "myPreference";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        Integer userID = sharedPreferences.getInt(KEY_ID, -1);
        userHelper.open();
        user = userHelper.getUserByID(userID);
        userHelper.close();

        tvEmailAddress = findViewById(R.id.tv_email_address);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnLogout = findViewById(R.id.btn_logout);

        tvEmailAddress.setText(user.getUserEmailAddress());
        tvPhoneNumber.setText(user.getUserPhoneNumber());

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = etOldPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();
                boolean validate = validateChangePassword(oldPassword, newPassword);
                if (validate){
                    userHelper.open();
                    userHelper.changePassword(userID, newPassword);
                    userHelper.close();
                    Toast.makeText(ProfileActivity.this, "Change Password Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean validateChangePassword(String oldPassword, String newPassword) {
        boolean validate = true;
        String message = "";

        if (!oldPassword.equals(user.getUserPassword())){
            message = message + "your old password are incorrect\n";
            validate = false;
        }

        int n = newPassword.length();
        if (n < 8) {
            message = message + "password must be at least eight characters\n";
            validate = false;
        }

        boolean num = false, alph = false;
        for (int i = 0; i < n; i++) {
            char c = newPassword.charAt(i);
            if (c >= 'A' && c < 'Z') alph = true;
            else if (c >= 'a' && c < 'z') alph = true;
            else if (c >= '0' && c < '9') num = true;
        }
        if (!num || !alph) {
            message = message + "password must contain both letters and numbers\n";
            validate = false;
        }

        if (!validate){
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setTitle("Change Password Failed");
            builder.setMessage(message);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return validate;
    }
}