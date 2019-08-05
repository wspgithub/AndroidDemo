package com.example.administrator.myapplication.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by wsp on 2019/3/11.
 */
@ShowActivity
public class AnimActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = findViewById(R.id.button);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.testanim);
        button.startAnimation(animation);
    }
}
