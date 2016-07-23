package com.tmp.micah.launcherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button btnLoginL, btnRegisterL;
    EditText txtPinL;
    String pin;
    ManagePackages DBASE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        txtPinL = (EditText)findViewById(R.id.txtPinL);
        btnLoginL = (Button)findViewById(R.id.btnLoginL);
        btnLoginL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = txtPinL.getText().toString();
                if(pin.equals("")){
                    Toast.makeText(getApplicationContext(), "Enter Pin", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(pin);
                }
            }
        });
        btnRegisterL = (Button)findViewById(R.id.btnRegisterL);
        btnRegisterL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginActivity = new Intent(Login.this,Register.class);
                startActivity(LoginActivity);
            }
        });
    }
    public void loginUser(String pin){
        DBASE = new ManagePackages(getApplicationContext());
        boolean loginStatus=DBASE.select_LoginUser(pin);

        if(loginStatus==true){
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this,Home.class);
            startActivity(intent);
            txtPinL.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
        }
    }
}
