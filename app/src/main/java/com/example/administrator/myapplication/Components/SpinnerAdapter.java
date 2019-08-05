package com.example.administrator.myapplication.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.administrator.myapplication.R;

import java.util.ArrayList;

/**
 * Created by wsp on 2019/1/4.
 */

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> dataList;

    public SpinnerAdapter(Context context, ArrayList<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
            holder.itemName = convertView.findViewById(R.id.item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(dataList.get(i));

        return convertView;
    }

    private class ViewHolder {
        TextView itemName;
    }
}
