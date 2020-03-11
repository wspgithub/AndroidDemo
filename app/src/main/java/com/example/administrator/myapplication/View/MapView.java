package com.example.administrator.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

/**
 * MapView
 *
 * @author: wangsipeng
 */
public class MapView extends View {

    private static final String TAG = "MapView";
    private MapViewListener mapViewListener = null;
    private boolean isMapLoadFinish = false;
    /**地图移动点基点的集合*/
    private List<MapBaseLayer> layers;
    private MapLayer mapLayer;

    private Canvas canvas;

    private float proportionX;

    private float proportionY;


    private float minZoom = 1.0f;
    private float maxZoom = 3.0f;

    private PointF startTouch = new PointF();
    private PointF mid = new PointF();

    private Matrix saveMatrix = new Matrix();
    private Matrix currentMatrix = new Matrix();
    private float currentZoom = 1.0f;
    private float saveZoom = 0f;
    private float currentRotateDegrees = 0.0f;
    private float saveRotateDegrees = 0.0f;

    private static final int TOUCH_STATE_NO = 0; // no touch
    private static final int TOUCH_STATE_SCROLL = 1; // scroll(one point)
    private static final int TOUCH_STATE_SCALE = 2; // scale(two points)
    private static final int TOUCH_STATE_ROTATE = 3; // rotate(two points)
    private static final int TOUCH_STATE_TWO_POINTED = 4; // two points touch
    private int currentTouchState = MapView.TOUCH_STATE_NO; // default touch state


    /**屏幕可见范围就是整个MapView显示在屏幕的大小**/
    private int showScrX = 0;
    private int showScrY = 0;

    private float oldDist = 0, oldDegree = 0;
    private boolean isScaleAndRotateTogether = false;

