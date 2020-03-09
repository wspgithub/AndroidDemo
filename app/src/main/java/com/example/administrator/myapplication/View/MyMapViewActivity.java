package com.example.administrator.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.myapplication.Annotation.ShowActivity;
import com.example.administrator.myapplication.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
@ShowActivity
public class MyMapViewActivity extends AppCompatActivity {
    /**
     * 显示地图的View
     */
    private MapView mapView;
    private Handler handler = null;
    private int zoom = 2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_map_view);
        mapView = findViewById(R.id.mapview);
        handler = new Handler();
        mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //getMapListResult = App.api.LocationGetArea(appID);
                mapView.setMapViewListener(new MapViewListener() {
                    @Override
                    public void onMapLoadSuccess() {
                        Log.i(TAG, "onMapLoadSuccess");
                        handler.post(runnableUi);
                    }

                    @Override
                    public void onMapLoadFail() {
                        Log.i(TAG, "onMapLoadFail");
                    }
                });



            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBitmap("http://www.iloveturong.com:8080/forever/img/room.jpg");
            }
        });


    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
            glideLoadBitmapCallback.getBitmapCallback(bitmap);
        }
    };


    public void getBitmap(String uri) {
        if (uri.contains("null")) {
            glideLoadBitmapCallback.getBitmapCallback(null);
        } else {
            Glide.with( this )
                    .asBitmap()// could be an issue!
                    .load(uri)
                    .into(target);

        }
    }

    //地图加载返回结果
    private GlideLoadBitmapCallback glideLoadBitmapCallback = new GlideLoadBitmapCallback() {
        @Override
        public void getBitmapCallback(Bitmap bitmap) {
            if (bitmap != null) {
                    mapView.clear();
                    mapView.loadMap(bitMapCover(bitmap));
            }

        }
    };

            // 构建Runnable对象，在runnable中更新界面
            Runnable runnableUi = new Runnable() {
                @Override
                public void run() {
                        mapView.initLocationInfo();
                        mapView.invalidate();


                    MovePointLayout movePointLayer = new MovePointLayout(MyMapViewActivity.this, mapView, new PointF(200, 200));
                    movePointLayer.setLayoutID("0");
                    movePointLayer.setPointName("移动点");
                    movePointLayer.setCanMove(true);
                    mapView.addLayer(movePointLayer);
                    mapView.invalidate();

                }

            };

            //返回一个合适尺寸的bitmap;
        private Bitmap bitMapCover (Bitmap bitmap){

                Bitmap myBitmap = null;
                int needWidth;
                int needHeight;

                BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
                decodeOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;


        if (decodeOptions.outHeight < mapView.getHeight()) {
            needHeight = mapView.getHeight() * zoom;
        } else {
            needHeight = decodeOptions.outHeight * zoom;
        }

        if (decodeOptions.outWidth < mapView.getWidth()) {
            needWidth = mapView.getWidth() * zoom;
        } else {
            needWidth = decodeOptions.outWidth * zoom;
        }

                mapView.setProportionX((float) bitmap.getWidth() / mapView.getWidth());
                mapView.setProportionY((float) bitmap.getHeight() / mapView.getHeight());
                myBitmap = Bitmap.createScaledBitmap(bitmap, needWidth, needHeight, true);

                return myBitmap;
            }

}
