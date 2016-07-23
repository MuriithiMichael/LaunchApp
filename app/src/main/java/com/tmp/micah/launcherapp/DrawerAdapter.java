package com.tmp.micah.launcherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Micah on 10/05/2016.
 */
public class DrawerAdapter extends BaseAdapter {
    Context mContext;
    MainActivity.Pac[] pacsForAdapter;
    public DrawerAdapter(Context c, MainActivity.Pac[] pacs){
        mContext = c;
        pacsForAdapter = pacs;
    }
    @Override
    public int getCount() {
        return pacsForAdapter.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        TextView text;
        ImageView icon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView = li.inflate(R.layout.drawer_item,null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView)convertView.findViewById(R.id.icon_text);
            viewHolder.icon = (ImageView)convertView.findViewById(R.id.icon_image);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.text.setText(pacsForAdapter[position].label);
        viewHolder.icon.setImageDrawable(pacsForAdapter[position].icon);
        return convertView;
    }
}
