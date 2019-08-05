package com.example.administrator.myapplication.DataBindingTest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;
//import com.example.administrator.myapplication.databinding.DataBindingUserBinding;

/**
 * Created by Administrator on 2018/5/7.
 */
@ShowActivity()
public class DataBingTestActivity extends AppCompatActivity {

//    private DataBindingUserBinding dataBindingUserBinding;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //DataBindingUtil.set
//        dataBindingUserBinding = DataBindingUtil.setContentView(this,R.layout.data_binding_user);
//        init();
//    }
//
//    private void init(){
//         User user = new User("下","上");
//        dataBindingUserBinding.setUser(user);
//    }
}
