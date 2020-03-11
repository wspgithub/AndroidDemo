package com.example.administrator.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * MapBaseLayer
 *
 * @author: onlylemi
 */
public abstract class MapBaseLayer extends RelativeLayout {

    // map layer level
    protected static final int MAP_LEVEL = 0;
    // location layer level
    protected static final int LOCATION_LEVEL = Integer.MAX_VALUE;

    // layer show level
    public int level;
    // layer is/not show
    public boolean isVisible = true;

    protected MapView mapView;
    public PointF startTouch = new PointF();
    public MapBaseLayer(Context context, MapView mapView) {
        super(context);
        this.mapView = mapView;
    }

    public MapBaseLayer(Context context, AttributeSet attrs, MapView mapView) {
        super(context, attrs);
        this.mapView = mapView;
    }

    public MapBaseLayer(Context context, AttributeSet attrs, int defStyleAttr, MapView mapView) {
        super(context, attrs, defStyleAttr);
        this.mapView = mapView;
    }


    //    public MapBaseLayer(MapView mapView) {
//        this.mapView = mapView;
//    }

    /**
     * touch event
     *
     * @param event
     */
    public abstract boolean onTouch(MotionEvent event);

    /**
     * draw event
     *
     * @param canvas
     * @param currentMatrix
     * @param currentZoom
     * @param currentRotateDegrees
     */
    public abstract void draw(Canvas canvas, Matrix currentMatrix, float currentZoom,
                              float currentRotateDegrees);

    public void setLevel(int level) {
        this.level = level;
    }

    protected float setValue(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, mapView.getResources()
                .getDisplayMetrics());
    }

    public void setStartTouch(PointF startTouch) {
        this.startTouch = startTouch;
    }
}
