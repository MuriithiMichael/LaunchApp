package com.tmp.micah.launcherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Micah on 16/05/2016.
 */
public class DrawerClickListener implements AdapterView.OnItemClickListener {

    Context mContext;
    MainActivity.Pac[] pacsForAdapter;
    PackageManager pmForListener;
    public DrawerClickListener(Context c, MainActivity.Pac[] pacs, PackageManager pm){
        mContext = c;
        pacsForAdapter = pacs;
        pmForListener = pm;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(MainActivity.appLaunchable) {
            Intent launchIntent = pmForListener.getLaunchIntentForPackage(pacsForAdapter[position].packageName);
//            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
//            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName cp = new ComponentName(pacsForAdapter[position].packageName,pacsForAdapter[position].name);
//            launchIntent.setComponent(cp);
            mContext.startActivity(launchIntent);
        }
    }
}
