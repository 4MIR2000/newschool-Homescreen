package de.newschool.homescreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.newschool.homescreen.R;

class DrawerAdapter extends BaseAdapter {
    private final Context mcontext;
    // AppDetail[] applist;

    public DrawerAdapter() {
        mcontext = MainActivity.getContext();
        //this.applist = list;
    }

    @Override
    public int getCount() {
        return MainActivity.getApps().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;

        if (convertView == null) {
            //convertView is the View which will apear on the list
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.text = (TextView) convertView.findViewById(R.id.item_app_label);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.item_app_icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ArrayList<AppDetail> apps = MainActivity.getApps();

        if(apps.size() >position) {
            viewHolder.text.setText(apps.get(position).label);
            viewHolder.icon.setImageDrawable(MainActivity.getApps().get(position).icon);

        }
        return convertView;
    }

    static class ViewHolder {
        TextView text;
        ImageView icon;
    }
}
