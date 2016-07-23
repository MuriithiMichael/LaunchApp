package com.tmp.micah.launcherapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockedApps extends AppCompatActivity {
    private PackageManager manager;
    private List<AppDetails> apps;
    private ListView list;
    ManagePackages DBASE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_apps);
        //setSupportActionBar(toolbar);
        setTitle("Blocked Applications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadApps2();
        loadListView();
        addClickListener();
    }
    public void loadApps(){
        manager = getPackageManager();
        apps = new ArrayList<AppDetails>();

        Intent i = new Intent(Intent.ACTION_MAIN,null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i,0);

        for(ResolveInfo ri : availableActivities){
            AppDetails app = new AppDetails();
            app.label = ri.loadLabel(manager);
            app.name = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(manager);
            apps.add(app);
        }
    }
    public void loadApps2(){
        try {
            manager = getPackageManager();
            apps = new ArrayList<AppDetails>();
            DBASE = new ManagePackages(getApplicationContext());
            ArrayList<HashMap<String,String>> allApps = DBASE.getApps(2);
            for(int i=0;i<allApps.size();i++){
                String id,packageName,status;
                HashMap<String,String> packageData = new HashMap<String, String>();
                packageData = allApps.get(i);
                packageName=packageData.get("package_name");
                status = packageData.get("status");
                ApplicationInfo app = this.getPackageManager().getApplicationInfo(packageName,0);
                AppDetails appDetails = new AppDetails();
                appDetails.icon = manager.getApplicationIcon(app);
                appDetails.label = manager.getApplicationLabel(app).toString();
                appDetails.name= app.packageName;
                appDetails.status = status;
                apps.add(appDetails);
            }
//            ApplicationInfo app = this.getPackageManager().getApplicationInfo("com.tmp.micah.childprotect",0);
//            final Intent mainIntent= new Intent(Intent.ACTION_MAIN,null);
//            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            //List<ResolveInfo>pacsList = pm.queryIntentActivities(mainIntent,0);
//            pacs = new Pac[2];
//            for(int i=0; i<2; i++){
//                pacs[i] = new Pac();
//                pacs[i].icon = pm.getApplicationIcon(app);//pacsList.get(i).loadIcon(pm);
//                pacs[i].name = "com.tmp.micah.childprotect";//pacsList.get(i).activityInfo.packageName;
//                pacs[i].label = pm.getApplicationLabel(app).toString(); //pacsList.get(i).loadLabel(pm).toString();
//            }
            // new SortApps().exchangeSort(apps);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void loadListView(){
        list = (ListView)findViewById(R.id.blocked_app_list);

        ArrayAdapter<AppDetails> adapter = new ArrayAdapter<AppDetails>(this,R.layout.list_item2,apps){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.list_item2,null);
                }

                ImageView appIcon = (ImageView)convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appLabel = (TextView)convertView.findViewById(R.id.item_app_label);
                appLabel.setText(apps.get(position).label);

                final String application_name = ""+apps.get(position).name;
                TextView appName = (TextView)convertView.findViewById(R.id.item_app_name);
                appName.setText(application_name);

                return convertView;
            }
        };
        list.setAdapter(adapter);
    }

    private void addClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = manager.getLaunchIntentForPackage(apps.get(position).name.toString());
                BlockedApps.this.startActivity(i);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Toast.makeText(getApplicationContext(), "Home Clicked",
//                        Toast.LENGTH_LONG).show();
// go to previous activity
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
