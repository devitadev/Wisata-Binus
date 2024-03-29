package com.temp.wisatabinus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    UserHelper userHelper = new UserHelper(this);
    ArrayList<User> users;

    EditText etEmail, etPhoneNumber, etPassword;
    Button btnLogin, btnRegister;

    Integer sms_permission;
    SmsManager sm;

    boolean register;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        userHelper.open();
        users = userHelper.getUsers();
        userHelper.close();

        sm = SmsManager.getDefault();
        sms_permission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (sms_permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},1);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();
                register = true;
                message = "";

                validateEmail(email);
                validatePhoneNumber(phoneNumber);
                validatePassword(password);

                // kalo register berhasil
                if(register){
                    userHelper.open();
                    userHelper.registerUser(email, phoneNumber, password);
                    userHelper.close();

                    // kirim SMS register successfull
                    sm.sendTextMessage(phoneNumber, null, "Account registration successfull!", null, null);

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Register Failed");
                    builder.setMessage(message);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validateEmail(String email){
        // email harus diakhiri .com
        if(!email.endsWith(".com")){
            message = "email must end with '.com'\n";
            register = false;
        }
        // email tidak boleh dimulai dengan @
        if(email.startsWith("@")){
            message = message + "email must not start with ‘@’\n";
            register= false;
        }
        // email hanya ada 1 @ (email biasa harus 1)
        int n = email.length();
        int count = 0;
        for(int i=0; i<n; i++){
            if(email.charAt(i) == '@') count++;
        }
        if(count != 1){
            message = message + "email must contain only one ‘@’\n";
            register = false;
        }
        // validasi email tidak pernah diregister
        n = users.size();
        for(int i=0; i<n; i++){
            if(users.get(i).getUserEmailAddress().equals(email)){
                message = message + "your email already been registered\n";
                register = false;
            }
        }
    }

    private void validatePhoneNumber(String phoneNumber){
        // phone number harus 10 - 12 digits
        if(phoneNumber.length() < 10 || phoneNumber.length() > 12) {
            message = message + "phone number must be 10-12 digits\n";
            register = false;
        }
        // phone number harus dimulai dengan 08
        if (!phoneNumber.startsWith("08")){
            message = message + "phone number must start with '08'\n";
            register = false;
        }
    }

    private void validatePassword(String password){
        int n = password.length();
        if(n < 8){
            message = message + "password must be at least eight characters\n";
            register = false;
        }
        // password harus ad number dan alphabet
        boolean num = false, alph = false;
        for(int i=0; i<n; i++){
            char c = password.charAt(i);
            if(c >= 'A' && c < 'Z') alph = true;
            else if(c >= 'a' && c < 'z') alph = true;
            else if(c >= '0' && c < '9') num = true;
        }
        if(!num || !alph){
            message = message + "password must contain both letters and numbers\n";
            register = false;
        }
    }

}