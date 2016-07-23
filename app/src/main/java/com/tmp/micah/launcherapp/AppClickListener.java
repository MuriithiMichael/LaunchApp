package com.tmp.micah.launcherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;

/**
 * Created by Micah on 22/05/2016.
 */
public class AppClickListener implements View.OnClickListener {
    Context mContext;
    MainActivity.Pac[] pacsForListener;
    PackageManager pmForListener;
    public AppClickListener(Context ctxt, MainActivity.Pac[] pacs){
    pacsForListener = pacs;
        mContext = ctxt;
    }

    @Override
    public void onClick(View v) {
        String [] data = (String [])v.getTag();
        Intent launchIntent = pmForListener.getLaunchIntentForPackage(data[0]);
//            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
//            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName cp = new ComponentName(pacsForAdapter[position].packageName,pacsForAdapter[position].name);
//            launchIntent.setComponent(cp);
        mContext.startActivity(launchIntent);

    }
}
