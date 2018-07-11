package com.example.administrator.myapplication.FramentTest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/4.
 */
@ShowActivity()
public class TabFramentTest extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    protected ActionBar actionBar;
    private BaseTabAdapter mTabAdapter;
    List<BaseFragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_frament_test);
         initActionBar();
        mViewPager = findViewById(R.id.mViewPager);
        mTabLayout = findViewById(R.id.mTabLayout);
        setTabFragMent();
    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_background);

        actionBar.setElevation(0);

        View view = LayoutInflater.from(TabFramentTest.this).inflate(R.layout.dev_tab_actionbar, null);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);

        actionBar.setCustomView(view, new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT));

        android.support.v7.app.ActionBar.LayoutParams mP = (android.support.v7.app.ActionBar.LayoutParams) view.getLayoutParams();
        mP.gravity = mP.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;

        actionBar.setCustomView(view, mP);
        mTabLayout = view.findViewById(R.id.tab_layout);
    }

    private void setTabFragMent() {
        fragments.clear();
        /**测试数据****/

        DetailFragment baseFragment = new DetailFragment();
        baseFragment.setTitle("测试1");

        DetailFragment baseFragment2 = new DetailFragment();
        baseFragment2.setTitle("测试2");

        DetailFragment baseFragment3 = new DetailFragment();
        baseFragment3.setTitle("测试3");

        fragments.add(baseFragment);
        fragments.add(baseFragment2);
        fragments.add(baseFragment3);
        /******/
        mTabAdapter = new BaseTabAdapter(getSupportFragmentManager());
        mViewPager.removeAllViews();

        if(fragments != null && fragments.size() >0){
            for (BaseFragment fragment : fragments) {
                mTabAdapter.addFragment(fragment);
            }
            if (fragments.size() > 1) {
                mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
            } else {
                mTabLayout.setSelected(false);
                mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
            }
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mTabAdapter);
    }
}
