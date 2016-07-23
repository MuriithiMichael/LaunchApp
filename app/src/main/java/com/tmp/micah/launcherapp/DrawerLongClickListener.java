package com.tmp.micah.launcherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * Created by Micah on 17/05/2016.
 */
public class DrawerLongClickListener implements AdapterView.OnItemLongClickListener{

    SlidingDrawer drawerForAdapter;
    RelativeLayout homeViewForAdapter;
    Context mContext;
    MainActivity.Pac[] pacsForListener;

    public DrawerLongClickListener(Context ctxt, SlidingDrawer slidingDrawer, RelativeLayout homeView, MainActivity.Pac[] pacs){
        mContext = ctxt;
        drawerForAdapter = slidingDrawer;
        homeViewForAdapter = homeView;
        pacsForListener = pacs;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity.appLaunchable = false;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getWidth(),view.getHeight());
        lp.leftMargin = (int) view.getX();
        lp.topMargin = (int)view.getY();

        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = (LinearLayout) li.inflate(R.layout.drawer_item, null);
        ((ImageView)ll.findViewById(R.id.icon_image)).setImageDrawable(((ImageView)view.findViewById(R.id.icon_image)).getDrawable());
        ((TextView)ll.findViewById(R.id.icon_text)).setText(((TextView)view.findViewById(R.id.icon_text)).getText());

        ll.setOnTouchListener(new AppTouchListener(view.getWidth()));
        ll.setOnClickListener(new AppClickListener(mContext, pacsForListener));
        String [] data = new String [2];
        data[0] = pacsForListener[position].packageName;
        data[1] = pacsForListener[position].name;
        ll.setTag(data);
        homeViewForAdapter.addView(ll,lp);
        drawerForAdapter.animateClose();
        drawerForAdapter.bringToFront();
        return false;
    }
}