    /**地图移动的最高点用于边界控制**/
    private float LeftTopX = 0;
    /**地图移动的最高点用于边界控制**/
    private float LeftTopY = 0;
    /**要处理点击事件的子layer*/
    private MapBaseLayer clickLayer = null;
    public MapView(Context context) {
        this(context, null);
        initMapView();
    }

    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initMapView();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMapView();
    }

    /**
     * init mapview
     */
    private void initMapView() {

        layers = new ArrayList<MapBaseLayer>() {
            @Override
            public boolean add(MapBaseLayer layer) {
                if (layers.size() != 0) {
                    if (layer.level >= this.get(this.size() - 1).level) {
                        super.add(layer);
                    } else {
                        for (int i = 0; i < layers.size(); i++) {
                            if (layer.level < this.get(i).level) {
                                super.add(i, layer);
                                break;
                            }
                        }
                    }
                } else {
                    super.add(layer);
                }
                return true;
            }
        };
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        refresh(canvas);
    }

    /**
     * reload mapview
     */
    public void refresh(Canvas canvas) {
            if (canvas != null) {
                canvas.drawColor(-1);
                if (isMapLoadFinish) {
                    for (MapBaseLayer layer : layers) {
                        if (layer.isVisible) {
                            layer.draw(canvas, currentMatrix, currentZoom, currentRotateDegrees);
                        }
                    }
                }
            }
    }

    public void clear(){
        isMapLoadFinish = false;
        layers.clear();
        saveMatrix.reset();
        currentMatrix.reset();
        currentZoom = 1.0f;
        saveZoom = 0f;
        currentRotateDegrees = 0.0f;
    }

    public void loadMap(Bitmap bitmap) {

       // initMapView();
        this.showScrX = getWidth();
        this.showScrY = getHeight();
      //  mapViewListener = null;
        //layers.clear();
        //移除基点和移动点保留地图
        ArrayList<MapBaseLayer> arrayList = new ArrayList<>();
        for (MapBaseLayer mapBaseLayer:layers){
            if(!(mapBaseLayer instanceof MapLayer)){
                arrayList.add(mapBaseLayer);
            }
        }

        layers.removeAll(arrayList);
        saveMatrix.reset();
        currentMatrix.reset();
        currentZoom = 1.0f;
        saveZoom = 0f;
        currentRotateDegrees = 0.0f;

        loadMap(MapUtils.getPictureFromBitmap(bitmap));
    }


    /**
     * load map image
     *
     * @param picture
     */
    public void loadMap(final Picture picture) {
        isMapLoadFinish = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (picture != null) {
                    mapLayer = new MapLayer(getContext(),MapView.this);
                    // add map image layer
                    layers.add(0,mapLayer);
                    mapLayer.setImage(picture);
                    if (mapViewListener != null) {
                        // load map success, and callback
                        mapViewListener.onMapLoadSuccess();
                    }
                    isMapLoadFinish = true;
                } else {
                    if (mapViewListener != null) {
                        mapViewListener.onMapLoadFail();
                    }
                    isMapLoadFinish = false;
                }
            }
        }).start();
    }

    public void SaveMapStatus(String mapID){
        MapStatusSave.MapStatus mapStatus = new MapStatusSave.MapStatus();

        ArrayList<MapBaseLayer> arrayList = new ArrayList<>();
        for (MapBaseLayer mapBaseLayer:layers){
            if(!(mapBaseLayer instanceof MapLayer)){
                arrayList.add(mapBaseLayer);
            }
        }

        mapLayer = null;
        layers.clear();
        layers.addAll(arrayList);

        mapStatus.setLayers(layers);

        mapStatus.setSaveMatrix(saveMatrix);
        mapStatus.setCurrentMatrix(currentMatrix);
        mapStatus.setStartTouch(startTouch);
        mapStatus.setMid(mid);
        mapStatus.setSaveZoom(saveZoom);
        mapStatus.setCurrentZoom(currentZoom);
        mapStatus.setCurrentRotateDegrees(currentRotateDegrees);
        mapStatus.setOldDist(oldDist);
        mapStatus.setLeftTopX(LeftTopX);
        mapStatus.setLeftTopY(LeftTopY);
        MapStatusSave.getHashMap().put(mapID,mapStatus);
    }

    public boolean RestoreMapStatus(String mapID, Bitmap bitmap){

        boolean is = true;
        this.showScrX = getWidth();
        this.showScrY = getHeight();

        MapStatusSave.MapStatus mapStatus = MapStatusSave.getHashMap().get(mapID);
        if(mapStatus != null) {

            layers.clear();
            layers.addAll(mapStatus.getLayers());
//            for (int i = 0; i < mapStatus.getLayers().size(); i++) {
//                if(mapStatus.getLayers().get(i) instanceof MapLayer){
//                    mapLayer = (MapLayer)mapStatus.getLayers().get(i);
//                }
//            }

          //  saveMatrix.set(mapStatus.getSaveMatrix());
            saveMatrix.reset();
           // currentMatrix.set(mapStatus.getCurrentMatrix());
            currentMatrix.reset();
            startTouch = mapStatus.getStartTouch();
            mid = mapStatus.getMid();
//            saveZoom = mapStatus.getSaveZoom();
//            currentZoom = mapStatus.getCurrentZoom();
//            currentRotateDegrees = mapStatus.getCurrentRotateDegrees();
            currentZoom = 1.0f;
            saveZoom = 0f;
            currentRotateDegrees = 0.0f;
            oldDist = mapStatus.getOldDist();
         //   LeftTopX = mapStatus.getLeftTopX();
         //   LeftTopY = mapStatus.getLeftTopY();

        }else{
            is = false;
            layers.clear();
            saveMatrix.reset();
            currentMatrix.reset();
            currentZoom = 1.0f;
            saveZoom = 0f;
            currentRotateDegrees = 0.0f;
        }
        loadMap(MapUtils.getPictureFromBitmap(bitmap));
        return is;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isMapLoadFinish) {
            return false;
        }


        float newDist;
        float newDegree;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.e("打印",event.getX()+"  "+event.getY());
                clickLayer = null;
                for (MapBaseLayer layer : layers) {
                    if(layer.onTouch(event) == true){
                        clickLayer = layer;
                        clickLayer.setStartTouch(new PointF(event.getX(), event.getY()));
                    }
                }
                saveMatrix.set(currentMatrix);
                startTouch.set(event.getX(), event.getY());
                currentTouchState = MapView.TOUCH_STATE_SCROLL;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    saveMatrix.set(currentMatrix);
                    saveZoom = currentZoom;
