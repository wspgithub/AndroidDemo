package com.example.administrator.myapplication.FramentTest;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class BaseTabAdapter extends FragmentStatePagerAdapter {

    private final List<BaseFragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public BaseTabAdapter(FragmentManager manager) {
        super(manager);
        this.fragmentManager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (BaseFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    public BaseFragment mCurrentFragment;

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(BaseFragment fragment) {
        fragmentList.add(fragment);
    }

    public void deleteAllFragment() {
        fragmentList.clear();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getTitle();
    }

    public List<BaseFragment> getFragmentList() {
        return fragmentList;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }
}
