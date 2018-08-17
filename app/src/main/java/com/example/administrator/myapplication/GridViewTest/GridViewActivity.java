package com.example.administrator.myapplication.GridViewTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utils.DensityUtil;
import com.example.administrator.myapplication.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/6/29.
 *  android:numColumns="2"设置列数
 *  android:horizontalSpacing="3dp"水平间隔
    android:verticalSpacing="1dp"垂直间隔

    //下面两行可以设置item距离顶部的间隔
    android:clipToPadding="false"
    android:paddingTop="10dp"
 */
@ShowActivity
public class GridViewActivity extends AppCompatActivity{

    private GridView gridView;
    private ArrayList<GridViewItem> dataList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_layout);
        gridView = findViewById(R.id.gridView);
        for (int i = 0; i < 100; i++) {
            GridViewItem gridViewItem = new GridViewItem(i+"");
            dataList.add(gridViewItem);
        }
        GridViewAdapt gridViewAdapt = new GridViewAdapt(this,dataList);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)gridView.getLayoutParams();
        //为了使item在底部与底部有一样的间隔(item的数量*item高度+（顶部距离+底部距离）+中间)
        layoutParams.height = DensityUtil.dip2px(this,5*40+(2*5)+(4*10));

        gridView.setAdapter(gridViewAdapt);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.showToast(GridViewActivity.this,dataList.get(position).getName());

            }
        });

    }
}
