package com.tmp.micah.launcherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    Button btnLoginL, btnRegisterL;
    EditText txtPin1, txtPin2, txtKeyword;
    String keyword, pin,cpin;
    ManagePackages DBASE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        btnLoginL = (Button)findViewById(R.id.btnLogin);
        txtPin1 = (EditText)findViewById(R.id.txtPin1);
        txtPin2 = (EditText)findViewById(R.id.txtPin2);
        txtKeyword = (EditText)findViewById(R.id.txtKeyword);
        btnLoginL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = txtKeyword.getText().toString().trim();
                pin = txtPin1.getText().toString().trim();
                Log.d("Variable",keyword+" "+pin);
                Intent RegisterActivity = new Intent(Register.this,Login.class);
                startActivity(RegisterActivity);
            }
        });
        btnRegisterL = (Button) findViewById(R.id.btnRegister);
        btnRegisterL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent RegisterActivity = new Intent(Login2.this,Register.class);
//                startActivity(RegisterActivity);
                keyword = txtKeyword.getText().toString().trim();
                pin = txtPin1.getText().toString().trim();
                cpin = txtPin2.getText().toString();
                if(keyword.equals("") || pin.equals("") || cpin.equals("") ){
                    Toast.makeText(getApplicationContext(), "Fill all fields.", Toast.LENGTH_SHORT).show();
                }else{
                    if(!pin.equals(cpin)){
                        Toast.makeText(getApplicationContext(), "Pins do not match", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("Variable",keyword+" "+pin);
                        addUser(keyword, pin);
                    }
                }
//                Log.d("Variable",keyword+" "+pin);
//                addUser(keyword, pin);
            }
        });
    }
    public void addUser(String keyword,String pin){
        DBASE = new ManagePackages(getApplicationContext());
        boolean saveStatus=DBASE.addNewUser(keyword,pin);

        if(saveStatus==true){
            Toast.makeText(getApplicationContext(), "User saved successfully.", Toast.LENGTH_SHORT).show();
            Intent RegisterActivity = new Intent(Register.this,Login.class);
            startActivity(RegisterActivity);
            txtKeyword.setText("");
            txtPin1.setText("");
            txtPin2.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(),"Not added",Toast.LENGTH_LONG).show();
        }
    }
}
