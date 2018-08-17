package com.example.administrator.myapplication.GridViewTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/6/29.
 */

public class GridViewAdapt extends BaseAdapter {

    private Context context;
    private ArrayList<GridViewItem> dataList;
    private int num = 7;
    private int hight=-1;
    private int width=-1;

    public GridViewAdapt(Context context, ArrayList<GridViewItem> dataList) {
        this.context = context;
//        hight = DensityUtil.dip2px(context,60);
//        width = DensityUtil.dip2px(context,100);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            // 获得容器
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_item, null);
            viewHolder.tv = convertView.findViewById(R.id.text);
            //layoutParams.width = 0;
            // 初始化组件

            // 给converHolder附加一个对象
            convertView.setTag(viewHolder);
//            AbsListView.LayoutParams lp=new AbsListView.LayoutParams(Utils.getScreenWidth(context)/4, LinearLayout.LayoutParams.WRAP_CONTENT);
//            convertView.setLayoutParams(lp);
        } else
        {
            if(width!=-1&&hight!=-1) {
                convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv = convertView.findViewById(R.id.text);
                if (width != -1 && hight != -1) {
                    convertView.setPadding(0, (hight/(num+1)/4), 0, (hight/(num+1)/4));
                    ViewGroup.LayoutParams tvl = viewHolder.tv.getLayoutParams();

                    if (tvl == null) {
                        ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(width / 3, (hight - 2*num *(hight/(num+1)/4)) / num);
                        convertView.setLayoutParams(layout);
                    } else {

                        tvl.height = (hight - 2*num * (hight/(num+1)/4)) / num;
                        tvl.width = width / 3;
                    }
                }
                convertView.setTag(viewHolder);
            }
            // 取得converHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(dataList.get(position).getName());

        return convertView;
    }

    class ViewHolder
    {
        public TextView tv;
    }


    public ArrayList<GridViewItem> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<GridViewItem> dataList) {
        this.dataList = dataList;
    }


    public void setWidthAndHight(int width,int hight) {
        this.width = width;
        this.hight = hight;
        notifyDataSetChanged();
    }


}
