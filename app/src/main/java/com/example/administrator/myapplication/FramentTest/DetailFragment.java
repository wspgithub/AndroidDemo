package com.example.administrator.myapplication.FramentTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2018/3/21.
 */

public class DetailFragment extends BaseFragment {


    public static DetailFragment newInstance() {
        DetailFragment newFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        newFragment.setArguments(bundle);
        return newFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_detail, container, false);
        return view;
    }
}
