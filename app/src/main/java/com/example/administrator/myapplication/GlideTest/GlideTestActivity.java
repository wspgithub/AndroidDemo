package com.example.administrator.myapplication.GlideTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

@ShowActivity
public class GlideTestActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_test_activity);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);//http://www.iloveturong.com:8080/forever/img/pig.jpg
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(GlideTestActivity.this)
                        .load("http://www.iloveturong.com:8080/forever/img/pig.jpg")
                        .into(imageView);
            }
        });
    }
}
