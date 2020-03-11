package com.example.administrator.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * BitmapLayer
 *
 * @author: onlylemi
 */
abstract public class MarkPointLayer extends MapBaseLayer {

    public View view;
    /***/
    public String LayoutID = "-1";

    /**原始位置*/
    public PointF location;
    /**当前位置*/
    public PointF CurrentLocation = new PointF();
    /***/
    public Paint paint;
    /***/
    public float width;
    /***/
    public float height;
    /**是否跟随地图进行放大**/
    public boolean autoScale = true;
    /**是否可以进行移动*/
    public boolean canMove = true;

    private OnBitmapClickListener onBitmapClickListener;

    public MarkPointLayer(Context context, MapView mapView, PointF location) {
        super(context, mapView);
        init();
        location.x = location.x-(width/2);
        location.y = location.y-(height/2);
        this.location = location;
        CurrentLocation.set(this.location.x,this.location.y);
    }

    public MarkPointLayer(Context context, MapView mapView, Bitmap bitmap, PointF location) {
        super(context, mapView);
        init();
        location.x = location.x-width/2;
        location.y = location.y-height/2;
        this.location = location;
        CurrentLocation.set(this.location.x,this.location.y);
        paint = new Paint();
    }

    public MarkPointLayer(Context context, MapView mapView) {
        super(context, mapView);
        init();
    }

    public MarkPointLayer(Context context, AttributeSet attrs, MapView mapView) {
        super(context, attrs, mapView);
        init();
    }

    public MarkPointLayer(Context context, AttributeSet attrs, int defStyleAttr, MapView mapView) {
        super(context, attrs, defStyleAttr, mapView);
        init();
    }


//    public BitmapLayer(MapView mapView, Bitmap bitmap) {
//        this(mapView, bitmap, null);
//    }
//
//    public BitmapLayer(MapView mapView, Bitmap bitmap, PointF location) {
//        super(mapView);
//        this.location = location;
//        this.bitmap = bitmap;
//
//        paint = new Paint();
//    }

    private void init(){

        view = LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        view.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        // measure(getWidth(), getHeight());
        width = view.getMeasuredWidth();
        height = view.getMeasuredHeight();
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    abstract public int getLayoutId();

    public void refreshContent(){
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());

        width = view.getMeasuredWidth();
        height = view.getMeasuredHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        boolean isConsumption = false;
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                float[] goalXY = mapView.convertMapXYToScreenXY(event.getX(), event.getY());
                if (goalXY[0] > CurrentLocation.x &&
                        goalXY[0] < CurrentLocation.x + width &&
                        goalXY[1] > CurrentLocation.y  &&
                        goalXY[1] < CurrentLocation.y + height ) {

                    isConsumption = true;
                }
                break;


            case MotionEvent.ACTION_MOVE:
                if(canMove) {
                    float scrolX = event.getX() - startTouch.x;
                    float scrolY = event.getY() - startTouch.y;
                    float endX = location.x + scrolX / mapView.getCurrentZoom();
                    float endY = location.y + scrolY / mapView.getCurrentZoom();
                    if(endY<0)
                        endY = 0;
                    if(endX<0)
                        endX = 0;
                    if((endY+height)>(mapView.getMapLayer().getImage().getHeight()))
                        endY = mapView.getMapLayer().getImage().getHeight()-height;

                    if((endX+width)>(mapView.getMapLayer().getImage().getWidth()))
                        endX = mapView.getMapLayer().getImage().getWidth()-width;

                    CurrentLocation.set(endX, endY);
                    mapView.invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                location.set(CurrentLocation.x,CurrentLocation.y);
                if (onBitmapClickListener != null) {
                    float[] goal = mapView.convertMapXYToScreenXY(event.getX(), event.getY());
                    Log.i("BitmapLayer", "goal: " + goal[0] + ", " + goal[1]);
                    if (goal[0] > location.x &&
                            goal[0] < location.x + width &&
                            goal[1] > location.y  &&
                            goal[1] < location.y + height ) {
                        onBitmapClickListener.onBitmapClick(this);
                    }
                }
                break;
        }

        return isConsumption;
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, float
            currentRotateDegrees) {

        if (isVisible) {

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
        }
    }

    /**根据放大比例重新计算坐标*/
    public void CalculationLocation(float scale){
        location.set(location.x*scale,location.x*scale);
        CurrentLocation.set(CurrentLocation.x*scale,CurrentLocation.x*scale);
    }

    public PointF getLocation() {
        return location;
    }

    public void setLocation(PointF location) {
        this.location = location;
    }


    public void setAutoScale(boolean autoScale) {
        this.autoScale = autoScale;
    }

    public boolean isAutoScale() {
        return autoScale;
    }

    public void setOnBitmapClickListener(OnBitmapClickListener onBitmapClickListener) {
        this.onBitmapClickListener = onBitmapClickListener;
    }

    public interface OnBitmapClickListener {
        void onBitmapClick(MarkPointLayer layer);
    }

    public PointF getCurrentLocation() {
        return CurrentLocation;
    }

    public String getLayoutID() {
        return LayoutID;
    }

    public void setLayoutID(String layoutID) {
        this.LayoutID = layoutID;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
}
