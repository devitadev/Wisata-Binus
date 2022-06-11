package com.temp.wisatabinus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etPhoneNumber, etPassword;
    Button btnLogin, btnRegister;

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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                String password = etPassword.getText().toString();
                register = true;
                message = "";

                validateEmail(email); // validate email ada yang kurang
                validatePhoneNumber(phoneNumber);
                validatePassword(password);

                // kalo register berhasil
                if(register){
                    // kalo berhasil save user data ke DB tapi belom !!
                    // kirim SMS register successfull tapi belom !!

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

    void validateEmail(String email){
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
        // validasi email address must be unique or have not been registered belom !!!
    }

    void validatePhoneNumber(String phoneNumber){
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

    void validatePassword(String password){
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