//                    saveRotateDegrees = currentRotateDegrees;
                    startTouch.set(event.getX(0), event.getY(0));
                    currentTouchState = MapView.TOUCH_STATE_TWO_POINTED;

                    mid = midPoint(event);
                    oldDist = distance(event, mid);
                    oldDegree = rotation(event, mid);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(clickLayer!=null){
                    clickLayer.onTouch(event);
                    return true;
                }
                if (withFloorPlan(event.getX(), event.getY())) {
//                    Log.i(TAG, event.getX() + " " + event.getY());
                    // layers on touch
                    for (MapBaseLayer layer : layers) {
                        layer.onTouch(event);
                    }
                }
                currentTouchState = MapView.TOUCH_STATE_NO;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if(clickLayer!=null){
                    clickLayer.onTouch(event);
                    return true;
                }
                currentTouchState = MapView.TOUCH_STATE_NO;
                break;
            case MotionEvent.ACTION_MOVE:
                switch (currentTouchState) {
                    case MapView.TOUCH_STATE_SCROLL:
                        if(clickLayer!=null){
                            clickLayer.onTouch(event);
                            return true;
                        }
                        float scrolX = event.getX() - startTouch.x;
                        float scrolY = event.getY() - startTouch.y;
                        currentMatrix.set(saveMatrix);
                        currentMatrix.postTranslate(scrolX, scrolY);
                        float[] values = new float[9];
                        currentMatrix.getValues(values);
                        MatrixBoundaryControl(values);
                        currentMatrix.setValues(values);
                        invalidate();
                        break;
                    case MapView.TOUCH_STATE_TWO_POINTED:
                        if (!isScaleAndRotateTogether) {
//                            float x = oldDist;
//                            float y = MapMath.getDistanceBetweenTwoPoints(event.getX(0),
//                                    event.getY(0), startTouch.x, startTouch.y);
//                            float z = distance(event, mid);
//                            float cos = (x * x + y * y - z * z) / (2 * x * y);
//                            float degree = (float) Math.toDegrees(Math.acos(cos));
//
//                            if (degree < 120 && degree > 45) {
//                                oldDegree = rotation(event, mid);
//                                currentTouchState = MapView.TOUCH_STATE_ROTATE;
//                            } else {
//                                oldDist = distance(event, mid);
                                currentTouchState = MapView.TOUCH_STATE_SCALE;
                            }
//                        } else
//
 //                          {
//                            currentMatrix.set(saveMatrix);
                            newDist = distance(event, mid);
                            //newDegree = rotation(event, mid);

 //                           float rotate = newDegree - oldDegree;
//                            float scale = newDist / oldDist;
//                            if (scale * saveZoom < minZoom) {
//                                scale = minZoom / saveZoom;
//                            } else if (scale * saveZoom > maxZoom) {
//                                scale = maxZoom / saveZoom;
//                            }
//                            currentZoom = scale * saveZoom;
//                            currentRotateDegrees = (newDegree - oldDegree + currentRotateDegrees)
//                                    % 360;

//                            currentMatrix.postScale(scale, scale, mid.x, mid.y);
                            //currentMatrix.postRotate(rotate, mid.x, mid.y);
//                            refresh();
  //                      }
                        break;
                    case MapView.TOUCH_STATE_SCALE:
                        currentMatrix.set(saveMatrix);
                        newDist = distance(event, mid);
                        float scale = newDist / oldDist;
                        if (scale * saveZoom < minZoom) {
                            scale = minZoom / saveZoom;
                        } else if (scale * saveZoom > maxZoom) {
                            scale = maxZoom / saveZoom;
                        }
                        currentZoom = scale * saveZoom;
                        currentMatrix.postScale(scale, scale, mid.x, mid.y);

//                        for (MapBaseLayer layer : layers) {
//                            if(layer instanceof MovePointLayer)
//                            {
//                                ((MovePointLayer)layer).CalculationLocation(scale);
//                            }
//                        }


                        float width = mapLayer.getImage().getWidth()*currentZoom- getWidth();
                        float height = mapLayer.getImage().getHeight()*currentZoom- getHeight()+300;
                        LeftTopX = -width;
                        LeftTopY = -height;

                        float[] scaleValues = new float[9];
                        currentMatrix.getValues(scaleValues);
                        MatrixBoundaryControl(scaleValues);
                        currentMatrix.setValues(scaleValues);


                        invalidate();
                        break;
                    case MapView.TOUCH_STATE_ROTATE:
//                        currentMatrix.set(saveMatrix);
//                        newDegree = rotation(event, mid);
//                        float rotate = newDegree - oldDegree;
//                        currentRotateDegrees = (rotate + saveRotateDegrees) % 360;
//                        currentRotateDegrees = currentRotateDegrees > 0 ? currentRotateDegrees :
//                                currentRotateDegrees + 360;
//                        currentMatrix.postRotate(rotate, mid.x, mid.y);
//                        refresh();
//                        Log.i(TAG, "rotate:" + currentRotateDegrees);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * set mapview listener
     *
     * @param mapViewListener
     */
    public void setMapViewListener(MapViewListener mapViewListener) {
        this.mapViewListener = mapViewListener;
    }

    /**
     * convert coordinate of map to coordinate of screen
     *
     * @param x
     * @param y
     * @return
     */
    public float[] convertMapXYToScreenXY(float x, float y) {
        Matrix invertMatrix = new Matrix();
        float[] value = {x, y};
        currentMatrix.invert(invertMatrix);
        invertMatrix.mapPoints(value);
        return value;
    }

    /**
     * map is/not load finish
     *
     * @return
     */
    public boolean isMapLoadFinish() {
        return isMapLoadFinish;
    }

    public void setMapLoadFinish(boolean mapLoadFinish) {
        isMapLoadFinish = mapLoadFinish;
    }

    /**
     * add layer
     *
     * @param layer
     */
    public void addLayer(MapBaseLayer layer) {
        if (layer != null) {
            layers.add(layer);
        }
    }

    /**
     * 获取所有点
     *
     * @return
     */
    public List<MapBaseLayer> getLayers() {
        return layers;
    }

    public void translate(float x, float y) {
        currentMatrix.postTranslate(x, y);
    }



    /**
     * set point to map center
     *
     * @param x
     * @param y
     */
    public void mapCenterWithPoint(float x, float y) {
        float[] goal = {x, y};
        currentMatrix.mapPoints(goal);

        float deltaX = getWidth() / 2 - goal[0];
        float deltaY = getHeight() / 2 - goal[1];
        currentMatrix.postTranslate(deltaX, deltaY);
    }

    public float getCurrentRotateDegrees() {
        return currentRotateDegrees;
    }

    /**
     * set rotate degrees
     *
     * @param degrees
     */
    public void setCurrentRotateDegrees(float degrees) {
        mapCenterWithPoint(getbgImageOriginalWidth() / 2, getbgImageOriginalHeight() / 2);
        setCurrentRotateDegrees(degrees, getWidth() / 2, getHeight() / 2);
    }

    /**
     * set rotate degrees
     *
     * @param degrees
     * @param x
     * @param y
     */
    public void setCurrentRotateDegrees(float degrees, float x, float y) {
        currentMatrix.postRotate(degrees - currentRotateDegrees, x, y);

        currentRotateDegrees = degrees % 360;
        currentRotateDegrees = currentRotateDegrees > 0 ? currentRotateDegrees :
                currentRotateDegrees + 360;
    }

    public float getCurrentZoom() {
        return currentZoom;
    }

    public boolean isScaleAndRotateTogether() {
        return isScaleAndRotateTogether;
    }

    /**
     * setting scale&rotate is/not together on touch
     *
     * @param scaleAndRotateTogether
     */
    public void setScaleAndRotateTogether(boolean scaleAndRotateTogether) {
        isScaleAndRotateTogether = scaleAndRotateTogether;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public void setMinZoom(float minZoom) {
        this.minZoom = minZoom;
    }

    public void setCurrentZoom(float zoom) {
        setCurrentZoom(zoom, getWidth() / 2, getHeight() / 2);
    }

    public void setCurrentZoom(float zoom, float x, float y) {
        currentMatrix.postScale(zoom / this.currentZoom, zoom / this.currentZoom, x, y);
        this.currentZoom = zoom;
    }

    private PointF midPoint(MotionEvent event) {
        return MapMath.getMidPointBetweenTwoPoints(event.getX(0), event.getY(0)
                , event.getX(1), event.getY(1));
    }

    private float distance(MotionEvent event, PointF mid) {
        return MapMath.getDistanceBetweenTwoPoints(event.getX(0), event.getY(0)
                , mid.x, mid.y);
    }

    private float rotation(MotionEvent event, PointF mid) {
        return MapMath.getDegreeBetweenTwoPoints(event.getX(0), event.getY(0)
                , mid.x, mid.y);
    }

    /**
     * point is/not in floor plan
     *
     * @param x
     * @param y
     * @return
     */
    public boolean withFloorPlan(float x, float y) {
        float[] goal = convertMapXYToScreenXY(x, y);
        return goal[0] > 0 && goal[0] < mapLayer.getImage().getWidth() && goal[1] > 0
                && goal[1] < mapLayer.getImage().getHeight();
    }

    public float getbgImageOriginalWidth() {
        return mapLayer.getImage().getWidth();
    }

    public float getbgImageOriginalHeight() {
        return mapLayer.getImage().getHeight();
    }



    public void setShowScrX(int showScrX) {
        this.showScrX = showScrX;
    }

    public void setShowScrY(int showScrY) {
        this.showScrY = showScrY;
    }

    /**地图边界控制*/
    private void MatrixBoundaryControl(float[] values)
    {
        //图片不能再往上移动
        if(values[Matrix.MTRANS_Y] < LeftTopY)
        {
            values[Matrix.MTRANS_Y] = LeftTopY;
        }

        //图片不能再往下移动
        if(values[Matrix.MTRANS_Y] > 0)
        {
            values[Matrix.MTRANS_Y] = 0;
        }

        //图片不能再往左移动
        if(values[Matrix.MTRANS_X] < LeftTopX)
        {
            values[Matrix.MTRANS_X] = LeftTopX;
        }

        //图片不能再往右移动
        if(values[Matrix.MTRANS_X] > 0)
        {
            values[Matrix.MTRANS_X] = 0;
        }
    }

    /**计算边界点*/
    private void CalculationBoundaryPoint(){
        if(mapLayer == null)
            return;
        float width = mapLayer.getImage().getWidth()- getWidth();
        float height = mapLayer.getImage().getHeight()- getHeight();//不知道为什么会有300的偏差值
        LeftTopX = -width;
        LeftTopY = -height;
    }

    /**地图移动到中心点-仅在初始化的时候调用*/
    public void initLocationInfo()
    {
        CalculationBoundaryPoint();
        //mapLayer.getImage().getWidth()/2- getWidth()/2
        currentMatrix.reset();
        translate(LeftTopX/2,LeftTopY/2);
    }

    public void goToCenter()
    {
        CalculationBoundaryPoint();
        //mapLayer.getImage().getWidth()/2- getWidth()/2
        currentMatrix.reset();
        translate(LeftTopX/2,LeftTopY/2);
        invalidate();
    }

    /**移动到一个坐标**/
    public void moveToPoint(String id){

        boolean isFind = false;
        float pointX = 0;
        float pointY = 0;
        float[] goal = {0, 0};
        for (MapBaseLayer layer : layers) {
            if(layer instanceof MarkPointLayer)
            {
                if(id.equals(((MarkPointLayer)layer).getLayoutID())) {
                    pointX = ((MarkPointLayer) layer).getCurrentLocation().x;
                    pointY = ((MarkPointLayer) layer).getCurrentLocation().y;
                    goal[0] = pointX;
                    goal[1] = pointY;
                    currentMatrix.mapPoints(goal);
                    isFind = true;
                    break;
                }
            }
        }

        if(isFind) {
            float[] values = new float[9];
            currentMatrix.getValues(values);
            values[Matrix.MTRANS_X] = -(pointX*currentZoom - showScrX / 2);
            values[Matrix.MTRANS_Y] = -(pointY*currentZoom - showScrY / 2);

            MatrixBoundaryControl(values);

            currentMatrix.setValues(values);
            invalidate();
        }
    }

    public float getLeftTopX() {
        return LeftTopX;
    }

    public float getLeftTopY() {
        return LeftTopY;
    }

    public MapLayer getMapLayer() {
        return mapLayer;
    }

    public float getProportionX() {
        return proportionX;
    }

    public void setProportionX(float proportionX) {
        this.proportionX = proportionX;
    }

    public float getProportionY() {
        return proportionY;
    }

    public void setProportionY(float proportionY) {
        this.proportionY = proportionY;
    }

    //拖动的时候不与ViewGroup的滑动冲突
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }



}
