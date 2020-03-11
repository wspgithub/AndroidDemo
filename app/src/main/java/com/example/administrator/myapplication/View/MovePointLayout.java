package com.example.administrator.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

/**
 * Created by wsp on 2019/1/15.
 */

public class MovePointLayout extends MarkPointLayer {

    private String pointName;
    private TextView name;

    public MovePointLayout(Context context, MapView mapView, PointF location) {
        super(context, mapView, location);
        init();
    }

    public MovePointLayout(Context context, MapView mapView, Bitmap bitmap, PointF location) {
        super(context, mapView, bitmap, location);
        init();
    }

    public MovePointLayout(Context context, MapView mapView) {
        super(context, mapView);
        init();
    }

    public MovePointLayout(Context context, AttributeSet attrs, MapView mapView) {
        super(context, attrs, mapView);
        init();
    }

    public MovePointLayout(Context context, AttributeSet attrs, int defStyleAttr, MapView mapView) {
        super(context, attrs, defStyleAttr, mapView);
        init();
    }

    private void init(){
        name = view.findViewById(R.id.name);
    }

    @Override
    public int getLayoutId() {
        return R.layout.move_point;
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, float
            currentRotateDegrees) {

        if (isVisible) {
//            Matrix matrix = new Matrix();
//            matrix.set(currentMatrix);
//            matrix.postScale(size,size);
            int saveId = canvas.save();
            float[] goal = {CurrentLocation.x, CurrentLocation.y};

            if (!autoScale) {
                //维持原大小
                currentMatrix.mapPoints(goal);
            } else {
                //自动放大 如果MapView继承的事surfaceView则需要使用canvas.setMatrix(currentMatrix);
                canvas.concat(currentMatrix);
            }
//            canvas.drawBitmap(bitmap, goal[0] - bitmap.getWidth() / 2,
//                    goal[1] - bitmap.getHeight() / 2, paint);
            canvas.translate(goal[0],goal[1]);
            draw(canvas);
            canvas.restoreToCount(saveId);
            //currentMatrix.set(matrix);
        }
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
        name.setText(this.pointName);
        refreshContent();
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}
