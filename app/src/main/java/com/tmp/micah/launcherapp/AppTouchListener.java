package com.tmp.micah.launcherapp;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Micah on 22/05/2016.
 */
public class AppTouchListener implements View.OnTouchListener {
    int iconSize;
    public AppTouchListener(int size){
        iconSize = size;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(iconSize,iconSize);
                lp.leftMargin = (int) event.getRawX()-iconSize/2;
                lp.topMargin = (int)event.getRawY()-iconSize/2;
                v.setLayoutParams(lp);

        }
        return false;
    }
}
