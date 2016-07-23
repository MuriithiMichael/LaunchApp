package com.tmp.micah.launcherapp;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {
    ListView appHomeView;
    String[] homeArray = {"Block/Unblock Apps","View Blocked Apps","Block Url","Logout"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.home_item, homeArray);
        appHomeView = (ListView)findViewById(R.id.appHomeView);
        appHomeView.setAdapter(adapter);
        appHomeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    //Toast.makeText(getApplicationContext(),"Block Apps",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Home.this,BlockApps.class);
                    startActivity(intent);
                }else if(position==1){
                    //Toast.makeText(getApplicationContext(),"View Blocked Apps",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Home.this,BlockedApps.class);
                    startActivity(intent);
                }else if(position==2){
                    Toast.makeText(getApplicationContext(),"Block URL",Toast.LENGTH_LONG).show();
                }else if(position == 3){
                    Intent intent = new Intent(Home.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
