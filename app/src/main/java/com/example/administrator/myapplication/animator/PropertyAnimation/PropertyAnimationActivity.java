package com.example.administrator.myapplication.animator.PropertyAnimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;
@ShowActivity
public class PropertyAnimationActivity extends AppCompatActivity {


    private Button button;
    private Button button2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_button_layout);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startObjectAnimatorAnim(button2);
                startValueAnimatorAnim(button2);
            }
        });
        button2 = findViewById(R.id.buttonTwo);


    }

    /**
     * ObjectAnimator基本使用继承子ValueAnimator
     * 对对象v的alpha参数进行操作，alpha的值从1.0变到0.3
     *
     * @param v
     */
    public void startObjectAnimatorAnim(View v) {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0.3f);
        //执行事件
        alphaAnim.setDuration(1000);
        //延迟
        alphaAnim.setStartDelay(300);
        alphaAnim.start();
    }

    /**
     * 在一段时间内生成连续的值完成view的缩放
     * @param v
     */
    public void startValueAnimatorAnim(final View v) {
        //不改变属性大小，只在一段事件内生成连续的值
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 100f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //百分比对应的值
                float value = (float) animation.getAnimatedValue();
                Log.e("TAG", "onAnimationUpdate: " + value);
                v.setScaleX(0.5f + value / 200);
                v.setScaleY(0.5f + value / 200);
            }
        });
        animator.start();
    }
}
