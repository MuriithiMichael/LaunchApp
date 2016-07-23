package com.tmp.micah.launcherapp;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
    ManagePackages DBASE;
    GridView drawerGrid;
    SlidingDrawer slidingDrawer;
    RelativeLayout homeView;
    DrawerAdapter drawerAdapterObject;
    class Pac{
        Drawable icon;
        String name;
        String packageName;
        String label;
    }
    Pac[] pacs;
    PackageManager pm, pmFromDB;
    AppWidgetManager mappWidgetManager;
    AppWidgetHost mappWidgetHost;
    int REQUEST_CREATE_APPWIDGET = 900;

    static boolean appLaunchable = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappWidgetManager = AppWidgetManager.getInstance(this);
        mappWidgetHost = new AppWidgetHost(this,R.id.APPWIDGET_HOST_ID);


        drawerGrid = (GridView) findViewById(R.id.content);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);
        homeView = (RelativeLayout) findViewById(R.id.home_view);
        pm = getPackageManager();
        addPacs();
        setPacs2();
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                appLaunchable = true;
            }
        });
        //setPacs();

        homeView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //selectWidget();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                return false;
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(new pacsReceiver(),filter);
        final Handler handler = new Handler();

        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                addPacs();
                setPacs2();
                handler.postDelayed(this, 15 * 1000);
            }
        };

        handler.postDelayed(refresh, 15 * 1000);
    }
   public void addPackage(String packageName,int status){
        DBASE = new ManagePackages(getApplicationContext());
        boolean saveStatus=DBASE.addNewPackage(packageName,status);

        if(saveStatus==true){
            Toast.makeText(getApplicationContext(), "Package saved successfully.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Not added",Toast.LENGTH_LONG).show();
        }
    }
    public void addUser(String keyword,String pin){
        DBASE = new ManagePackages(getApplicationContext());
        boolean saveStatus=DBASE.addNewUser(keyword,pin);

        if(saveStatus==true){
            Toast.makeText(getApplicationContext(), "User saved successfully.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Not added",Toast.LENGTH_LONG).show();
        }
    }

    public void getApps(){
        DBASE = new ManagePackages(getApplicationContext());
        ArrayList<HashMap<String,String>> allApps = DBASE.getApps(0);

        for(int i=0;i<allApps.size();i++){
            String id,packageName,status;
            HashMap<String,String> packageData = new HashMap<String, String>();
            packageData = allApps.get(i);

              id=packageData.get("id");



        }

    }

    public void setPacs2(){
        try {
            DBASE = new ManagePackages(getApplicationContext());
            ArrayList<HashMap<String,String>> allApps = DBASE.getApps(1);
            pacs = new Pac[allApps.size()];
            for(int i=0;i<allApps.size();i++){
                String id,packageName,status;
                HashMap<String,String> packageData = new HashMap<String, String>();
                packageData = allApps.get(i);
                packageName=packageData.get("package_name");
                ApplicationInfo app = this.getPackageManager().getApplicationInfo(packageName,0);
                pacs[i] = new Pac();
                pacs[i].icon = pm.getApplicationIcon(app);
                pacs[i].label = pm.getApplicationLabel(app).toString();
                pacs[i].name= app.name;
                pacs[i].packageName= packageName;
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
            new SortApps().exchangeSort(pacs);
            drawerAdapterObject = new DrawerAdapter(this, pacs);
            drawerGrid.setAdapter(drawerAdapterObject);
            drawerGrid.setOnItemClickListener(new DrawerClickListener(this,pacs,pm));
            drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(this,slidingDrawer,homeView,pacs));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void setPacs(){
        final Intent mainIntent= new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo>pacsList = pm.queryIntentActivities(mainIntent,0);
        pacs = new Pac[pacsList.size()];
        for(int i=0; i<pacsList.size(); i++){
            pacs[i] = new Pac();
            pacs[i].icon = pacsList.get(i).loadIcon(pm);
            pacs[i].packageName = pacsList.get(i).activityInfo.packageName;
            pacs[i].name = pacsList.get(i).activityInfo.name;
            pacs[i].label = pacsList.get(i).loadLabel(pm).toString();
        }
        new SortApps().exchangeSort(pacs);
        drawerAdapterObject = new DrawerAdapter(this, pacs);
        drawerGrid.setAdapter(drawerAdapterObject);
        drawerGrid.setOnItemClickListener(new DrawerClickListener(this,pacs,pm));
        drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(this,slidingDrawer,homeView,pacs));
    }

    public void addPacs(){
        DBASE = new ManagePackages(getApplicationContext());
        final Intent mainIntent= new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo>pacsList = pm.queryIntentActivities(mainIntent,0);
        //pacs = new Pac[pacsList.size()];
        for(int i=0; i<pacsList.size(); i++){
            String package_name=  pacsList.get(i).activityInfo.packageName;
            DBASE.addNewPackage2(package_name,1);
        }
        //new SortApps().exchangeSort(pacs);
//        drawerAdapterObject = new DrawerAdapter(this, pacs);
//        drawerGrid.setAdapter(drawerAdapterObject);
//        drawerGrid.setOnItemClickListener(new DrawerClickListener(this,pacs,pm));
//        drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(this,slidingDrawer,homeView));
    }


    public class pacsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            setPacs2();
        }
    }

    void selectWidget(){
        int appWidget = this.mappWidgetHost.allocateAppWidgetId();
        Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidget);
        addEmptyData(pickIntent);
        startActivityForResult(pickIntent,R.id.REQUEST_PICK_APPWIDGET);
    }

    void addEmptyData(Intent pickIntent){
        ArrayList customInfo = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO,customInfo);
        ArrayList customExtras = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS,customExtras);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == R.id.REQUEST_PICK_APPWIDGET){
                    configureWidget(data);
            }
            else if(requestCode == REQUEST_CREATE_APPWIDGET){
                    createWidget(data);
            }
        }
        else if(resultCode == RESULT_CANCELED && data != null){
            int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,-1);
            if(appWidgetId != -1){
                mappWidgetHost.deleteAppWidgetId(appWidgetId);
            }
        }

    }

    private void configureWidget(Intent data){
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mappWidgetManager.getAppWidgetInfo(appWidgetId);
        if(appWidgetInfo.configure != null){
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            intent.setComponent(appWidgetInfo.configure);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
        }else{
            createWidget(data);
        }

    }

    public void createWidget(Intent data){
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mappWidgetManager.getAppWidgetInfo(appWidgetId);
        AppWidgetHostView hostView = mappWidgetHost.createView(this, appWidgetId, appWidgetInfo);
        hostView.setAppWidget(appWidgetId,appWidgetInfo);
        //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getWidth(),view.getHeight());
        homeView.addView(hostView);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mappWidgetHost.startListening();
    }

    @Override
    protected  void onStop(){
        super.onStop();
        mappWidgetHost.stopListening();
    }
}
