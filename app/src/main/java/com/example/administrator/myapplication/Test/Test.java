package com.example.administrator.myapplication.Test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

/**
 * Created by wsp on 2019/4/15.
 */
@ShowActivity
public class Test extends AppCompatActivity {

    private Button button;
    private TestAsyncTask testAsyncTask;
    private TestTwo testTwo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        testAsyncTask = new TestAsyncTask();
        testTwo = new TestTwo();
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAsyncTask.execute();
               // testTwo.execute();
            }
        });


    }

    private class TestTwo extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
                Log.e("TestAsyncTask","TestAsyncTask");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }
    }


    private class TestAsyncTask extends MyAsyncTask<Void,Void,String>{

        public TestAsyncTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
                Log.e("TestAsyncTask","TestAsyncTask");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
